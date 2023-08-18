CREATE TABLE IF NOT EXISTS usuario (
    id bigint not null auto_increment,
    nome varchar(100) not null,
    login varchar(100) not null,
    senha  text not null,

    primary key(id)
);

INSERT INTO usuario(nome, login, senha) VALUES ('Melon Husk', 'admin@email.com', '$2a$10$UiEooQBNnGBGKvaeSdb5jejB14eZV.2lQJ.6snCoJwMyG9c69IjJ6');
INSERT INTO usuario(nome, login, senha) VALUES ('Reanu Keaves', 'user@email.com', '$2a$10$UiEooQBNnGBGKvaeSdb5jejB14eZV.2lQJ.6snCoJwMyG9c69IjJ6');