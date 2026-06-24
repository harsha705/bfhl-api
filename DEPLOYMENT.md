# BFHL REST API - Deployment Guide

## Overview
This is a Spring Boot REST API implementation of the BFHL (Bajaj Finserv Health Limited) assignment.

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Git

## Local Build and Test

### Build Command
```bash
MVN clean package
```

### Run Tests
```bash
mvn clean test
```

### Run Application Locally
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoint

### POST /bfhl

**Request Body:**
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

## Environment Variables

The application uses the following environment variables (configured in `application.properties`):

| Variable | Description | Default Value |
|----------|-------------|---------------|
| `app.full-name` | Full name for user_id generation | harsha |
| `app.dob` | Date of birth in DDMMYYYY format | 24012005 |
| `app.email` | Email address | vermaharsha314@gmail.com |
| `app.roll-number` | Roll number | 2311981227 |

## Deployment to Render

### Option 1: Using render.yaml (Recommended)

1. Fork this repository
2. Connect your GitHub account to Render
3. Create a new Web Service on Render
4. Select your forked repository
5. Render will automatically detect `render.yaml` and configure the service

### Option 2: Manual Configuration

1. Create a new Web Service on Render
2. Select your repository
3. Configure the following:

**Build Settings:**
- Build Command: `mvn clean package -DskipTests`
- Runtime: Java 17

**Environment Variables:**
```
APP_FULL_NAME=harsha
APP_DOB=24012005
APP_EMAIL=vermaharsha314@gmail.com
APP_ROLL_NUMBER=2311981227
```

**Start Command:**
```
java -jar target/bfhl-1.0.0.jar
```

### Option 3: Using Procfile

If using the included `Procfile`, Render will automatically use it for the start command.

## Verification After Deployment

### Test Example A
```bash
curl -X POST https://your-app-url.onrender.com/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["a", "1", "334", "4", "R", "$"]}'
```

Expected response:
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

### Test Example B
```bash
curl -X POST https://your-app-url.onrender.com/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["2", "a", "y", "4", "&", "-", "*", "5", "92", "b"]}'
```

Expected response:
```json
{
  "is_success": true,
  "user_id": "harsha_24012005",
  "email": "vermaharsha314@gmail.com",
  "roll_number": "2311981227",
  "odd_numbers": ["5"],
  "even_numbers": ["2", "4", "92"],
  "alphabets": ["A", "Y", "B"],
  "special_characters": ["&", "-", "*"],
  "sum": "103",
  "concat_string": "ByA"
}
```

### Test Example C
```bash
curl -X POST https://your-app-url.onrender.com/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["A", "ABCD", "DOE"]}'
```

Expected response:
```json
{
  "is_success": true,
  "user_id": "harsha_24012005",
  "email": "vermaharsha314@gmail.com",
  "roll_number": "2311981227",
  "odd_numbers": [],
  "even_numbers": [],
  "alphabets": ["A", "ABCD", "DOE"],
  "special_characters": [],
  "sum": "0",
  "concat_string": "EoDdCbAa"
}
```

## Business Logic Verification

- **user_id format**: `fullname_ddmmyyyy` (lowercase, spaces replaced with underscores)
- **Numbers**: Returned as strings in odd_numbers and even_numbers
- **Sum**: Returned as a string
- **Alphabets**: Converted to uppercase
- **Negative numbers**: Treated as special_characters
- **Mixed alphanumeric**: Treated as special_characters
- **Pure digits only**: Classified as numbers
- **Pure alphabets only**: Classified as alphabets
- **Anything else**: Classified as special_characters

## Troubleshooting

### Build Failures
- Ensure Java 17 is installed
- Ensure Maven 3.6+ is installed
- Check that all dependencies are available

### Runtime Errors
- Verify environment variables are set correctly
- Check application.properties configuration
- Review Render logs for specific error messages

### Test Failures
- Run `mvn clean test` locally before deploying
- Ensure all test cases pass
- Verify test data matches expected outputs

## Project Structure

```
bfhl/
├── src/
│   ├── main/
│   │   ├── java/com/harsha/bfhl/
│   │   │   ├── BfhlApplication.java
│   │   │   ├── controller/
│   │   │   │   └── BfhlController.java
│   │   │   ├── dto/
│   │   │   │   ├── BfhlRequest.java
│   │   │   │   └── BfhlResponse.java
│   │   │   ├── exception/
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── InvalidRequestException.java
│   │   │   └── service/
│   │   │       ├── BfhlService.java
│   │   │       └── impl/
│   │   │           └── BfhlServiceImpl.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/harsha/bfhl/
│           ├── controller/
│           │   └── BfhlControllerTest.java
│           └── service/
│               └── BfhlServiceImplTest.java
├── pom.xml
├── Procfile
├── render.yaml
└── DEPLOYMENT.md
```

## Support

For issues or questions, please refer to the BFHL assignment documentation or contact the development team.
