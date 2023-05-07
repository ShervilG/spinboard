FROM openjdk:11-jdk-slim as builder
WORKDIR /app
COPY . .
RUN ./mvnw -B -DskipTests clean package

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
