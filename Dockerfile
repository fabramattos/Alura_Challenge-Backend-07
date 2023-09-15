FROM openjdk:17
WORKDIR /app
COPY  build/libs/*.jar /app/app.jar

#ENV DB_URL=${MYSQL_URL}
#ENV DB_USER=${MYSQLUSER}
#ENV DB_PASSWORD=${MYSQLPASSWORD}
#ENV JWT_SECRET=${JWT_SECRET}

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=prod"]
#CMD java -XX:+UseContainerSupport -jar app.jar

# docker build -t jornada-milhas-api .