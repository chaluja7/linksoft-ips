package cz.linksoft.hr.test.core.service;

import cz.linksoft.hr.test.api.model.City;
import cz.linksoft.hr.test.api.model.IpAddressRange;
import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.entity.IpAddressRangeEntity;
import cz.linksoft.hr.test.business.entity.RegionEntity;
import cz.linksoft.hr.test.business.service.CityService;
import cz.linksoft.hr.test.business.service.IpAddressRangeService;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Collections;
import java.util.List;

/**
 * Besides check valid transformation of some entity objects to api object.
 * Checks valid transformation of decimal IP to hostName and conversely as well.
 *
 * @author jakubchalupa
 * @since 26.10.17
 */
public class IpAddressServiceImplTest extends EasyMockSupport {

    private IPAddressServiceImpl ipAddressService;
    private IpAddressRangeService ipAddressRangeServiceMock = createMock(IpAddressRangeService.class);
    private CityService cityServiceMock = createMock(CityService.class);

    @Before
    public void init() {
        ipAddressService = new IPAddressServiceImpl(ipAddressRangeServiceMock, cityServiceMock);
    }

    @Test
    public void testGetAllCityIPAddressRanges() throws Exception {
        final Long cityId = 5L;
        final Long rangeFrom = 85367296L;
        final Long rangeTo = 85367551L;
        final String rangeFromIp = "5.22.154.0";
        final String rangeToIp = "5.22.154.255";

        final IpAddressRangeEntity ipAddressRangeEntity = new IpAddressRangeEntity();
        ipAddressRangeEntity.setRangeFrom(rangeFrom);
        ipAddressRangeEntity.setRangeTo(rangeTo);

        EasyMock.expect(ipAddressRangeServiceMock.findByCityId(cityId)).andReturn(Collections.singletonList(ipAddressRangeEntity));

        replayAll();

        List<IpAddressRange> ipAddressRanges = ipAddressService.getAllCityIPAddressRanges(cityId);
        Assert.assertNotNull(ipAddressRanges);
        Assert.assertEquals(1, ipAddressRanges.size());

        // check valid tranformation of IP from decimal number to string
        IpAddressRange ipAddressRange = ipAddressRanges.get(0);
        Assert.assertEquals("Decimal IP to IP conversion failed", rangeFromIp, ipAddressRange.getFrom().getHostName());
        Assert.assertEquals("Decimal IP to IP conversion failed", rangeToIp, ipAddressRange.getTo().getHostName());

        verifyAll();
    }

    @Test
    public void testGuessCityForIpAddress() throws Exception {
        final String ip = "5.22.154.0";
        final InetAddress inetAddress = InetAddress.getByName(ip);
        final Long decimalIp = 85367296L;

        final Long cityId = 100L;
        final String cityName = "cityName";
        final Double cityLat = 50.0;
        final Double cityLon = 60.0;
        final Long regionId = 200L;

        final RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(regionId);

        final CityEntity cityEntity = new CityEntity();
        cityEntity.setId(cityId);
        cityEntity.setName(cityName);
        cityEntity.setLatitude(cityLat);
        cityEntity.setLongitude(cityLon);
        cityEntity.setRegion(regionEntity);

        // check valid tranformation of IP from string to decimal
        EasyMock.expect(cityServiceMock.guessCityForIpNumber(decimalIp)).andReturn(cityEntity);

        replayAll();

        City city = ipAddressService.guessCityForIPAddress(inetAddress);
        Assert.assertNotNull(city);
        Assert.assertEquals(cityId, city.getId());
        Assert.assertEquals(cityName, city.getName());
        Assert.assertEquals(cityLat, city.getGpsCoordinates().getLatitude());
        Assert.assertEquals(cityLon, city.getGpsCoordinates().getLongtitude());
        Assert.assertEquals(regionId, city.getRegionId());

        verifyAll();
    }

}
