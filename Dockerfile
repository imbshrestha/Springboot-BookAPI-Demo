# --- Stage 1: Build the Application ---
# Use an official Maven image with a specific JDK version to build the project.
# Naming this stage 'build' allows us to reference it later.
FROM maven:3.9.8-eclipse-temurin-17 AS build

# Set the working directory inside the container for our build steps.
WORKDIR /app

# Copy the pom.xml file first. This leverages Docker's layer caching.
# Dependencies will only be re-downloaded if pom.xml changes.
COPY pom.xml .

# Copy the entire source code into the container.
COPY src ./src

# Run the Maven package command to compile the code and create the executable JAR.
# -DskipTests is included to speed up the build process in the container,
# as tests should ideally be run before this stage.
RUN mvn clean package -DskipTests


# --- Stage 2: Create the Final Runtime Image ---
# Use a lightweight, production-ready Java Runtime Environment (JRE) image.
# This results in a smaller and more secure final image than one with the full JDK.
FROM eclipse-temurin:17-jre-jammy

# Set the working directory for the runtime application.
WORKDIR /app

# Copy only the compiled JAR file from the 'build' stage into the final image.
# This is the core of the multi-stage build, keeping the final image clean.
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the Spring Boot application listens on.
EXPOSE 8080

# Define the command to run the application when the container starts.
# This is the final command that brings your API to life.
ENTRYPOINT ["java", "-jar", "app.jar"]