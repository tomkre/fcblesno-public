package cz.tomek.fcblesno.model.enums;

import lombok.Getter;

/**
 * Season part.
 * 
 * @author tomek
 *
 */
@Getter
public enum SeasonPart {
	
	AUTUMN(9, 12),
	
	SPRING(0, 8);
	
	private int startMonth;
	
	private int endMonth;
	
	private SeasonPart(int startMonth, int endMonth) {
		this.startMonth = startMonth;
		this.endMonth = endMonth;
	}

}
