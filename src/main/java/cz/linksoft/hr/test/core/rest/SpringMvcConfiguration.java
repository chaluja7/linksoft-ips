package cz.linksoft.hr.test.core.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cz.linksoft.hr.test.core.rest.converter.InetAddressConverter;

@Configuration
@EnableWebMvc
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		super.addFormatters(registry);
		registry.addConverter(new InetAddressConverter());
	}

}
