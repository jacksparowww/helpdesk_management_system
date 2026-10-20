# HelpDesk Ticket Management System

A web-based HelpDesk application that allows users to create support tickets, track their status, and manage support requests.

## Features

- User registration and login
- Dashboard with ticket statistics
- Create support tickets
- View tickets created by the logged-in user
- Track ticket status and priority
- Refresh ticket information
- Add comments to tickets
- Update ticket status through the backend
- MySQL database integration

## Technologies Used

**Backend**
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL

**Frontend**
- HTML
- CSS
- JavaScript
- Thymeleaf

**Tools**
- IntelliJ IDEA
- Git and GitHub
- Maven

## Project Structure

```text
helpdesk/
├── src/
│   └── main/
│       ├── java/com/helpdesk/helpdesk/
│       │   ├── controller/
│       │   ├── entity/
│       │   ├── repository/
│       │   ├── request/
│       │   ├── service/
│       │   └── HelpdeskApplication.java
│       └── resources/
│           ├── static/
│           │   ├── css/
│           │   └── js/
│           ├── templates/
│           └── application.properties
├── pom.xml
└── README.md
```

## Prerequisites

Install the following before running the application:

- Java JDK
- Maven
- MySQL Server
- IntelliJ IDEA (recommended)

## Setup and Installation

### 1. Clone the repository

```bash
git clone https://github.com/jacksparowww/helpdesk_management_system.git
cd helpdesk_management_system
```

### 2. Configure MySQL

Create the database:

```sql
CREATE DATABASE helpdesk_db;
```

Configure your database URL, username, and password in `src/main/resources/application.properties`.

Do not commit real database passwords or other secrets to GitHub.

### 3. Run the application

Open the project in IntelliJ IDEA and run `HelpdeskApplication.java`.

Alternatively, if Maven is installed, run:

```bash
mvn spring-boot:run
```

### 4. Open the application

Visit:

http://localhost:8080

## Main API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/register` | Register a user |
| POST | `/api/auth/login` | Log in |
| GET | `/api/auth/users` | Get users |
| POST | `/api/tickets?userId={id}` | Create a ticket |
| GET | `/api/tickets/my?userId={id}` | Get a user's tickets |
| GET | `/api/tickets` | Get all tickets |
| GET | `/api/tickets/{id}` | Get a ticket by ID |
| PUT | `/api/tickets/{id}?userId={id}` | Update a ticket |
| DELETE | `/api/tickets/{id}?userId={id}` | Delete a ticket |
| GET | `/api/tickets/{ticketId}/comments` | Get ticket comments |
| POST | `/api/tickets/{ticketId}/comments?userId={id}` | Add a comment |

## Future Improvements

- Secure authentication and password hashing
- Role-based access control
- Improved ticket filtering and search
- Automated testing
- Deployment to a cloud platform

## Author

**Abhinandan**

GitHub: https://github.com/jacksparowww

---

This project was developed to practice Java, Spring Boot, REST APIs, MySQL, and frontend web development.
