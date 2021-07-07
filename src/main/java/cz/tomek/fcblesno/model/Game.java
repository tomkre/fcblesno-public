package cz.tomek.fcblesno.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import cz.tomek.fcblesno.model.marking.ContainsPhoto;
import cz.tomek.fcblesno.model.marking.IdGeneratable;
import cz.tomek.fcblesno.util.DateService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* Match.
*
* @author tomek
*
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "game")
public class Game extends AppEntity implements IdGeneratable, ContainsPhoto {
	
	@Column(name = "game_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = DateService.TIMESTAMP_PATTERN)
	private Date gameDate;
	
	@ManyToOne
	@JoinColumn(name = "league_id")
	private League league;
	
	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;
	
	@Column(name = "fans")
	private int fans;
	
	@ManyToOne
	@JoinColumn(name = "team_home_id")
	private Team teamHome;
	
	@ManyToOne
	@JoinColumn(name = "team_guest_id")
	private Team teamGuest;
	
	@Column(name = "goals_home")
	private int goalsHome;
	
	@Column(name = "goals_guest")
	private int goalsGuest;
	
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PlayerStatistic> playersStatistics = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "article_id")
	private Article article;
	
	@Override
	public String getDisplayName() {
		return String.format("%s : %s", teamHome.getName(), teamGuest.getName());
	}

	@Override
	public String generateId() {
		throw new UnsupportedOperationException("ID for Game entity should be generated at frontend!");
	}
	
	/**
	 * Convenient method for retrieving rival goals.
	 * 
	 * @return rival goals
	 */
	public int getRivalGoals() {
		return teamHome.isFcb() ? goalsGuest : goalsHome; 
	}
	
	/**
	 * Convenient method for retrieving team representing FC Blesno.
	 * 
	 * @return fcb team
	 */
	public Team getTeamFcb() {
		return teamHome.isFcb() ? teamHome : teamGuest;
	}

}
