FROM openjdk:17
WORKDIR /app
COPY  build/libs/*.jar /app/app.jar

ENV MYSQL_URL=jdbc:mysql://localhost:8080/database
ENV MYSQLUSER=root
ENV MYSQLPASSWORD=root
ENV JWT_SECRET=minha_chave_secreta

EXPOSE 8080

ENTRYPOINT["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]

# docker build -t jornada-milhas-api .