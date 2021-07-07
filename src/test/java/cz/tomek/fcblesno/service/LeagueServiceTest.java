package cz.tomek.fcblesno.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.tomek.fcblesno.model.League;
import cz.tomek.fcblesno.model.Team;

/**
 * League service test.
 * 
 * @author tomek
 *
 */
public class LeagueServiceTest extends AppEntityServiceTest {

	@Autowired
	private LeagueService leagueService;
	
	@Test
	public void whenCurrentLeagueOfTeamIsRequestedThenRelevantResultIsReturned() {
		League aLeague = leagueService.getCurrentOfTeam(Team.FCB_A);
		League bLeague = leagueService.getCurrentOfTeam(Team.FCB_B);
		assertEquals("League of A team should be 'kp1819'!", "kp1819", aLeague.getId());
		assertEquals("League of B team should be '1b1819'!", "1b1819", bLeague.getId());
	}
	
}
