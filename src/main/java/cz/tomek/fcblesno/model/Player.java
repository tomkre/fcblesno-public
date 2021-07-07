package cz.tomek.fcblesno.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import cz.tomek.fcblesno.model.enums.Post;
import cz.tomek.fcblesno.model.jpalistener.IdGeneratorListener;
import cz.tomek.fcblesno.model.marking.ContainsPhoto;
import cz.tomek.fcblesno.model.marking.IdGeneratable;
import cz.tomek.fcblesno.util.DateService;
import lombok.Getter;
import lombok.Setter;

/**
* Player.
*
* @author tomek
*
*/
@Getter
@Setter
@Entity
@Table(name = "player")
@EntityListeners(IdGeneratorListener.class)
public class Player extends AppEntity implements Comparable<Player>, IdGeneratable, ContainsPhoto {
	
	@NotBlank
	@Column(name = "first_name")
	private String firstName;
	
	@NotBlank
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = DateService.DATE_PATTERN)
	private Date birthDate;
	
	@Column(name = "post")
	@Enumerated(EnumType.STRING)
	private Post post;
	
	@ManyToMany
	@JoinTable(
		name = "team_player",
		joinColumns = @JoinColumn(name = "player_id"),
		inverseJoinColumns = @JoinColumn(name = "team_id"))
	private List<Team> teams;
	
	@OneToMany(mappedBy = "player")
	private List<PlayerStatistic> playerStatistics = new ArrayList<>();
	
	/**
	 * Gets full name of the player.
	 * 
	 * @return full name of the player
	 */
	public String getFullName() {
		return String.format("%s %s", lastName, firstName);
	}
	
	/**
	 * Checks whether the player is active.
	 * 
	 * @return flag whether the player is active
	 */
	public boolean isActive() {
		return post != Post.LEGEND;
	}
	
	@Override
	public int compareTo(Player o) {
		return getFullName().compareTo(o.getFullName());
	}
	
	@Override
	public String getDisplayName() {
		return getFullName();
	}

	@Override
	public String generateId() {
		return lastName.toLowerCase().substring(0, 3) + firstName.toLowerCase().substring(0, 3);
	}
	
}
