# Use OpenJDK 17 as base image
FROM openjdk:21

# Set working directory
WORKDIR /app

# Copy JAR file into container
COPY target/*.jar app.jar

# Expose application port (change accordingly for each service)
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
