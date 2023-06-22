FROM amazoncorretto:17.0.7-alpine
EXPOSE 8081
ADD build/libs/*.jar UserManagementService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/UserManagementService-0.0.1-SNAPSHOT.jar"]