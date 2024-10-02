FROM openjdk:17-alpine as builder

WORKDIR /app

COPY target/rating-service-cab-aggregator-0.0.1-SNAPSHOT.jar /app/rating-service.jar

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=builder /app/rating-service.jar /app/rating-service.jar

ENTRYPOINT ["java", "-jar", "rating-service.jar"]
