package cz.tomek.fcblesno.service;

import java.util.List;

import cz.tomek.fcblesno.model.PlayerStatistic;

/**
* Player Statistic service.
*
* @author tomek
*
*/
public interface PlayerStatisticService extends AppEntityService<PlayerStatistic> {
	
	/**
	 * Gets summed goalkeeper statistics of given team for current season part.
	 * 
	 * @param teamId
	 * @return summed goalkeeper statistics of given team for current season part
	 */
	List<PlayerStatistic> getSummedGoalkeeperStatsOfCurrentSeasonPart(String teamId);
	
	/**
	 * Gets summed player statistics of given team for current season part.
	 * 
	 * @param teamId
	 * @return summed player statistics of given team for current season part
	 */
	List<PlayerStatistic> getSummedPlayerStatsOfCurrentSeasonPart(String teamId);
	
	/**
	 * Gets summed goalkeeper statistics of given player for all seasons.
	 * 
	 * @param teamId
	 * @return summed goalkeeper statistics of given player for all seasons
	 */
	List<PlayerStatistic> getSummedGoalkeeperStatsOfPlayer(String playerId);
	
	/**
	 * Gets summed player statistics of given player for all seasons.
	 * 
	 * @param teamId
	 * @return summed player statistics of given player for all seasons
	 */
	List<PlayerStatistic> getSummedPlayerStatsOfPlayer(String playerId);
	
	/**
	 * Get players statistics related to a game with given <code>gameId</code>. 
	 * 
	 * @param gameId
	 * @return players statistics of the game
	 */
	List<PlayerStatistic> getByGameId(String gameId);
	
	/**
	 * Extracts shooters from players statistics and sort them
	 * by number of goals.
	 * 
	 * @param playersStatistics
	 * @return shooters sorted by number of goals 
	 */
	List<PlayerStatistic> extractShooters(List<PlayerStatistic> playersStatistics);

	/**
	 * Extracts yellow card holders from players statistics and sort them
	 * by number of yellow cards.
	 * 
	 * @param playersStatistics
	 * @return yellow card holders sorted by number of yellow cards
	 */
	List<PlayerStatistic> extractYellowCardHolders(List<PlayerStatistic> playersStatistics);
	
	/**
	 * Extracts red card holders from players statistics. 
	 * 
	 * @param playersStatistics
	 * @return red card holders 
	 */
	List<PlayerStatistic> extractRedCardHolders(List<PlayerStatistic> playersStatistics);

}
