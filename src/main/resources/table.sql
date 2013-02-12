create table people (
	id int auto_increment not null primary key,
	name varchar(50) not null,
	surname varchar(50) not null,
	city varchar(50),
	age int
);

alter table people
	add constraint people_uq
	unique (name, surname);
	