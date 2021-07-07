import { loadPosts, loadPlayers } from './http-service.js';
import { registerEvents } from './events.js';
import { registerComponents } from './components.js';

$(document).ready(function() {
	loadPosts();
	loadPlayers();
	registerEvents();
	registerComponents();
});
