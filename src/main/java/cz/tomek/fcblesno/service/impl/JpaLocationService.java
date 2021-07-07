package cz.tomek.fcblesno.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.tomek.fcblesno.model.Location;
import cz.tomek.fcblesno.repository.GameRepository;
import cz.tomek.fcblesno.service.LocationService;

/**
* Default implementation of {@link LocationService}.
*
* @author tomek
*
*/
@Service
public class JpaLocationService extends JpaAppEntityService<Location> implements LocationService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Override
	public boolean isDeletionPermitted(String id) {
		return gameRepository.findByLocationId(id).isEmpty();
	}

}
