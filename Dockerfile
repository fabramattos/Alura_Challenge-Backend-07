FROM openjdk:17
WORKDIR /app
COPY  build/libs/*.jar /app/app.jar
EXPOSE 8080

# variáveis de ambiente em -prod.yml
ENV MYSQL_URL jdbc:${MYSQL_URL}
ENV MYSQLUSER ${MYSQLUSER}
ENV MYSQLPASSWORD ${MYSQLPASSWORD}
ENV JWT_SECRET ${JWT_SECRET}
CMD java -XX:+UseContainerSupport -jar app.jar

# docker build -t jornada-milhas-api .