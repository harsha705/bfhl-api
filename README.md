# BFHL REST API

A Spring Boot REST API implementation for the BFHL (Bajaj Finserv Health Limited) assignment.

## Features

- POST endpoint at `/bfhl` for processing data arrays
- Classification of input data into numbers, alphabets, and special characters
- Calculation of sum of numbers
- Generation of concatenated string with alternating capitalization
- Configurable personal details via environment variables
- Comprehensive test coverage
- Ready for deployment to Render

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+

### Build
```bash
mvn clean package
```

### Run Tests
```bash
mvn clean test
```

### Run Locally
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/bfhl`

## API Usage

### POST /bfhl

**Request:**
```json
{
  "data": ["a", "1", "334", "4", "R", "$"]
}
```

**Response:**
```json
{
  "is_success": true,
  "user_id": "harsha_24012005",
  "email": "vermaharsha314@gmail.com",
  "roll_number": "2311981227",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

## Classification Rules

- **Pure digits only** (e.g., "123") → numbers (odd/even)
- **Pure alphabets only** (e.g., "abc") → alphabets (uppercase)
- **Anything else** (e.g., "a1", "-5", "@#") → special_characters

## Configuration

Configure personal details in `application.properties` or via environment variables:

| Property | Environment Variable | Default |
|----------|----------------------|---------|
| app.full-name | APP_FULL_NAME | harsha |
| app.dob | APP_DOB | 24012005 |
| app.email | APP_EMAIL | vermaharsha314@gmail.com |
| app.roll-number | APP_ROLL_NUMBER | 2311981227 |

## Deployment

See [DEPLOYMENT.md](DEPLOYMENT.md) for detailed deployment instructions to Render.

## Project Structure

```
bfhl/
├── src/main/java/com/harsha/bfhl/
│   ├── BfhlApplication.java
│   ├── controller/BfhlController.java
│   ├── dto/BfhlRequest.java, BfhlResponse.java
│   ├── exception/ (error handling)
│   └── service/ (business logic)
├── src/test/java/ (unit and integration tests)
├── pom.xml
├── Procfile
├── render.yaml
└── DEPLOYMENT.md
```

## License

This project is for educational purposes as part of the BFHL assignment.
