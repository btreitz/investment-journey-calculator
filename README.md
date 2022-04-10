# Investment Journey Calculator

\>\> https://investmentjourneycalc.com/ <<

A Fullstack Web-Application to calculate long term returns for investment plans. The calculator differs from other conventional investment calculators by offering the possibility to seperate the investment journey into multiple phases with different parameters instead of assuming the parameters are valid for the entire investment journey.

## Technologies

Backend:

- Java 11
- [Spring Boot 2](https://spring.io/projects/spring-boot)

Frontend:

- JavaScript

Testing:

- [JUnit 5 Jupiter](https://junit.org/junit5/) (using maven)

Deployment & Hosting:

- [Railway](https://railway.app/)

## Prerequisites

| Tool  | Version |
| ----- | ------- |
| Java  | 11      |
| Maven | 3.8.x   |

## Run

Start the Tomcat Server by running the Spring Boot application

        mvn spring-boot:run

By default, the server will start on port [8080](localhost:8080)

## Test

Run all tests:

        mvn test

Run a specific test class (replace '[...]'):

        mvn test -Dtest=[test-class-name]
