CREATE TABLE contacts (
	id BIGSERIAL PRIMARY KEY ,
    person_id BIGINT NOT NULL,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	phone_number VARCHAR(20) NOT NULL,
    FOREIGN KEY (person_id) REFERENCES people(id));

insert into contacts (id, person_id, name, email, phone_number) values (1, 1, 'Benjamim Thiago', 'ben@algamoney.com', '00 0000-0000');

