package cz.tomek.fcblesno.repository;

import java.util.List;

import cz.tomek.fcblesno.model.Player;
import cz.tomek.fcblesno.model.enums.Post;

/**
* Player repository.
*
* @author tomek
*
*/
public interface PlayerRepository extends AppEntityRepository<Player> {
	
	/**
	 * Finds all players related to team with given <code>teamId</code>.
	 * 
	 * @param teamId
	 * @return all players related to specified team
	 */
	List<Player> findByTeamsIdOrderByPostDescLastNameAsc(String teamId);
	
	/**
	 * Finds all players of given post.
	 * 
	 * @param post
	 * @return all players of given post
	 */
	List<Player> findByPostOrderByLastNameAsc(Post post);

}
