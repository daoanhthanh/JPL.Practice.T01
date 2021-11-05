drop database if exists problem02
create database problem02;
use problem02;

drop table if exists employees;
drop table if exists departments;
drop table if exists working_histories;

create table employees (
	emp_no INT not null auto_increment,
	birth_date DATE not null ,
	first_name VARCHAR(50) not null ,
	last_name VARCHAR(50) not null ,
	gender ENUM('MALE','FEMALE','OTHER') default 'OTHER',
	hired_date DATE NOT NULL,
	primary key (emp_no)
);

create table departments (
	dept_no INT NOT NULL AUTO_INCREMENT,
	dept_name VARCHAR(50),
	description VARCHAR(50),
	PRIMARY KEY (dept_no)
);

create table working_histories (
	dept_no INT NOT NULL ,
	emp_no INT NOT NULL ,
	from_date DATE NOT NULL ,
	to_date DATE,
	FOREIGN KEY (dept_no) REFERENCES departments(dept_no),
	FOREIGN KEY (emp_no) REFERENCES employees(emp_no)

);

# employees
/* insert into employees (first_name, last_name, gender, birth_date, hired_date) values ('Barri', 'Gogay', 'MALE', '1994-08-21 15:58:00', '1989-10-19 21:21:06');
insert into employees (first_name, last_name, gender, birth_date, hired_date) values ('Rozella', 'Espadater', 'MALE', '1978-08-09 07:54:54', '1982-09-13 02:02:28');
insert into employees (first_name, last_name, gender, birth_date, hired_date) values ('Giffie', 'Leneve', 'OTHER', '1975-11-07 18:45:55', '1999-06-23 19:47:32');
insert into employees (first_name, last_name, gender, birth_date, hired_date) values ('Lavinie', 'O''Heffernan', 'OTHER', '1988-01-25 04:01:28', '1980-04-06 18:07:49');
insert into employees (first_name, last_name, gender, birth_date, hired_date) values ('Rodolphe', 'Urlin', 'FEMALE', '1973-08-05 02:09:29', '1988-10-26 22:01:51');
insert into employees (first_name, last_name, gender, birth_date, hired_date) values ('Brewer', 'Labarre', 'FEMALE', '1970-06-23 19:27:13', '1979-01-09 15:44:05');
insert into employees (first_name, last_name, gender, birth_date, hired_date) values ('Marielle', 'Bonallick', 'FEMALE', '1973-08-12 00:26:15', '2000-04-06 06:54:08');
insert into employees (first_name, last_name, gender, birth_date, hired_date) values ('Tracy', 'Willingam', 'FEMALE', '1982-03-12 15:44:28', '1992-02-01 05:24:56');
insert into employees (first_name, last_name, gender, birth_date, hired_date) values ('Leela', 'Arnot', 'MALE', '1970-05-20 15:41:48', '1975-01-12 22:12:06');
insert into employees (first_name, last_name, gender, birth_date, hired_date) values ('Suzi', 'Ennever', 'MALE', '1980-12-17 13:53:11', '1999-10-15 05:52:42'); */

