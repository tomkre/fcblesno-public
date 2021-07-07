package cz.tomek.fcblesno.service.impl;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.tomek.fcblesno.model.Game;
import cz.tomek.fcblesno.model.League;
import cz.tomek.fcblesno.model.PlayerStatistic;
import cz.tomek.fcblesno.model.Team;
import cz.tomek.fcblesno.repository.PlayerStatisticRepository;
import cz.tomek.fcblesno.service.LeagueService;
import cz.tomek.fcblesno.service.PlayerStatisticService;

/**
* Default implementation of {@link PlayerStatisticService}.
*
* @author tomek
*
*/
@Service("playerStatisticService") // named in order to be available in thymeleaf templates
@Transactional(readOnly = true)
public class JpaPlayerStatisticService extends JpaAppEntityService<PlayerStatistic> implements PlayerStatisticService {

	@Autowired
	private PlayerStatisticRepository playerStatisticRepository;
	
	@Autowired
	private LeagueService leagueService;
	
	@Override
	public List<PlayerStatistic> getSummedGoalkeeperStatsOfCurrentSeasonPart(String teamId) {
		return getSummedStatsOfCurrentSeason(teamId, PlayerStatistic::isGoalkeeper);
	}
	
	@Override
	public List<PlayerStatistic> getSummedPlayerStatsOfCurrentSeasonPart(String teamId) {
		return getSummedStatsOfCurrentSeason(teamId, PlayerStatistic::isPlayerInField);
	}
	
	private List<PlayerStatistic> getSummedStatsOfCurrentSeason(
			String teamId, Predicate<PlayerStatistic> filterFn) {
		return playerStatisticRepository
				.findByGameLeagueId(leagueService.getCurrentOfTeam(teamId).getId())
				.stream().filter(filterFn)
				.collect(groupingBy(PlayerStatistic::getPlayer, reducing(PlayerStatistic::merge))).values()
				.stream().map(o -> o.get()).sorted().collect(toList());
	}
	
	@Override
	public List<PlayerStatistic> getSummedGoalkeeperStatsOfPlayer(String playerId) {
		return getSummedStatsOfPlayer(playerId, PlayerStatistic::isGoalkeeper);
	}

	@Override
	public List<PlayerStatistic> getSummedPlayerStatsOfPlayer(String playerId) {
		return getSummedStatsOfPlayer(playerId, PlayerStatistic::isPlayerInField);
	}
	
	private List<PlayerStatistic> getSummedStatsOfPlayer(String playerId, Predicate<PlayerStatistic> filterFn) {
		List<PlayerStatistic> stats = playerStatisticRepository
				.findByPlayerId(playerId)
				.stream().filter(filterFn)
				.collect(groupingBy(ps -> ps.getGame().getLeague(), reducing(PlayerStatistic::merge))).values()
				.stream().map(o -> o.get()).sorted(PlayerStatistic.LEAGUE_ORDER)
				.collect(toList());
		List<PlayerStatistic> clonedStats = new ArrayList<>(stats);
		stats.add(getTotalStatsOfPlayer(clonedStats, Team.FCB_F, League.F_TEAM_TOTAL));
		stats.add(getTotalStatsOfPlayer(clonedStats, Team.FCB_A, League.A_TEAM_TOTAL));
		stats.add(getTotalStatsOfPlayer(clonedStats, Team.FCB_B, League.B_TEAM_TOTAL));
		stats.add(getTotalStatsOfPlayer(clonedStats, null, League.TOTAL));
		return stats;
	}
	
	private PlayerStatistic getTotalStatsOfPlayer(List<PlayerStatistic> allStats, String teamId, League league) {
		PlayerStatistic totalStat = allStats.stream()
				.filter(ps -> teamId == null || teamId.equals(ps.getGame().getTeamFcb().getId()))
				.collect(reducing(PlayerStatistic::merge)).orElse(new PlayerStatistic()).clone();
		totalStat.setGame(Game.builder().league(league).build());
		return totalStat;
	}
	
	@Override
	public List<PlayerStatistic> getByGameId(String gameId) {
		List<PlayerStatistic> result = 
				playerStatisticRepository.findByGameId(gameId);
		Collections.sort(result, PlayerStatistic.POST_ORDER);
		return result;
	}

	@Override
	public List<PlayerStatistic> extractShooters(List<PlayerStatistic> playersStatistics) {
		return playersStatistics
				.stream()
				.filter(ps -> ps.getGoals() > 0)
				.sorted(PlayerStatistic.GOALS_ORDER)
				.collect(toList());
	}

	@Override
	public List<PlayerStatistic> extractYellowCardHolders(List<PlayerStatistic> playersStatistics) {
		return playersStatistics
				.stream()
				.filter(ps -> ps.getYellowCards() > 0)
				.sorted(PlayerStatistic.YELLOW_CARDS_ORDER)
				.collect(toList());
	}

	@Override
	public List<PlayerStatistic> extractRedCardHolders(List<PlayerStatistic> playersStatistics) {
		return playersStatistics
				.stream()
				.filter(ps -> ps.getRedCards() > 0)
				.sorted()
				.collect(toList());
	}

}
