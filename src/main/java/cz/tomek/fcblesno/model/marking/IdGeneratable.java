package cz.tomek.fcblesno.model.marking;

/**
 * Marks an entity whose id is generated.
 * 
 * @author tomek
 *
 */
public interface IdGeneratable {

	/**
	 * Generates unique id of an entity.
	 * 
	 * @return id
	 */
	String generateId();
	
}
