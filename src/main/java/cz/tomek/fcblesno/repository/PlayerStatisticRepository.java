package cz.tomek.fcblesno.repository;

import java.util.List;

import cz.tomek.fcblesno.model.PlayerStatistic;

/**
* Player Statistic repository.
*
* @author tomek
*
*/
public interface PlayerStatisticRepository extends AppEntityRepository<PlayerStatistic> {
	
	/**
	 * Finds all aggregated players statistics related to league with given <code>leagueId</code>.
	 * 
	 * @param leagueId
	 * @return all player statistics related to given league
	 */
	List<PlayerStatistic> findByGameLeagueId(String leagueId);
	
	/**
	 * Finds all players statistics related to game with given <code>gameId</code>.
	 * 
	 * @param gameId
	 * @return player statistics of the game
	 */
	List<PlayerStatistic> findByGameId(String gameId);
	
	/**
	 * Finds all player statistics related to player with given <code>playerId</code>.
	 * 
	 * @param playerId
	 * @return all player statistics of the player
	 */
	List<PlayerStatistic> findByPlayerId(String playerId);
	
}
