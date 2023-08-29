FROM openjdk:17
WORKDIR /app
COPY  build/libs/*.jar /app/app.jar
EXPOSE 8080
CMD java -XX:+UseContainerSupport -jar app.jar

# docker build -t jornada-milhas-api:v1.0 .