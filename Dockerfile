# Stage 1: Build stage
FROM maven:3.9.9-amazoncorretto-21-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Run stage
FROM amazoncorretto:21-alpine3.20-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build /app/target/bms-server-0.0.1-SNAPSHOT.jar /app/bms-server-0.0.1-SNAPSHOT.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "bms-server-0.0.1-SNAPSHOT.jar"]
