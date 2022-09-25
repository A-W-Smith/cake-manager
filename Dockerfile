FROM maven:3.6.3 AS maven
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn clean install

FROM openjdk:8-jdk-alpine
ARG JAR_FILE=/usr/src/app/target/*.war
ADD . /src/main/resources/static
COPY --from=maven ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]