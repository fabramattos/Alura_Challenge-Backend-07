# Projeto API de Destinos de Viagem

Este repositório contém o código-fonte e as configurações necessárias para executar a API de Destinos de Viagem desenvolvida como parte do Challenge Backend 7ª edição da Alura.
A API fornece informações sobre destinos de viagem, depoimentos de viajantes e integração com uma IA para aprimorar a experiência do usuário.

| :placard: Vitrine.Dev |     |
| -------------  | --- |
| :sparkles: Nome        | **Projeto API de Destinos de Viagem**
| :label: Tecnologias | Kotlin, SpringBoot, Docker, mySQL, OpenAI
| :rocket: URL         |
| :fire: Desafio     | [Backend 7ª edição](https://www.alura.com.br/challenges/back-end-7/semana-01-classes-relacionamentos-depoimentos)

<!-- Inserir imagem com a #vitrinedev ao final do link -->
![](https://seranking.com/blog/wp-content/uploads/2021/01/404_01-min.jpg#vitrinedev)

## Tecnologias Utilizadas
O projeto foi desenvolvido utilizando as seguintes tecnologias e bibliotecas:
* **Spring Boot:** Framework Java para criação de aplicações web e APIs.
* **Spring Data JPA:** Biblioteca para acesso e manipulação de bancos de dados utilizando JPA.
* **Spring Validation:** Biblioteca para validação de dados de entrada.
* **Spring Web:** Biblioteca para criação de endpoints REST.
* **Spring Security:** Biblioteca para autenticação e autorização.
* **Jackson Module Kotlin:** Suporte para a serialização e desserialização de objetos Kotlin em JSON.
* **MySQL Connector:** Driver de conexão com o banco de dados MySQL.
* **Flyway:** Biblioteca para controle de versão e migração de bancos de dados.
* **Kotlin Reflect:** Biblioteca para introspecção e reflexão em Kotlin.
* **Java JWT:** Biblioteca para criação e validação de JSON Web Tokens (JWT).
* **OpenAI Client:** Biblioteca para integração com a API da OpenAI.
* **Ktor Client OkHttp:** Cliente HTTP para o framework Ktor.
* **Springdoc OpenAPI:** Biblioteca para geração de documentação OpenAPI.


## Configuração do Projeto para Execução Local
### Pré-requisitos
Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:
* [**Java Development Kit (JDK)**](https://www.oracle.com/java/technologies/downloads/)
* [**Docker**](https://www.docker.com/get-started/)


### Configuração do Banco de Dados
1. Abra o arquivo docker-compose.yml e verifique as configurações do serviço MySQL.
2. Execute o docker-compose via IDE ou via terminal.
   - via terminal: execute o seguinte comando na pasta onde se encontra o docker-compose.yml para iniciar o container com MySQL:
```
docker-compose up -d mysql
```

### Configuração da Aplicação
1. Abra o arquivo src/main/resources/application.yml.
2. Caso tenha alterado algo no Docker-compose.yml, faça as alterações necessárias para acesso ao mySQL.
3. Configure a chave secreta da OpenAI, substituindo os valores 123456 pelos valores reais ou configurando em variáveis de sistema.
 - obs: Caso não tenha uma conta na openAi para utilizar sua chave secreta, o GPT não será utilizado na API


## Executando a Aplicação
1. Na IDE, execute a main
2. A aplicação estará disponível em http://localhost:8080.

## Testando a Api / Acesso aos Endoints
Para testar a API, pode-se utilizar Postman ou ferramenta semelhante acesando a url http://localhost:8080/ (endpoint desejado).
Porém, para facilitar, está disponibilizado o Swagger-UI

### Acessando via Swagger-ui
A partir do Swagger UI, **diretamente no navegador** você pode explorar todos os endpoints disponíveis, visualizar os parâmetros necessários para cada requisição, bem como testar cada endpoint interativamente.
1. Após a API estar em execução, acesse: http://localhost:8080/swagger-ui/index.html
2. A API possui segurança de Token JWT e Role. Informações sobre Login e recebimento de Token JWT estarão no Swagger-UI
