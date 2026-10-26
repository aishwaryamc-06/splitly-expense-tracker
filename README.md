# Splitly – Smart Expense Tracker

Splitly is a group expense-tracking application that helps users record shared expenses, split costs among group members, calculate balances, and identify who owes whom.

## Features

- **Group Management:** Create and view expense groups.
- **Member Management:** Add members to groups.
- **Expense Tracking:** Record expenses and track who paid.
- **Equal Expense Splitting:** Divide expenses equally among group members.
- **Balance Calculation:** Calculate how much each member owes or should receive.
- **Settlement Suggestions:** Identify payments that can settle outstanding balances.
- **Input Validation:** Handle invalid requests and missing records.
- **Web Interface:** Interact with the application through an HTML, CSS, and JavaScript frontend.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA / Hibernate
- MySQL
- HTML
- CSS
- JavaScript
- Maven
- Postman

## Project Structure

```text
splittracker/
├── frontend/
│   └── index.html
├── src/
│   ├── main/
│   │   ├── java/com/aishwarya/splittracker/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── ...
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/index.html
│   └── test/
└── pom.xml
```

## Getting Started

### Prerequisites

- Java 21 or a compatible JDK
- MySQL
- Maven, or the included Maven wrapper

### Setup

1. Clone this repository.
2. Open the `splittracker` project folder in IntelliJ IDEA.
3. Create a MySQL database named `split_tracker`.
4. Configure your local database connection in `src/main/resources/application.properties`.
5. Run `SplittrackerApplication.java`.
6. Open `http://localhost:8080/` in your browser.

### Testing

Use the web interface to create groups, add members, record expenses, and check balances and settlement suggestions. Postman can also be used to test the REST APIs.

## Learning Outcomes

This project demonstrates REST API development, database integration, layered application design, input validation, exception handling, and frontend-backend integration.

## Future Improvements

- Support exact and percentage-based expense splits.
- Add user authentication and authorization.
- Improve automated test coverage.
- Deploy the application online.

---

**Project:** Splitly – Smart Expense Tracker  
**Status:** Under development
