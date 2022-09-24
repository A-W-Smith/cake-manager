FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} cake-manager.war
ENTRYPOINT ["java","-jar","/cake-manager.war"]