FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/rating-service-cab-aggregator-0.0.1-SNAPSHOT.jar /app/rating-service.jar

ENTRYPOINT ["java", "-jar", "rating-service.jar"]