--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3
-- Dumped by pg_dump version 13.3

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
-- Name: bike_sharing; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA bike_sharing;


ALTER SCHEMA bike_sharing OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: abbonamento; Type: TABLE; Schema: bike_sharing; Owner: postgres
--

CREATE TABLE bike_sharing.abbonamento (
    codice character varying(36) NOT NULL,
    password character varying(20),
    tipo character varying(30),
    studente boolean,
    sospeso boolean,
    dataacquisto date,
    datainizio date,
    datascadenzavalidita date,
    ammonizioni integer,
    cartadicredito character varying(16)
);


ALTER TABLE bike_sharing.abbonamento OWNER TO postgres;

--
-- Name: bicicletta; Type: TABLE; Schema: bike_sharing; Owner: postgres
--

CREATE TABLE bike_sharing.bicicletta (
    id character varying(36) NOT NULL,
    tipo character varying(30),
    danneggiata boolean
);


ALTER TABLE bike_sharing.bicicletta OWNER TO postgres;

--
-- Name: cartadicredito; Type: TABLE; Schema: bike_sharing; Owner: postgres
--

CREATE TABLE bike_sharing.cartadicredito (
    numero character varying(16) NOT NULL,
    scadenza character varying(10),
    cvv character varying(3)
);


ALTER TABLE bike_sharing.cartadicredito OWNER TO postgres;

--
-- Name: morsa; Type: TABLE; Schema: bike_sharing; Owner: postgres
--

CREATE TABLE bike_sharing.morsa (
    id character varying(36) NOT NULL,
    posizione integer,
    tipo character varying(30),
    bicicletta character varying(36),
    totem character varying(36)
);


ALTER TABLE bike_sharing.morsa OWNER TO postgres;

--
-- Name: noleggio; Type: TABLE; Schema: bike_sharing; Owner: postgres
--

CREATE TABLE bike_sharing.noleggio (
    id character varying(36) NOT NULL,
    orarioinizionoleggio timestamp without time zone,
    orariofinenoleggio timestamp without time zone,
    bicicletta character varying(36),
    abbonamento character varying(36),
    totem character varying(36)
);


ALTER TABLE bike_sharing.noleggio OWNER TO postgres;

--
-- Name: totem; Type: TABLE; Schema: bike_sharing; Owner: postgres
--

CREATE TABLE bike_sharing.totem (
    id character varying(36) NOT NULL,
    indirizzo character varying(50)
);


ALTER TABLE bike_sharing.totem OWNER TO postgres;

--
-- Data for Name: abbonamento; Type: TABLE DATA; Schema: bike_sharing; Owner: postgres
--

COPY bike_sharing.abbonamento (codice, password, tipo, studente, sospeso, dataacquisto, datainizio, datascadenzavalidita, ammonizioni, cartadicredito) FROM stdin;
53dd1104-d045-4d4f-a7fa-946b4714e1bc	Dennis12345	PERSONALE_SERVIZIO	f	f	2021-08-14	2021-08-14	2024-08-14	0	12345678903555
482e875d-8814-4e86-9fff-1c07ea4e33cc	Dennis123	ANNUALE	t	f	2021-08-14	2021-08-14	2022-08-14	1	012850003580200
4bcd3928-f425-40db-bd31-c3bd2186be5d	Test123	ANNUALE	f	t	2021-08-21	2021-08-21	2022-08-21	0	4463309147938770
18597456-7524-4318-b4d5-5603612dfbab	Test123456	GIORNALIERO	t	f	2021-08-21	\N	2021-11-21	0	4880358154407760
18e0afb4-554e-4c20-b575-d17752c412d9	Test12345	SETTIMANALE	t	f	2021-08-21	\N	2021-11-21	0	4131708522575903
\.


--
-- Data for Name: bicicletta; Type: TABLE DATA; Schema: bike_sharing; Owner: postgres
--

COPY bike_sharing.bicicletta (id, tipo, danneggiata) FROM stdin;
4a6e8f3d-8521-46dc-b60d-292031099908	ELETTRICA_SEGGIOLINO	f
18b3665a-bd9d-4e1a-9583-f6ff835cbaec	NORMALE	f
8ec6bbd6-edf1-42e8-ba8c-bf30982432d4	ELETTRICA	f
42b47d5a-e6c6-4a02-b5eb-2f8186146067	NORMALE	f
19438f8e-5189-439c-87b3-ff0ba32e1d70	ELETTRICA	f
063c0714-45ce-4c83-83d6-8aa81e0741b5	ELETTRICA_SEGGIOLINO	f
d8d80ae5-b159-4961-8335-61fc825f25ef	ELETTRICA	f
d10ac900-b9af-49fe-b0a2-deed3a9c38fa	NORMALE	f
234e6164-9cec-49ec-ad1e-74a6388e5671	NORMALE	t
db97a279-f670-4079-99da-23552d75349d	NORMALE	f
\.


