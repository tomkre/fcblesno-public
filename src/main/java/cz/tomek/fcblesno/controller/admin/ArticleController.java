package cz.tomek.fcblesno.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.tomek.fcblesno.model.Article;
import cz.tomek.fcblesno.model.enums.ArticleType;
import cz.tomek.fcblesno.service.ArticleService;

/**
 * Article controller.
 * 
 * @author tomek
 *
 */
@Controller
@RequestMapping("admin/{contextUrl:articles}")
public class ArticleController extends AppEntityController<Article> {

	@Autowired
	private ArticleService articleService;
	
	public ArticleController() {
		super(Article.class);
	}
	
	@Override
	protected void addListModelAttributes(Model model, Map<String, String> requestParams) {
		requestParams.putIfAbsent("type", ArticleType.GENERAL.name());
		ArticleType type = ArticleType.valueOf(requestParams.get("type"));
		int page = Integer.valueOf(requestParams.get("page"));
		model.addAttribute("entities", articleService.getByType(type, page - 1));
		model.addAttribute("numOfPages", articleService.getNumberOfPages(type));
		model.addAttribute("specialParams", "type=" + type.name());
	}

}
