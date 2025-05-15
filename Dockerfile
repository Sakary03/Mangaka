FROM eclipse-temurin:17-jdk-alpine

# Install Maven
RUN apk add --no-cache maven

WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the app
RUN mvn clean package -DskipTests

# Run the app
CMD ["java", "-jar", "target/mangaka-0.0.1-SNAPSHOT.jar"]
