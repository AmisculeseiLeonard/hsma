FROM amazoncorretto:17.0.7-alpine
EXPOSE 8090
ADD build/libs/*.jar appointment-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/appointment-service-0.0.1-SNAPSHOT.jar"]