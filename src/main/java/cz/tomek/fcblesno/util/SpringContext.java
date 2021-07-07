package cz.tomek.fcblesno.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Provides access to spring context for POJO.
 * 
 * @author tomek
 *
 */
@Component
public class SpringContext {

	private static ApplicationContext ctx;
	
	public static <T> T getBean(Class<T> clazz) {
		return ctx.getBean(clazz);
	}
	
	@Autowired
	public void setApplicationContext(ApplicationContext ctx) {
		SpringContext.ctx = ctx;
	}
	
}
