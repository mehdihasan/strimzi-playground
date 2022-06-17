FROM maven:3.8.1-adoptopenjdk-11 AS build
WORKDIR /usr/src/app
COPY src ./src
COPY pom.xml .
RUN mvn clean package -DskipTests


FROM adoptopenjdk/openjdk11:alpine AS deploy
RUN mkdir /app
COPY --from=build /usr/src/app/target/*.jar /app/application.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","application.jar"]
#CMD ["--spring.profiles.active=postgres"]