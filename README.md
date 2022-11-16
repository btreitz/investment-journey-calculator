# Investment Journey Calculator

\>\> https://investmentjourneycalc.com/ <<

A Fullstack Web-Application to calculate long term returns for investment plans. The calculator differs from other
conventional investment calculators by offering the possibility to seperate the investment journey into multiple phases
with different parameters instead of assuming the parameters are valid for the entire investment journey.

## Technologies

Backend:

- Java 11
- [Spring Boot 2](https://spring.io/projects/spring-boot)

Frontend:

- JavaScript
- [Thymeleaf](https://www.thymeleaf.org/)

Testing:

- [JUnit 5 Jupiter](https://junit.org/junit5/) (using maven)

Deployment & Hosting:

- [Railway](https://railway.app/)
- [Docker](https://www.docker.com/)

## Prerequisites

| Tool  | Version |
| ----- | ------- |
| Java  | 11      |
| Maven | 3.8.x   |

## Run

Start the Tomcat Server by running the Spring Boot application

```bash
mvn spring-boot:run
```

By default, the server will start on port [8080](http://localhost:8080/)

## Test

Run all tests:

```bash
mvn test
```

Run a specific test class (replace '[...]'):

```bash
mvn test -Dtest=[test-class-name]
```

## Docker

Build the Docker image:

```bash
docker build -t investment-journey-calc .
```

Run the Docker container:

```bash
docker run -p 8080:8080 investment-journey-calc
```

Open port [localhost:8080](http://localhost:8080/)