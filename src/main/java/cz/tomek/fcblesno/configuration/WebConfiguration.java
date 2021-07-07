package cz.tomek.fcblesno.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * App configuration.
 * 
 * @author tomek
 *
 */
@Configuration
public class WebConfiguration {

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
	
}
