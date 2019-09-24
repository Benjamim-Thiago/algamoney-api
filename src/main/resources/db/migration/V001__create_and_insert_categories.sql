CREATE TABLE categories(
	id BIGSERIAL PRIMARY KEY ,
	name VARCHAR(100) UNIQUE NOT NULL
);

insert into categories(name)values('LAZER');
insert into categories(name)values('ALIMENTAÇÃO');
insert into categories(name)values('SUPERMERCADO');
insert into categories(name)values('FARMACIA');
insert into categories(name)values('OUTROS');
