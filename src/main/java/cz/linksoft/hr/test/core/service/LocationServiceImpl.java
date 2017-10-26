package cz.linksoft.hr.test.core.service;

import cz.linksoft.hr.test.api.model.City;
import cz.linksoft.hr.test.api.model.Country;
import cz.linksoft.hr.test.api.model.Region;
import cz.linksoft.hr.test.api.service.LocationService;
import cz.linksoft.hr.test.business.service.CityService;
import cz.linksoft.hr.test.business.service.CountryService;
import cz.linksoft.hr.test.business.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
public class LocationServiceImpl implements LocationService {

	private final CountryService countryService;
	private final RegionService regionService;
	private final CityService cityService;

	@Autowired
	public LocationServiceImpl(CountryService countryService, RegionService regionService, CityService cityService) {
		this.countryService = countryService;
		this.regionService = regionService;
		this.cityService = cityService;
	}

	@Nonnull
	@Override
	public List<Country> getAllCountries() {
		return countryService.getAll().stream().map(EntityConverter::mapCountry).collect(Collectors.toList());
	}

	@Nonnull
	@Override
	public List<Region> getAllCountryRegions(@Nonnull Long countryId) {
		return regionService.findByCountryId(countryId).stream().map(EntityConverter::mapRegion).collect(Collectors.toList());
	}

	@Nonnull
	@Override
	public List<City> getAllRegionCities(@Nonnull Long regionId) {
		return cityService.findByRegionId(regionId).stream().map(EntityConverter::mapCity).collect(Collectors.toList());
	}

	@Nonnull
	@Override
	public List<City> getAllCountryCitites(@Nonnull Long countryId) {
		return cityService.findByCountryId(countryId).stream().map(EntityConverter::mapCity).collect(Collectors.toList());
	}

}
