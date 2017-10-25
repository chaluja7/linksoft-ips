package cz.linksoft.hr.test.core.service;

import cz.linksoft.hr.test.api.model.*;
import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.entity.CountryEntity;
import cz.linksoft.hr.test.business.entity.IpAddressRangeEntity;
import cz.linksoft.hr.test.business.entity.RegionEntity;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author jakubchalupa
 * @since 25.10.17
 */
public class EntityConverter {

    /**
     * maps country entity to country
     * @param countryEntity country entity
     * @return country
     */
    public static Country mapCountry(CountryEntity countryEntity) {
        if (countryEntity == null) {
            return null;
        }

        Country country = new Country();
        country.setId(countryEntity.getId());
        country.setName(countryEntity.getName());
        country.setCode(countryEntity.getCode());

        return country;
    }

    /**
     * maps region entity to region.
     * @param regionEntity region Entity
     * @return region
     */
    public static Region mapRegion(RegionEntity regionEntity) {
        if (regionEntity == null) {
            return null;
        }

        Region region = new Region();
        region.setId(regionEntity.getId());
        region.setName(regionEntity.getName());
        if (regionEntity.getCountry() != null) {
            region.setCountryId(regionEntity.getCountry().getId());
        }

        return region;
    }

    /**
     * maps city entity to city
     * @param cityEntity city entity
     * @return city
     */
    public static City mapCity(CityEntity cityEntity) {
        if (cityEntity == null) {
            return null;
        }

        City city = new City();
        city.setId(cityEntity.getId());
        city.setName(cityEntity.getName());
        city.setGpsCoordinates(new GpsCoordinates(cityEntity.getLatitude(), cityEntity.getLongitude()));

        if (cityEntity.getRegion() != null) {
            city.setRegionId(cityEntity.getRegion().getId());
        }

        return city;
    }

    /**
     * maps ipAddressRange entity to ipAddressRange
     * @param ipAddressRangeEntity ipAddressRange entity
     * @return ipAddressRange
     */
    public static IpAddressRange mapIpAddressRange(IpAddressRangeEntity ipAddressRangeEntity) {
        if (ipAddressRangeEntity == null) {
            return null;
        }

        IpAddressRange ipAddressRange = new IpAddressRange();
        ipAddressRange.setFrom(mapIpNumberToInetAddress(ipAddressRangeEntity.getRangeFrom()));
        ipAddressRange.setTo(mapIpNumberToInetAddress(ipAddressRangeEntity.getRangeTo()));

        return ipAddressRange;
    }

    /**
     * maps ipNumber to inetAddress, see https://www.mkyong.com/java/java-convert-ip-address-to-decimal-number/
     *
     * IP Address = w.x.y.z
     * IP Number = 256^3*w + 256^2*x + 256*y + z
     * w = int ( IP Number / 256^3 ) % 256
     * x = int ( IP Number / 256^2 ) % 256
     * y = int ( IP Number / 256   ) % 256
     * z = int ( IP Number         ) % 256
     *
     * @param ipNumber ipNumber
     * @return inetAddress
     */
    public static InetAddress mapIpNumberToInetAddress(Long ipNumber) {
        if (ipNumber == null) {
            return null;
        }

        final String ip = ((ipNumber >> 24) & 0xFF) + "." + ((ipNumber >> 16) & 0xFF) + "." + ((ipNumber >> 8) & 0xFF) + "." + (ipNumber & 0xFF);

        try {
            return InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Input string is not represent a valid IP address: " + ipNumber, e);
        }
    }

    /**
     * maps inetAddress to ipNumber, see https://www.mkyong.com/java/java-convert-ip-address-to-decimal-number/
     *
     * IP Address = w.x.y.z
     * IP Number = 256^3*w + 256^2*x + 256*y + z
     * w = int ( IP Number / 256^3 ) % 256
     * x = int ( IP Number / 256^2 ) % 256
     * y = int ( IP Number / 256   ) % 256
     * z = int ( IP Number         ) % 256
     *
     * @param inetAddress inetAddress
     * @return ipNumber
     */
    public static Long mapInetAddressToIpNumber(InetAddress inetAddress) {
        if (inetAddress == null || inetAddress.getHostAddress() == null) {
            return null;
        }

        String[] ipAddressInArray = inetAddress.getHostAddress().split("\\.");

        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);
        }

        return result;
    }



}
