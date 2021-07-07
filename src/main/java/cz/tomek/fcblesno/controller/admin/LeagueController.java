package cz.tomek.fcblesno.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.tomek.fcblesno.model.League;

/**
 * League controller.
 * 
 * @author tomek
 *
 */
@Controller
@RequestMapping("admin/{contextUrl:leagues}")
public class LeagueController extends AppEntityController<League> {

	public LeagueController() {
		super(League.class);
	}

}
