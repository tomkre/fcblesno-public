package cz.tomek.fcblesno.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import cz.tomek.fcblesno.model.Article;
import cz.tomek.fcblesno.model.Game;
import cz.tomek.fcblesno.model.PlayerStatistic;
import cz.tomek.fcblesno.service.LeagueService;
import cz.tomek.fcblesno.service.LocationService;
import cz.tomek.fcblesno.service.PlayerService;
import cz.tomek.fcblesno.service.TeamService;

/**
 * Game controller.
 * 
 * @author tomek
 *
 */
@Controller
@RequestMapping("admin/{contextUrl:games}")
public class GameController extends AppEntityController<Game> {

	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private PlayerService playerService;
	
	public GameController() {
		super(Game.class);
	}
	
	@Override
	protected void addFormModelAttributes(Model model) {
		model.addAttribute("leagues", leagueService.getAll());
		model.addAttribute("locations", locationService.getAll());
		model.addAttribute("teams", teamService.getAll());
		model.addAttribute("players", playerService.getAll());
	}
	
	@Override
	protected void preSave(Game game) {
		setDefaultArticleTitleIfNotSet(game);
		setMissingGoalkeeperStatFields(game);
	}

	private void setDefaultArticleTitleIfNotSet(Game game) {
		Article article = game.getArticle();
		if (article.getTitle() == null || article.getTitle().isEmpty()) {
			String defaultArticleTitle = 
					String.format("%s : %s  %d : %d", 
							teamService.getOne(game.getTeamHome().getId()).getName(), 
							teamService.getOne(game.getTeamGuest().getId()).getName(), 
							game.getGoalsHome(), game.getGoalsGuest());
			article.setTitle(defaultArticleTitle);
		}
	}
	
	private void setMissingGoalkeeperStatFields(Game game) {
		PlayerStatistic goalkeeperStat = 
				game.getPlayersStatistics().stream().filter(PlayerStatistic::isGoalkeeper).findFirst().orElse(null);
		if (goalkeeperStat != null) {
			int receivedGoals = game.getRivalGoals();
			goalkeeperStat.setReceivedGoals(receivedGoals);
			if (receivedGoals == 0) {
				goalkeeperStat.setShotouts(1);
			}
		}
	}
	
	@Override
	protected boolean processPhoto(MultipartFile file, String contextUrl, String entityId) {
		return super.processPhoto(file, "articles", "a-" + entityId);
	}

}
