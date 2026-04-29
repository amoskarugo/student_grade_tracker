# Student Grade Tracker API

A RESTful API built with **Spring Boot** and **JdbcTemplate** for managing students, courses, and academic grades. Includes GPA calculation per student per semester.

---

## Tech Stack

| Technology | Version | Purpose |
|---|---------|---|
| Java | 17      | Language |
| Spring Boot | 3.2.0   | Framework |
| Spring JDBC | -       | JdbcTemplate data access |
| PostgreSQL | -       | Database |
| ModelMapper | 3.2.0   | DTO ↔ Entity mapping |
| Lombok | -       | Boilerplate reduction |
| Maven | -       | Build tool |

---

## Project Structure

```
src/main/java/com/example/studenttracker/
├── config/
│   └── MapperConfig.java
├── constants/
│   └── SqlQueries.java
├── controller/
│   ├── StudentController.java
│   ├── CourseController.java
│   └── GradeController.java
├── dao/
│   ├── StudentDao.java
│   ├── CourseDao.java
│   └── GradeDao.java
├── dto/
│   ├── StudentDto.java
│   ├── CourseDto.java
│   ├── GradeDto.java
│   └── GpaResult.java
├── entity/
│   ├── Student.java
│   ├── Course.java
│   └── Grade.java
├── exception/
│   ├── ApiException.java
│   ├── ResourceNotFoundException.java
│   ├── ConflictException.java
│   ├── BusinessException.java
│   ├── DatabaseException.java
│   ├── DuplicateResourceException.java
│   └── GlobalExceptionHandler.java
├── mapper/
│   ├── Mapper.java
│   ├── StudentMapper.java
│   ├── CourseMapper.java
│   └── GradeMapper.java
├── repository/
│   ├── StudentRepository.java
│   ├── CourseRepository.java
│   └── GradeRepository.java
├── response/
│   └── ApiResponse.java
├── service/
│   ├── StudentService.java
│   ├── CourseService.java
│   └── GradeService.java
└── StudentTrackerApplication.java

src/main/resources/
├── application.yml
└── schema.sql
```

---

## Database Schema

```sql
CREATE TABLE IF NOT EXISTS students (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NOT NULL UNIQUE,
    enrolled_at DATE         NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS courses (
    id         BIGSERIAL    PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    code       VARCHAR(20)  NOT NULL UNIQUE,
    credits    INT          NOT NULL,
    department VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS grades (
    id         BIGSERIAL    PRIMARY KEY,
    student_id BIGINT       NOT NULL REFERENCES students(id) ON DELETE CASCADE,
    course_id  BIGINT       NOT NULL REFERENCES courses(id)  ON DELETE CASCADE,
    grade      DECIMAL(4,2) NOT NULL CHECK (grade >= 0 AND grade <= 100),
    semester   VARCHAR(20)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (student_id, course_id, semester)
);
```

---

## Getting Started

### Prerequisites

- Java 21
- Maven
- PostgreSQL

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/student-tracker.git
cd student-tracker
```

### 2. Create the database

```bash
sudo -u postgres psql -c "CREATE DATABASE student_tracker;"
```

### 3. Configure `application.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/student_tracker
    username: postgres
    password: yourpassword
```

### 4. Run the project

```bash
./mvnw clean spring-boot:run
```

The API will start on `http://localhost:8080`

---

## API Endpoints

### Students

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/students` | Get all students |
| `GET` | `/api/v1/students/{id}` | Get student by ID |
| `POST` | `/api/v1/students` | Create a student |
| `PUT` | `/api/v1/students/{id}` | Update a student |
| `DELETE` | `/api/v1/students/{id}` | Delete a student |

### Courses

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/courses` | Get all courses |
| `GET` | `/api/v1/courses/{id}` | Get course by ID |
| `GET` | `/api/v1/courses/department/{department}` | Get courses by department |
| `POST` | `/api/v1/courses` | Create a course |
| `PUT` | `/api/v1/courses/{id}` | Update a course |
| `DELETE` | `/api/v1/courses/{id}` | Delete a course |

