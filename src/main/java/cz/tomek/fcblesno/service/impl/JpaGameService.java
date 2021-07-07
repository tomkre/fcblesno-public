package cz.tomek.fcblesno.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.tomek.fcblesno.model.Game;
import cz.tomek.fcblesno.model.League;
import cz.tomek.fcblesno.model.Team;
import cz.tomek.fcblesno.model.enums.SeasonPart;
import cz.tomek.fcblesno.repository.GameRepository;
import cz.tomek.fcblesno.service.GameService;
import cz.tomek.fcblesno.service.LeagueService;
import cz.tomek.fcblesno.util.DateService;

/**
* Default implementation of {@link GameService}.
*
* @author tomek
*
*/
@Service
@Transactional(readOnly = true)
public class JpaGameService extends JpaAppEntityService<Game> implements GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private DateService dateService;
	
	@Autowired
	private LeagueService leagueService;
	
	@Override
	public Game getLastOfTeam(String teamId) {
		List<Game> games = getLastOfTeam(teamId, 1);
		return !games.isEmpty() ? games.get(0) : null;
	}

	@Override
	public List<Game> getLastOfTeam(String teamId, int count) {
		Date today = dateService.now();
		return gameRepository.findByTeamIdAndGameDateLessThanOrderByGameDateDesc(teamId, today, PageRequest.of(0, count));
	}
	
	@Override
	public Game getUpcomingOfTeam(String teamId) {
		List<Game> games =  getUpcomingOfTeam(teamId, 1);
		return !games.isEmpty() ? games.get(0) : null;
	}
	
	@Override
	public List<Game> getUpcomingOfTeam(String teamId, int count) {
		Date today = dateService.now();
		return gameRepository.findByTeamIdAndGameDateGreaterThanEqualOrderByGameDateAsc(teamId, today, PageRequest.of(0, 2));
	}

	@Override
	public List<Game> getAllOfCurrentSeasonByTeam(String teamId) {
		Date dateFrom, dateTo;
		if (Team.FCB_F.equals(teamId)) {
			dateFrom = dateService.getCurrentSeasonStart(teamId);
			dateTo = dateService.getCurrentSeasonEnd(teamId);
		} else {
			dateFrom = dateService.getCurrentSeasonPartStart();
			dateTo = dateService.getCurrentSeasonPartEnd();
		}
		return gameRepository.findByTeamIdAndGameDateBetweenOrderByGameDateAsc(teamId, dateFrom, dateTo);
	}
	
	@Override
	public List<Game> getSchedule(String leagueId, String teamId, SeasonPart seasonPart) {
		League league = leagueService.getOne(leagueId);
		Date dateFrom = league.getSeasonStart();
		Date dateTo = league.getSeasonEnd();
		if (!Team.FCB_F.equals(teamId)) {
			switch (seasonPart) {
			case AUTUMN:
				Calendar calTo = Calendar.getInstance();
				calTo.setTime(dateFrom);
				calTo.set(Calendar.MONTH, Calendar.DECEMBER);
				calTo.set(Calendar.DAY_OF_MONTH, 31);
				dateTo = calTo.getTime();
				break;
			case SPRING:
				Calendar calFrom = Calendar.getInstance();
				calFrom.setTime(dateTo);
				calFrom.set(Calendar.MONTH, Calendar.JANUARY);
				calFrom.set(Calendar.DAY_OF_MONTH, 1);
				dateFrom = calFrom.getTime();
			}
		}
		return gameRepository.findByTeamIdAndGameDateBetweenOrderByGameDateAsc(teamId, dateFrom, dateTo);
	}
	
	@Override
	protected Sort getSort() {
		return Sort.by(Direction.DESC, "gameDate");
	}

}
