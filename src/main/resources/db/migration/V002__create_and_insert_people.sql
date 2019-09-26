CREATE TABLE people(
	id BIGSERIAL PRIMARY KEY ,
	name VARCHAR(100) NOT NULL,
	status BIT NOT NULL
);

insert into people(name, status)values('Benjamim Thiago', '1');
insert into people(name, status)values('Raquel Evangelista', '1');
