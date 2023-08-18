CREATE TABLE IF NOT EXISTS role (
    id bigint not null auto_increment,
    nome varchar(100) not null,

    primary key(id)
);

INSERT INTO role(nome) VALUES('ADMIN');
INSERT INTO role(nome) VALUES('USER');
