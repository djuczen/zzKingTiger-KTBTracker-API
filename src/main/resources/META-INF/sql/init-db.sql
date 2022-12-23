--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1 (Debian 15.1-1.pgdg110+1)
-- Dumped by pg_dump version 15.1

-- Started on 2022-12-23 04:08:34 UTC

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
-- TOC entry 3392 (class 0 OID 17558)
-- Dependencies: 217
-- Data for Name: cycles; Type: TABLE DATA; Schema: public; Owner: ktbtracker
--

INSERT INTO public.cycles VALUES (1, 'fall-2008', '2008-10-24', NULL, NULL, '2008-08-09', 6, 'Fall 2008', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 10, 10, 10, 10, 10, 15, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (2, 'spring-2009', '2009-04-15', NULL, NULL, '2009-02-01', 6, 'Spring 2009', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 10, 10, 10, 10, 10, 15, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (3, 'fall-2009', '2009-12-15', NULL, NULL, '2009-10-01', 6, 'Fall 2009', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 10, 10, 10, 10, 10, 15, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (4, 'spring-2010', '2010-04-15', NULL, NULL, '2010-02-01', 6, 'Spring 2010', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 10, 10, 10, 10, 10, 15, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (5, 'fall-2010', '2010-12-15', NULL, NULL, '2010-10-01', 6, 'Fall 2010', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 10, 10, 10, 10, 10, 15, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (6, 'spring-2011', '2011-04-15', NULL, NULL, '2011-02-01', 6, 'Spring 2011', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 10, 10, 10, 10, 10, 15, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (7, 'fall-2011', '2011-12-15', NULL, NULL, '2011-10-01', 6, 'Fall 2011', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 10, 10, 10, 10, 10, 15, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (8, 'spring-2012', '2012-04-15', NULL, NULL, '2012-02-01', 6, 'Spring 2012', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 10, 10, 10, 10, 10, 15, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (9, 'fall-2012', '2012-11-02', NULL, NULL, '2012-08-18', 6, 'Fall 2012', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 10, 10, 10, 10, 10, 15, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (10, 'spring-2013', '2013-04-12', NULL, NULL, '2013-01-12', 6, 'Spring 2013', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2000-03-08 00:00:00', '', 1000, 10, 10, 10, 10, 10, 15, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (11, 'fall-2013', '2013-10-25', '2013-11-01', '2013-06-16', '2013-08-10', 6, 'Fall 2013', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2000-03-08 00:00:00', '', 1000, 10, 10, 10, 10, 10, 15, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (12, 'spring-2014', '2014-05-03', '2014-05-03', '2014-01-12', '2014-01-18', 6, 'Spring 2014', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 0, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (13, 'fall-2014', '2014-11-22', NULL, '2014-06-21', '2014-08-16', 6, 'Fall 2014', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 10, 10, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (14, 'spring-2015', '2015-05-29', NULL, '2014-12-20', '2015-01-17', 6, 'Spring 2015', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (15, 'fall-2015', '2015-11-08', NULL, '2015-07-11', '2015-08-22', 6, 'Fall 2015', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (16, 'spring-2016', '2016-04-23', '2016-04-22', NULL, '2016-01-09', 6, 'Spring 2016', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (17, 'fall-2016', '2016-11-04', '2016-11-04', '2016-07-02', '2016-08-06', 6, 'Fall 2016', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (18, 'spring-2017', '2017-05-05', NULL, NULL, '2017-01-21', 6, 'Spring 2017', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (19, 'fall-2017', '2017-10-06', NULL, '2017-07-08', '2017-08-07', 6, 'Fall 2017', '', NULL, '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2000-03-08 00:00:00', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (20, 'spring-2018', '2018-04-12', NULL, '2017-12-23', '2018-01-20', 6, 'Spring 2018', '', NULL, '2017-12-16 13:31:12', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2018-04-13 22:43:42', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (21, 'fall-2018', '2018-10-06', NULL, '2018-07-07', '2018-08-11', 6, 'Fall 2018', '', NULL, '2018-07-07 06:51:29', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (22, 'spring-2019', '2019-04-24', '2019-04-24', '2018-12-08', '2019-01-19', 6, 'Spring 2019', '', NULL, '2018-12-05 22:19:37', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2019-04-23 12:22:41', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (23, 'fall-2019', '2019-10-11', '2019-10-25', '2019-07-06', '2019-07-27', 6, 'Fall 2019', '', NULL, '2019-07-02 21:13:28', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2019-07-10 01:33:23', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (24, 'spring-2020', '2020-04-17', NULL, '2019-12-20', '2020-01-04', 6, 'Spring 2020', '', NULL, '2019-12-17 05:54:20', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2020-04-08 12:10:25', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (25, 'fall-2020', '2020-10-09', '2020-10-23', '2020-07-06', '2020-08-01', 6, 'Fall 2020', '', NULL, '2020-07-01 00:53:04', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (26, 'spring-2021', '2021-04-24', '2021-05-01', '2021-01-02', '2021-01-30', 6, 'Spring 2021', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', '2021-01-16 21:37:04', '2021-01-01 23:19:17', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (27, 'fall-2021', '2021-10-22', '2021-11-05', '2021-07-05', '2021-08-21', 6, 'Fall 2021', '', NULL, '2021-07-05 14:47:20', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 1000, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 1000, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (28, 'spring-2022', '2022-04-15', '2022-04-29', NULL, '2022-01-01', 6, 'Spring 2022', '', NULL, '2021-12-16 03:12:21', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 1000, 0, 0, 0, 10, 10, 0, 10, 50, 210, 10500, 20, 10, 500, 15, 15, 105, 0, 1500, 350, 10000, 200, 2500, 1500, 10000, 210);
INSERT INTO public.cycles VALUES (29, 'fall-2022', '2022-12-14', NULL, NULL, '2022-07-02', 6, 'Fall 2022', '', NULL, '2022-06-30 17:32:07', 'jKEZzJ0jZXMnkmsycL9LZ7UDf4Y2', NULL, '', 350, 0, 0, 0, 0, 12, 12, 12, 50, 210, 832, 20, 10, 0, 15, 15, 103, 340, 1512, 350, 350, 200, 83, 1512, 350, 210);


--
-- TOC entry 3401 (class 0 OID 17606)
-- Dependencies: 226
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: ktbtracker
--



--
-- TOC entry 3390 (class 0 OID 17548)
-- Dependencies: 215
-- Data for Name: candidates; Type: TABLE DATA; Schema: public; Owner: ktbtracker
--



--
-- TOC entry 3394 (class 0 OID 17568)
-- Dependencies: 219
-- Data for Name: journal_posts; Type: TABLE DATA; Schema: public; Owner: ktbtracker
--



--
-- TOC entry 3396 (class 0 OID 17577)
-- Dependencies: 221
-- Data for Name: mentor_checks; Type: TABLE DATA; Schema: public; Owner: ktbtracker
--



--
-- TOC entry 3398 (class 0 OID 17587)
-- Dependencies: 223
-- Data for Name: parent_checks; Type: TABLE DATA; Schema: public; Owner: ktbtracker
--



--
-- TOC entry 3400 (class 0 OID 17597)
-- Dependencies: 225
-- Data for Name: tracking; Type: TABLE DATA; Schema: public; Owner: ktbtracker
--



--
-- TOC entry 3403 (class 0 OID 17614)
-- Dependencies: 228
-- Data for Name: usergroups; Type: TABLE DATA; Schema: public; Owner: ktbtracker
--



--
-- TOC entry 3404 (class 0 OID 17623)
-- Dependencies: 229
-- Data for Name: user_usergroups; Type: TABLE DATA; Schema: public; Owner: ktbtracker
--



--
-- TOC entry 3410 (class 0 OID 0)
-- Dependencies: 214
-- Name: candidates_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ktbtracker
--

SELECT pg_catalog.setval('public.candidates_id_seq', 1, false);


--
-- TOC entry 3411 (class 0 OID 0)
-- Dependencies: 216
-- Name: cycles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ktbtracker
--

SELECT pg_catalog.setval('public.cycles_id_seq', 1, false);


--
-- TOC entry 3412 (class 0 OID 0)
-- Dependencies: 218
-- Name: journal_posts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ktbtracker
--

SELECT pg_catalog.setval('public.journal_posts_id_seq', 1, false);


--
-- TOC entry 3413 (class 0 OID 0)
-- Dependencies: 220
-- Name: mentor_checks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ktbtracker
--

SELECT pg_catalog.setval('public.mentor_checks_id_seq', 1, false);


--
-- TOC entry 3414 (class 0 OID 0)
-- Dependencies: 222
-- Name: parent_checks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ktbtracker
--

SELECT pg_catalog.setval('public.parent_checks_id_seq', 1, false);


--
-- TOC entry 3415 (class 0 OID 0)
-- Dependencies: 224
-- Name: tracking_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ktbtracker
--

SELECT pg_catalog.setval('public.tracking_id_seq', 1, false);


--
-- TOC entry 3416 (class 0 OID 0)
-- Dependencies: 227
-- Name: usergroups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ktbtracker
--

SELECT pg_catalog.setval('public.usergroups_id_seq', 1, false);


-- Completed on 2022-12-23 04:08:34 UTC

--
-- PostgreSQL database dump complete
--

