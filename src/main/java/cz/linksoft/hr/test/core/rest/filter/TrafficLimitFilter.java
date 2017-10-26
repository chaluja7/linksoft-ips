package cz.linksoft.hr.test.core.rest.filter;

import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.service.CityService;
import cz.linksoft.hr.test.core.service.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Will perform traffic limit check based on IP (v4) before proceeding each request to Controller.
 *
 * @author jakubchalupa
 * @since 26.10.2017
 */
@Component
public class TrafficLimitFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficLimitFilter.class);

    // Does not need to be concurrentHashMap or AtomicLong value because read/write takes place ONLY in synchronized methods
    private static final Map<Long, Long> CITY_TRAFFIC_MAP = new HashMap<>();
    private static final Map<Long, Long> REGION_TRAFFIC_MAP = new HashMap<>();
    private static final Map<Long, Long> COUNTRY_TRAFFIC_MAP = new HashMap<>();

    private static final long MAX_NUMBER_OF_REQUESTS_FROM_CITY_PER_HOUR = 2000;
    private static final long MAX_NUMBER_OF_REQUESTS_FROM_REGION_PER_HOUR = 20000;
    private static final long MAX_NUMBER_OF_REQUESTS_FROM_COUNTRY_PER_HOUR = 200000;

    private final CityService cityService;

    @Autowired
    public TrafficLimitFilter(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        LOGGER.info("Traffic limit filter initiated.");
    }

    /**
     * every hour will clear maps with number of accesses. So Traffic is limited in one hour window.
     */
    @Scheduled(fixedDelay = 3600000)
    private synchronized void clearMaps() {
        CITY_TRAFFIC_MAP.clear();
        REGION_TRAFFIC_MAP.clear();
        COUNTRY_TRAFFIC_MAP.clear();
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        // get IP address
        final String requestIpAddress = request.getRemoteAddr();
        final InetAddress inetAddress = InetAddress.getByName(requestIpAddress);
        try {
            final Long ipNumber = EntityConverter.mapInetAddressToIpNumber(inetAddress);
            // locate IP address to city, region and country
            final CityEntity cityEntity = cityService.guessCityForIpNumber(ipNumber);

            if (cityEntity != null) {
                // check number of requests from this city, region and country in last hour
                if (!isAccessEnabled(cityEntity)) {
                    LOGGER.warn("TOO MANY REQUESTS FOR IP " + requestIpAddress + " - INTERRUPTING");
                    ((HttpServletResponse) response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    return;
                }
            } else {
                // TODO should the request proceed?
                LOGGER.warn("Unknown city for ip " + requestIpAddress + " - proceeding request");
            }
        } catch (IllegalArgumentException e) {
            // TODO should the request proceed?
            LOGGER.warn("Unsupported IP - " + requestIpAddress + " - proceeding request");
        }

        // proceed request
        chain.doFilter(request, response);
    }

    /**
     * checks if max number of requests was not exceede, if not, number of request is incremented and true is returned. false otherwise
     *
     * synchronized on purpose - we need to chceck all limits (city, region, country) and only if they are not exceeded
     * we can proceed request and increment number of requests for given city, region and country
     *
     * @param cityEntity city
     * @return true, if and only if max number of requests per city, region and country was not exceeded
     */
    private synchronized boolean isAccessEnabled(CityEntity cityEntity) {
        if (!CITY_TRAFFIC_MAP.containsKey(cityEntity.getId())) {
            CITY_TRAFFIC_MAP.put(cityEntity.getId(), 0L);
        }

        if (!REGION_TRAFFIC_MAP.containsKey(cityEntity.getId())) {
            REGION_TRAFFIC_MAP.put(cityEntity.getRegion().getId(), 0L);
        }

        if (!COUNTRY_TRAFFIC_MAP.containsKey(cityEntity.getId())) {
            COUNTRY_TRAFFIC_MAP.put(cityEntity.getRegion().getCountry().getId(), 0L);
        }

        long requestsInThisHourFromCity = CITY_TRAFFIC_MAP.get(cityEntity.getId());
        long requestsInThisHourFromRegion = REGION_TRAFFIC_MAP.get(cityEntity.getRegion().getId());
        long requestsInThisHourFromCountry = COUNTRY_TRAFFIC_MAP.get(cityEntity.getRegion().getCountry().getId());

        if (requestsInThisHourFromCity < MAX_NUMBER_OF_REQUESTS_FROM_CITY_PER_HOUR
                && requestsInThisHourFromRegion < MAX_NUMBER_OF_REQUESTS_FROM_REGION_PER_HOUR
                && requestsInThisHourFromCountry < MAX_NUMBER_OF_REQUESTS_FROM_COUNTRY_PER_HOUR) {

            // all limits OK - increment
            CITY_TRAFFIC_MAP.put(cityEntity.getId(), requestsInThisHourFromCity + 1);
            REGION_TRAFFIC_MAP.put(cityEntity.getRegion().getId(), requestsInThisHourFromRegion + 1);
            COUNTRY_TRAFFIC_MAP.put(cityEntity.getRegion().getCountry().getId(), requestsInThisHourFromCountry + 1);

            return true;
        }

        // some limit exceeded
        return false;
    }

    @Override
    public void destroy() {
        LOGGER.warn("Traffic limit filter destroyed.");
    }
}
