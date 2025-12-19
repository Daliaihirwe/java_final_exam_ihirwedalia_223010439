--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-12-20 00:28:35

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 25793)
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    customer_id integer NOT NULL,
    name character varying(255) NOT NULL,
    address text,
    phone character varying(50),
    email character varying(255) NOT NULL,
    plan_id integer
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 25792)
-- Name: customer_customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.customer_customer_id_seq OWNER TO postgres;

--
-- TOC entry 4922 (class 0 OID 0)
-- Dependencies: 219
-- Name: customer_customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_customer_id_seq OWNED BY public.customer.customer_id;


--
-- TOC entry 228 (class 1259 OID 25853)
-- Name: maintenance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.maintenance (
    maintenance_id integer NOT NULL,
    meter_id integer NOT NULL,
    maintenance_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    technician_name character varying(255) NOT NULL,
    notes text
);


ALTER TABLE public.maintenance OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 25852)
-- Name: maintenance_maintenance_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.maintenance_maintenance_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.maintenance_maintenance_id_seq OWNER TO postgres;

--
-- TOC entry 4923 (class 0 OID 0)
-- Dependencies: 227
-- Name: maintenance_maintenance_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.maintenance_maintenance_id_seq OWNED BY public.maintenance.maintenance_id;


--
-- TOC entry 222 (class 1259 OID 25809)
-- Name: meter; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.meter (
    meter_id integer NOT NULL,
    serial_number character varying(255) NOT NULL,
    installation_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    customer_id integer
);


ALTER TABLE public.meter OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 25808)
-- Name: meter_meter_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.meter_meter_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.meter_meter_id_seq OWNER TO postgres;

--
-- TOC entry 4924 (class 0 OID 0)
-- Dependencies: 221
-- Name: meter_meter_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.meter_meter_id_seq OWNED BY public.meter.meter_id;


--
-- TOC entry 226 (class 1259 OID 25838)
-- Name: outage; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.outage (
    outage_id integer NOT NULL,
    meter_id integer NOT NULL,
    start_time timestamp with time zone NOT NULL,
    end_time timestamp with time zone,
    description text,
    CONSTRAINT check_outage_times CHECK (((end_time IS NULL) OR (start_time < end_time)))
);


ALTER TABLE public.outage OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 25837)
-- Name: outage_outage_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.outage_outage_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.outage_outage_id_seq OWNER TO postgres;

--
-- TOC entry 4925 (class 0 OID 0)
-- Dependencies: 225
-- Name: outage_outage_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.outage_outage_id_seq OWNED BY public.outage.outage_id;


--
-- TOC entry 218 (class 1259 OID 25781)
-- Name: plan; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.plan (
    plan_id integer NOT NULL,
    name character varying(255) NOT NULL,
    rate_per_kwh numeric(10,4) NOT NULL,
    description text,
    CONSTRAINT plan_rate_per_kwh_check CHECK ((rate_per_kwh >= (0)::numeric))
);


ALTER TABLE public.plan OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 25780)
-- Name: plan_plan_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.plan_plan_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.plan_plan_id_seq OWNER TO postgres;

--
-- TOC entry 4926 (class 0 OID 0)
-- Dependencies: 217
-- Name: plan_plan_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.plan_plan_id_seq OWNED BY public.plan.plan_id;


--
-- TOC entry 224 (class 1259 OID 25824)
-- Name: reading; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reading (
    reading_id bigint NOT NULL,
    meter_id integer NOT NULL,
    reading_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    kwh_consumed numeric(10,2) NOT NULL,
    CONSTRAINT reading_kwh_consumed_check CHECK ((kwh_consumed >= (0)::numeric))
);


ALTER TABLE public.reading OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 25823)
-- Name: reading_reading_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reading_reading_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reading_reading_id_seq OWNER TO postgres;

--
-- TOC entry 4927 (class 0 OID 0)
-- Dependencies: 223
-- Name: reading_reading_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reading_reading_id_seq OWNED BY public.reading.reading_id;


--
-- TOC entry 4721 (class 2604 OID 25796)
-- Name: customer customer_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer ALTER COLUMN customer_id SET DEFAULT nextval('public.customer_customer_id_seq'::regclass);


