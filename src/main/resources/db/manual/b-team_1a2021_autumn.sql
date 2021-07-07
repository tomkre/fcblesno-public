begin;

insert into league(id, league_name, season_start, season_end) values
	('1a2021', '1A. třída', '2020-09-01', '2021-06-30');
	
insert into team(id, name) values
	('gnka', 'Gank');

insert into game(id, game_date, league_id, location_id, fans, team_home_id, team_guest_id, goals_home, goals_guest) values
	('1a2021-fcbb-cana', '2020-09-06 10:00', '1a2021', 'tpo', 0, 'fcbb', 'cana', 0, 0),
	('1a2021-fcbb-frdb', '2020-09-13 08:00', '1a2021', 'tpo', 0, 'fcbb', 'frdb', 0, 0),
	('1a2021-cama-fcbb', '2020-09-19 09:00', '1a2021', 'oly', 0, 'cama', 'fcbb', 0, 0),
	('1a2021-fcbb-repa', '2020-09-26 08:00', '1a2021', 'tpo', 0, 'fcbb', 'repa', 0, 0),
	('1a2021-bufa-fcbb', '2020-10-04 10:00', '1a2021', 'sev', 0, 'bufa', 'fcbb', 0, 0),
	('1a2021-hroa-fcbb', '2020-10-10 11:00', '1a2021', 'oly', 0, 'hroa', 'fcbb', 0, 0),
	('1a2021-fcbb-pena', '2020-10-11 08:00', '1a2021', 'tpo', 0, 'fcbb', 'pena', 0, 0),
	('1a2021-gnka-fcbb', '2020-10-17 09:00', '1a2021', 'oly', 0, 'gnka', 'fcbb', 0, 0),
	('1a2021-fcbb-poha', '2020-10-18 08:00', '1a2021', 'tpo', 0, 'fcbb', 'poha', 0, 0),
	('1a2021-koja-fcbb', '2020-10-24 08:00', '1a2021', 'oly', 0, 'koja', 'fcbb', 0, 0),
	('1a2021-fcbb-legb', '2020-10-31 09:00', '1a2021', 'tpo', 0, 'fcbb', 'legb', 0, 0),
	('1a2021-vesa-fcbb', '2020-11-07 08:00', '1a2021', 'oly', 0, 'vesa', 'fcbb', 0, 0),
	('1a2021-bfkc-fcbb', '2020-11-14 10:00', '1a2021', 'goc', 0, 'bfkc', 'fcbb', 0, 0);
	
commit;