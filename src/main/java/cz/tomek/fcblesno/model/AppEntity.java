package cz.tomek.fcblesno.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import cz.tomek.fcblesno.model.jpalistener.AppEntityListener;
import cz.tomek.fcblesno.util.DateService;
import lombok.Getter;
import lombok.Setter;

/**
 * App entity.
 * 
 * @author tomek
 *
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AppEntityListener.class)
public abstract class AppEntity {

	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = DateService.TIMESTAMP_PATTERN)
	private Date createdAt;
	
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = DateService.TIMESTAMP_PATTERN)
	private Date updatedAt;
	
	@Override
	public String toString() {
		return String.format("%s[id=%s]", this.getClass().getSimpleName(), id);
	}
	
	/**
	 * Gets human readable representation of this entity.
	 * 
	 * @return human readable representation of this entity
	 */
	public abstract String getDisplayName();
	
}
