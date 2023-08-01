ALTER TABLE destinos
ADD COLUMN foto1Url varchar(500) not null;

ALTER TABLE destinos
ADD COLUMN foto2Url varchar(500) not null;

ALTER TABLE destinos
ADD COLUMN meta varchar(160) not null;

ALTER TABLE destinos
ADD COLUMN descricao varchar(1024);

UPDATE destinos
SET foto1Url = foto;

ALTER TABLE destinos
DROP COLUMN foto;