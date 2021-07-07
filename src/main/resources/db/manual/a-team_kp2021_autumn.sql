begin;

insert into league(id, league_name, season_start, season_end) values
	('kp2021', 'Krajský přebor', '2020-09-01', '2021-06-30');
	
insert into team(id, name) values
	('rasa', 'Rasošky');

insert into game(id, game_date, league_id, location_id, fans, team_home_id, team_guest_id, goals_home, goals_guest) values
	('kp2021-fcba-sena', '2020-09-06 09:00', 'kp2021', 'tpo', 0, 'fcba', 'sena', 0, 0),
	('kp2021-piva-fcba', '2020-09-12 13:00', 'kp2021', 'sev', 0, 'piva', 'fcba', 0, 0),
	('kp2021-fcba-lega', '2020-09-13 09:00', 'kp2021', 'tpo', 0, 'fcba', 'lega', 0, 0),
	('kp2021-nkla-fcba', '2020-09-20 09:00', 'kp2021', 'sev', 0, 'nkla', 'fcba', 0, 0),
	('kp2021-fcba-joka', '2020-09-26 09:00', 'kp2021', 'tpo', 0, 'fcba', 'joka', 0, 0),
	('kp2021-czma-fcba', '2020-09-27 08:00', 'kp2021', 'sev', 0, 'czma', 'fcba', 0, 0),
	('kp2021-fcba-nkga', '2020-10-03 09:00', 'kp2021', 'tpo', 0, 'fcba', 'nkga', 0, 0),
	('kp2021-infa-fcba', '2020-10-10 09:00', 'kp2021', 'sev', 0, 'infa', 'fcba', 0, 0),
	('kp2021-fcba-lgna', '2020-10-11 09:00', 'kp2021', 'tpo', 0, 'fcba', 'lgna', 0, 0),
	('kp2021-flmb-fcba', '2020-10-24 08:00', 'kp2021', 'sev', 0, 'flmb', 'fcba', 0, 0),
	('kp2021-fcba-giga', '2020-10-25 09:00', 'kp2021', 'tpo', 0, 'fcba', 'giga', 0, 0),
	('kp2021-snoa-fcba', '2020-10-28 09:00', 'kp2021', 'goc', 0, 'snoa', 'fcba', 0, 0),
	('kp2021-fcba-rasa', '2020-11-07 10:00', 'kp2021', 'tpo', 0, 'fcba', 'rasa', 0, 0),
	('kp2021-blaa-fcba', '2020-11-08 09:00', 'kp2021', 'goc', 0, 'blaa', 'fcba', 0, 0),
	('kp2021-bfka-fcba', '2020-11-14 08:00', 'kp2021', 'sev', 0, 'bfka', 'fcba', 0, 0);
	
commit;