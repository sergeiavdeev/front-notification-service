FROM alpine/java:21-jdk

WORKDIR /app

ADD target/front-notification-service-0.0.1-SNAPSHOT.jar .
COPY www_ttc-tops_ru.crt .
COPY 14776967.key .

CMD ["java", "-jar", "front-notification-service-0.0.1-SNAPSHOT.jar"]