--
-- TOC entry 4727 (class 2604 OID 25856)
-- Name: maintenance maintenance_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maintenance ALTER COLUMN maintenance_id SET DEFAULT nextval('public.maintenance_maintenance_id_seq'::regclass);


--
-- TOC entry 4722 (class 2604 OID 25812)
-- Name: meter meter_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.meter ALTER COLUMN meter_id SET DEFAULT nextval('public.meter_meter_id_seq'::regclass);


--
-- TOC entry 4726 (class 2604 OID 25841)
-- Name: outage outage_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outage ALTER COLUMN outage_id SET DEFAULT nextval('public.outage_outage_id_seq'::regclass);


--
-- TOC entry 4720 (class 2604 OID 25784)
-- Name: plan plan_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plan ALTER COLUMN plan_id SET DEFAULT nextval('public.plan_plan_id_seq'::regclass);


--
-- TOC entry 4724 (class 2604 OID 25827)
-- Name: reading reading_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reading ALTER COLUMN reading_id SET DEFAULT nextval('public.reading_reading_id_seq'::regclass);


--
-- TOC entry 4908 (class 0 OID 25793)
-- Dependencies: 220
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer (customer_id, name, address, phone, email, plan_id) FROM stdin;
1	John Doe	123 Main St, Anytown, USA	555-1234	john.doe@example.com	1
2	Jane Smith	456 Oak Ave, Anytown, USA	555-5678	jane.smith@example.com	2
3	Peter Jones	789 Pine Ln, Anytown, USA	555-9012	peter.jones@example.com	1
\.


--
-- TOC entry 4916 (class 0 OID 25853)
-- Dependencies: 228
-- Data for Name: maintenance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.maintenance (maintenance_id, meter_id, maintenance_date, technician_name, notes) FROM stdin;
1	1	2024-05-01 14:00:00+03	Bob The Builder	Replaced a faulty component. Meter is now working correctly.
\.


--
-- TOC entry 4910 (class 0 OID 25809)
-- Dependencies: 222
-- Data for Name: meter; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.meter (meter_id, serial_number, installation_date, customer_id) FROM stdin;
1	SN123456789	2024-01-15 12:00:00+02	1
2	SN987654321	2024-02-20 16:30:00+02	2
3	SN555555555	2024-03-10 11:00:00+02	3
\.


--
-- TOC entry 4914 (class 0 OID 25838)
-- Dependencies: 226
-- Data for Name: outage; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.outage (outage_id, meter_id, start_time, end_time, description) FROM stdin;
1	2	2024-04-01 20:00:00+02	2024-04-02 00:00:00+02	Scheduled maintenance in the area.
\.


--
-- TOC entry 4906 (class 0 OID 25781)
-- Dependencies: 218
-- Data for Name: plan; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.plan (plan_id, name, rate_per_kwh, description) FROM stdin;
1	Standard Residential	0.1250	A standard plan for residential customers.
3	Green Energy	0.1500	A plan for customers who want to support renewable energy sources.
2	Off-Peak Saver	0.0850	A discounted plan for customers who use energy during off-peak hours.
\.


--
-- TOC entry 4912 (class 0 OID 25824)
-- Dependencies: 224
-- Data for Name: reading; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reading (reading_id, meter_id, reading_timestamp, kwh_consumed) FROM stdin;
1	1	2024-02-15 12:00:00+02	350.50
2	1	2024-03-15 12:00:00+02	420.75
3	2	2024-03-20 16:30:00+02	280.00
4	2	2024-04-20 16:30:00+02	310.25
6	3	2024-05-10 12:00:00+03	550.50
7	1	2025-11-14 18:41:59.019+02	450.25
\.


--
-- TOC entry 4928 (class 0 OID 0)
-- Dependencies: 219
-- Name: customer_customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_customer_id_seq', 4, true);


--
-- TOC entry 4929 (class 0 OID 0)
-- Dependencies: 227
-- Name: maintenance_maintenance_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.maintenance_maintenance_id_seq', 1, true);


--
-- TOC entry 4930 (class 0 OID 0)
-- Dependencies: 221
-- Name: meter_meter_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.meter_meter_id_seq', 4, true);


