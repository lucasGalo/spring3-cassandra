# Etapa 1: Build da aplicação com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia pom.xml e baixa dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte
COPY src ./src

# Compila e gera o jar sem rodar testes
RUN mvn clean package -DskipTests -B

# Etapa 2: Runtime
FROM openjdk:21-ea
WORKDIR /app

# Copia o jar gerado
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
