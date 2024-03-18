insert into university.groups(id, group_name) values (1,'-');
insert into university.groups(id, group_name) values (2,'df-45');
insert into university.groups(id, group_name) values (3,'we-09');
insert into university.groups(id, group_name) values (4,'dd-89');

insert into university.courses(id, course_name, course_description) values (1, 'English', 'Learn English very well');
insert into university.courses(id, course_name, course_description) values (2, 'Math', 'Learn Mathematics very well');
insert into university.courses(id, course_name, course_description) values (3, 'Biology', 'Learn Biology very well');

insert into university.students(id, group_id, student_name, student_surname) values (1, 2, 'Bob', 'Rojers'); 
insert into university.students(id, group_id, student_name, student_surname) values (2, 1, 'Mary', 'Kaily'); 
insert into university.students(id, group_id, student_name, student_surname) values (3, 3, 'Merlyn', 'Menson');

insert into university.teachers(id, course_id, teacher_name, teacher_surname) values (1, 3, 'GARRY', 'RO'); 
insert into university.teachers(id, course_id, teacher_name, teacher_surname) values (2, 1, 'ROBERT', 'KLON'); 
insert into university.teachers(id, course_id, teacher_name, teacher_surname) values (3, null, 'MEMFIC', 'BRODY');
insert into university.teachers(id, course_id, teacher_name, teacher_surname) values (6, null, 'MEMFIC1245', 'BRODY1223');

insert into university.timetable(id, pair_number, timetable_date, group_id, teacher_id) values (1, 2, '2023-05-25', 2, 1); 
insert into university.timetable(id, pair_number, timetable_date, group_id, teacher_id) values (2, 1, '2023-05-18', 2, 3); 
insert into university.timetable(id, pair_number, timetable_date, group_id, teacher_id) values (3, 3, '2023-05-15', 1, 2);
insert into university.timetable(id, pair_number, timetable_date, group_id, teacher_id) values (4, 3, '2023-05-18', 1, 3);
insert into university.timetable(id, pair_number, timetable_date, group_id, teacher_id) values (5, 2, '2023-05-18', 1, 2);
insert into university.timetable(id, pair_number, timetable_date, group_id, teacher_id) values (6, 3, '2023-05-18', 3, 2);
insert into university.timetable(id, pair_number, timetable_date, group_id, teacher_id) values (7, 2, '2023-05-18', 2, 3);