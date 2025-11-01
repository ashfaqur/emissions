# Build stage
FROM alpine/java:21-jdk AS build
WORKDIR /app
# Copy all required files
COPY src ./src
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Build (and test) the app
RUN ./mvnw clean package

# Run stage
FROM alpine/java:21-jdk AS run
WORKDIR /app
# Copy the earlier built jar file
COPY --from=build /app/target/emissions-calculator.jar ./emissions-calculator.jar

ENTRYPOINT ["java", "-jar", "emissions-calculator.jar"]
