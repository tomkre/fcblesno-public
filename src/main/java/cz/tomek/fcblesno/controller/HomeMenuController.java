package cz.tomek.fcblesno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.tomek.fcblesno.model.Article;
import cz.tomek.fcblesno.service.ArticleService;

/**
 * Main menu controller.
 * 
 * @author tomek
 *
 */
@Controller
@RequestMapping("/")
public class HomeMenuController {

	private static String HOME_VIEW = "display/home";

	@Autowired
	private ArticleService articleService;
	
	@GetMapping({"", "home"})
	public String hello(Model model) {
		List<Article> newest = articleService.getNewest();
		model.addAttribute("articles", newest);
		return HOME_VIEW;
	}
	
}
