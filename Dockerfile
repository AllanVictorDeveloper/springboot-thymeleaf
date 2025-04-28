# Etapa 1: Build com Maven
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src ./src

# Faz o build da aplicação (gera o JAR)
RUN mvn clean package -DskipTests

# Etapa 2: Runtime com JDK leve
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o JAR gerado da imagem anterior
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENV TZ=America/Sao_Paulo

CMD ["java", "-jar", "app.jar"]