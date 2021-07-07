package cz.tomek.fcblesno.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.tomek.fcblesno.model.PlayerStatistic;
import cz.tomek.fcblesno.model.Team;

/**
 * Player statistic service test.
 * 
 * @author tomek
 *
 */
public class PlayerStatisticServiceTest extends AppEntityServiceTest {

	@Autowired
	private PlayerStatisticService playerStatisticService;
	
	@Test
	public void whenPlayersStatisticsAreRequestedThenRelevantResultIsReturned() {
		List<PlayerStatistic> aTeamStatistics = playerStatisticService.getSummedPlayerStatsOfCurrentSeasonPart(Team.FCB_A);
		PlayerStatistic kreTomStat = 
				aTeamStatistics
					.stream()
					.filter(ps -> "kretom".equals(ps.getPlayer().getId()))
					.findFirst()
					.get();
		assertEquals("Player 'kretom' should have together 4 goals!", 4, kreTomStat.getGoals());
		assertEquals("Player 'kretom' should have together 3 assists!", 3, kreTomStat.getAssists());
	}
	
	@Test
	public void whenShootersAreRequestedThenRelevantResultIsReturned() {
		List<PlayerStatistic> gameStatistics = playerStatisticService.getByGameId("kp1819-sena-fcba");
		List<PlayerStatistic> shooters = playerStatisticService.extractShooters(gameStatistics);
		boolean haveShootersAtLeastOneGoal = shooters.stream().allMatch(p -> p.getGoals() > 0);
		assertTrue("All shooters should have at least one goal!", haveShootersAtLeastOneGoal);
	}

	@Test
	public void whenYellowCardHoldersAreRequestedThenRelevantResultIsReturned() {
		List<PlayerStatistic> gameStatistics = playerStatisticService.getByGameId("kp1819-sena-fcba");
		List<PlayerStatistic> yellowCardHolders = playerStatisticService.extractYellowCardHolders(gameStatistics);
		boolean haveYellowCardHoldersAtLeastOneYellowCard = yellowCardHolders.stream().allMatch(p -> p.getYellowCards() > 0);
		assertTrue("All yellow card holders should have at least one yellow card!", haveYellowCardHoldersAtLeastOneYellowCard);
	}
	
	@Test
	public void whenRedCardHoldersAreRequestedThenRelevantResultIsReturned() {
		List<PlayerStatistic> gameStatistics = playerStatisticService.getByGameId("kp1819-sena-fcba");
		List<PlayerStatistic> redCardHolders = playerStatisticService.extractRedCardHolders(gameStatistics);
		boolean haveRedCardHoldersAtLeastOneRedCard = redCardHolders.stream().allMatch(p -> p.getRedCards() > 0);
		assertTrue("All red card holders should have at least one red card!", haveRedCardHoldersAtLeastOneRedCard);
	}
	
}
