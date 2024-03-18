DO $$ 
BEGIN 
  IF NOT EXISTS (SELECT 1 FROM university.groups) THEN
    INSERT INTO university.groups (id, group_name) VALUES (1, 'df-45');
    INSERT INTO university.groups (id, group_name) VALUES (2, 'sd-50');
    INSERT INTO university.groups (id, group_name) VALUES (3, 'xy-22');
    INSERT INTO university.groups (id, group_name) VALUES (4, 'ab-10');
    INSERT INTO university.groups (id, group_name) VALUES (5, 'pq-35');
    INSERT INTO university.groups (id, group_name) VALUES (23, 'we-85');
    INSERT INTO university.groups (id, group_name) VALUES (24, 'yu-65');
  END IF;
END $$;

DO $$ 
BEGIN 
  IF NOT EXISTS (SELECT 1 FROM university.students) THEN
    INSERT INTO university.students (id, group_id, student_name, student_surname) VALUES (1,  1,  'John', 'Doe');
    INSERT INTO university.students (id, group_id, student_name, student_surname) VALUES (2,  2,  'Jane', 'Smith');
    INSERT INTO university.students (id, group_id, student_name, student_surname) VALUES (3,  3,  'Alice', 'Johnson');
    INSERT INTO university.students (id, group_id, student_name, student_surname) VALUES (4,  4,  'Bob', 'Williams');
    INSERT INTO university.students (id, group_id, student_name, student_surname) VALUES (5,  23, 'Eva', 'Brown');
    INSERT INTO university.students (id, group_id, student_name, student_surname) VALUES (6,  24, 'Michael', 'Jones');
    INSERT INTO university.students (id, group_id, student_name, student_surname) VALUES (7,  1,  'Sophia', 'Miller');
    INSERT INTO university.students (id, group_id, student_name, student_surname) VALUES (8,  2,  'David', 'Davis');
    INSERT INTO university.students (id, group_id, student_name, student_surname) VALUES (9,  24, 'Emma', 'Moore');
    INSERT INTO university.students (id, group_id, student_name, student_surname) VALUES (10, 4,  'Oliver', 'Smith');
	END IF;
END $$;

DO $$ 
BEGIN 
  IF NOT EXISTS (SELECT 1 FROM university.courses) THEN
    INSERT INTO university.courses (id, course_name, course_description) VALUES (1,  'Mathematics', 'Basic mathematics course');
    INSERT INTO university.courses (id, course_name, course_description) VALUES (2,  'History', 'Introduction to World History');
    INSERT INTO university.courses (id, course_name, course_description) VALUES (3,  'Computer Science', 'Fundamentals of Computer Science');
    INSERT INTO university.courses (id, course_name, course_description) VALUES (4,  'Physics', 'Introduction to Physics');
    INSERT INTO university.courses (id, course_name, course_description) VALUES (5,  'English', 'English Language Basics');
    INSERT INTO university.courses (id, course_name, course_description) VALUES (6,  'Chemistry', 'Introduction to Chemistry');
    INSERT INTO university.courses (id, course_name, course_description) VALUES (7,  'Biology', 'Basic Biology Concepts');
    INSERT INTO university.courses (id, course_name, course_description) VALUES (8,  'Economics', 'Principles of Economics');
    INSERT INTO university.courses (id, course_name, course_description) VALUES (9,  'Psychology', 'Introduction to Psychology');
    INSERT INTO university.courses (id, course_name, course_description) VALUES (10, 'Sociology', 'Fundamentals of Sociology');
  END IF;
END $$;

DO $$ 
BEGIN 
  IF NOT EXISTS (SELECT 1 FROM university.course_group) THEN
    INSERT INTO university.course_group (group_id, course_id) VALUES (1, 1);
    INSERT INTO university.course_group (group_id, course_id) VALUES (1, 2);
    INSERT INTO university.course_group (group_id, course_id) VALUES (2, 1);
    INSERT INTO university.course_group (group_id, course_id) VALUES (2, 3);
    INSERT INTO university.course_group (group_id, course_id) VALUES (3, 4);
    INSERT INTO university.course_group (group_id, course_id) VALUES (3, 5);
    INSERT INTO university.course_group (group_id, course_id) VALUES (4, 6);
    INSERT INTO university.course_group (group_id, course_id) VALUES (4, 7);
    INSERT INTO university.course_group (group_id, course_id) VALUES (5, 8);
    INSERT INTO university.course_group (group_id, course_id) VALUES (5, 9);
    INSERT INTO university.course_group (group_id, course_id) VALUES (1, 10);
    INSERT INTO university.course_group (group_id, course_id) VALUES (2, 10);
    INSERT INTO university.course_group (group_id, course_id) VALUES (3, 10);
    INSERT INTO university.course_group (group_id, course_id) VALUES (4, 10);
    INSERT INTO university.course_group (group_id, course_id) VALUES (5, 10);
    INSERT INTO university.course_group (group_id, course_id) VALUES (1, 3);
    INSERT INTO university.course_group (group_id, course_id) VALUES (2, 5);
    INSERT INTO university.course_group (group_id, course_id) VALUES (3, 7);
    INSERT INTO university.course_group (group_id, course_id) VALUES (4, 9);
    INSERT INTO university.course_group (group_id, course_id) VALUES (5, 2);
  END IF;
