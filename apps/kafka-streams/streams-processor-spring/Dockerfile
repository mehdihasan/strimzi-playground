FROM maven:3.8.1-adoptopenjdk-11 AS build
WORKDIR /usr/src/app
COPY src ./src
COPY pom.xml .
RUN mvn clean package -DskipTests

FROM adoptopenjdk/openjdk11:jre-11.0.9.1_1-alpine@sha256:b6ab039066382d39cfc843914ef1fc624aa60e2a16ede433509ccadd6d995b1f AS deploy
RUN mkdir /app
COPY --from=build /usr/src/app/target/*.jar /app/application.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","application.jar"]
#CMD ["--spring.profiles.active=postgres"]