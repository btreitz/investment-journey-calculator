# syntax=docker/dockerfile:1

FROM openjdk:11
MAINTAINER basti.treitz@gmail.com
WORKDIR /app

COPY .mvn ./.mvn
COPY src ./src
COPY mvnw pom.xml ./

RUN apt-get update && apt-get install dos2unix
RUN dos2unix ./mvnw
RUN ./mvnw clean package -DskipTests

ARG JAR_FILE=target/*.jar
RUN cp ${JAR_FILE} app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]