END $$;

DO $$ 
BEGIN 
  IF NOT EXISTS (SELECT 1 FROM university.teachers) THEN
    INSERT INTO university.teachers (id, teacher_name, teacher_surname) VALUES (1, 'John', 'Doe');
    INSERT INTO university.teachers (id, teacher_name, teacher_surname) VALUES (2, 'Jane', 'Smith');
    INSERT INTO university.teachers (id, teacher_name, teacher_surname) VALUES (3, 'Alice', 'Johnson');
    INSERT INTO university.teachers (id, teacher_name, teacher_surname) VALUES (4, 'Bob', 'Williams');
    INSERT INTO university.teachers (id, teacher_name, teacher_surname) VALUES (5, 'Eva', 'Brown');
    INSERT INTO university.teachers (id, teacher_name, teacher_surname) VALUES (6, 'Michael', 'Jones');
    INSERT INTO university.teachers (id, teacher_name, teacher_surname) VALUES (7, 'Sophia', 'Miller');
    INSERT INTO university.teachers (id, teacher_name, teacher_surname) VALUES (8, 'David', 'Davis');
    INSERT INTO university.teachers (id, teacher_name, teacher_surname) VALUES (9, 'Emma', 'Moore');
    INSERT INTO university.teachers (id, teacher_name, teacher_surname) VALUES (10, 'Oliver', 'Smith');
  END IF;
END $$;

DO $$ 
BEGIN 
  IF NOT EXISTS (SELECT 1 FROM university.timetable) THEN
 
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (1, 1, '2023-05-25', 1, 1);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (2, 2, '2023-05-25', 2, 2);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (3, 3, '2023-05-25', 3, 3);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (4, 4, '2023-05-25', 4, 4);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (5, 1, '2023-05-26', 5, 5);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (6, 2, '2023-05-26', 1, 6);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (7, 3, '2023-05-26', 2, 7);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (8, 4, '2023-05-26', 3, 8);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (9, 1, '2023-05-27', 4, 9);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (10, 2, '2023-05-27', 5, 10);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (11, 3, '2023-05-27', 1, 1);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (12, 4, '2023-05-27', 2, 2);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (13, 1, '2023-05-28', 3, 3);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (14, 2, '2023-05-28', 4, 4);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (15, 3, '2023-05-28', 5, 5);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (16, 4, '2023-05-28', 1, 6);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (17, 1, '2023-05-29', 2, 7);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (18, 2, '2023-05-29', 3, 8);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (19, 3, '2023-05-29', 4, 9);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (20, 4, '2023-05-29', 5, 10);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (21, 2, '2023-05-25', 1, 6);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (22, 3, '2023-05-25', 2, 7);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (23, 4, '2023-05-25', 3, 8);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (24, 1, '2023-05-25', 4, 9);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (25, 2, '2023-05-26', 5, 10);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (26, 3, '2023-05-26', 1, 1);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (27, 4, '2023-05-26', 2, 2);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (28, 1, '2023-05-27', 3, 3);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (29, 2, '2023-05-27', 4, 4);
    INSERT INTO university.timetable (id, pair_number, timetable_date, group_id, teacher_id) VALUES (30, 3, '2023-05-27', 5, 5);
  END IF;
END $$;

DO $$ 
BEGIN 
  IF NOT EXISTS (SELECT 1 FROM university.users) THEN
	insert into university.users(id, user_name, password) values (1, 'user1', '$2a$10$bDV1tHmlNrAm5Ol1PcsnFeNKX8yXWXUgRgK7MLVbDHfoMB/Ds9dkS');
	insert into university.users(id, user_name, password) values (2, 'user2', '$2a$10$lFbTpsAKnZLL7DDXONVLm.r76g4aRS9bpmJ0CI32V67xLeElMEZKy');
 END IF;
END $$;

DO $$ 
BEGIN 
  IF NOT EXISTS (SELECT 1 FROM university.roles) THEN
	insert into university.roles(id, name) values(1, 'ROLE_USER');
	insert into university.roles(id, name) values(2, 'ROLE_ADMIN');
 END IF;
END $$;

DO $$ 
BEGIN 
  IF NOT EXISTS (SELECT 1 FROM university.users_roles) THEN
	insert into university.users_roles(user_id, role_id) values(1, 1);
	insert into university.users_roles(user_id, role_id) values(2, 2);
 END IF;
END $$;

