package cz.tomek.fcblesno.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Security configuration.
 * 
 * @author tomek
 *
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			.and()
			.	authorizeRequests()
				.antMatchers("/admin/**").authenticated()
			.and()
				.formLogin()
				.loginPage("/admin/login").permitAll()
				.defaultSuccessUrl("/admin/articles")
				.failureUrl("/admin/login?error=true")
			.and()
				.logout();
	}
	
}
