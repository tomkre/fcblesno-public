import { updateStatsTable } from './view.js';
import { PlayerStat } from './model.js';

function loadPosts() {
	$.get('/admin/rest/posts', function(data) {
		fcb.posts = data;
	});
}

function loadPlayers() {
	$.get('/admin/rest/players', function(data) {
		fcb.allPlayers = data;
		loadStatistics();
	});
}

function loadStatistics() {
	$.get('/admin/rest/playerStatistics?gameId=' + fcb.gameId, function(data) {
		fcb.gameStats = data;
		fcb.gamePlayers = fcb.gameStats.map(s => s.player);
		fcb.nonGamePlayers = fcb.allPlayers.filter(p => fcb.gamePlayers.findIndex(gp => gp.id == p.id) < 0);
		updateStatsTable();
	});
};

export { loadPosts, loadPlayers };