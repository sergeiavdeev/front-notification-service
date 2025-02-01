FROM alpine/java:21-jdk

WORKDIR /app

ADD target/front-notification-service-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "front-notification-service-0.0.1-SNAPSHOT.jar"]