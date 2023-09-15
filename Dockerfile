FROM openjdk:17
WORKDIR /app
COPY  build/libs/*.jar /app/app.jar
EXPOSE 8080

ENV MYSQL_URL=jdbc:mysql://localhost:8080/database
ENV MYSQLUSER=root
ENV MYSQLPASSWORD=root
ENV JWT_SECRET=minha_chave_secreta

CMD java -XX:+UseContainerSupport -jar app.jar

# docker build -t jornada-milhas-api .