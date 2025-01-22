# Build container
FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

COPY gradlew gradlew
COPY gradle/ gradle/
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY src/ src/

RUN chmod +x gradlew
RUN ./gradlew build -x test

# Runtime container
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]