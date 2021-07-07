package cz.tomek.fcblesno.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.tomek.fcblesno.model.Location;

/**
 * Location controller.
 * 
 * @author tomek
 *
 */
@Controller
@RequestMapping("admin/{contextUrl:locations}")
public class LocationController extends AppEntityController<Location> {

	public LocationController() {
		super(Location.class);
	}

}