### Grades

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/grades` | Record a grade |
| `GET` | `/api/v1/grades/student/{studentId}` | Get grades with details |
| `GET` | `/api/v1/grades/student/{studentId}/gpa` | Calculate GPA per semester |
| `DELETE` | `/api/v1/grades/{id}` | Delete a grade |

---

## Request & Response Examples

### Create Student

**Request**
```json
POST /api/v1/students
{
    "name": "John Doe",
    "email": "john@mail.com"
}
```

**Response**
```json
{
    "success": true,
    "message": "Student created",
    "data": {
        "id": 1,
        "name": "John Doe",
        "email": "john@mail.com",
        "enrolledAt": "2026-04-29"
    },
    "timestamp": "2026-04-29T10:30:00",
    "status": 201
}
```

### Create Course

**Request**
```json
POST /api/v1/courses
{
    "name": "Introduction to Programming",
    "code": "CS101",
    "credits": 3,
    "department": "Computer Science"
}
```

### Record Grade

**Request**
```json
POST /api/v1/grades
{
    "studentId": 1,
    "courseId": 1,
    "grade": 85.50,
    "semester": "SEM1-2024"
}
```

### Get GPA

**Response**
```json
{
    "success": true,
    "message": "GPA calculated",
    "data": [
        {
            "studentId": 1,
            "studentName": "John Doe",
            "semester": "SEM1-2024",
            "gpa": 85.17,
            "totalCourses": 3
        }
    ],
    "timestamp": "2026-04-29T10:30:00",
    "status": 200
}
```

### Error Response

```json
{
    "success": false,
    "message": "Student not found with id: 99",
    "error": null,
    "timestamp": "2026-04-29T10:30:00",
    "status": 404
}
```

---

## Exception Handling

All exceptions are handled globally by `GlobalExceptionHandler` and return a consistent `ApiResponse`.

| Exception | HTTP Status | When |
|---|---|---|
| `ResourceNotFoundException` | 404 | Resource not found by ID |
| `ConflictException` | 409 | Duplicate email or course code |
| `DuplicateResourceException` | 409 | Duplicate grade for same student/course/semester |
| `BusinessException` | 422 | Business rule violation |
| `DatabaseException` | 500 | Database operation failure |
| `MethodArgumentNotValidException` | 400 | Validation failure on request body |

---

## Architecture

```
Request
   ↓
Controller       — receives DTO, returns ApiResponse
   ↓
Service          — business rules, @Transactional, DTO ↔ Entity mapping
   ↓
DAO Interface    — defines database operations
   ↓
Repository       — JdbcTemplate + SqlQueries execution + exception handling
   ↓
Database
```

### Key Design Decisions

- **`SqlQueries.java`** — all SQL statements organised as static constants in nested classes (`SqlQueries.Student.*`, `SqlQueries.Course.*`, `SqlQueries.Grade.*`)
- **`StudentMapper`** implements both `Mapper<StudentDto, Student>` and `RowMapper<Student>` — one class handles both ResultSet → Entity and Entity → DTO conversion
- **`ApiResponse<T>`** — every endpoint returns the same response envelope with `success`, `message`, `data`, `error`, `timestamp`, and `status`
- **`@Builder(access = AccessLevel.PRIVATE)`** on `ApiResponse` — enforces that responses can only be built through the `success()`, `created()`, and `error()` factory methods

---

## Validation Rules

### Student
- `name` — required, 2–100 characters
- `email` — required, valid email format, unique

### Course
- `name` — required
- `code` — required, unique
- `credits` — required, minimum 1
- `department` — required

### Grade
- `studentId` — required
- `courseId` — required
- `grade` — required, 0.0 to 100.0
- `semester` — required
