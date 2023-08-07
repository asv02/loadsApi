FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
VOLUME /tmp
ADD target/TruckApi-0.0.1-SNAPSHOT.jar TruckApi-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/TruckApi-0.0.1-SNAPSHOT.jar"]
