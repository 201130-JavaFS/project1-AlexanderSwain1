CREATE TABLE ers.user_roles (
	id serial NOT NULL,
	"role" varchar(10) NOT NULL,
	CONSTRAINT user_roles_pk PRIMARY KEY (id)
);

CREATE TABLE ers.users (
	id serial NOT NULL,
	username varchar(50) NOT NULL,
	"password" varchar(50) NOT NULL,
	first_name varchar(100) NOT NULL,
	last_name varchar(100) NOT NULL,
	email varchar(150) NOT NULL,
	role_id int NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id),
	CONSTRAINT users_username_un UNIQUE (username),
	CONSTRAINT users_email_un UNIQUE (email),
	CONSTRAINT role_id_fk FOREIGN KEY(role_id) REFERENCES ers.user_roles(id)
);

CREATE TABLE ers.reimbursement_status (
	id serial NOT NULL,
	status varchar(10) NOT NULL,
	CONSTRAINT reimbursement_status_pk PRIMARY KEY (id)
);

CREATE TABLE ers.reimbursement_type (
	id serial NOT NULL,
	"type" varchar(10) NOT NULL,
	CONSTRAINT reimbursement_type_pk PRIMARY KEY (id)
);

CREATE TABLE ers.reimbursements (
	id serial NOT NULL,
	amount bigint NOT NULL,
	submitted timestamp(0) NOT NULL,
	resolved timestamp(0) NULL,
	description varchar(250) NULL,
	receipt bytea NULL,
	author int NOT NULL,
	resolver int NULL,
	status_id int NOT NULL,
	type_id int NOT NULL,
	CONSTRAINT reimbursement_pk PRIMARY KEY (id),
	CONSTRAINT author_fk FOREIGN KEY(author) REFERENCES ers.users(id),
	CONSTRAINT resolver_fk FOREIGN KEY(resolver) REFERENCES ers.users(id),
	CONSTRAINT status_id_fk FOREIGN KEY(status_id) REFERENCES ers.reimbursement_status(id),
	CONSTRAINT type_id_fk FOREIGN KEY(type_id) REFERENCES ers.reimbursement_type(id)
);

insert into ers.user_roles(role) values ('EMPLOYEE');
insert into ers.user_roles(role) values ('MANAGER');

insert into ers.reimbursement_status(status) values ('PENDING');
insert into ers.reimbursement_status(status) values ('APPROVED');
insert into ers.reimbursement_status(status) values ('DENIED');

insert into ers.reimbursement_type(type) values ('LODGING');
insert into ers.reimbursement_type(type) values ('TRAVEL');
insert into ers.reimbursement_type(type) values ('FOOD');
insert into ers.reimbursement_type(type) values ('OTHER');

insert into ers.users(username, password, first_name, last_name, email, role_id) values('John', 'úñGªŽr=õ÷]Ý©ý!¼', 'John', 'Taylor', 'JohnTaylor@armyspy.com', 1);--password: Password4John
insert into ers.users(username, password, first_name, last_name, email, role_id) values('Adrean', 'úñGªŽr=õ÷]Ý©ý!¼', 'Adrean', 'Jackson', 'AndreaJackson@armyspy.com', 1);
insert into ers.users(username, password, first_name, last_name, email, role_id) values('Jennifer', 'úñGªŽr=õ÷]Ý©ý!¼', 'Jennifer', 'Wilson', 'JenniferWilson@armyspy.com', 1);
insert into ers.users(username, password, first_name, last_name, email, role_id) values('Lisa', 'úñGªŽr=õ÷]Ý©ý!¼', 'Lisa', 'Hutchins', 'LisaHutchins@armyspy.com', 1);

insert into ers.users(username, password, first_name, last_name, email, role_id) values('Brian', 'úñGªŽr=õ÷]Ý©ý!¼', 'Brian', 'Bryan', 'BrianBryan@armyspy.com', 2);
insert into ers.users(username, password, first_name, last_name, email, role_id) values('Martha', 'úñGªŽr=õ÷]Ý©ý!¼', 'Martha', 'Wisdom', 'MarthaWisdom@armyspy.com', 2);

--SELECT * FROM ERS.USERS WHERE id = 1;