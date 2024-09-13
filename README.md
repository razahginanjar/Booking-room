# Final Project Booking Room API

### Description

The Room Booking API is designed to replace the manual room booking process 
at Enigma Camp with a digital-based solution. This application simplifies 
the management and availability of room resources, ensuring efficient communication 
and accessibility for all employees.

The goal of this API is to streamline room booking operations and provide real-time 
availability information to employees, enabling them to make reservations quickly 
and easily.

### Features

- Room booking accessible to all employees
- Users can view room availability
- Admin can manage all master data
- Room booking approval by General Affairs
- Admin and General Affairs can download reports in CSV format

### Documentation

To view the full API documentation, navigate to the following endpoint after starting the application: \
http://localhost:8081/swagger-ui.html

### Instalation Guide

1. Ensure you have installed at least Java Development Kit (JDK) version 17 and Maven on your computer.
2. Clone this repository to your local machine:
``` bash
git clone https://github.com/razahginanjar/Booking-room.git
```
3. Open a terminal or command prompt and navigate to the project directory where you saved the files.
4. Resolve all dependencies:
```bash
mvn dependency:resolve
```
5. Update and install:
```bash
mvn clean install
```
6. If you wanna mess with docker (thing we ain't gonna do, but if you figured it ou pls let us know)
```bash
docker compose up --build
```
### INSTRUCTIONS
Before you run the app, you should makes ure of the application.properties configuration such as the email sender(email that have mailjet account), or the database name.

# System Requirements

## Java
#### Java Version: 21
The project requires JDK 21 for compiling and running.
## Maven
#### Maven Compiler Plugin
The configuration specifies maven.compiler.source and maven.compiler.target both set to 21, indicating that Maven 3.x is suitable for building the project.
## Dependencies
#### Spring Boot 3.3.3
#### PostgreSQL 14 & PostgreSQL JDBC Driver
#### Micrometer & Prometheus
#### JUnit & Mockito
#### Mailjet Client
#### OpenCSV
#### Springdoc OpenAPI
#### Lombok
#### Spring Boot DevTools
#### Build Tools
#### Spring Boot Maven Plugin
#### Asciidoctor Maven Plugin
## Repositories
#### Spring Milestones
#### Maven Central

# Recommended System Setup
- JDK 21: Install JDK 21 to compile and run the application.
- Maven 3.x: Ensure Maven is installed and configured properly.
- PostgreSQL 14 Database: Set up PostgreSQL if the application requires a database.
- Internet Access: Required to download dependencies from repositories.
- IDE: It’s beneficial to use an IDE like IntelliJ IDEA, Eclipse, or VSCode that supports Java and Maven.
- **Ensure you have correctly configured the `application.properties` file.**

# Developer Team

- Carlos Richard **Gerald**ine ~ _allmyfriends_
- **Ferdi**nand Johannes Robert Simanjuntak
- Muhammad **Rasyad**dany Prasetyo
- **Razah** Deden Ginanjar