--
-- Data for Name: cartadicredito; Type: TABLE DATA; Schema: bike_sharing; Owner: postgres
--

COPY bike_sharing.cartadicredito (numero, scadenza, cvv) FROM stdin;
012850003580200	9/2022	123
12345678903555	12/2021	121
4475794209397914	12/2023	721
4291790808416693	12/2021	118
4870709574110347	12/2021	523
4079029256061430	9/2024	242
4259547800086803	02/2024	321
4618801449126934	03/2022	323
4463309147938770	04/2027	321
4131708522575903	02/2024	212
4880358154407760	01/2027	856
\.


--
-- Data for Name: morsa; Type: TABLE DATA; Schema: bike_sharing; Owner: postgres
--

COPY bike_sharing.morsa (id, posizione, tipo, bicicletta, totem) FROM stdin;
81aad07d-2e96-4823-9fb8-0a83adcd75ba	1	NORMALE	42b47d5a-e6c6-4a02-b5eb-2f8186146067	dec6ac44-8f3c-4275-993b-9f9f1d63c3b9
f669953a-1340-4bff-a2e7-a7f568009655	3	NORMALE	234e6164-9cec-49ec-ad1e-74a6388e5671	dec6ac44-8f3c-4275-993b-9f9f1d63c3b9
fad30331-c00b-498c-8548-37470e5ebaf5	7	NORMALE	\N	ef4980e3-8024-46eb-810f-f2ae799095a3
332bd9c3-0d5a-499d-9ff7-af5cf18c45d5	1	NORMALE	db97a279-f670-4079-99da-23552d75349d	ef4980e3-8024-46eb-810f-f2ae799095a3
a64c176b-903c-4a5c-82fa-216833beb585	7	ELETTRICA	\N	dec6ac44-8f3c-4275-993b-9f9f1d63c3b9
8441a17f-b778-4b7c-930d-e03228c12ebd	4	ELETTRICA	\N	ef4980e3-8024-46eb-810f-f2ae799095a3
7f2abb12-76ee-4031-b96f-0b48f2e6b4a6	3	NORMALE	\N	ef4980e3-8024-46eb-810f-f2ae799095a3
2c8b8c9b-a816-48ff-b80b-da15b7348f74	5	ELETTRICA	4a6e8f3d-8521-46dc-b60d-292031099908	ef4980e3-8024-46eb-810f-f2ae799095a3
f79088d4-0cdf-4cdb-ab66-65f1c41d8552	2	NORMALE	18b3665a-bd9d-4e1a-9583-f6ff835cbaec	ef4980e3-8024-46eb-810f-f2ae799095a3
4a8ce851-00ea-422d-a029-eea43263df1a	6	ELETTRICA	8ec6bbd6-edf1-42e8-ba8c-bf30982432d4	ef4980e3-8024-46eb-810f-f2ae799095a3
7cd37ae6-7bc6-4007-bf5d-253b2ebd4c8d	4	ELETTRICA	19438f8e-5189-439c-87b3-ff0ba32e1d70	dec6ac44-8f3c-4275-993b-9f9f1d63c3b9
de1c6f76-f175-4bae-9378-4fba1e8dc253	5	ELETTRICA	063c0714-45ce-4c83-83d6-8aa81e0741b5	dec6ac44-8f3c-4275-993b-9f9f1d63c3b9
21175bf0-565e-4ffc-924a-b0ee3337acf6	6	ELETTRICA	d8d80ae5-b159-4961-8335-61fc825f25ef	dec6ac44-8f3c-4275-993b-9f9f1d63c3b9
0362058a-cfae-4ed8-a200-2660b19bd3ce	2	NORMALE	d10ac900-b9af-49fe-b0a2-deed3a9c38fa	dec6ac44-8f3c-4275-993b-9f9f1d63c3b9
\.


--
-- Data for Name: noleggio; Type: TABLE DATA; Schema: bike_sharing; Owner: postgres
--

