package cz.tomek.fcblesno.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cz.tomek.fcblesno.model.Game;

/**
* Game repository.
*
* @author tomek
*
*/
public interface GameRepository extends AppEntityRepository<Game> {

	/**
	 * Finds the last match of the team with specified id.
	 * 
	 * @param teamId
	 * @param gameDate
	 * @param pageRequest
	 * @return the last match of the team
	 */
	@Query("select g from Game g " 
		 + "where (g.teamHome.id = :teamId or g.teamGuest.id = :teamId) " 
		 + "and g.gameDate < :gameDate "
		 + "order by g.gameDate desc")
	List<Game> findByTeamIdAndGameDateLessThanOrderByGameDateDesc(String teamId, Date gameDate, Pageable pageRequest);

	/**
	 * Finds an upcoming match of the team with specified id.
	 * 
	 * @param teamId
	 * @param gameDate
	 * @param pageRequest
	 * @return an upcoming match of the team
	 */
	@Query("select g from Game g " 
			 + "where (g.teamHome.id = :teamId or g.teamGuest.id = :teamId) " 
			 + "and g.gameDate >= :gameDate "
			 + "order by g.gameDate asc")
	List<Game> findByTeamIdAndGameDateGreaterThanEqualOrderByGameDateAsc(String teamId, Date gameDate, Pageable pageRequest);

	/**
	 * Finds games that are held between <code>dateFrom</code> and <code>dateTo</code>.
	 * 
	 * @param teamId
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@Query("select g from Game g " 
			+ "where (g.teamHome.id = :teamId or g.teamGuest.id = :teamId) " 
			+ "and g.gameDate > :dateFrom and g.gameDate < :dateTo "
			+ "order by g.gameDate asc")
	List<Game> findByTeamIdAndGameDateBetweenOrderByGameDateAsc(String teamId, Date dateFrom, Date dateTo);
	
	/**
	 * Finds all games of team with given <code>teamId</code>.
	 * 
	 * @param teamId
	 * @param pageRequest
	 * @return
	 */
	@Query("select g from Game g "
			+ "where (g.teamHome.id = :teamId or g.teamGuest.id = :teamId) "
			+ "order by g.gameDate desc")
	List<Game> findByTeamId(String teamId, Pageable pageRequest);
	
	/**
	 * Finds all games related to league with given <code>leagueId</code>.
	 * 
	 * @param leagueId
	 * @return all league games
	 */
	List<Game> findByLeagueId(String leagueId);
	
	/**
	 * Finds all games related to location with given <code>locationId</code>.
	 * 
	 * @param locationId
	 * @return games played at location with given <code>locationId</code>.
	 */
	List<Game> findByLocationId(String locationId);

}
