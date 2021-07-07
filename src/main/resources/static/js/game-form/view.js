import { registerDeletePlayerStatEvents, registerChangeStatEvents } from './events.js';
import { PlayerStat } from './model.js';

function updateStatsTable() {
	let $tableData = $('.stat-field tbody');
	$tableData.empty();
	let fields = ['goals', 'assists', 'plusMinus', 'yellowCards', 'redCards'];
	let index = 0;
	let row = '';
	fcb.gameStats.forEach(stat => {
		row = `<tr>
			  	   <td>
			  	   	<span>${stat.player.fullName}</span>
			  	   	<input id="playersStatistics${index}.id" 
		  	 	         name="playersStatistics[${index}].id" 
		  	 	         value="${stat.id}"
		  	 	         type="hidden">
				  	<input id="playersStatistics${index}.game.id" 
				  		name="playersStatistics[${index}].game.id" 
				  		value="${fcb.gameId}"
				  		type="hidden">
				  	<input id="playersStatistics${index}.player.id" 
				  		name="playersStatistics[${index}].player.id" 
				  		value="${stat.player.id}"
				  		type="hidden">
					<input id="playersStatistics${index}.numOfGames" 
						name="playersStatistics[${index}].numOfGames" 
						value="1"
						type="hidden">
			  	   </td>`;
		row += `<td>
					<select id="playersStatistics${index}.post"
						name="playersStatistics[${index}].post"
						value="${stat.post}">`;
		for (let post in fcb.posts) {
			row += `<option value="${post}" ${stat.post == post ? 'selected' : ''}>${fcb.posts[post]}</option>`;
		}
		row += `</select>
			</td>`;
		fields.forEach(field => {
			row += `<td>
						<input id="playersStatistics${index}.${field}"
						       name="playersStatistics[${index}].${field}"
						       value="${stat[field]}"
						       min="${field != 'plusMinus' ? 0 : 'unbounded'}"
						       type="number">
					</td>`;
		});
		row += `<td>
					<button type="button" class="delete-stat delete-stat-${stat.id}">${fcb.msg['link.delete']}</button>
				</td>
			</tr>`;
		$tableData.append(row);
		index++;
	});
	registerDeletePlayerStatEvents();
	registerChangeStatEvents();
	updatePlayersSelection();
}

function updatePlayersSelection() {
	let $playersSelection = $('.players-selection')
	$playersSelection.empty();
	let options = '';
	fcb.nonGamePlayers.sort((p1, p2) => p1.id < p2.id ? -1 : 1);
	fcb.nonGamePlayers.forEach(player => {
		options += `<option value="${player.id}">${player.fullName}</option>`;
	});
	$playersSelection.append(options);
}

function addPlayerStatistic(playerId) {
	let player = fcb.nonGamePlayers.find(p => p.id == playerId);
	fcb.nonGamePlayers = fcb.nonGamePlayers.filter(p => p.id != playerId);
	fcb.gamePlayers.push(player);
	fcb.gameStats.push(new PlayerStat(player));
	updateStatsTable();
}

function removePlayerStatistic(statId) {
	let player = fcb.gameStats.find(ps => ps.id == statId).player;
	fcb.nonGamePlayers.push(player);
	fcb.nonGamePlayers.sort((p1, p2) => p1.fullName < p2.fullName);
	fcb.gamePlayers = fcb.gamePlayers.filter(p => p.id != player.id);
	fcb.gameStats = fcb.gameStats.filter(ps => ps.id != statId);
	updateStatsTable();
}

function updatePlayerStatistics() {
	fcb.gameStats.forEach(ps => ps.id = ps.id.replace(/\w{6}-\w{4}-\w{4}/, fcb.gameId));
	updateStatsTable();
}

export { updateStatsTable, addPlayerStatistic, removePlayerStatistic, updatePlayerStatistics };