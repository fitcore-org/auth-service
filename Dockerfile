# Etapa 1: Build da aplicação com Gradle e cache ativado
FROM gradle:8.7.0-jdk17 AS build
WORKDIR /app

COPY . .
# Executa o Gradle no subprojeto 'app' com cache
RUN gradle -p app bootJar --no-daemon --build-cache --parallel

# Extração das camadas da aplicação
FROM build as extractor
WORKDIR /app
RUN java -Djarmode=layertools -jar app/build/libs/*SNAPSHOT.jar extract

# Imagem final, montada em camadas para máximo aproveitamento do cache
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia as camadas separadamente 
COPY --from=extractor /app/dependencies/ ./
COPY --from=extractor /app/spring-boot-loader/ ./
COPY --from=extractor /app/snapshot-dependencies/ ./
COPY --from=extractor /app/application/ ./

EXPOSE 8080

# Executa a aplicação a partir das camadas extraídas
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]