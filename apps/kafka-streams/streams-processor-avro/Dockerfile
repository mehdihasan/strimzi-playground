FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /usr/src/app
COPY src ./src
COPY pom.xml .
RUN mvn clean package


FROM openjdk:17-alpine AS deploy
WORKDIR /app
COPY --from=build /usr/src/app/target/streams-processor-avro-0.2-jar-with-dependencies.jar .
EXPOSE 8095
ENTRYPOINT ["java","-jar","/app/soim-streams-processor-0.2-jar-with-dependencies.jar"]