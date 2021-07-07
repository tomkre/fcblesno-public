package cz.tomek.fcblesno.service;

import java.util.List;

import cz.tomek.fcblesno.model.AppEntity;

/**
 * App Entity service.
 * 
 * @author tomek
 *
 */
public interface AppEntityService<T extends AppEntity> {
	
	/**
	 * Gets an entity by id.
	 * 
	 * @param id
	 * @return found entity
	 */
	T getOne(String id);
	
	/**
	 * Gets all entites.
	 * 
	 * @return all entities
	 */
	List<T> getAll();

	/**
	 * Gets entities of given page.
	 * 
	 * @return entities of given page
	 */
	List<T> getByPage(int page);
	
	/**
	 * Gets number of pages for this entity type.
	 * 
	 * @return number of pages for this entity type
	 */
	int getNumberOfPages();
	
	/**
	 * Checks whether entity with given <code>id</code> exists in DB.
	 * 
	 * @param id
	 * @return true if entity exists in DB, false otherwise
	 */
	boolean exists(String id);
	
	/**
	 * Saves an entity.
	 * 
	 * @param entity
	 * @return persisted entity
	 */
	T save(T entity);
	
	/**
	 * Deletes an entity by id.
	 * 
	 * @param id
	 */
	void delete(String id); 
	
	/**
	 * Checks whether an entity with specified <code>id</code>
	 * can be safely deleted. In other words it means that this entity
	 * shouldn't be referenced by other entities.
	 * 
	 * @param id of an entity to be removed
	 * @return flag whether an entity can be safely removed
	 */
	boolean isDeletionPermitted(String id);

}
