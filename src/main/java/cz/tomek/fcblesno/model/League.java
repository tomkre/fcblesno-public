package cz.tomek.fcblesno.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import cz.tomek.fcblesno.model.jpalistener.IdGeneratorListener;
import cz.tomek.fcblesno.model.marking.IdGeneratable;
import cz.tomek.fcblesno.service.impl.JpaLeagueService;
import cz.tomek.fcblesno.util.DateService;
import cz.tomek.fcblesno.util.MessageService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
* League.
*
* @author tomek
*
*/
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "league")
@EntityListeners(IdGeneratorListener.class)
public class League extends AppEntity implements Comparable<League>, IdGeneratable {
	
	public static final League A_TEAM_TOTAL = new League() { 
		public String getNameWithTeam() { 
			return "Celkem A-tým"; 
		} 
	};
	
	public static final League B_TEAM_TOTAL = new League() { 
		public String getNameWithTeam() { 
			return "Celkem B-tým"; 
		} 
	};
	
	public static final League F_TEAM_TOTAL = new League() { 
		public String getNameWithTeam() { 
			return "Celkem Futsal"; 
		} 
	};
	
	public static final League TOTAL = new League() { 
		public String getNameWithTeam() { 
			return "Celkem"; 
		} 
	};
	
	@NotBlank
	@Column(name = "league_name")
	private String leagueName;
	
	@Column(name = "season_start")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = DateService.DATE_PATTERN)
	private Date seasonStart;

	@Column(name = "season_end")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = DateService.DATE_PATTERN)
	private Date seasonEnd;
	
	public String getNameWithTeam() {
		return getName(true);
	}
	
	/**
	 * Return formatted name including league name and season.
	 * 
	 * @return formatted name
	 */
	public String getName() {
		return getName(false);
	}
	
	/**
	 * Return formatted name including either league name or internal team name.
	 * 
	 * @param includesTeam
	 * @return formatted name including league name or internal team name
	 */
	public String getName(boolean includesTeam) {
		String contextName = null;
		if (includesTeam) {
			String teamId = JpaLeagueService.LEAGUE_TEAM_MAP.get(this);
			contextName = MessageService.getMessage("team." + teamId);
		} else {
			contextName = leagueName;
		}
		DateFormat yearDateFormat = new SimpleDateFormat("yyyy");
		String formattedSeasonStart = yearDateFormat.format(seasonStart);
		String formattedSeasonEnd = yearDateFormat.format(seasonEnd);
		return String.format("%s %s/%s", contextName, formattedSeasonStart, formattedSeasonEnd);
	}
	
	@Override
	public int compareTo(League anotherLeague) {
		String year = id.substring(2);
		String anotherLeagueYear = anotherLeague.getId().substring(2);
		int result = anotherLeagueYear.compareTo(year);
		if (result == 0) {
			String leagueCode = id.substring(0, 2);
			String anotherLeagueCode = anotherLeague.getId().substring(0, 2);
			if (Character.isDigit(leagueCode.charAt(0))) {
				leagueCode = "z" + leagueCode;
			}
			if (Character.isDigit(anotherLeagueCode.charAt(0))) {
				anotherLeagueCode = "z" + anotherLeagueCode;
			}
			result = leagueCode.compareTo(anotherLeagueCode);
		}
		return result;
	}
	
	@Override
	public String getDisplayName() {
		return getName();
	}

	@Override
	public String generateId() {
		StringBuilder idBuilder = new StringBuilder();
		if (Character.isDigit(leagueName.charAt(0)) || leagueName.trim().matches("\\S+")) {
			idBuilder.append(leagueName.substring(0, 2));
		} else {
			String[] nameParts = leagueName.split(" ");
			idBuilder.append(nameParts[0].charAt(0)).append(nameParts[1].charAt(0));
		}
		idBuilder.append(DateService.formatSeason(seasonStart, seasonEnd));
		return idBuilder.toString().toLowerCase();
	}

}
