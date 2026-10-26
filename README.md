# Splitly – Smart Expense Tracker

*Split bills, track balances, settle up.*

Splitly is a full-stack group expense-tracking application. Users create groups, add members, record shared expenses, split costs equally, see each member's balance, and get suggestions on who should pay whom to clear all debts.

## Screenshots

![Dashboard]("C:\Users\ankit\OneDrive\Pictures\Screenshots\Screenshot 2026-10-26 130328.png")
![Expenses](screenshots/expenses.png)
![Balances](screenshots/balances.png)
![Settlements](screenshots/settlements.png)

## Features

- **Group management:** create and view expense groups
- **Member management:** add members to a group
- **Expense tracking:** record expenses and who paid
- **Equal splitting:** divide an expense equally, with rounding handled so shares always add up to the total
- **Balance calculation:** see how much each member owes or should receive
- **Settlement suggestions:** a greedy algorithm matches debtors with creditors to suggest payments that clear all balances
- **Validation and error handling:** clear 400, 404 and 409 responses for invalid input, missing records and duplicate splits
- **Web interface:** dashboard built with HTML, CSS and JavaScript, served by Spring Boot

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Backend | Spring Boot 4, Spring Web, Spring Data JPA, Hibernate |
| Database | MySQL |
| Frontend | HTML, CSS, JavaScript |
| Build tool | Maven |
| API testing | Postman |

## Architecture

```
Browser / Postman
      ↓
Controller  →  Service  →  Repository  →  MySQL
```

Money values use `BigDecimal` to avoid floating-point errors.

## How Balances Work

For every split expense:
- the payer gets **+ full amount**
- each member gets **− their share**

A positive balance means the member should receive money, a negative balance means they owe money, and all balances in a group sum to zero.

**Example:** Rahul pays ₹1,020 in total for 4 people (₹255 each). Rahul's balance is +₹765, and Priya, Amit and Sneha each have −₹255. The settlement suggestions are three payments of ₹255 to Rahul.

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/groups` | Create a group |
| GET | `/api/groups` | List all groups |
| POST | `/api/members` | Add a member to a group |
| GET | `/api/members` | List all members |
| POST | `/api/expenses` | Record an expense |
| GET | `/api/expenses/group/{groupId}` | List a group's expenses |
| POST | `/api/expense-splits/{expenseId}` | Split an expense equally |
| GET | `/api/expense-splits/{expenseId}` | View an expense's splits |
| GET | `/api/groups/{groupId}/balances` | Get member balances |
| GET | `/api/groups/{groupId}/settlements` | Get settlement suggestions |

**Example response:** `GET /api/groups/2/balances`

```json
[
  { "memberId": 1, "memberName": "Rahul", "balance": 765.00 },
  { "memberId": 2, "memberName": "Priya", "balance": -255.00 },
  { "memberId": 4, "memberName": "Amit",  "balance": -255.00 },
  { "memberId": 5, "memberName": "Sneha", "balance": -255.00 }
]
```

## Project Structure

```
splittracker/
├── src/
│   ├── main/
│   │   ├── java/com/aishwarya/splittracker/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/index.html
│   └── test/
└── pom.xml
```

## Getting Started

### Prerequisites
- Java 21 (or a compatible JDK)
- MySQL (XAMPP or a standalone install)
- Maven (or the included Maven wrapper)

### Run locally
1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/split-tracker.git
   cd split-tracker
   ```
2. Start MySQL and create the database:
   ```sql
   CREATE DATABASE split_tracker;
   ```
3. Set your database username and password in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/split_tracker
   spring.datasource.username=root
   spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=update
   ```
4. Run the app:
   ```bash
   ./mvnw spring-boot:run
   ```
   or run `SplittrackerApplication.java` from your IDE.
5. Open **http://localhost:8080** in your browser.

Tables are created automatically by Hibernate on first run.

## Testing

The REST APIs were tested with Postman, covering normal flows and invalid cases (blank names, invalid emails, zero or negative amounts, missing groups and members, duplicate splits).

## Future Improvements

- Record real settlement payments
- Exact and percentage-based splits
- Edit and delete expenses
- User authentication and authorization
- Automated unit and integration tests
- Docker setup and online deployment

## Author

**Aishwarya M C**, B.Tech CSE, VTU Mysore
