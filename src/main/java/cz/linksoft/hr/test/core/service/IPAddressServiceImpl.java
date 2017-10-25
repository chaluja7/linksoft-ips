package cz.linksoft.hr.test.core.service;

import cz.linksoft.hr.test.api.model.City;
import cz.linksoft.hr.test.api.model.IpAddressRange;
import cz.linksoft.hr.test.api.service.IPAddressService;
import cz.linksoft.hr.test.business.service.CityService;
import cz.linksoft.hr.test.business.service.IpAddressRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
public class IPAddressServiceImpl implements IPAddressService {

	@Autowired
	protected IpAddressRangeService ipAddressRangeService;

	@Autowired
	private CityService cityService;

	@Nonnull
	@Override
	public List<IpAddressRange> getAllCityIPAddressRanges(@Nonnull Long cityId) {
		return ipAddressRangeService.findByCityId(cityId).stream().map(EntityConverter::mapIpAddressRange).collect(Collectors.toList());
	}

	@Nullable
	@Override
	public City guessCityForIPAddress(@Nonnull InetAddress ipAddress) {
		return EntityConverter.mapCity(cityService.guessCityForIpNumber(EntityConverter.mapInetAddressToIpNumber(ipAddress)));
	}

}
