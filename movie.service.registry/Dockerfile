FROM openjdk:17-alpine
COPY target/movie.service.registry-1.0-SNAPSHOT.jar registry.jar
ENTRYPOINT ["java","-jar","/registry.jar"]