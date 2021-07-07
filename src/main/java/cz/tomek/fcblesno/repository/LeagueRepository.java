package cz.tomek.fcblesno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cz.tomek.fcblesno.model.League;

/**
* League repository.
*
* @author tomek
*
*/
public interface LeagueRepository extends AppEntityRepository<League> {

	@Query("select distinct g.league from Game g where g.teamHome.id = :teamId or g.teamGuest.id = :teamId"
			+ " order by g.league.seasonStart desc")
	List<League> findByTeamId(String teamId);
	
}
