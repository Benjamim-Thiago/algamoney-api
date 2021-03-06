CREATE TABLE postings(
	id BIGSERIAL PRIMARY KEY ,
	description VARCHAR(100) NOT NULL,
	expiration_date date NOT NULL,
	payment_date DATE,
	value DECIMAL(10, 2) NOT NULL,
	comments VARCHAR,
	type VARCHAR(20) NOT NULL,
	category_id BIGINT NOT NULL,
	person_id BIGINT NOT NULL,
	FOREIGN KEY (category_id) REFERENCES categories(id),
	FOREIGN KEY (person_id) REFERENCES people(id)
);

INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Salário mensal', '2018-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 1, 1);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Bahamas', '2018-02-10', '2018-02-10', 100.32, null, 'DESPESA', 2, 2);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Top Club', '2018-06-10', null, 120, null, 'RECEITA', 3, 3);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('CEMIG', '2018-02-10', '2018-02-10', 110.44, 'Geração', 'RECEITA', 3, 4);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('DMAE', '2018-06-10', null, 200.30, null, 'DESPESA', 3, 5);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Extra', '2018-03-10', '2018-03-10', 1010.32, null, 'RECEITA', 4, 6);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Bahamas', '2018-06-10', null, 500, null, 'RECEITA', 1, 7);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Top Club', '2018-03-10', '2018-03-10', 400.32, null, 'DESPESA', 4, 8);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Despachante', '2018-06-10', null, 123.64, 'Multas', 'DESPESA', 3, 9);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Pneus', '2018-04-10', '2018-04-10', 665.33, null, 'RECEITA', 5, 10);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Café', '2018-06-10', null, 8.32, null, 'DESPESA', 1, 5);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Eletrônicos', '2018-04-10', '2018-04-10', 2100.32, null, 'DESPESA', 5, 4);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Instrumentos', '2018-06-10', null, 1040.32, null, 'DESPESA', 4, 3);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Café', '2018-04-10', '2018-04-10', 4.32, null, 'DESPESA', 4, 2);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Lanche', '2020-02-10', null, 10.20, null, 'DESPESA', 4, 1);

INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Lanche', '2020-02-04', null, 10.20, null, 'DESPESA', 4, 1);
INSERT INTO postings (description, expiration_date, payment_date, value, comments, type, category_id, person_id) values ('Intercambio', '2020-02-10', null, 10000.20, null, 'DESPESA', 1, 1);
