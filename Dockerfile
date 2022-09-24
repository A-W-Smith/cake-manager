FROM openjdk:8-jdk-alpine
COPY --from=build /cake-manager/target/cake-manager.war cake-manager.war
ENTRYPOINT ["java","-jar","/cake-manager.war"]