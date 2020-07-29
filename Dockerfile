FROM openjdk:14-alpine

EXPOSE 8080

ADD target/spring-0.0.1-SNAPSHOT.jar spring.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/spring.jar"]