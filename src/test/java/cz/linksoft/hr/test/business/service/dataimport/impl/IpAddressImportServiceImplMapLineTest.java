package cz.linksoft.hr.test.business.service.dataimport.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Will run one time for each record in testingData method.
 *
 * @author jakubchalupa
 * @since 26.10.17
 */
@RunWith(value = Parameterized.class)
public class IpAddressImportServiceImplMapLineTest {

    private final String line;
    private final Long ipFrom;
    private final Long ipTo;
    private final String countryCode;
    private final String countryName;
    private final String regionName;
    private final String cityName;
    private final Double cityLatitude;
    private final Double cityLongitude;

    public IpAddressImportServiceImplMapLineTest(String line, String ipFrom, String ipTo, String countryCode,
                                                 String countryName, String regionName, String cityName,
                                                 String cityLatitude, String cityLongitude) {
        this.line = line;
        this.ipFrom = Long.parseLong(ipFrom);
        this.ipTo = Long.parseLong(ipTo);
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.regionName = regionName;
        this.cityName = cityName;
        this.cityLatitude = Double.parseDouble(cityLatitude);
        this.cityLongitude = Double.parseDouble(cityLongitude);
    }

    @Test
    public void testMapLine() throws Exception {
        // line i line that should be parsed, other class properties are expected values to be in importRow after parsing
        IpAddressesImportServiceImpl.ImportRow importRow = IpAddressesImportServiceImpl.mapLine(line);

        Assert.assertNotNull(importRow);
        Assert.assertEquals(ipFrom, importRow.getIpFrom());
        Assert.assertEquals(ipTo, importRow.getIpTo());
        Assert.assertEquals(countryCode, importRow.getCountryCode());
        Assert.assertEquals(countryName, importRow.getCountryName());
        Assert.assertEquals(regionName, importRow.getRegionName());
        Assert.assertEquals(cityName, importRow.getCityName());
        Assert.assertEquals(cityLatitude, importRow.getCityLatitude());
        Assert.assertEquals(cityLongitude, importRow.getCityLongitude());
    }


    @Parameterized.Parameters(name = "Row number {index}, ipFrom {0}, ipTo {1}, countryCode {2}, countryName {3}, regionName {4}, cityName {5}, cityLatitude {6}, cityLongitude {7}")
    public static Collection<String[]> testingData() {
        // lets test number of variants to be parsed
        // first param in array is line to be parsed, other params are correct values of line
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"\"85367296\",\"85367551\",\"SK\",\"Slovakia\",\"Nitriansky kraj\",\"Zlate Moravce\",\"48.385530\",\"18.400630\"",
            "85367296", "85367551", "SK", "Slovakia", "Nitriansky kraj", "Zlate Moravce", "48.385530", "18.400630"});
        data.add(new String[]{"85367296,85367551,\"SK\",\"Slovakia\",\"Nitriansky kraj\",Zlate Moravce,48.385530,18.400630",
            "85367296", "85367551", "SK", "Slovakia", "Nitriansky kraj", "Zlate Moravce", "48.385530", "18.400630"});
        data.add(new String[]{"\"85367296\",\"85367551\",\"SK\",\"Slov\n\rakia\",\"Nitriansky\nkraj\",\"Zlate Moravce\",\"48.385530\",\"18.400630\"",
            "85367296", "85367551", "SK", "Slov\n\rakia", "Nitriansky\nkraj", "Zlate Moravce", "48.385530", "18.400630"});
        data.add(new String[]{"\"85367296\",\"85367551\",\"SK\",\"Slovakia\",\"Nitriansky,kraj\",\"Zlate Moravce\",\"48.385530\",\"18.400630\"",
            "85367296", "85367551", "SK", "Slovakia", "Nitriansky,kraj", "Zlate Moravce", "48.385530", "18.400630"});
        data.add(new String[]{"\"85367296\",\"85367551\",\"SK\",\"Slovakia\",\"Nitri\"ansky\",kraj\",\"Zlate Moravce\",\"48.385530\",\"18.400630\"",
            "85367296", "85367551", "SK", "Slovakia", "Nitri\"ansky\",kraj", "Zlate Moravce", "48.385530", "18.400630"});

        return data;
    }

}
