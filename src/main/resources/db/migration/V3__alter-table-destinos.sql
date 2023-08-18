ALTER TABLE destino
ADD COLUMN foto1Url varchar(500) not null;

ALTER TABLE destino
ADD COLUMN foto2Url varchar(500) not null;

ALTER TABLE destino
ADD COLUMN meta varchar(160) not null;

ALTER TABLE destino
ADD COLUMN descricao varchar(1024);

UPDATE destino
SET foto1Url = foto;

ALTER TABLE destino
DROP COLUMN foto;