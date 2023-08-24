# Projeto API de Destinos de Viagem
Este repositório contém o código-fonte e as configurações necessárias para executar a API de Destinos de Viagem desenvolvida como parte do Challenge Backend 7ª edição da Alura.
A API fornece informações sobre destinos de viagem, depoimentos de viajantes e integração com uma IA para aprimorar a experiência do usuário.

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
   Caso precise alterar algo, lembre-se de alterar também em Application.yml.

2. Execute o seguinte comando na raiz do projeto para iniciar o contêiner do MySQL:
```
docker-compose up -d mysql
```

### Configuração da Aplicação
1. Abra o arquivo src/main/resources/application.yml.
2. Caso tenha alterado algo no Docker-compose.yml, configure de acordo o acesso ao mySQL.
3. Configure a chave secreta da OpenAI, substituindo os valores 123456 pelos valores reais ou configurando em variáveis de sistema.

### Executando a Aplicação
1. Na raiz do projeto, execute o seguinte comando para compilar e executar a aplicação:
```
./gradlew bootRun
```
2. A aplicação estará disponível em http://localhost:8080.

### Observações
Caso não tenha uma conta na openAi para utilizar sua chave secreta, o GPT não será utilizado na API
