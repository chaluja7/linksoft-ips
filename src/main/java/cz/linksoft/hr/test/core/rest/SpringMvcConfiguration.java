package cz.linksoft.hr.test.core.rest;

import cz.linksoft.hr.test.core.rest.converter.InetAddressConverter;
import cz.linksoft.hr.test.core.rest.filter.TrafficLimitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		super.addFormatters(registry);
		registry.addConverter(new InetAddressConverter());
	}

	/**
	 * Will check traffic limit to app.
	 *
	 * @param trafficLimitFilter trafficLimitFilter
	 * @return filterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean appTrafficFilterBean(TrafficLimitFilter trafficLimitFilter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(trafficLimitFilter);
		registration.addUrlPatterns("/*");
		registration.setName("Traffic limit filter");
		registration.setOrder(1);
		return registration;
	}

}
