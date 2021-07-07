package cz.tomek.fcblesno.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
* Location.
*
* @author tomek
*
*/
@Getter
@Setter
@Entity
@Table(name = "location")
public class Location extends AppEntity {
	
	@NotBlank
	@Column(name = "name")
	private String name;
	
	@Override
	public String getDisplayName() {
		return name;
	}

}
