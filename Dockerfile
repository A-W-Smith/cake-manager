FROM openjdk:8-jdk-alpine
COPY ./target/cake-manager.war cake-manager.war
ENTRYPOINT ["java","-jar","/cake-manager.war"]