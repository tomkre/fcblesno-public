package cz.tomek.fcblesno.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.tomek.fcblesno.model.Player;

/**
 * Player controller.
 * 
 * @author tomek
 *
 */
@Controller
@RequestMapping("admin/{contextUrl:players}")
public class PlayerController extends AppEntityController<Player> {

	public PlayerController() {
		super(Player.class);
	}
	
}
