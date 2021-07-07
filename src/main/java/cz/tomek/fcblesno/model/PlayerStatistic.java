package cz.tomek.fcblesno.model;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cz.tomek.fcblesno.model.enums.Post;
import lombok.Getter;
import lombok.Setter;

/**
* Player Statistic.
*
* @author tomek
*
*/
@Getter
@Setter
@Entity
@Table(name = "player_statistic")
public class PlayerStatistic extends AppEntity implements Comparable<PlayerStatistic>, Cloneable {
	
	public static final Comparator<PlayerStatistic> POST_ORDER =
			(ps1, ps2) -> {
				int compareByPost = ps1.getPost().compareTo(ps2.getPost());
				return compareByPost != 0 ? compareByPost : ps1.compareTo(ps2);
			};
			
	public static final Comparator<PlayerStatistic> GOALS_ORDER =
			(ps1, ps2) -> {
				int compareByGoals = ps2.getGoals() - ps1.getGoals();
				return compareByGoals != 0 ? compareByGoals : ps1.compareTo(ps2);
			};
			
	public static final Comparator<PlayerStatistic> YELLOW_CARDS_ORDER =
			(ps1, ps2) -> {
				int compareByYellowCards = ps2.getYellowCards() - ps1.getYellowCards();
				return compareByYellowCards != 0 ? compareByYellowCards : ps1.compareTo(ps2);
			};

	public static final Comparator<PlayerStatistic> LEAGUE_ORDER =
			(ps1, ps2) -> ps1.getGame().getLeague().compareTo(ps2.getGame().getLeague());

	@Column(name = "goals")
	private int goals;
	
	@Column(name = "assists")
	private int assists;
	
	@Column(name = "plus_minus", columnDefinition = "integer default 0")
	private int plusMinus;
	
	@Column(name = "yellow_cards", columnDefinition = "integer default 0")
	private int yellowCards;
	
	@Column(name = "red_cards", columnDefinition = "integer default 0")
	private int redCards;
	
	@Column(name =  "games", columnDefinition = "integer default 1")
	private int numOfGames;
	
	@Column(name = "shotouts", columnDefinition = "integer default 0")
	private int shotouts;
	
	@Column(name = "received_goals", columnDefinition = "integer default 0")
	private int receivedGoals;
	
	@Column(name = "post")
	@Enumerated(EnumType.STRING)
	private Post post;
	
	@ManyToOne
	@JoinColumn(name = "game_id")
	private Game game;
	
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;
	
	/**
	 * Checks whether current statistic is related to goalkeeper.
	 * 
	 * @return true, if statistic is related to goalkeeper, false otherwise
	 */
	public boolean isGoalkeeper() {
		return post == Post.GOALKEEPER;
	}
	
	/**
	 * Checks whether current statistic is related to player in field.
	 * 
	 * @return true, if statistic is related to player in field, false otherwise
	 */
	public boolean isPlayerInField() {
		return post == Post.DEFENDER || post == Post.ATTACKER;
	}
	
	/**
	 * Gets received goals per game.
	 * 
	 * @return received goals per game
	 */
	public double getReceivedGoalsPerGame() {
		if (numOfGames == 0) {
			numOfGames = 1;
		}
		return Double.valueOf(String.format("%.2f", (double) receivedGoals / numOfGames));
	}
	
	/**
	 * Merges two statistics into one.
	 * 
	 * @param ps1
	 * @param ps2
	 * @return merged statistic
	 */
	public static PlayerStatistic merge(PlayerStatistic ps1, PlayerStatistic ps2) {
		PlayerStatistic ps = new PlayerStatistic();
		ps.setGame(ps1.getGame());
		ps.setPlayer(ps1.getPlayer());
		ps.setNumOfGames(ps1.getNumOfGames() + ps2.getNumOfGames());
		ps.setGoals(ps1.getGoals() + ps2.getGoals());
		ps.setAssists(ps1.getAssists() + ps2.getAssists());
		ps.setPlusMinus(ps1.getPlusMinus() + ps2.getPlusMinus());
		ps.setYellowCards(ps1.getYellowCards() + ps2.getYellowCards());
		ps.setRedCards(ps1.getRedCards() + ps2.getRedCards());
		ps.setShotouts(ps1.getShotouts() + ps2.getShotouts());
		ps.setReceivedGoals(ps1.getReceivedGoals() + ps2.getReceivedGoals());
		return ps;
	}
	
	@Override
	public int compareTo(PlayerStatistic anotherPs) {
		return player.getFullName().compareTo(anotherPs.getPlayer().getFullName());
	}
	
	@Override
	public String getDisplayName() {
		return id;
	}
	
	@Override
	public PlayerStatistic clone() {
		try {
			return (PlayerStatistic) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
}
