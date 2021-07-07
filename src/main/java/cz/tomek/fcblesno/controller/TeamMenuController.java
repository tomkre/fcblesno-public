package cz.tomek.fcblesno.controller;

import static java.util.stream.Collectors.toList;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cz.tomek.fcblesno.model.League;
import cz.tomek.fcblesno.model.Team;
import cz.tomek.fcblesno.model.enums.SeasonPart;
import cz.tomek.fcblesno.service.GameService;
import cz.tomek.fcblesno.service.LeagueService;
import cz.tomek.fcblesno.service.PlayerService;
import cz.tomek.fcblesno.service.PlayerStatisticService;
import lombok.extern.slf4j.Slf4j;

/**
 * Game menu controller.
 * 
 * @author tomek
 *
 */
@Controller
@RequestMapping("{team:a-team|b-team|futsal}")
@Slf4j
public class TeamMenuController {
	
	private static String TEAM_VIEW = "display/team";

	private static String GAMES_VIEW = "display/games";

	private static String GAME_VIEW = "display/game";

	private static String PLAYERS_VIEW = "display/players";
	
	private static String PLAYER_VIEW = "display/player";

	private static String STATISTICS_VIEW = "display/statistics";
	
	@Autowired
	private GameService gameService;

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PlayerStatisticService playerStatisticService;
	
	@Autowired
	private LeagueService leagueService;
	
	@GetMapping
	public String team(@PathVariable String team, Model model) {
		return TEAM_VIEW;
	}
	
	@GetMapping("games")
	public String games(@PathVariable String team, @RequestParam(required = false) String leagueId, 
			@RequestParam(required = false) SeasonPart seasonPart, Model model) {
		String teamId = currentTeamId(team);
		if (leagueId == null) {
			leagueId = leagueService.getCurrentOfTeam(teamId).getId();
		}
		if (seasonPart == null) {
			int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
			seasonPart = currentMonth >= Calendar.SEPTEMBER 
					|| currentMonth <= Calendar.FEBRUARY ? SeasonPart.AUTUMN : SeasonPart.SPRING;
		}
		model.addAttribute("games", gameService.getSchedule(leagueId, teamId, seasonPart));
		List<League> leagues = leagueService.getByTeamId(teamId)
				.stream().filter(l -> l.getSeasonStart().after(new GregorianCalendar(2018, 7, 31).getTime()))
				.collect(toList());
		model.addAttribute("leagues", leagues);
		model.addAttribute("selectedLeagueId", leagueId);
		model.addAttribute("seasonParts", SeasonPart.values());
		model.addAttribute("selectedSeasonPart", seasonPart);
		return GAMES_VIEW;
	}

	@GetMapping("games/{id}")
	public String game(@PathVariable String id, Model model) {
		model.addAttribute("game", gameService.getOne(id));
		return GAME_VIEW;
	}
	
	@GetMapping("players")
	public String players(@PathVariable String team, Model model) {
		model.addAttribute("playersByPost", playerService.getByTeamIdGroupedByPost(currentTeamId(team)));
		return PLAYERS_VIEW;
	}
	
	@GetMapping("players/{id}")
	public String player(@PathVariable String id, Model model) {
		model.addAttribute("player", playerService.getOne(id));
		model.addAttribute("playerStatistics", 
				playerStatisticService.getSummedPlayerStatsOfPlayer(id));
		model.addAttribute("goalkeeperStatistics", 
				playerStatisticService.getSummedGoalkeeperStatsOfPlayer(id));
		return PLAYER_VIEW;
	}
	
	@GetMapping("statistics")
	public String statistics(@PathVariable String team, Model model) {
		String teamId = currentTeamId(team);
		model.addAttribute("playerStatistics", 
				playerStatisticService.getSummedPlayerStatsOfCurrentSeasonPart(teamId));
		model.addAttribute("goalkeeperStatistics", 
				playerStatisticService.getSummedGoalkeeperStatsOfCurrentSeasonPart(teamId));
		return STATISTICS_VIEW;
	}
	
	@ModelAttribute
	public void addModelAttributes(@PathVariable String team, Model model) {
		model.addAttribute("team", team);
	}
	
	private String currentTeamId(String team) {
		switch (team) {
		case "a-team": 
			return Team.FCB_A;
		case "b-team": 
			return Team.FCB_B;
		case "futsal":
			return Team.FCB_F;
		}
		throw new IllegalArgumentException("Value of 'team' parameter must be one of [a-team, b-team, futsal].");
	}

}
