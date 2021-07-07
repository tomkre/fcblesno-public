package cz.tomek.fcblesno.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.tomek.fcblesno.model.Game;
import cz.tomek.fcblesno.model.Team;

/**
 * Game service test.
 * 
 * @author tomek
 *
 */
public class GameServiceTest extends AppEntityServiceTest {

	@Autowired
	private GameService gameService;

	@Test
	public void whenPreviousAndUpcomingGamesAreRequestedThenRelevantGamesAreReturned() {
		Game lastGameOfATeam = gameService.getLastOfTeam(Team.FCB_A);
		Game lastGameOfBTeam = gameService.getLastOfTeam(Team.FCB_B);
		Game upcomingGameOfATeam = gameService.getUpcomingOfTeam(Team.FCB_A);
		Game upcomingGameOfBTeam = gameService.getUpcomingOfTeam(Team.FCB_B);
		assertEquals("Last game of A team should be 'kp1819-sena-fcba'!", "kp1819-sena-fcba", lastGameOfATeam.getId());
		assertEquals("Last game of B team should be 'kp1819-sena-fcba'!", "1b1819-nxta-fcbb", lastGameOfBTeam.getId());
		assertEquals("Upcoming game of A team should be 'kp1819-sena-fcba'!", "kp1819-lega-fcba", upcomingGameOfATeam.getId());
		assertEquals("Upcoming game of B team should be 'kp1819-sena-fcba'!", "1b1819-fcbb-frdb", upcomingGameOfBTeam.getId());
	}
	
	@Test
	public void whenGamesOfCurrentLeagueAndTeamAreRequestedThenRelevantResultIsReturned() {
		List<Game> aGames = gameService.getAllOfCurrentSeasonByTeam(Team.FCB_A);
		boolean areGamesHeldAfterCurrentSeasonStart = aGames.stream().allMatch(g -> g.getGameDate().after(dateService.getCurrentSeasonPartStart()));
		boolean areGamesHeldBeforeCurrentSeasonEnd = aGames.stream().allMatch(g -> g.getGameDate().before(dateService.getCurrentSeasonPartEnd()));
		boolean areGamesPartOfKp1819Season = aGames.stream().allMatch(g -> "kp1819".equals(g.getLeague().getId())); 
		assertTrue("Games should be held after current season's start!", areGamesHeldAfterCurrentSeasonStart);
		assertTrue("Games should be held before current season's end!", areGamesHeldBeforeCurrentSeasonEnd);
		assertTrue("League of all games should be 'kp1819'!", areGamesPartOfKp1819Season);
	}
	
}