--
-- TOC entry 4931 (class 0 OID 0)
-- Dependencies: 225
-- Name: outage_outage_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.outage_outage_id_seq', 1, true);


--
-- TOC entry 4932 (class 0 OID 0)
-- Dependencies: 217
-- Name: plan_plan_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.plan_plan_id_seq', 3, true);


--
-- TOC entry 4933 (class 0 OID 0)
-- Dependencies: 223
-- Name: reading_reading_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reading_reading_id_seq', 8, true);


--
-- TOC entry 4737 (class 2606 OID 25802)
-- Name: customer customer_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_email_key UNIQUE (email);


--
-- TOC entry 4739 (class 2606 OID 25800)
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (customer_id);


--
-- TOC entry 4754 (class 2606 OID 25861)
-- Name: maintenance maintenance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maintenance
    ADD CONSTRAINT maintenance_pkey PRIMARY KEY (maintenance_id);


--
-- TOC entry 4743 (class 2606 OID 25815)
-- Name: meter meter_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.meter
    ADD CONSTRAINT meter_pkey PRIMARY KEY (meter_id);


--
-- TOC entry 4745 (class 2606 OID 25817)
-- Name: meter meter_serial_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.meter
    ADD CONSTRAINT meter_serial_number_key UNIQUE (serial_number);


--
-- TOC entry 4751 (class 2606 OID 25846)
-- Name: outage outage_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outage
    ADD CONSTRAINT outage_pkey PRIMARY KEY (outage_id);


--
-- TOC entry 4733 (class 2606 OID 25791)
-- Name: plan plan_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plan
    ADD CONSTRAINT plan_name_key UNIQUE (name);


--
-- TOC entry 4735 (class 2606 OID 25789)
-- Name: plan plan_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plan
    ADD CONSTRAINT plan_pkey PRIMARY KEY (plan_id);


--
-- TOC entry 4748 (class 2606 OID 25831)
-- Name: reading reading_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reading
    ADD CONSTRAINT reading_pkey PRIMARY KEY (reading_id);


--
-- TOC entry 4740 (class 1259 OID 25867)
-- Name: idx_customer_plan_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_customer_plan_id ON public.customer USING btree (plan_id);


--
-- TOC entry 4752 (class 1259 OID 25871)
-- Name: idx_maintenance_meter_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_maintenance_meter_id ON public.maintenance USING btree (meter_id);


--
-- TOC entry 4741 (class 1259 OID 25868)
-- Name: idx_meter_customer_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_meter_customer_id ON public.meter USING btree (customer_id);


--
-- TOC entry 4749 (class 1259 OID 25870)
-- Name: idx_outage_meter_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_outage_meter_id ON public.outage USING btree (meter_id);


--
-- TOC entry 4746 (class 1259 OID 25869)
-- Name: idx_reading_meter_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_reading_meter_id ON public.reading USING btree (meter_id);


--
-- TOC entry 4755 (class 2606 OID 25803)
-- Name: customer customer_plan_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_plan_id_fkey FOREIGN KEY (plan_id) REFERENCES public.plan(plan_id) ON DELETE SET NULL;


--
-- TOC entry 4759 (class 2606 OID 25862)
-- Name: maintenance maintenance_meter_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maintenance
    ADD CONSTRAINT maintenance_meter_id_fkey FOREIGN KEY (meter_id) REFERENCES public.meter(meter_id) ON DELETE CASCADE;


--
-- TOC entry 4756 (class 2606 OID 25818)
-- Name: meter meter_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.meter
    ADD CONSTRAINT meter_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(customer_id) ON DELETE CASCADE;


--
-- TOC entry 4758 (class 2606 OID 25847)
-- Name: outage outage_meter_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outage
    ADD CONSTRAINT outage_meter_id_fkey FOREIGN KEY (meter_id) REFERENCES public.meter(meter_id) ON DELETE CASCADE;


--
-- TOC entry 4757 (class 2606 OID 25832)
-- Name: reading reading_meter_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reading
    ADD CONSTRAINT reading_meter_id_fkey FOREIGN KEY (meter_id) REFERENCES public.meter(meter_id) ON DELETE CASCADE;


-- Completed on 2025-12-20 00:28:36

--
-- PostgreSQL database dump complete
--

