# Use Maven image to build the project
FROM maven:3.9-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Build the project (this will compile the code and create the JAR file)
RUN mvn clean package -DskipTests

# Use the openjdk image to run the application
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Expose the port the app will run on
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "demo.jar"]
