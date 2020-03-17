CREATE TABLE states (
	id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE cities (
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    state_id BIGINT NOT NULL
);

ALTER TABLE cities ADD CONSTRAINT fk_state FOREIGN KEY (state_id) REFERENCES states(id);

INSERT INTO states(name) VALUES ('PIAUÍ');
INSERT INTO states(name) VALUES ('MARANHÃO');
INSERT INTO states(name) VALUES ('CEARÁ');

INSERT INTO cities(name, state_id) VALUES ('TERESINA', 1);
INSERT INTO cities(name, state_id) VALUES ('PICOS', 1);
INSERT INTO cities(name, state_id) VALUES ('PIRIPIRI', 1);
INSERT INTO cities(name, state_id) VALUES ('OEIRAS', 1);
INSERT INTO cities(name, state_id) VALUES ('PARNAIBA', 1);
INSERT INTO cities(name, state_id) VALUES ('JAICOS', 1);
INSERT INTO cities(name, state_id) VALUES ('URUCÇUÍ', 1);


INSERT INTO cities(name, state_id) VALUES ('SÃO LUIS', 2);
INSERT INTO cities(name, state_id) VALUES ('TIMON', 2);
INSERT INTO cities(name, state_id) VALUES ('CAXIAS', 2);
INSERT INTO cities(name, state_id) VALUES ('CODÓ', 2);


INSERT INTO cities(name, state_id) VALUES ('FORTALEZA', 3);
INSERT INTO cities(name, state_id) VALUES ('NOVO ORIENTE', 3);
INSERT INTO cities(name, state_id) VALUES ('QUIXERAMOBIM', 3);



ALTER TABLE people DROP COLUMN city;
ALTER TABLE people DROP COLUMN state;

ALTER TABLE people ADD COLUMN city_id BIGINT;
ALTER TABLE people ADD CONSTRAINT fk_person_city FOREIGN KEY (city_id) REFERENCES cities(id);

UPDATE people SET city_id=1




