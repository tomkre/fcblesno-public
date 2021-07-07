package cz.tomek.fcblesno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.tomek.fcblesno.model.enums.Post;
import cz.tomek.fcblesno.service.ArticleService;
import cz.tomek.fcblesno.service.PlayerService;

/**
 * Main menu controller.
 * 
 * @author tomek
 *
 */
@Controller
@RequestMapping("history")
public class HistoryMenuController {

	private static String HISTORY_VIEW = "display/history";
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private TeamMenuController teamMenuController;
	
	@GetMapping("")
	public String history(Model model) {
		model.addAttribute("article", articleService.getOne("a-history"));
		model.addAttribute("articleType", "history");
		model.addAttribute("legends", playerService.getByPost(Post.LEGEND));
		return HISTORY_VIEW;
	}
	
	@GetMapping("players/{id}")
	public String player(@PathVariable String id, Model model) {
		return teamMenuController.player(id, model);
	}
	
}
