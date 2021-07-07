package cz.tomek.fcblesno.service;

import java.util.List;
import java.util.Map;

import cz.tomek.fcblesno.model.Player;
import cz.tomek.fcblesno.model.PlayerStatistic;
import cz.tomek.fcblesno.model.enums.Post;

/**
* Player service.
*
* @author tomek
*
*/
public interface PlayerService extends AppEntityService<Player> {
	
	/**
	 * Gets all players of team with given <code>teamId</code>.
	 * 
	 * @param teamId
	 * @return all players of required team
	 */
	List<Player> getByTeamId(String teamId);

	/**
	 * Same as {@link PlayerService#getByTeamId(String), but in addition
	 * players are grouped by post.
	 */
	Map<Post, List<Player>> getByTeamIdGroupedByPost(String teamId);
	
	/**
	 * Extracts players from players statistics.
	 * 
	 * @param playersStatistics
	 * @return extracted players
	 */
	Map<Post, List<Player>> extractFromPlayersStatistics(List<PlayerStatistic> playersStatistics);
	
	/**
	 * Gets all players of given post.
	 * 
	 * @param post
	 * @return all players of given post
	 */
	List<Player> getByPost(Post post);
	
}
