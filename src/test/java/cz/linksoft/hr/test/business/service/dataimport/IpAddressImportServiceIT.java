package cz.linksoft.hr.test.business.service.dataimport;

import cz.linksoft.hr.test.IntegrationTest;
import cz.linksoft.hr.test.business.service.CityService;
import cz.linksoft.hr.test.business.service.CountryService;
import cz.linksoft.hr.test.business.service.IpAddressRangeService;
import cz.linksoft.hr.test.business.service.RegionService;
import cz.linksoft.hr.test.business.service.dataimport.exception.ImportRowFailedException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author cen83414
 * @since 26.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class IpAddressImportServiceIT {

    private static final int OK_IMPORT_NUMBER_OF_COUNTRIES = 1;
    private static final int OK_IMPORT_NUMBER_OF_REGIONS = 7;
    private static final int OK_IMPORT_NUMBER_OF_CITIES = 13;
    private static final int OK_IMPORT_NUMBER_OF_ROWS = 20;

    @Autowired
    private IpAddressesImportService ipAddressesImportService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CountryService countryService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private CityService cityService;

    @Autowired
    private IpAddressRangeService ipAddressRangeService;

    @Test
    public void testImportOK() throws Exception {
        Assert.assertEquals(0, countryService.getAll().size());
        Assert.assertEquals(0, regionService.getAll().size());
        Assert.assertEquals(0, cityService.getAll().size());
        Assert.assertEquals(0, ipAddressRangeService.getAll().size());

        ipAddressesImportService.importAllIpAddresses(resourceLoader.getResource("classpath:test-data/ip2location-test-ok.csv.gz"));

        Assert.assertEquals(OK_IMPORT_NUMBER_OF_COUNTRIES, countryService.getAll().size());
        Assert.assertEquals(OK_IMPORT_NUMBER_OF_REGIONS, regionService.getAll().size());
        Assert.assertEquals(OK_IMPORT_NUMBER_OF_CITIES, cityService.getAll().size());
        Assert.assertEquals(OK_IMPORT_NUMBER_OF_ROWS, ipAddressRangeService.getAll().size());
    }

    @Test(expected = ImportRowFailedException.class)
    public void testImportInvalidCsv() throws Exception {
        ipAddressesImportService.importAllIpAddresses(resourceLoader.getResource("classpath:test-data/ip2location-test-corrupted.csv.gz"));
    }

}
