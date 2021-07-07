package cz.tomek.fcblesno.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import cz.tomek.fcblesno.model.Team;

/**
 * Date service.
 * 
 * @author tomek
 *
 */
@Service
public class DateService {

	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final String TIMESTAMP_PATTERN = DATE_PATTERN + " HH:mm";
	
	private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(TIMESTAMP_PATTERN);

	private static final DateFormat YEAR_SHORT_FORMAT = new SimpleDateFormat("yy");
	
	/**
	 * Parses <code>dateAsText</code> to date.
	 * 
	 * @param dateAsText
	 * @return parsed date
	 */
	public static Date parse(String dateAsText) {
		try {
			return TIMESTAMP_FORMAT.parse(dateAsText);
		} catch (ParseException e) {
			String errorMsg = String.format("Error has occured during parsing date from string[value=%s]: %s", dateAsText, e);
			throw new RuntimeException(errorMsg, e);
		}
	}
	
	/**
	 * Formats <code>date</code> to text.
	 * 
	 * @param dateAsText
	 * @return formatted date
	 */
	public static String format(Date date) {
		return TIMESTAMP_FORMAT.format(date);
	}
	
	/**
	 * Gets current time stamp.
	 * 
	 * @return current time stamp
	 */
	public Date now() {
		return new Date();
	}
	
	/**
	 * Gets season start.
	 * 
	 * @param referenceDate
	 * @param futsal
	 * @return season start
	 */
	public Date getSeasonStart(Date referenceDate, boolean futsal) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(referenceDate);
		int thresholdMonth = seasonDividerMonth(futsal);
		if (cal.get(Calendar.MONTH) < thresholdMonth) {
			cal.add(Calendar.YEAR, -1);
		}
		cal.set(Calendar.MONTH, thresholdMonth);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		cal.set(Calendar.HOUR, 0);
		return cal.getTime();
	}
	
	/**
	 * Gets season end.
	 * 
	 * @param referenceDate
	 * @param futsal
	 * @return season end
	 */
	public Date getSeasonEnd(Date referenceDate, boolean futsal) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(referenceDate);
		if (cal.get(Calendar.MONTH) >= seasonDividerMonth(futsal)) {
			cal.add(Calendar.YEAR, 1);
		}
		cal.set(Calendar.MONTH, futsal ? Calendar.MARCH : Calendar.JUNE);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.MONTH));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		return cal.getTime();
	}
	
	private int seasonDividerMonth(boolean isFutsal) {
		return Calendar.SEPTEMBER; // isFutsal ? Calendar.NOVEMBER : Calendar.SEPTEMBER;
	}

	
	/**
	 * Gets season part start.
	 * 
	 * @param seasonReferenceDate
	 * @return season part start
	 */
	public Date getSeasonPartStart(Date seasonReferenceDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(seasonReferenceDate);
		int month = cal.get(Calendar.MONTH);
		if (month > Calendar.MARCH && month < Calendar.SEPTEMBER) {
			cal.set(Calendar.MONTH, Calendar.APRIL);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		} else {
			if (month < Calendar.APRIL) {
				cal.add(Calendar.YEAR, -1);
			}
			cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		cal.set(Calendar.HOUR_OF_DAY, 0);
		return cal.getTime();
	}
	
	/**
	 * Gets season part end.
	 * 
	 * @param seasonReferenceDate
	 * @return season part end
	 */
	public Date getSeasonPartEnd(Date seasonReferenceDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(seasonReferenceDate);
		int month = cal.get(Calendar.MONTH);
		if (month > Calendar.MARCH && month < Calendar.SEPTEMBER) {
			cal.set(Calendar.MONTH, Calendar.JUNE);
			cal.set(Calendar.DAY_OF_MONTH, 30);
		} else {
			if (month > Calendar.AUGUST) {
				cal.add(Calendar.YEAR, 1);
			}
			cal.set(Calendar.MONTH, Calendar.MARCH);
			cal.set(Calendar.DAY_OF_MONTH, 31);
		}
		cal.set(Calendar.HOUR_OF_DAY, 23);
		return cal.getTime();
	}
	
	/**
	 * Gets current season start.
	 * 
	 * @param teamId
	 * @return current season start
	 */
	public Date getCurrentSeasonStart(String teamId) {
		return getSeasonStart(now(), Team.FCB_F.equals(teamId));
	}
	
	/**
	 * Gets current season start.
	 * 
	 * @param teamId
	 * @return current season end
	 */
	public Date getCurrentSeasonEnd(String teamId) {
		return getSeasonEnd(now(), Team.FCB_F.equals(teamId));
	}
	
	/**
	 * Gets current season part start.
	 * 
	 * @return current season part start
	 */
	public Date getCurrentSeasonPartStart() {
		return getSeasonPartStart(now());
	}
	
	/**
	 * Gets current season part end.
	 * 
	 * @return current season part end
	 */
	public Date getCurrentSeasonPartEnd() {
		return getSeasonPartEnd(now());
	}
	
	/**
	 * Works like {@link DateService#formatSeason(Date, Date, String)} with <code>sep</code> equals to empty string.
	 * 
	 * @see {@link DateService#formatSeason(Date, Date, String)}
	 */
	public static String formatSeason(Date seasonStart, Date seasonEnd) {
		return formatSeason(seasonStart, seasonEnd, "");
	}
	
	/**
	 * Formats season start and season end to readable representation of season.
	 * 
	 * @param seasonStart
	 * @param seasonEnd
	 * @param sep
	 * @return readable representation of season
	 */
	public static String formatSeason(Date seasonStart, Date seasonEnd, String sep) {
		return String.format("%s%s%s", YEAR_SHORT_FORMAT.format(seasonStart), sep, YEAR_SHORT_FORMAT.format(seasonEnd));
	}
	
}
