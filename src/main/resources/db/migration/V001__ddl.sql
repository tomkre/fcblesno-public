--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10 (Ubuntu 10.10-1.pgdg18.04+1)
-- Dumped by pg_dump version 10.10 (Ubuntu 10.10-1.pgdg18.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: article; Type: TABLE; Schema: public; Owner: fcblesno
--

CREATE TABLE public.article (
    id character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone,
    article_type character varying(255),
    content text,
    published_at timestamp without time zone,
    title character varying(255)
);


ALTER TABLE public.article OWNER TO fcblesno;

--
-- Name: game; Type: TABLE; Schema: public; Owner: fcblesno
--

CREATE TABLE public.game (
    id character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone,
    fans integer,
    game_date timestamp without time zone,
    goals_guest integer,
    goals_home integer,
    article_id character varying(255),
    league_id character varying(255),
    location_id character varying(255),
    team_guest_id character varying(255),
    team_home_id character varying(255)
);


ALTER TABLE public.game OWNER TO fcblesno;

--
-- Name: goalkeeper_statistic; Type: TABLE; Schema: public; Owner: fcblesno
--

CREATE TABLE public.goalkeeper_statistic (
    received_goals integer NOT NULL,
    received_goals_per_game double precision NOT NULL,
    shotouts integer NOT NULL,
    id character varying(255) NOT NULL
);


ALTER TABLE public.goalkeeper_statistic OWNER TO fcblesno;

--
-- Name: league; Type: TABLE; Schema: public; Owner: fcblesno
--

CREATE TABLE public.league (
    id character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone,
    league_name character varying(255),
    season_end date,
    season_start date
);


ALTER TABLE public.league OWNER TO fcblesno;

--
-- Name: location; Type: TABLE; Schema: public; Owner: fcblesno
--

CREATE TABLE public.location (
    id character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone,
    name character varying(255)
);


ALTER TABLE public.location OWNER TO fcblesno;

--
-- Name: player; Type: TABLE; Schema: public; Owner: fcblesno
--

CREATE TABLE public.player (
    id character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone,
    birth_date date,
    first_name character varying(255),
    last_name character varying(255),
    post character varying(255)
);


ALTER TABLE public.player OWNER TO fcblesno;

--
-- Name: player_statistic; Type: TABLE; Schema: public; Owner: fcblesno
--

CREATE TABLE public.player_statistic (
    id character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone,
    assists integer,
    goals integer,
    games integer DEFAULT 1,
    plus_minus integer DEFAULT 0,
    post character varying(255),
    received_goals integer DEFAULT 0,
    red_cards integer DEFAULT 0,
    shotouts integer DEFAULT 0,
    yellow_cards integer DEFAULT 0,
    game_id character varying(255),
    player_id character varying(255)
);


ALTER TABLE public.player_statistic OWNER TO fcblesno;

--
-- Name: player_team; Type: TABLE; Schema: public; Owner: fcblesno
--

CREATE TABLE public.player_team (
    player_id character varying(255) NOT NULL,
    team_id character varying(255) NOT NULL
);


ALTER TABLE public.player_team OWNER TO fcblesno;

--
-- Name: team; Type: TABLE; Schema: public; Owner: fcblesno
--

CREATE TABLE public.team (
    id character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone,
    name character varying(255)
);


ALTER TABLE public.team OWNER TO fcblesno;

--
-- Name: team_player; Type: TABLE; Schema: public; Owner: fcblesno
--

CREATE TABLE public.team_player (
    player_id character varying(255) NOT NULL,
    team_id character varying(255) NOT NULL
);


ALTER TABLE public.team_player OWNER TO fcblesno;

--
-- Name: article article_pkey; Type: CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.article
    ADD CONSTRAINT article_pkey PRIMARY KEY (id);


--
-- Name: game game_pkey; Type: CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT game_pkey PRIMARY KEY (id);


--
-- Name: goalkeeper_statistic goalkeeper_statistic_pkey; Type: CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.goalkeeper_statistic
    ADD CONSTRAINT goalkeeper_statistic_pkey PRIMARY KEY (id);


--
-- Name: league league_pkey; Type: CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.league
    ADD CONSTRAINT league_pkey PRIMARY KEY (id);


--
-- Name: location location_pkey; Type: CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);


--
-- Name: player player_pkey; Type: CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.player
    ADD CONSTRAINT player_pkey PRIMARY KEY (id);


--
-- Name: player_statistic player_statistic_pkey; Type: CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.player_statistic
    ADD CONSTRAINT player_statistic_pkey PRIMARY KEY (id);


--
-- Name: team team_pkey; Type: CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (id);


--
-- Name: team_player fk61af6hcog98vhvpvrypcp3liu; Type: FK CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.team_player
    ADD CONSTRAINT fk61af6hcog98vhvpvrypcp3liu FOREIGN KEY (player_id) REFERENCES public.player(id);


--
-- Name: game fk6ifx03pkqlrpuickivf79w2rf; Type: FK CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT fk6ifx03pkqlrpuickivf79w2rf FOREIGN KEY (team_guest_id) REFERENCES public.team(id);


--
-- Name: game fk7qvgtve9tgc1vu39lpbdn7ynv; Type: FK CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT fk7qvgtve9tgc1vu39lpbdn7ynv FOREIGN KEY (article_id) REFERENCES public.article(id);


--
-- Name: game fk9i9qkunl208s2lyr6cwtnh56q; Type: FK CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT fk9i9qkunl208s2lyr6cwtnh56q FOREIGN KEY (location_id) REFERENCES public.location(id);


--
-- Name: player_statistic fkakb3p92dypm7evbdcdu6qriiv; Type: FK CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.player_statistic
    ADD CONSTRAINT fkakb3p92dypm7evbdcdu6qriiv FOREIGN KEY (game_id) REFERENCES public.game(id);


--
-- Name: game fkec93uhpddmlnvxj2ofyjl5f0n; Type: FK CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT fkec93uhpddmlnvxj2ofyjl5f0n FOREIGN KEY (team_home_id) REFERENCES public.team(id);


--
-- Name: player_statistic fkf4ox0qcd8y4kjdm1ke0aqn9oc; Type: FK CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.player_statistic
    ADD CONSTRAINT fkf4ox0qcd8y4kjdm1ke0aqn9oc FOREIGN KEY (player_id) REFERENCES public.player(id);


--
-- Name: team_player fkgadi21l58c1a65823rn8cgrps; Type: FK CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.team_player
    ADD CONSTRAINT fkgadi21l58c1a65823rn8cgrps FOREIGN KEY (team_id) REFERENCES public.team(id);


--
-- Name: game fkquabskn2i23o86heouko0wi5x; Type: FK CONSTRAINT; Schema: public; Owner: fcblesno
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT fkquabskn2i23o86heouko0wi5x FOREIGN KEY (league_id) REFERENCES public.league(id);


--
-- PostgreSQL database dump complete
--

