CREATE TABLE IF NOT EXISTS university.courses
(
    id bigint NOT NULL DEFAULT nextval('university.courses_course_id_seq'::regclass),
    course_name character(255) COLLATE pg_catalog."default",
    course_description character(255) COLLATE pg_catalog."default",
    CONSTRAINT pk_course_id PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.courses
    OWNER to postgres;
--/////////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.groups
(
    id bigint NOT NULL DEFAULT nextval('university.groups_group_id_seq'::regclass),
    group_name character(255) COLLATE pg_catalog."default",
    CONSTRAINT group_pkey PRIMARY KEY (group_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.groups
    OWNER to postgres;
--////////////////////////////////////////////////////    
CREATE TABLE IF NOT EXISTS university.teachers
(
  	id bigint NOT NULL DEFAULT nextval('university.teachers_id_seq'::regclass),
    course_id bigint,
    teacher_name character(255) COLLATE pg_catalog."default",
    teacher_surname character(255) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT teachers_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.teachers
    OWNER to postgres;
--///////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.students
(
  	id bigint NOT NULL DEFAULT nextval('university.students_student_id_seq'::regclass),
    group_id bigint,
    student_name character(255) COLLATE pg_catalog."default",
    student_surname character(255) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT student_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.students
    OWNER to postgres;
--//////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.timetable
(
    timetable_id bigint NOT NULL DEFAULT nextval('university.timetable_timetable_id_seq'::regclass),
    pair_number integer NOT NULL,
    timetable_date date NOT NULL,
    group_id bigint,
    teacher_id bigint,
    CONSTRAINT timetable_pkey PRIMARY KEY (timetable_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.timetable
    OWNER to postgres;
--////////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.course_group
(
    group_id bigint NOT NULL,
    course_id bigint NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.course_group
    OWNER to postgres;
--////////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.users
(
    id bigint NOT NULL DEFAULT nextval('university.users_id_seq'::regclass),
    user_name character(20) COLLATE pg_catalog."default" NOT NULL,
    password character(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.users
    OWNER to postgres;
--///////////////////////////////////////////////////    
CREATE TABLE IF NOT EXISTS university.roles
(
    id bigint NOT NULL DEFAULT nextval('university.rols_id_seq'::regclass),
    name character(20) COLLATE pg_catalog."default",
    CONSTRAINT rols_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.roles
    OWNER to postgres;
--///////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.users_roles
(
    user_id bigint NOT NULL DEFAULT nextval('university.users_roles_user_id_seq'::regclass),
    role_id bigint NOT NULL DEFAULT nextval('university.users_roles_roles_id_seq'::regclass),
    CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_role_id FOREIGN KEY (role_id)
        REFERENCES university.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
        REFERENCES university.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.users_roles
    OWNER to postgres;
--//////////////////////////////////////////////////