package cz.tomek.fcblesno.model.jpalistener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import cz.tomek.fcblesno.model.Article;
import cz.tomek.fcblesno.util.DateService;
import lombok.extern.slf4j.Slf4j;

/**
 * Article listener.
 * 
 * @author tomek
 *
 */
@Slf4j
public class ArticleListener {
	
	@PrePersist
	public void prePersist(Article article) {
		Date now = new Date();
		article.setCreatedAt(now);
		log.debug("Added audit data to article {}: [timestamp={}]", article, DateService.format(now));
	}
	
	@PreUpdate
	public void preUpdate(Article article) {
		Date now = new Date();
		article.setUpdatedAt(now);
		log.debug("Updated audit data of article {}: [timestamp={}]", article, DateService.format(now));
	}

}
