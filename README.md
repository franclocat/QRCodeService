# QR Code Service

This project is a simple RESTful web service for generating QR codes based on user input.

## Features

- Generate QR codes with custom content, size, image type, and error correction level.
- Supports PNG, JPEG, and GIF image types.
- Validates input parameters and provides meaningful error messages.
- Health check endpoint for monitoring the service status.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven

### Installation

1. Clone the repository:

```bash
git clone https://github.com/franclocat/QRCodeService.git

Navigate to the project directory:
bash
Copy code
cd QRCodeService
Build the project using Maven:
bash
Copy code
mvn clean install
Run the application:
bash
Copy code
mvn spring-boot:run
The application should now be running locally at http://localhost:8080.

Usage
Health Check Endpoint
To check the health of the service, send a GET request to /api/health:

bash
Copy code
curl -X GET http://localhost:8080/api/health
Generate QR Code
Send a GET request to /api/qrcode with the following query parameters:

contents (required): The content to be encoded in the QR code.
size (optional): The size of the QR code (default is 250 pixels).
type (optional): The image type of the QR code (PNG, JPEG, or GIF, default is PNG).
correction (optional): The error correction level of the QR code (L, M, Q, or H, default is L).
Example:

bash
Copy code
curl -X GET 'http://localhost:8080/api/qrcode?contents=Hello%20World&type=PNG&size=300&correction=M'
This will generate a QR code with the content "Hello World" in PNG format, with a size of 300 pixels and medium error correction level.

Contributing
Contributions are welcome! Please feel free to submit issues or pull requests.
