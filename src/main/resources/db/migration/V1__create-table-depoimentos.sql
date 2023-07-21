CREATE TABLE IF NOT EXISTS depoimentos (
id bigint not null auto_increment,
nome varchar(100) not null unique,
foto varchar(500) not null,
depoimento varchar(500) not null,

primary key(id)
);