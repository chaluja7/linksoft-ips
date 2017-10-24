package cz.linksoft.hr.test.core.rest.converter;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.core.convert.converter.Converter;

/**
 * Converter to support {@link InetAddress} as a valid parameter in Spring MVC. Conversion is performed via
 * {@link InetAddress#getByName(String)}.
 */
public class InetAddressConverter implements Converter<String, InetAddress> {

	@Override
	public InetAddress convert(String source) {
		try {
			return InetAddress.getByName(source);
		} catch (UnknownHostException e) {
			throw new IllegalArgumentException("Input string is not represent a valid IP address: " + source, e);
		}
	}

}
