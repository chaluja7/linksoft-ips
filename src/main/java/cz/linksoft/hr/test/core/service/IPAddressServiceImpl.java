package cz.linksoft.hr.test.core.service;

import java.net.InetAddress;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cz.linksoft.hr.test.api.model.City;
import cz.linksoft.hr.test.api.model.IpAddressRange;
import cz.linksoft.hr.test.api.service.IPAddressService;

@Primary
@Service
public class IPAddressServiceImpl implements IPAddressService {

	@Nonnull
	@Override
	public List<IpAddressRange> getAllCityIPAddressRanges(@Nonnull Long cityId) {
		//TODO implement me!
		return null;
	}

	@Nullable
	@Override
	public City guessCityForIPAddress(@Nonnull InetAddress ipAddress) {
		//TODO implement me!
		return null;
	}

}
