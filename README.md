*Mude o idioma:* [![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/fabramattos/Alura_Challenge-Backend-07/blob/master/README_pt-BR.md)

# Travel Destinations API Project

This repository contains the source code and configurations needed to run the Travel Destinations API developed as part of Alura's Backend Challenge 7th edition. 
The API provides information about travel destinations, traveler testimonials, and integration with AI to enhance user experience.

![koala](https://github.com/fabramattos/Alura_Challenge-Backend-07/assets/45768087/a851dfab-4a6d-4f84-860d-e0b5e2046ed3#vitrinedev)

## Stack
- Spring Boot (Data JPA, Validation, Web, Security)
- Jackson Module Kotlin
- MySQL Connector
- Flyway
- Kotlin Reflect
- Java JWT
- OpenAI Client
- Ktor Client OkHttp
- Swagger UI


## Setting Up the Project for Local Execution
### Prerequisites
Make sure you have the following tools installed on your machine:
* [**Java Development Kit (JDK)**](https://www.oracle.com/java/technologies/downloads/)
* [**Docker**](https://www.docker.com/get-started/)


### Starting the Application:
1. In src/main/resources/application.yml, configure the OpenAI secret key by replacing the values *123456* with your own key or by setting it as environment variables.<br>
Note: If you don't have an OpenAI account to use its secret key, GPT will not be used by the API.
2. Build the project with:
```
./gradlew clean build
```
3. In the terminal, in the project's root directory, generate a Docker image:
```
docker build -t jornada-milhas-api-dev -f Dockerfile-dev .
```
4. Open and run the Docker Compose file, or via terminal:
```
docker compose -p jornada-milhas-api-dev up
```
5. The application will be available at http://localhost:8080.

   

## Testing the API / Accessing Endpoints
To test the API, you can use Postman or a similar tool by accessing the URL http://localhost:8080/(desired_endpoint).<br>
However, to make it easier, Swagger-UI is available at http://localhost:8080 via your browser.

### Accessing via Swagger-UI
From Swagger UI, **directly in your browser**, you can explore all available endpoints, view the required parameters for each request, and test each endpoint interactively.<br>
After the API is running, go to: http://localhost:8080/<br>
The API has JWT Token and Role security. Information about login and receiving JWT Token will be available in Swagger-UI.
