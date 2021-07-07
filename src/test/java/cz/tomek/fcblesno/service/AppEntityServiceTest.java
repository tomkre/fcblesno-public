package cz.tomek.fcblesno.service;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import cz.tomek.fcblesno.util.DateService;

/**
 * App entity service test.
 * 
 * @author tomek
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AppEntityServiceTest {
	
	@SpyBean
	protected DateService dateService;
	
	@Before
	public void setup() {
		Date thresholdDate = DateService.parse("2019-01-01 00:00");
		when(dateService.now()).thenReturn(thresholdDate);
	}

}
