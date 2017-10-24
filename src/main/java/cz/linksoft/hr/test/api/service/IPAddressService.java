package cz.linksoft.hr.test.api.service;

import java.net.InetAddress;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cz.linksoft.hr.test.api.model.City;
import cz.linksoft.hr.test.api.model.IpAddressRange;

/**
 * Service for retrieving IP address related data.
 *
 * @see IpAddressRange
 * @see java.net.InetAddress
 */
public interface IPAddressService {

	/**
	 * @param cityId ID of city relates to {@link City#getId()}.
	 * @return List of IP address ranges currently associated to the city.
	 */
	@Nonnull
	List<IpAddressRange> getAllCityIPAddressRanges(@Nonnull Long cityId);

	/**
	 * Guess city in which is given IP address located.
	 *
	 * @param ipAddress IP address to locate.
	 * @return City in which is given IP address probably located.
	 */
	@Nullable
	City guessCityForIPAddress(@Nonnull InetAddress ipAddress);

}
