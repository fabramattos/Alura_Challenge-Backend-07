FROM openjdk:17
WORKDIR /app
COPY  build/libs/*.jar /app/app.jar
EXPOSE 8080

# docker build -t jornada-milhas-api .