package cz.tomek.fcblesno.service;

import java.util.List;

import cz.tomek.fcblesno.model.Article;
import cz.tomek.fcblesno.model.enums.ArticleType;

/**
* Article service.
*
* @author tomek
*
*/
public interface ArticleService extends AppEntityService<Article> {
	
	/**
	 * Gets newest articles (sorted by <code>publishDate</code>).
	 * 
	 * @return  newest articles
	 */
	List<Article> getNewest();

	/**
	 * Gets articles by type.
	 * 
	 * @param articleType
	 * @param page
	 * @return articles by type
	 */
	List<Article> getByType(ArticleType articleType, int page);
	
	/**
	 * Gets number of pages of articles of given type.
	 * 
	 * @param articleType
	 * @return number of pages
	 */
	int getNumberOfPages(ArticleType articleType);

}
