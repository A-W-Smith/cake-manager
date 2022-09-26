FROM maven:3.6.3 AS maven
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package

FROM openjdk:8-jdk-alpine
ARG JAR_FILE=/usr/src/app/target/cake-manager.jar
COPY --from=maven ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=noAuth", "-cp","/app.jar", "com.waracle.cakemgr.Application"]