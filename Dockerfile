# Etapa 1: Build da aplicação
FROM gradle:8.7.0-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle -p app bootJar --no-daemon

# Etapa 2: Imagem final
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]