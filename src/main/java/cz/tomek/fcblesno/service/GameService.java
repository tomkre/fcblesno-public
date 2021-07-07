package cz.tomek.fcblesno.service;

import java.util.List;

import cz.tomek.fcblesno.model.Game;
import cz.tomek.fcblesno.model.enums.SeasonPart;

/**
* Match service.
*
* @author tomek
*
*/
public interface GameService extends AppEntityService<Game> {
	
	/**
	 * Gets the last game of the team with specified <code>teamId</code>.
	 * 
	 * @param teamId
	 * @return the last game of the team
	 */
	Game getLastOfTeam(String teamId);
	
	/**
	 * Gets last n games of the team with specified <code>teamId</code>.
	 * 
	 * @param teamId
	 * @return the last game of the team
	 */
	List<Game> getLastOfTeam(String teamId, int count);

	/**
	 * Gets upcoming game of the team with specified <code>teamId</code>.
	 * 
	 * @param teamId
	 * @return upcoming game of the team
	 */
	Game getUpcomingOfTeam(String teamId);
	
	/**
	 * Gets upcoming n games of the team with specified <code>teamId</code>.
	 * 
	 * @param teamId
	 * @return upcoming game of the team
	 */
	List<Game> getUpcomingOfTeam(String teamId, int count);
	
	/**
	 * Gets all games of the current season of the team with specified <code>teamId</code>.
	 * 
	 * @param teamId
	 * @return all team's games of the current season
	 */
	List<Game> getAllOfCurrentSeasonByTeam(String teamId);
	
	/**
	 * Get schedule for team in league in season part.
	 * 
	 * @param leagueId
	 * @param teamId
	 * @param seasonPart
	 * @return schedule of specified team
	 */
	List<Game> getSchedule(String leagueId, String teamId, SeasonPart seasonPart);
	
}
