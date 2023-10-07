insert into university.groups(group_id, group_name) values (1,'-');
insert into university.groups(group_id, group_name) values (2,'df-45');
insert into university.groups(group_id, group_name) values (3,'we-09');
insert into university.groups(group_id, group_name) values (4,'dd-89');

insert into university.courses(course_id, course_name, course_description) values (1, 'English', 'Learn English very well');
insert into university.courses(course_id, course_name, course_description) values (2, 'Math', 'Learn Mathematics very well');
insert into university.courses(course_id, course_name, course_description) values (3, 'Biology', 'Learn Biology very well');

insert into university.students(student_id, group_id, student_name, student_surname) values (1, 2, 'Bob', 'Rojers'); 
insert into university.students(student_id, group_id, student_name, student_surname) values (2, 1, 'Mary', 'Kaily'); 
insert into university.students(student_id, group_id, student_name, student_surname) values (3, 3, 'Merlyn', 'Menson');

insert into university.teachers(teacher_id, course_id, teacher_name, teacher_surname) values (1, 2, 'GARRY', 'RO'); 
insert into university.teachers(teacher_id, course_id, teacher_name, teacher_surname) values (2, 1, 'ROBERT', 'KLON'); 
insert into university.teachers(teacher_id, course_id, teacher_name, teacher_surname) values (3, 3, 'MEMFIC', 'BRODY');