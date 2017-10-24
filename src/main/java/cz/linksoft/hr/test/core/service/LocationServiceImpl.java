package cz.linksoft.hr.test.core.service;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cz.linksoft.hr.test.api.model.City;
import cz.linksoft.hr.test.api.model.Country;
import cz.linksoft.hr.test.api.model.Region;
import cz.linksoft.hr.test.api.service.LocationService;

@Primary
@Service
public class LocationServiceImpl implements LocationService {

	@Nonnull
	@Override
	public List<Country> getAllCountries() {
		//TODO implement me!
		return null;
	}

	@Nonnull
	@Override
	public List<Region> getAllCountryRegions(@Nonnull Long countryId) {
		//TODO implement me!
		return null;
	}

	@Nonnull
	@Override
	public List<City> getAllRegionCities(@Nonnull Long regionId) {
		//TODO implement me!
		return null;
	}

	@Nonnull
	@Override
	public List<City> getAllCountryCitites(@Nonnull Long countryId) {
		//TODO implement me!
		return null;
	}

}
