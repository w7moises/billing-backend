FROM eclipse-temurin:20

LABEL mentainer="w7moises@gmail.com"

WORKDIR /app

COPY target/billing-0.0.1-SNAPSHOT.jar /app/billing.jar

ENTRYPOINT ["java", "-jar", "billing.jar"]