package cz.linksoft.hr.test.business;

import cz.linksoft.hr.test.business.service.dataimport.IpAddressesImportService;
import cz.linksoft.hr.test.core.ApplicationDataResourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Run after application is ready, as in https://stackoverflow.com/questions/27405713/running-code-after-spring-boot-starts
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartup.class);

    private final IpAddressesImportService ipAddressesImportService;
    private final ApplicationDataResourceProvider applicationDataResourceProvider;

    @Autowired
    public ApplicationStartup(IpAddressesImportService ipAddressesImportService,
                              ApplicationDataResourceProvider applicationDataResourceProvider) {
        this.ipAddressesImportService = ipAddressesImportService;
        this.applicationDataResourceProvider = applicationDataResourceProvider;
    }

    /**
     * Import all CSV files from resources.
     *
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        try {
            for (Resource resource : applicationDataResourceProvider.getDataFileResources()) {
                ipAddressesImportService.importAllIpAddresses(resource);
            }
        } catch (Exception e) {
            // on purpose NO other resource will be imported, this can be customized of course
            LOGGER.error("IP addresses import failed", e);
        }
    }
}
