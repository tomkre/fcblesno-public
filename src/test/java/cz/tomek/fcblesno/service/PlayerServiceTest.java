package cz.tomek.fcblesno.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.tomek.fcblesno.model.Player;
import cz.tomek.fcblesno.model.Team;

/**
 * Player service test.
 * 
 * @author tomek
 *
 */
public class PlayerServiceTest extends AppEntityServiceTest {

	@Autowired
	private PlayerService playerService;
	
	@Test
	public void whenTeamPlayersAreRequestedThenRelevantResultIsReturned() {
		List<Player> teamPlayers = playerService.getByTeamId(Team.FCB_A); 
		boolean areAllPlayersPartOfATeam = teamPlayers.stream().allMatch(p -> p.getId().matches("kretom|voljar|deddom"));
		assertTrue("All players should be part of A team!", areAllPlayersPartOfATeam);
	}
	
}
