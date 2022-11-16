# syntax=docker/dockerfile:1

FROM openjdk:11
MAINTAINER basti.treitz@gmail.com
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw clean package

ARG JAR_FILE=target/*.jar \
COPY ${JAR_FILE} app.jar
COPY src ./src

CMD ["java", "-jar", "app.jar"]