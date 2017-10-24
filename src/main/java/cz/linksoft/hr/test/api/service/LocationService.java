package cz.linksoft.hr.test.api.service;

import java.util.List;

import javax.annotation.Nonnull;

import cz.linksoft.hr.test.api.model.City;
import cz.linksoft.hr.test.api.model.Country;
import cz.linksoft.hr.test.api.model.Region;

/**
 * Service providing various location-related data.
 */
public interface LocationService {

	/**
	 * @return All stored countries.
	 */
	@Nonnull
	List<Country> getAllCountries();

	/**
	 * @param countryId ID of country relates to {@link Country#getId()}.
	 * @return All regions of the country.
	 */
	@Nonnull
	List<Region> getAllCountryRegions(@Nonnull Long countryId);

	/**
	 * @param regionId ID of region relates to {@link Region#getId()}.
	 * @return All cities of the region.
	 */
	@Nonnull
	List<City> getAllRegionCities(@Nonnull Long regionId);

	/**
	 * @param countryId ID of country relates to {@link Country#getId()}.
	 * @return All cities of the country.
	 */
	@Nonnull
	List<City> getAllCountryCitites(@Nonnull Long countryId);

}
