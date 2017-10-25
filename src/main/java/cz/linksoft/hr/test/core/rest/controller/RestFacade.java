package cz.linksoft.hr.test.core.rest.controller;

import cz.linksoft.hr.test.api.model.City;
import cz.linksoft.hr.test.api.model.Country;
import cz.linksoft.hr.test.api.model.IpAddressRange;
import cz.linksoft.hr.test.api.model.Region;
import cz.linksoft.hr.test.api.service.IPAddressService;
import cz.linksoft.hr.test.api.service.LocationService;
import cz.linksoft.hr.test.business.service.CityService;
import cz.linksoft.hr.test.business.service.CountryService;
import cz.linksoft.hr.test.business.service.RegionService;
import cz.linksoft.hr.test.core.rest.controller.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.InetAddress;
import java.util.List;

/**
 * RESTfull facade over {@link LocationService} and {@link IPAddressService}.
 *
 * Notes - if it was completely up to me, i'd rewrite this controller from scratch. Methods should return ResponseEntity<>
 * and so this Controller does not need to implements LocationService and IpAddressService.
 *
 * LocationService and IpAddressService are another candidates to refactor. I'd love to remove it completely, so controller
 * coud inject entityServices only. For purpose of this demo I'll leave it this way, because task is to implements these services
 * but I consider it a unnecessary layer. Mapping from DB entities to API entities could take place directly here on Controller.
 *
 * Now its weird, how ResourceNotFoundException are handled - if LocationService would not exists, than only one call to
 * enties services would be necessary.
 *
 */
@RestController
public class RestFacade extends AbstractController implements LocationService, IPAddressService {

	private final LocationService locationService;
	private final IPAddressService ipAddressService;
	private final CountryService countryService;
	private final RegionService regionService;
	private final CityService cityService;

	@Autowired
	public RestFacade(LocationService locationService, IPAddressService ipAddressService, CountryService countryService,
					  RegionService regionService, CityService cityService) {
		this.locationService = locationService;
		this.ipAddressService = ipAddressService;
		this.countryService = countryService;
		this.regionService = regionService;
		this.cityService = cityService;
	}

	//region LocationService

	@RequestMapping("country")
	@Override
	@Nonnull
	public List<Country> getAllCountries() {
		return locationService.getAllCountries();
	}

	@RequestMapping("country/{countryId}/region")
	@Override
	@Nonnull
	public List<Region> getAllCountryRegions(@PathVariable("countryId") @Nonnull Long countryId) {
		if (countryService.get(countryId) == null) {
			throw new ResourceNotFoundException();
		}

		return locationService.getAllCountryRegions(countryId);
	}

	@RequestMapping("region/{regionId}/city")
	@Override
	@Nonnull
	public List<City> getAllRegionCities(@PathVariable("regionId") @Nonnull Long regionId) {
		if (regionService.get(regionId) == null) {
			throw new ResourceNotFoundException();
		}

		return locationService.getAllRegionCities(regionId);
	}

	@RequestMapping("country/{countryId}/city")
	@Override
	@Nonnull
	public List<City> getAllCountryCitites(@PathVariable("countryId") @Nonnull Long countryId) {
		if (countryService.get(countryId) == null) {
			throw new ResourceNotFoundException();
		}

		return locationService.getAllCountryCitites(countryId);
	}

	//endregion


	//region IPAddressService

	@RequestMapping("city/{cityId}/ipaddress")
	@Override
	@Nonnull
	public List<IpAddressRange> getAllCityIPAddressRanges(@PathVariable("cityId") @Nonnull Long cityId) {
		if (cityService.get(cityId) == null) {
			throw new ResourceNotFoundException();
		}

		return ipAddressService.getAllCityIPAddressRanges(cityId);
	}

	@RequestMapping("ipaddress/guess")
	@Override
	@Nullable
	public City guessCityForIPAddress(@RequestParam("ip") @Nonnull InetAddress ipAddress) {
		return ipAddressService.guessCityForIPAddress(ipAddress);
	}

	//endregion
}
