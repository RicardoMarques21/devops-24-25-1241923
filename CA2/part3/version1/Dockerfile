# Build stage using Gradle with JDK 17
FROM gradle:jdk17 AS builder

# Set the working directory
WORKDIR /app

# Clone the application repository
RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git

# Change directory to the cloned project
WORKDIR /app/gradle_basic_demo

# Give execution permission to the Gradle wrapper
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build

# Final stage: lightweight image with only the JRE
FROM eclipse-temurin:17-jre

# Set the working directory in the final container
WORKDIR /app

# Copy the generated JAR from the builder stage
COPY --from=builder /app/gradle_basic_demo/build/libs/basic_demo-0.1.0.jar .

# Expose the port used by the chat server
EXPOSE 59001

# Command to run the chat server when the container starts
ENTRYPOINT ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
