FROM maven:3.6.0-jdk-11 AS build
WORKDIR /usr/src/app
COPY src ./src
COPY pom.xml .
RUN mvn clean package


FROM openjdk:11 AS deploy
WORKDIR /app
COPY --from=build /usr/src/app/target/streams-processor-0.1-jar-with-dependencies.jar .
EXPOSE 8095
ENTRYPOINT ["java","-jar","/app/soim-streams-processor-0.1-jar-with-dependencies.jar"]