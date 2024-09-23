FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/Spring-BookLibtary-0.0.1-SNAPSHOT.jar /app/Spring-BookLibtary.jar

CMD ["java", "-jar", "/app/Spring-BookLibtary.jar"]