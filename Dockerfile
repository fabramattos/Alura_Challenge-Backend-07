FROM openjdk:17
WORKDIR /app
COPY  build/libs/*.jar /app/app.jar

#ENV DB_URL="jdbc:mysql://\${DB_HOST:localhost}:\${DB_PORT:3306}/jornadamilhas?createDatabaseIfNotExist=true"
#ENV DB_USER=root
#ENV DB_PASSWORD=root
#ENV JWT_SECRET=123456

EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar","-e DB_URL=$MYSQL_URL -e DB_USER=$MYSQLUSER -e DB_PASSWORD=$MYSQLPASSWORD -e JWT_SECRET=$JWT_SECRET"]
CMD java -XX:+UseContainerSupport -jar app.jar

# docker build -t jornada-milhas-api .