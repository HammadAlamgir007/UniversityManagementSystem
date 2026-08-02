# University Management System

A Java backend learning project built with **Spring Boot**, designed to practice and apply core Java and backend development concepts in a university domain.

## 📌 Repository

- **Repo:** [HammadAlamgir007/UniversityManagementSystem](https://github.com/HammadAlamgir007/UniversityManagementSystem)
- **Primary Language:** Java (100%)
- **Default Branch:** `master`

## 🧰 Tech Stack

- **Java 17**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
  - Spring Security
- **MySQL** (runtime connector)
- **JWT (jjwt 0.12.7)** for token-based authentication
- **Lombok**
- **Maven** (with Maven Wrapper)

## 📁 Project Structure

```text
UniversityManagementSystem/
├── .mvn/
├── logs/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   └── resources/
    └── test/
        └── java/
```

## ⚙️ Prerequisites

Make sure you have installed:

- **Java 17+**
- **MySQL**
- (Optional) **Maven** — not required if you use `mvnw`

## 🚀 Getting Started

### 1) Clone the repository

```bash
git clone https://github.com/HammadAlamgir007/UniversityManagementSystem.git
cd UniversityManagementSystem
```

### 2) Configure the database

Create a MySQL database (example):

```sql
CREATE DATABASE university_management;
```

Then configure your DB credentials in `src/main/resources/application.properties` (or `application.yml`) with your actual values.

### 3) Run the application

Using Maven Wrapper:

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

Or build and run JAR:

```bash
./mvnw clean package
java -jar target/firstproject-0.0.1-SNAPSHOT.jar
```

## 🔐 Security

This project includes:

- **Spring Security**
- **JWT-based authentication** (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`)

You can extend it with role-based authorization for modules like students, faculty, and admin.

## ✅ Testing

Run tests with:

```bash
./mvnw test
```

## 🎯 Learning Goals Covered

This project is a great practice ground for:

- Layered backend architecture
- REST API development
- Validation and exception handling
- Database integration with JPA/Hibernate
- Authentication with JWT
- Secure API design with Spring Security

## 📈 Future Improvements

- Add API documentation (Swagger / OpenAPI)
- Add Docker support
- Add role-based access control (RBAC)
- Improve logging strategy and profiles (`dev`, `prod`)
- Add CI workflow for build/test checks

---

Made with ❤️ for Java backend learning.
