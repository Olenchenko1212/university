CREATE TABLE IF NOT EXISTS university.courses
(
    course_name character(255) COLLATE pg_catalog."default",
    course_description character(255) COLLATE pg_catalog."default",
    course_id integer NOT NULL DEFAULT nextval('university.courses_course_id_seq'::regclass),
    CONSTRAINT course_id PRIMARY KEY (course_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.courses
    OWNER to postgres;
--/////////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.groups
(
    group_name character(255) COLLATE pg_catalog."default",
    group_id integer NOT NULL DEFAULT nextval('university.groups_group_id_seq'::regclass),
    CONSTRAINT group_id PRIMARY KEY (group_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.groups
    OWNER to postgres;
--////////////////////////////////////////////////////    
CREATE TABLE IF NOT EXISTS university.teachers
(
    course_id integer NOT NULL,
    teather_name character(255) COLLATE pg_catalog."default",
    teacher_surname character(255) COLLATE pg_catalog."default",
    teacher_id integer NOT NULL DEFAULT nextval('university.teachers_teacher_id_seq'::regclass),
    CONSTRAINT teacher_pkey PRIMARY KEY (teacher_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.teachers
    OWNER to postgres;
--///////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.students
(
    group_id integer NOT NULL,
    student_name character(255) COLLATE pg_catalog."default",
    student_surname character(255) COLLATE pg_catalog."default",
    student_id integer NOT NULL DEFAULT nextval('university.students_student_id_seq'::regclass),
    CONSTRAINT student_pkey PRIMARY KEY (student_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.students
    OWNER to postgres;
--//////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.timetable
(
    timetable_date date,
    pair_number integer NOT NULL,
    group_id integer NOT NULL,
    teacher_id integer NOT NULL,
    timetable_id integer NOT NULL DEFAULT nextval('university.timetable_timetable_id_seq'::regclass),
    CONSTRAINT timetable_pkey PRIMARY KEY (timetable_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.timetable
    OWNER to postgres;