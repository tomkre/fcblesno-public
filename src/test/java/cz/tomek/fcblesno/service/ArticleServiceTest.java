package cz.tomek.fcblesno.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.tomek.fcblesno.model.Article;
import cz.tomek.fcblesno.model.enums.ArticleType;

/**
 * Article service test.
 * 
 * @author tomek
 *
 */
public class ArticleServiceTest extends AppEntityServiceTest {
	
	@Autowired
	private ArticleService articleService;

	@Test
	public void whenArticleIsSavedThenExistsInDb() {
		String id = "a1";
		createAndSaveArticle(id);
		assertTrue("Article should exist in DB.", articleService.exists(id));
	}
	
	@Test
	public void whenArticleIsDeletedThenNotExistsInDb() {
		String id = "b1";
		createAndSaveArticle(id);
		articleService.delete(id);
		assertFalse("Article should not exist in DB.", articleService.exists(id));
	}
	
	@Test
	public void whenArticleWithoutTitleIsCreatedThenConstraintIsViolated() {
		Article article = new Article();
		article.setId("c1");
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Article>> violations = validator.validate(article);
		assertFalse("Article should contain at least one constraint violation.", violations.isEmpty());
	}
	
	private void createAndSaveArticle(String id) {
		Article article = new Article();
		article.setId(id);
		article.setTitle("Title of " + id);
		articleService.save(article);
	}
	
	@Test
	public void whenArticlesOfGivenTypeAreRequestedThenRelevantResultIsReturned() {
		List<Article> tournaments = articleService.getByType(ArticleType.TOURNAMENT, 0);
		boolean areAllArticlesTournaments = tournaments.stream().allMatch(a -> a.getArticleType().equals(ArticleType.TOURNAMENT));
		assertTrue("All provided articles should be tournaments!", areAllArticlesTournaments);
	}
	
}