COPY bike_sharing.noleggio (id, orarioinizionoleggio, orariofinenoleggio, bicicletta, abbonamento, totem) FROM stdin;
2dfbc1af-6af3-4db3-a0ac-ecd7ce6291fd	2021-08-21 19:25:16.028	2021-08-21 19:25:21.668	234e6164-9cec-49ec-ad1e-74a6388e5671	18e0afb4-554e-4c20-b575-d17752c412d9	ef4980e3-8024-46eb-810f-f2ae799095a3
37fac101-7952-402f-a1ef-8ebf43920d74	2021-08-21 22:21:43.396	2021-08-21 22:21:54.851	42b47d5a-e6c6-4a02-b5eb-2f8186146067	53dd1104-d045-4d4f-a7fa-946b4714e1bc	dec6ac44-8f3c-4275-993b-9f9f1d63c3b9
a7ef56d9-7598-42a9-89a3-22e0e5caf59e	2021-08-21 22:39:56.73	2021-08-21 22:40:02.467	234e6164-9cec-49ec-ad1e-74a6388e5671	53dd1104-d045-4d4f-a7fa-946b4714e1bc	ef4980e3-8024-46eb-810f-f2ae799095a3
6c1a224d-bc9f-4db8-a303-41d993233b6f	2021-08-21 12:26:09.222	2021-08-21 12:26:15.285	234e6164-9cec-49ec-ad1e-74a6388e5671	53dd1104-d045-4d4f-a7fa-946b4714e1bc	ef4980e3-8024-46eb-810f-f2ae799095a3
\.


--
-- Data for Name: totem; Type: TABLE DATA; Schema: bike_sharing; Owner: postgres
--

COPY bike_sharing.totem (id, indirizzo) FROM stdin;
ef4980e3-8024-46eb-810f-f2ae799095a3	Via Celoria 18
dec6ac44-8f3c-4275-993b-9f9f1d63c3b9	Via Celoria 20
\.


--
-- Name: abbonamento abbonamento_pkey; Type: CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.abbonamento
    ADD CONSTRAINT abbonamento_pkey PRIMARY KEY (codice);


--
-- Name: bicicletta bicicletta_pkey; Type: CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.bicicletta
    ADD CONSTRAINT bicicletta_pkey PRIMARY KEY (id);


--
-- Name: cartadicredito cartadicredito_pkey; Type: CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.cartadicredito
    ADD CONSTRAINT cartadicredito_pkey PRIMARY KEY (numero);


--
-- Name: morsa morsa_pkey; Type: CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.morsa
    ADD CONSTRAINT morsa_pkey PRIMARY KEY (id);


--
-- Name: noleggio noleggio_pkey; Type: CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.noleggio
    ADD CONSTRAINT noleggio_pkey PRIMARY KEY (id);


--
-- Name: totem totem_pkey; Type: CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.totem
    ADD CONSTRAINT totem_pkey PRIMARY KEY (id);


--
-- Name: abbonamento abbonamento_cartadicredito_fkey; Type: FK CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.abbonamento
    ADD CONSTRAINT abbonamento_cartadicredito_fkey FOREIGN KEY (cartadicredito) REFERENCES bike_sharing.cartadicredito(numero) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: morsa morsa_bicicletta_fkey; Type: FK CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.morsa
    ADD CONSTRAINT morsa_bicicletta_fkey FOREIGN KEY (bicicletta) REFERENCES bike_sharing.bicicletta(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: morsa morsa_bicicletta_fkey1; Type: FK CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.morsa
    ADD CONSTRAINT morsa_bicicletta_fkey1 FOREIGN KEY (bicicletta) REFERENCES bike_sharing.bicicletta(id);


--
-- Name: morsa morsa_totem_fkey; Type: FK CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.morsa
    ADD CONSTRAINT morsa_totem_fkey FOREIGN KEY (totem) REFERENCES bike_sharing.totem(id);


--
-- Name: noleggio noleggio_abbonamento_fkey; Type: FK CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.noleggio
    ADD CONSTRAINT noleggio_abbonamento_fkey FOREIGN KEY (abbonamento) REFERENCES bike_sharing.abbonamento(codice);


--
-- Name: noleggio noleggio_bicicletta_fkey; Type: FK CONSTRAINT; Schema: bike_sharing; Owner: postgres
--

ALTER TABLE ONLY bike_sharing.noleggio
    ADD CONSTRAINT noleggio_bicicletta_fkey FOREIGN KEY (bicicletta) REFERENCES bike_sharing.bicicletta(id);


--
-- PostgreSQL database dump complete
--

