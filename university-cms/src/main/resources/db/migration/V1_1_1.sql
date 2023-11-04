CREATE TABLE IF NOT EXISTS university.courses
(
   course_id bigint NOT NULL DEFAULT nextval('university.courses_course_id_seq'::regclass),
    course_name character(255) COLLATE pg_catalog."default",
    course_description character(255) COLLATE pg_catalog."default",
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.courses
    OWNER to postgres;
--/////////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.groups
(
     group_id bigint NOT NULL DEFAULT nextval('university.groups_group_id_seq'::regclass),
    group_name character(255) COLLATE pg_catalog."default",
    CONSTRAINT group_pkey PRIMARY KEY (group_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.groups
    OWNER to postgres;
--////////////////////////////////////////////////////    
CREATE TABLE IF NOT EXISTS university.teachers
(
  	teacher_id bigint NOT NULL DEFAULT nextval('university.teachers_teacher_id_seq'::regclass),
    teacher_name character(255) COLLATE pg_catalog."default",
    teacher_surname character(255) COLLATE pg_catalog."default",
    CONSTRAINT teacher_pkey PRIMARY KEY (teacher_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.teachers
    OWNER to postgres;
--///////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.students
(
  student_id bigint NOT NULL DEFAULT nextval('university.students_student_id_seq'::regclass),
    group_id bigint NOT NULL DEFAULT '-1'::bigint,
    student_name character(255) COLLATE pg_catalog."default",
    student_surname character(255) COLLATE pg_catalog."default",
    CONSTRAINT student_pkey PRIMARY KEY (student_id),
    CONSTRAINT fk_group_id FOREIGN KEY (group_id)
        REFERENCES university.groups (group_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET DEFAULT
        NOT VALID
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
--////////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS university.course_group
(
    group_id bigint NOT NULL,
    course_id bigint NOT NULL,
    CONSTRAINT groups_courses_pkey PRIMARY KEY (group_id, course_id),
    CONSTRAINT fk_course_id FOREIGN KEY (course_id)
        REFERENCES university.courses (course_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT fk_group_id FOREIGN KEY (group_id)
        REFERENCES university.groups (group_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS university.course_group
    OWNER to postgres;