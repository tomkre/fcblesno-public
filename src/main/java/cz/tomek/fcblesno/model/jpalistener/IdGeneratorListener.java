package cz.tomek.fcblesno.model.jpalistener;

import javax.persistence.PrePersist;

import cz.tomek.fcblesno.model.AppEntity;
import cz.tomek.fcblesno.model.marking.IdGeneratable;
import lombok.extern.slf4j.Slf4j;

/**
 * Manually adds id to an entity.
 * 
 * @author tomek
 *
 */
@Slf4j
public class IdGeneratorListener {

	@PrePersist
	public <T extends AppEntity & IdGeneratable> void generateId(T entity) {
		String id = entity.generateId();
		entity.setId(id);
		log.debug("Generated id {} for {}", id, entity);
	}
	
}
