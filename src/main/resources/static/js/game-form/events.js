import { addPlayerStatistic, removePlayerStatistic, updatePlayerStatistics } from './view.js';

function registerEvents() {
	registerAddPlayerStatEvent();
	registerChangeIdEvent();
	registerChangeGameDateEvent();
}

function registerAddPlayerStatEvent() {
	$('.add-stat').click(function() {
		let playerId = $('.players-selection').val();
		addPlayerStatistic(playerId);
	});
}

function registerDeletePlayerStatEvents() {
	$('.delete-stat').click(function() {
		let statId = $(this).attr('class').split(' ')[1].replace('delete-stat-', '');
		removePlayerStatistic(statId);
	});
}

function registerChangeIdEvent() {
	let fields = ['league', 'teamHome', 'teamGuest'];
	for (let [index, field] of fields.entries()) {
		$(`select[id="${field}.id"]`).change(function() {
			updateGameId(index, $(this).val());
		});
	}
}

function updateGameId(pos, replacement) {
	let gameIdParts = fcb.gameId.split('-');
	gameIdParts[pos] = replacement;
	fcb.gameId = `${gameIdParts[0]}-${gameIdParts[1]}-${gameIdParts[2]}`;
	$('#id').val(fcb.gameId);
	$('#article\\.id').val(`a-${fcb.gameId}`);
	updatePlayerStatistics();
}

function registerChangeStatEvents() {
	$('.stat-field tbody').find('input[type="number"], select').change(function() {
		let [rowId, fieldName] = $(this).attr('id').split('.');
		let statId = $(this).closest('tr').find(`#${rowId}\\.id`).val();
		fcb.gameStats.find(ps => ps.id == statId)[fieldName] = $(this).val();
	});
}

function registerChangeGameDateEvent() {
	let $gameDateField = $('#gameDate');
	let [ date, time ] = $gameDateField.val().split(' ');
	$('#gameDate-date').val(date).change(function() {
		date = $(this).val();
		$gameDateField.val(`${date} ${time}`);
	});
	$('#gameDate-time').val(time).change(function() {
		time = $(this).val();
		$gameDateField.val(`${date} ${time}`);
	});
}

export { registerEvents, registerDeletePlayerStatEvents, registerChangeStatEvents };