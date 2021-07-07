package cz.tomek.fcblesno.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Admin controller.
 * 
 * @author tomek
 *
 */
@Controller
@RequestMapping("admin")
public class AdminController {
	
	private static final String LOGIN_VIEW = "admin/login";

	@GetMapping("login")
	public String login(Model model) {
		return LOGIN_VIEW;
	}
	
}
