FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/backendTemplate-0.0.1-SNAPSHOT.jar backendTemplate.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","backendTemplate.jar"]
