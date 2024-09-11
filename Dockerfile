# Use an official Maven image with OpenJDK 17 as a parent image for building the project
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and other necessary Maven files
COPY pom.xml /app/

# Copy the source code into the container
COPY src /app/src

# Run the Maven package command to build the jar, skipping tests and compilation
RUN mvn dependency:resolve && mvn package -DskipTests -DskipCompile

# Use a minimal JDK image to run the application
FROM openjdk:21

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file generated during the build stage
COPY --from=build /app/target/challenge-booking-room-0.0.1-SNAPSHOT.jar /app/challenge-booking-room.jar

# Expose the port the app runs on
EXPOSE 8081

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "challenge-booking-room.jar"]
