package cz.tomek.fcblesno.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
* Team.
*
* @author tomek
*
*/
@Getter
@Setter
@Entity
@Table(name = "team")
public class Team extends AppEntity {
	
	public static final String FCB_A = "fcba";

	public static final String FCB_B = "fcbb";
	
	public static final String FCB_F = "fcbf";
	
	public static final List<String> FCB_TEAMS = Arrays.asList(FCB_A, FCB_B, FCB_F);
	
	@NotBlank
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "teamHome")
	private List<Game> gamesHome;

	@OneToMany(mappedBy = "teamGuest")
	private List<Game> gamesGuest;
	
	@ManyToMany(mappedBy = "teams")
	private List<Player> players;
	
	@Override
	public String getDisplayName() {
		return name;
	}
	
	/**
	 * Checks whether current team is FCB or not.
	 * 
	 * @return true if this team is FCB, false otherwise
	 */
	public boolean isFcb() {
		return id.startsWith("fcb");
	}
	
	/**
	 * Gets context path associated with this team if this team
	 * relates to FC Blesno club.
	 * 
	 * @return context path of FC Blesno team
	 */
	public String getContextPath() {
		switch (id) {
		case FCB_A: return  "a-team";
		case FCB_B: return  "b-team";
		case FCB_F: return  "futsal";
		}
		throw new UnsupportedOperationException("Team " + name + " is not part of FC Blesno club!");
	}

}
