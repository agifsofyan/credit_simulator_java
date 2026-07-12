FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
RUN groupadd -r appuser && useradd -r -g appuser -u 1000 appuser
COPY --from=builder /app/target/credit-simulator.jar /app/credit-simulator.jar
RUN mkdir -p /app/data && chown -R appuser:appuser /app
USER appuser
ENTRYPOINT ["java", "-jar", "/app/credit-simulator.jar"]
