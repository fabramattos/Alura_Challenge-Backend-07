CREATE TABLE IF NOT EXISTS destinos (
id bigint not null auto_increment,
foto varchar(500) not null,
nome varchar(100) not null,
preco  decimal(10, 2) not null,

primary key(id)
);