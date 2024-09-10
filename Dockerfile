# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the host to the container
COPY target/challenge-booking-room-0.0.1-SNAPSHOT.jar /app/challenge-booking-room.jar

# Expose the port the app runs on
EXPOSE 8081

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "challenge-booking-room.jar"]
