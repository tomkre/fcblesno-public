package cz.tomek.fcblesno.model.jpalistener;

import javax.persistence.PreUpdate;

import cz.tomek.fcblesno.model.AppEntity;
import cz.tomek.fcblesno.util.DateService;
import cz.tomek.fcblesno.util.SpringContext;

/**
 * Updates auditing data.
 * 
 * @author tomek
 *
 */
public class AppEntityListener {
	
	@PreUpdate
	public void update(AppEntity entity) {
		DateService dateService = SpringContext.getBean(DateService.class);
		entity.setUpdatedAt(dateService.now());
	}

}
