# PrivateConnect – Real-Time Private Messaging Application

PrivateConnect is a privacy-focused, one-to-one messaging web application developed using Java and Spring Boot. It allows authenticated users to communicate with each other through real-time text messaging.

The project was developed to explore how secure authentication and real-time communication can be implemented in a custom messaging platform.

## Features

- **JWT Authentication:** Token-based user authentication and authorization.
- **Real-Time Messaging:** Instant message exchange using WebSocket and STOMP.
- **Private One-to-One Chat:** Communication between authenticated users.
- **Message History:** Retrieve previous conversations from the database.
- **Message Status:** Supports message delivery and read receipts.
- **Password Security:** Passwords are hashed using BCrypt.
- **Cloud Database:** Persistent data storage using TiDB Cloud.
- **Online Deployment:** Hosted on Render.

## Tech Stack

| Category | Technologies |
|---|---|
| Language | Java |
| Backend | Spring Boot |
| Security | Spring Security, JWT |
| Real-Time Communication | WebSocket, STOMP |
| Database | MySQL, TiDB Cloud |
| ORM | Spring Data JPA, Hibernate |
| Password Hashing | BCrypt |
| Build Tool | Maven |
| Deployment | Render |
| Version Control | Git, GitHub |

## Application Architecture

1. Users register and log in to the application.
2. The backend authenticates users using JWT.
3. WebSocket establishes a real-time connection.
4. STOMP handles private message communication.
5. Spring Data JPA stores and retrieves messages from the database.

## Live Demo

**Application:** https://privateconnect-nru2.onrender.com/chat-test.html

**GitHub Repository:** https://github.com/shashank12125/PrivateConnect

## Getting Started

### Prerequisites

- Java 25 or compatible JDK
- Maven
- MySQL database or TiDB Cloud account
- Git

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/shashank12125/PrivateConnect.git
   ```

2. Navigate to the project directory:

   ```bash
   cd PrivateConnect
   ```

3. Configure your database and JWT secret using environment variables or a local `application-local.properties` file.

4. Run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

5. Open the chat application:

   ```text
   http://localhost:8080/chat-test.html
   ```

## Important Security Note

PrivateConnect implements JWT-based authentication and BCrypt password hashing. It is not currently an end-to-end encrypted (E2EE) messaging application.

Never commit database passwords, JWT secrets, or other sensitive configuration files to GitHub.

## Future Enhancements

- Clear Chat functionality
- End-to-end message encryption
- Improved user interface
- Additional account and privacy controls

---

**Developed by Shashank Charpe**

Java Developer | Spring Boot | Backend Development
