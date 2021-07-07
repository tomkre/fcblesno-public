package cz.tomek.fcblesno.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import cz.tomek.fcblesno.model.Game;
import cz.tomek.fcblesno.model.Team;
import cz.tomek.fcblesno.service.GameService;

/**
 * App controller advice.
 * 
 * @author tomek
 *
 */
@ControllerAdvice
public class AppControllerAdvice {

	@Autowired
	private GameService gameService;
	
	@ModelAttribute
	public void addModelAttributes(Model model) {
		List<Game> lastGames = new ArrayList<>();
		List<Game> upcomingGames = new ArrayList<>();
		lastGames.add(gameService.getLastOfTeam(Team.FCB_A));
		lastGames.add(gameService.getLastOfTeam(Team.FCB_B));
		upcomingGames.add(gameService.getUpcomingOfTeam(Team.FCB_A));
		upcomingGames.add(gameService.getUpcomingOfTeam(Team.FCB_B));
		/*
		lastGames = gameService.getLastOfTeam(Team.FCB_F, 2);
		Collections.reverse(lastGames);
		upcomingGames = gameService.getUpcomingOfTeam(Team.FCB_F, 2);
		while (upcomingGames.size() < 2) {
			upcomingGames.add(null);
		}
		 */
		model.addAttribute("lastGames", lastGames);
		model.addAttribute("upcomingGames", upcomingGames);
	}
	
}
