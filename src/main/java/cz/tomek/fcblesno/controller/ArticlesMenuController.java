package cz.tomek.fcblesno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.tomek.fcblesno.model.enums.ArticleType;
import cz.tomek.fcblesno.service.ArticleService;

/**
 * Articles menu controller.
 * 
 * @author tomek
 *
 */
@Controller
@RequestMapping("articles")
public class ArticlesMenuController {

	private static String ARTICLES_VIEW = "display/articles";

	private static String ARTICLE_VIEW = "display/article";
	
	@Autowired
	private ArticleService articleService;

	@GetMapping
	public String articles(Model model) {
		return articles("general", 1, model);
	}
	
	@GetMapping("{typeName:(?:general|interview|tournament)}/{page:\\d+}")
	public String articles(@PathVariable String typeName, @PathVariable int page, Model model) {
		ArticleType type = ArticleType.valueOf(typeName.toUpperCase());
		model.addAttribute("articles", articleService.getByType(type, page - 1));
		model.addAttribute("numberOfPages", articleService.getNumberOfPages(type));
		model.addAttribute("currentPage", page);
		model.addAttribute("articleType", typeName);
		return ARTICLES_VIEW;
	}
	
	@GetMapping("{typeName:(?:general|interview|tournament|info)}/{id:a-[a-z]{3}-[a-z0-9]+}")
	public String article(@PathVariable String typeName, @PathVariable String id, Model model) {
		model.addAttribute("article", articleService.getOne(id));
		model.addAttribute("articleType", typeName);
		return ARTICLE_VIEW;
	}
	
}
