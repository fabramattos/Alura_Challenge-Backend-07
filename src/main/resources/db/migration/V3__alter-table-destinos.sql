ALTER TABLE destino
ADD COLUMN foto1Url varchar(500) not null;

ALTER TABLE destino
ADD COLUMN foto2Url varchar(500) not null;

ALTER TABLE destino
ADD COLUMN meta text;

ALTER TABLE destino
ADD COLUMN descricao text;

UPDATE destino
SET foto1Url = foto;

ALTER TABLE destino
DROP COLUMN foto;