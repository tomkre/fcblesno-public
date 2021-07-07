function PlayerStat(player) {
	this.id = `${fcb.gameId}-${player.id}`;
	this.goals = 0;
	this.assists = 0;
	this.plusMinus = 0;
	this.yellowCards = 0;
	this.redCards = 0;
	this.numOfGames = 1;
	this.post = player.post;
	this.game = null;
	this.player = player;
	this.game = { id: fcb.gameId };
}

export { PlayerStat };