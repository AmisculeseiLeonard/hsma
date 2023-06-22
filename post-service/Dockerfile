FROM amazoncorretto:17.0.7-alpine
EXPOSE 8761
ADD build/libs/*.jar post-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/post-service-0.0.1-SNAPSHOT.jar"]