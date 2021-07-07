package cz.tomek.fcblesno.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.tomek.fcblesno.model.Team;
import cz.tomek.fcblesno.repository.GameRepository;
import cz.tomek.fcblesno.service.TeamService;

/**
* Default implementation of {@link TeamService}.
*
* @author tomek
*
*/
@Service
public class JpaTeamService extends JpaAppEntityService<Team> implements TeamService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Override
	public boolean isDeletionPermitted(String id) {
		return gameRepository.findByTeamId(id, FIRST_RECORD).isEmpty();
	}

}
