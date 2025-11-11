# bajaj-assesment

# Bajaj Webhook Assessment App

This is a Spring Boot application built for the HealthRx webhook assessment.

## Features

- On startup, sends a POST request to generate a webhook.
- Based on the response, solves a SQL problem (decided by your registration number).
- Submits the final SQL query to the returned webhook URL using a JWT token.
- Runs automatically without any controller or manual trigger.

## Tech Stack

- Java 17
- Spring Boot 3.2.2
- Maven
- RestTemplate for HTTP calls

1. Clone the repository:

git clone https://github.com/your-username/bajaj-webhook.git
cd bajaj-webhook
Build the project:

bash
Copy code
mvn clean package
Run the JAR:
bash
Copy code
java -jar target/bajaj-webhook-0.0.1-SNAPSHOT.jar

The app will automatically generate the webhook, solve the SQL problem, and submit the solution.

