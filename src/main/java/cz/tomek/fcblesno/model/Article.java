package cz.tomek.fcblesno.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import cz.tomek.fcblesno.model.enums.ArticleType;
import cz.tomek.fcblesno.model.jpalistener.ArticleListener;
import cz.tomek.fcblesno.model.marking.ContainsPhoto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* Article.
*
* @author tomek
*
*/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "article")
@EntityListeners(ArticleListener.class)
public class Article extends AppEntity implements ContainsPhoto {
	
	@Column(name = "published_at")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date publishedAt;

	@Column(name = "article_type")
	@Enumerated(EnumType.STRING)
	private ArticleType articleType;
	
	@NotBlank
	@Column(name = "title")
	private String title;
	
	@Column(name = "content", columnDefinition = "TEXT")
	private String content;
	
	@Override
	public String getDisplayName() {
		return title;
	}
	
	@Override
	public String toString() {
		return String.format("Article [id=%s, title=%s, articleType=%s]", id, title, articleType);
	}

}
