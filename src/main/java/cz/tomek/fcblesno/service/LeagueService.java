package cz.tomek.fcblesno.service;

import java.util.List;

import cz.tomek.fcblesno.model.League;

/**
* League service.
*
* @author tomek
*
*/
public interface LeagueService extends AppEntityService<League> {
	
	/**
	 * Gets current league of team with given <code>teamId</code>.
	 * 
	 * @param teamId
	 * @return current league of team
	 */
	League getCurrentOfTeam(String teamId);
	
	/**
	 * Gets all leagues that team with specified {@code teamId} has been part of.
	 * 
	 * @param teamId
	 * @return all team leagues
	 */
	List<League> getByTeamId(String teamId);

}
