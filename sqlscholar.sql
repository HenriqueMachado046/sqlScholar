--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2024-11-11 16:06:26 -03

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

DROP DATABASE sqlscholar;
--
-- TOC entry 3455 (class 1262 OID 16993)
-- Name: sqlscholar; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE sqlscholar WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'pt_BR.UTF-8';


ALTER DATABASE sqlscholar OWNER TO postgres;

\connect sqlscholar

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
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3456 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16994)
-- Name: question; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.question (
    id uuid NOT NULL,
    answer_sheet character varying(255),
    created_at timestamp(6) without time zone,
    description character varying(255),
    difficulty character varying(255),
    is_shared boolean,
    sql character varying(255),
    title character varying(255),
    teacher_id uuid
);


ALTER TABLE public.question OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 17001)
-- Name: question_question_lists; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.question_question_lists (
    question_id uuid NOT NULL,
    questionlist_id uuid NOT NULL
);


ALTER TABLE public.question_question_lists OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 17004)
-- Name: questionlist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.questionlist (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone,
    database_script character varying(255),
    is_private boolean,
    title character varying(255),
    teacher_id uuid,
    owner_id uuid
);


ALTER TABLE public.questionlist OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 17011)
-- Name: student; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.student (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone,
    email character varying(255),
    first_name character varying(255),
    is_teacher boolean,
    last_name character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.student OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17018)
-- Name: tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tag (
    id uuid NOT NULL,
    name character varying(255),
    question_id uuid
);


ALTER TABLE public.tag OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 17023)
-- Name: teacher; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.teacher (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone,
    email character varying(255),
    first_name character varying(255),
    is_teacher boolean,
    last_name character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.teacher OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17030)
-- Name: teacher_students; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.teacher_students (
    teacher_id uuid NOT NULL,
    student_id uuid NOT NULL
);


ALTER TABLE public.teacher_students OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 17033)
-- Name: test_case; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.test_case (
    id uuid NOT NULL,
    teste character varying(255),
    question_id uuid
);


ALTER TABLE public.test_case OWNER TO postgres;

--
-- TOC entry 3442 (class 0 OID 16994)
-- Dependencies: 215
-- Data for Name: question; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3443 (class 0 OID 17001)
-- Dependencies: 216
-- Data for Name: question_question_lists; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3444 (class 0 OID 17004)
-- Dependencies: 217
-- Data for Name: questionlist; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3445 (class 0 OID 17011)
-- Dependencies: 218
-- Data for Name: student; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.student VALUES ('1cbff617-fe1f-4ea9-bff8-8df628577cf6', '2024-10-29 13:36:54.489205', 'wt@w.com', 'witness', false, 'testify', '', 'witnesstestify');


--
-- TOC entry 3446 (class 0 OID 17018)
-- Dependencies: 219
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3447 (class 0 OID 17023)
-- Dependencies: 220
-- Data for Name: teacher; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.teacher VALUES ('6051b1be-93db-459e-bdd1-80a8ee8514ed', '2024-10-29 13:10:10.36512', 'Teste@teste.com', 'primeiro professor', true, 'ultimo nome', '1234', 'professor1ultimonome');


--
-- TOC entry 3448 (class 0 OID 17030)
-- Dependencies: 221
-- Data for Name: teacher_students; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3449 (class 0 OID 17033)
-- Dependencies: 222
-- Data for Name: test_case; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3279 (class 2606 OID 17000)
-- Name: question question_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.question
    ADD CONSTRAINT question_pkey PRIMARY KEY (id);


--
-- TOC entry 3281 (class 2606 OID 17010)
-- Name: questionlist questionlist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questionlist
    ADD CONSTRAINT questionlist_pkey PRIMARY KEY (id);


--
-- TOC entry 3283 (class 2606 OID 17017)
-- Name: student student_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (id);


--
-- TOC entry 3285 (class 2606 OID 17022)
-- Name: tag tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);


--
-- TOC entry 3287 (class 2606 OID 17029)
-- Name: teacher teacher_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher
    ADD CONSTRAINT teacher_pkey PRIMARY KEY (id);


--
-- TOC entry 3289 (class 2606 OID 17037)
-- Name: test_case test_case_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test_case
    ADD CONSTRAINT test_case_pkey PRIMARY KEY (id);


--
-- TOC entry 3291 (class 2606 OID 17043)
-- Name: question_question_lists fk2yn525pl2u56vu5anqr1r35nb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.question_question_lists
    ADD CONSTRAINT fk2yn525pl2u56vu5anqr1r35nb FOREIGN KEY (questionlist_id) REFERENCES public.questionlist(id);


--
-- TOC entry 3295 (class 2606 OID 17058)
-- Name: tag fkbyiibg9l1w2o6bt6yfgni31vm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT fkbyiibg9l1w2o6bt6yfgni31vm FOREIGN KEY (question_id) REFERENCES public.question(id);


--
-- TOC entry 3292 (class 2606 OID 17048)
-- Name: question_question_lists fkfij2nm24omp2kl2dur5fyodjj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.question_question_lists
    ADD CONSTRAINT fkfij2nm24omp2kl2dur5fyodjj FOREIGN KEY (question_id) REFERENCES public.question(id);


--
-- TOC entry 3290 (class 2606 OID 17038)
-- Name: question fkgnm83qijywvywwgsmi39x9wth; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.question
    ADD CONSTRAINT fkgnm83qijywvywwgsmi39x9wth FOREIGN KEY (teacher_id) REFERENCES public.teacher(id);


--
-- TOC entry 3293 (class 2606 OID 17078)
-- Name: questionlist fkieapy4c13wc896nhqqmha1w0p; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questionlist
    ADD CONSTRAINT fkieapy4c13wc896nhqqmha1w0p FOREIGN KEY (owner_id) REFERENCES public.teacher(id);


--
-- TOC entry 3294 (class 2606 OID 17053)
-- Name: questionlist fkmqr998rwbjasgfuy8nfel3142; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questionlist
    ADD CONSTRAINT fkmqr998rwbjasgfuy8nfel3142 FOREIGN KEY (teacher_id) REFERENCES public.teacher(id);


--
-- TOC entry 3296 (class 2606 OID 17063)
-- Name: teacher_students fkmr4gbx2ded156yatgxme336u3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_students
    ADD CONSTRAINT fkmr4gbx2ded156yatgxme336u3 FOREIGN KEY (student_id) REFERENCES public.student(id);


--
-- TOC entry 3298 (class 2606 OID 17073)
-- Name: test_case fkmsp5lwbu99ugwh0nc8276a0lt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test_case
    ADD CONSTRAINT fkmsp5lwbu99ugwh0nc8276a0lt FOREIGN KEY (question_id) REFERENCES public.question(id);


--
-- TOC entry 3297 (class 2606 OID 17068)
-- Name: teacher_students fkt32x16pocfyjaun2oar8ye5fo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_students
    ADD CONSTRAINT fkt32x16pocfyjaun2oar8ye5fo FOREIGN KEY (teacher_id) REFERENCES public.teacher(id);


-- Completed on 2024-11-11 16:06:27 -03

--
-- PostgreSQL database dump complete
--

