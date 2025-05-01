# Use a JRE base image for runtime
FROM eclipse-temurin:21-jre as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built on the host into the /app directory in the image
COPY CA1/part2/gradle_basic_demo/build/libs/basic_demo-0.1.0.jar /app/basic_demo-0.1.0.jar

# Expose the port the server will use
EXPOSE 59001

# Command to run the chat server when the container starts
ENTRYPOINT ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
