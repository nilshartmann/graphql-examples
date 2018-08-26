FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY build/libs/shopservice-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9010
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]