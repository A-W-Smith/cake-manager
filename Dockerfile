FROM openjdk:8-jdk-alpine
COPY /home/runner/.m2/repository/com/waracle/cake-manager/1.0-SNAPSHOT/cake-manager-1.0-SNAPSHOT.war cake-manager.war
ENTRYPOINT ["java","-jar","/cake-manager.war"]