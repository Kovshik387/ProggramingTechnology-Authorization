FROM eclipse-temurin:21 as builder
MAINTAINER baeldung.com
COPY target/docker-java-jar-0.0.1-SNAPSHOT.jar ProgrammingTechnologyAuthorizationApplication.jar
ENTRYPOINT ["java","-jar","/app.jar"]