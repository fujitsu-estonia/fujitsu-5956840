FROM --platform=linux/amd64 maven:3.9.3-eclipse-temurin-17-alpine AS build
WORKDIR /app

COPY ./backend/. .

RUN mvn clean install

FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine AS runtime

COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
