package cz.tomek.fcblesno.controller.admin.rest;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.tomek.fcblesno.model.Player;
import cz.tomek.fcblesno.model.PlayerStatistic;
import cz.tomek.fcblesno.model.enums.Post;
import cz.tomek.fcblesno.service.PlayerService;
import cz.tomek.fcblesno.service.PlayerStatisticService;

/**
 * Player rest controller.
 * 
 * @author tomek
 *
 */
@RestController
@RequestMapping("/admin/rest")
public class AppRestController {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PlayerStatisticService playerStatisticService;
	
	@Autowired
	private MessageSource messageSource;
	
	private MessageSourceAccessor msa;
	
	@PostConstruct
	private void init() {
		msa = new MessageSourceAccessor(messageSource);
	}
	
	@GetMapping("players")
	public List<Player> players() {
		List<Player> players = playerService.getAll();
		players.forEach(player -> detachPlayerDependencies(player));
		return players;
	}

	@GetMapping("playerStatistics")
	public List<PlayerStatistic> playerStatisticsByGameId(@RequestParam String gameId) {
		List<PlayerStatistic> playerStatistics =  playerStatisticService.getByGameId(gameId);
		playerStatistics.forEach(ps -> {
			ps.setGame(null);
			detachPlayerDependencies(ps.getPlayer());
		});
		return playerStatistics;
	}
	
	private void detachPlayerDependencies(Player player) {
		player.setPlayerStatistics(null);
		player.setTeams(null);
	}
	
	@GetMapping("posts")
	public Map<String, String> posts() {
		return Arrays
				.stream(Post.values())
				.filter(p -> p != Post.LEGEND)
				.collect(toMap(Post::name, p -> msa.getMessage("post." + p.name() + ".singular")));
	}
	
	@PostMapping("removeSessionAttribute/{sessionAttribute}")
	public void removeSessionAttribute(@PathVariable String sessionAttribute, HttpServletRequest request) {
		request.getSession().removeAttribute(sessionAttribute);
	}
	
}
