package cz.tomek.fcblesno.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.tomek.fcblesno.model.Team;

/**
 * Team controller.
 * 
 * @author tomek
 *
 */
@Controller
@RequestMapping("admin/{contextUrl:teams}")
public class TeamController extends AppEntityController<Team> {

	public TeamController() {
		super(Team.class);
	}

}
