package cz.tomek.fcblesno.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cz.tomek.fcblesno.model.Game;
import cz.tomek.fcblesno.model.League;
import cz.tomek.fcblesno.model.Team;
import cz.tomek.fcblesno.repository.GameRepository;
import cz.tomek.fcblesno.repository.LeagueRepository;
import cz.tomek.fcblesno.service.LeagueService;

/**
* Default implementation of {@link LeagueService}.
*
* @author tomek
*
*/
@Service
public class JpaLeagueService extends JpaAppEntityService<League> implements LeagueService {
	
	private Map<String, League> teamLeagueMap = new HashMap<>();
	
	public static Map<League, String> LEAGUE_TEAM_MAP = new HashMap<>();
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private LeagueRepository leagueRepository;
	
	@PostConstruct
	private void init() {
		Team.FCB_TEAMS.forEach(teamId -> {
			List<Game> games = gameRepository.findByTeamId(teamId, FIRST_RECORD);
			League currentLeague = !games.isEmpty() ? games.get(0).getLeague() : null;
			teamLeagueMap.put(teamId, currentLeague);
			
			List<League> leagues = leagueRepository.findByTeamId(teamId);
			leagues.forEach(league -> {
				LEAGUE_TEAM_MAP.put(league, teamId);
			});
		});
	}

	@Override
	public League getCurrentOfTeam(String teamId) {
		return teamLeagueMap.get(teamId);
	}
	
	@Override
	public List<League> getByTeamId(String teamId) {
		return leagueRepository.findByTeamId(teamId);
	}
	
	@Override
	public boolean isDeletionPermitted(String id) {
		return gameRepository.findByLeagueId(id).isEmpty();
	}
	
	@Override
	protected Sort getSort() {
		return Sort.by(Direction.DESC, "seasonStart", "leagueName");
	}

}
