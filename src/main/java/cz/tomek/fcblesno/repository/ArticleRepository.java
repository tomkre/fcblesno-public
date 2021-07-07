package cz.tomek.fcblesno.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import cz.tomek.fcblesno.model.Article;
import cz.tomek.fcblesno.model.enums.ArticleType;

/**
* Article repository.
*
* @author tomek
*
*/
public interface ArticleRepository extends AppEntityRepository<Article> {
	
	/**
	 * Finds all articles which were published.
	 * 
	 * @param pageRequest
	 * @return all published articles
	 */
	List<Article> findByPublishedAtNotNullOrderByPublishedAtDesc(Pageable pageRequest);
	
	/**
	 * Finds articles by <code>articleType</code>.
	 * 
	 * @param pageRequest
	 * @return articles of given type
	 */
	List<Article> findByArticleTypeOrderByPublishedAtDesc(ArticleType articleType, Pageable pageRequest);

}
