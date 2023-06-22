FROM amazoncorretto:17.0.7-alpine
EXPOSE 8090
ADD build/libs/*.jar api-gateway-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/api-gateway-service-0.0.1-SNAPSHOT.jar"]