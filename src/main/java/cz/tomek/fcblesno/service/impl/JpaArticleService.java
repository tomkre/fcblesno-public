package cz.tomek.fcblesno.service.impl;

import java.util.EnumSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.tomek.fcblesno.model.Article;
import cz.tomek.fcblesno.model.enums.ArticleType;
import cz.tomek.fcblesno.repository.ArticleRepository;
import cz.tomek.fcblesno.service.ArticleService;

/**
* Default implementation of {@link ArticleService}.
*
* @author tomek
*
*/
@Service
@Transactional(readOnly = true)
public class JpaArticleService extends JpaAppEntityService<Article> implements ArticleService {
	
	private static final EnumSet<ArticleType> DELETABLE_ARTICLES = 
			EnumSet.of(ArticleType.GENERAL, ArticleType.INTERVIEW, ArticleType.TOURNAMENT);
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Override
	public List<Article> getNewest() {
		Pageable pageRequest = PageRequest.of(0, 7);
		return articleRepository.findByPublishedAtNotNullOrderByPublishedAtDesc(pageRequest);
	}

	@Override
	public List<Article> getByType(ArticleType articleType, int page) {
		Pageable pageRequest = PageRequest.of(page, ITEMS_PER_PAGE);
		return articleRepository.findByArticleTypeOrderByPublishedAtDesc(articleType, pageRequest);
	}

	@Override
	public int getNumberOfPages(ArticleType articleType) {
		Article exampledArticle = Article.builder().articleType(articleType).build();
		long numberOfArticles = articleRepository.count(Example.of(exampledArticle));
		int numberOfPages = (int) numberOfArticles / ITEMS_PER_PAGE;
		if (numberOfArticles % ITEMS_PER_PAGE > 0) {
			numberOfPages += 1;
		}
		return numberOfPages;
	}
	
	@Override
	public boolean isDeletionPermitted(String id) {
		Article article = articleRepository.getOne(id);
		return DELETABLE_ARTICLES.contains(article.getArticleType());
	}

}
