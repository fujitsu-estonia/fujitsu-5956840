# Hotel application backend

Hotel application backend with REST-ful API written in `Java 17` and `Spring Boot`.

### Environment

Project is using [`Maven`](https://maven.apache.org/download.cgi) project manager

This project is using `Java 17` version, make sure you have it installed

For database is used H2 in-memory database - no manual installation steps needed

### Build

For generating jar:

```mvn clean install```

For generating jar, but also creating a docker image:

```mvn spring-boot:build-image```

Second option can be useful, if you want to run application with docker

### Run tests

```mvn clean test```

#### For calculating tests code coverage

```mvn verify``` then coverage data can be found under `target/site/jacoco` directory (as html file or in
xml/csv formats)

### Run application

Run the generated jar file with ```java -jar {path/to/hotel.jar}``` 

Or run with docker (see distribution/README.md)

### Useful links

#### Status

[`http://localhost:8090/actuator`](http://localhost:8080/actuator)

#### Swagger

[`http://localhost:8090/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)

#### H2 Console

[`http://localhost:8090/h2-console`](http://localhost:8080/h2-console)
