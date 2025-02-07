# Mail Sender Application with Hexagonal Architecture

A Quarkus app for sending emails via AWS SQS messages. Uses Hexagonal Architecture for clean separation of concerns.

## Features

- Consumes email requests from AWS SQS
- Sends emails via SMTP
- Scheduled polling of messages
- Configurable via environment variables
- High test coverage (BDD + Unit tests)

## Hexagonal Structure

### Core Layers

- **Domain**: `domain/` (EmailRequest model, validation logic)
- **Application**: `application/` (EmailService, SqsConsumerService)

### Adapters

- **Incoming**: `presentation/` (SqsScheduler triggers processing)
- **Outgoing**: `infrastructure/` (SqsClientProducer, SMTP client)

## Project Structure

### Source Code

- `src/main/java/org/jfm/config/`: Configuration classes
- `src/main/java/org/jfm/domain/`: EmailRequest model
- `src/main/java/org/jfm/application/`: Business logic
- `src/main/java/org/jfm/infrastructure/`: AWS/SMTP implementations
- `src/main/java/org/jfm/presentation/`: Scheduler entry points

### Tests

- `src/test/java/org/jfm/test/`: Unit tests
- `src/test/java/org/jfm/test/`: BDD tests (Cucumber)

## Dependencies

- Quarkus Framework
- AWS SDK for SQS
- JavaMail (SMTP)
- JUnit 5, Mockito, Cucumber

## Configuration

Edit `src/main/resources/application.properties`:

```properties
# SMTP
quarkus.mailer.host=smtp.example.com  
quarkus.mailer.port=587  
quarkus.mailer.username=user@example.com  
quarkus.mailer.password=secret  

# AWS SQS
aws.sqs.queue-url=https://sqs.region.amazonaws.com/account-id/queue-name  
aws.region=us-east-1  
```

## Build & Run

### Dev Mode

```sh
./mvnw quarkus:dev
```

### Package

```sh
./mvnw package # JAR: target/quarkus-app/quarkus-run.jar
```

### Native Executable

```sh
./mvnw package -Dnative # Run: ./target/mail-sender-1.0.0-SNAPSHOT-runner
```

## Testing

### Run All Tests

```sh
./mvnw test
```

### Code Coverage

After tests: open `target/site/jacoco/index.html`

### BDD Tests

Feature files in `src/test/resources/features/`:

```gherkin
Feature: Email Processing  
  Scenario: Valid email  
    Given valid SQS message  
    When processed  
    Then email sent and message deleted  
```

Run:

```sh
./mvnw test -Dtest=*CucumberTest*
```

## Flow

1. Scheduler triggers every 30s
2. SQS client fetches messages
3. Messages → EmailRequest objects
4. EmailService sends via SMTP
5. Messages deleted from queue

## Contributing

1. Fork repo
2. Create branch: `git checkout -b feature/your-feature`
3. Commit with BDD scenarios
4. Push: `git push origin feature/your-feature`
5. Open PR

## License

MIT License - See LICENSE file.

## FIAP - HACKATHON

This project was produced as an assignment for a postgraduation course in Software Engineering Architecture at FIAP.