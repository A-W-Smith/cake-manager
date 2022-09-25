FROM openjdk:8-jdk-alpine
ARG JAR_FILE=/usr/src/app/target/*.war
COPY --from=maven ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]