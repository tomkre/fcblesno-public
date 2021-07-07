package cz.tomek.fcblesno.service.impl;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.tomek.fcblesno.model.Player;
import cz.tomek.fcblesno.model.PlayerStatistic;
import cz.tomek.fcblesno.model.enums.Post;
import cz.tomek.fcblesno.repository.PlayerRepository;
import cz.tomek.fcblesno.repository.PlayerStatisticRepository;
import cz.tomek.fcblesno.service.PlayerService;

/**
* Default implementation of {@link PlayerService}.
*
* @author tomek
*
*/
@Service("playerService") // named in order to be available in thymeleaf templates
@Transactional(readOnly = true)
public class JpaPlayerService extends JpaAppEntityService<Player> implements PlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerStatisticRepository playerStatisticRepository;
	
	@Override
	public List<Player> getByTeamId(String teamId) {
		return playerRepository.findByTeamsIdOrderByPostDescLastNameAsc(teamId);
	}

	@Override
	public Map<Post, List<Player>> getByTeamIdGroupedByPost(String teamId) {
		Map<Post, List<Player>> playersByPost =
				getByTeamId(teamId).stream()
					.filter(Player::isActive)
					.collect(groupingBy(Player::getPost, TreeMap::new, toList()));
		return playersByPost;
	}

	@Override
	public Map<Post, List<Player>> extractFromPlayersStatistics(List<PlayerStatistic> playersStatistics) {
		return playersStatistics
					.stream()
					.peek(ps -> ps.getPlayer().setPost(ps.getPost()))
					.map(ps -> ps.getPlayer())
					.sorted()
					.collect(groupingBy(Player::getPost, TreeMap::new, toList()));
	}
	
	@Override
	public List<Player> getByPost(Post post) {
		return playerRepository.findByPostOrderByLastNameAsc(post);
	}
	
	@Override
	public boolean isDeletionPermitted(String id) {
		return playerStatisticRepository.findByPlayerId(id).isEmpty();
	}

}
