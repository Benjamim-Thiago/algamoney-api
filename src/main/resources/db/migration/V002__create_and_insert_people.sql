CREATE TABLE people(
	id BIGSERIAL PRIMARY KEY ,
	name VARCHAR(100) NOT NULL,
	status BOOLEAN NOT NULL,
	place VARCHAR(100),
	number VARCHAR(10),
	complement VARCHAR(50),
	neighborhood VARCHAR(50),
	zipcode VARCHAR(8),
	city VARCHAR(50),
	state VARCHAR(30)
);

insert into people(name, status)values('Benjamim Thiago', 'true');
insert into people(name, status, place, number, neighborhood, zipcode, city, state)values('Crispilino', 'true', 'Rua José X', '4878', 'Centro', '64000300', 'Teresina', 'Piauí');
insert into people(name, status)values('José Arlequin', 'true');
insert into people(name, status)values('Amanda Nunes', 'true');
insert into people(name, status)values('Sheldon Couper', 'true');
insert into people(name, status)values('Shal morffi', 'true');
insert into people(name, status)values('Arlindo Marques', 'false');
insert into people(name, status)values('Lyoto Mashida', 'true');
insert into people(name, status)values('Jon Jones', 'false');
insert into people(name, status)values('José Aldo', 'false');
