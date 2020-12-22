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

insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(12754, '2020-12-01', '2020-12-05', 'Office Supplies for office in LA', null, 2, 5, 2, 4);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(20058, '2020-12-02', '2020-12-07', 'Desk for office in New York', null, 2, 6, 2, 4);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(35098, '2020-12-03', '2020-12-09', 'Monitor with docking station', null, 3, 5, 3, 4);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(35431, '2020-12-04', '2020-12-10', 'Microsoft SQL Server Enterprise', null, 3, 6, 2, 4);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(6059, '2020-12-05', '2020-12-11', 'Whiteboard for second office', null, 4, 5, 2, 4);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(10930, '2020-12-06', '2020-12-12', 'Oracle Java Associates Certification Exam', null, 4, 6, 3, 4);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(5098, '2020-12-07', '2020-12-13', 'Gas to travel to Johns Creek to deliver supplies', null, 4, 5, 2, 2);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(10079, '2020-12-09', '2020-12-14', 'La Qinta Inn stay in for Tampa Florida Project', null, 2, 6, 2, 1);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(4379, '2020-12-10', '2020-12-15', 'Hotel stay while on secondary mission', null, 3, 5, 3, 1);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(9312, '2020-12-11', '2020-12-16', 'Stayed at Holiday Inn when working in San Adreas', null, 3, 6, 2, 1);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(7580, '2020-12-12', '2020-12-17', 'Unexpectd issue with Air Handler, had to AirBNB', null, 2, 5, 2, 1);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(3590, '2020-12-13', '2020-12-18', 'Dinner with CEO', null, 3, 6, 3, 3);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(2039, '2020-12-14', '2020-12-19', 'Bojangles for approved employee gathering at Johns Creek', null, 4, 5, 2, 3);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(1986, '2020-12-15', '2020-12-20', 'Captain Ds lunch while working in field at Johns Creek', null, 2, 6, 2, 3);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(569, '2020-12-16', '2020-12-21', 'McDonalds lunch while in the field', null, 3, 5, 2, 3);
insert into ers.reimbursements(amount , submitted, resolved, description, receipt, author, resolver, status_id, type_id) values(25987, '2020-12-17', '2020-12-22', 'Food for end of year party', null, 4, 6, 3, 3);

--truncate table ers.reimbursements;
--SELECT * FROM ERS.USERS WHERE id = 1;