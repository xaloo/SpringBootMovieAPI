FROM maven:3.8.4-openjdk-11-slim AS builder

COPY . /app
WORKDIR /app

RUN mvn clean package

FROM amazoncorretto:17.0.10

COPY --from=builder /app/target/demo-1.0.0.jar /app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
