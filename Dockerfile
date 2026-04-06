# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only what we need for a reproducible build
COPY pom.xml .
COPY src ./src

# Install Node, restore Angular dependencies, and let Maven build the UI + API
RUN apt-get update \
    && apt-get install -y curl ca-certificates gnupg \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs \
    && cd src/main/UI \
    && npm ci \
    && cd /app \
    && mvn -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
