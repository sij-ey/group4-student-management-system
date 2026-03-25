# Student Course Registration and Management System


## Project Description

A full-stack Spring Boot MVC web application that manages student records and course registrations within a university environment. Administrators can manage students and courses; students can be registered for (and dropped from) courses each semester.

### Key Features
- Dashboard with live statistics (total students, courses, registrations)
- Full CRUD for Students (Add / View / Edit / Delete / Profile)
- Full CRUD for Courses (Add / View / Edit / Delete)
- Course Registration (register a student, drop a course)
- Global exception handling with a clean error page
- Input validation with user-friendly error messages
- Responsive Bootstrap 5 UI with custom CSS

---

## System Architecture

The project follows the standard **Spring Boot Layered (MVC) Architecture**:

```
HTTP Request
    ↓
Controller Layer  (handles HTTP, delegates to service)
    ↓
Service Layer     (business logic, validation)
    ↓
Repository Layer  (Spring Data JPA — talks to DB)
    ↓
Entity/Model Layer (JPA-mapped POJOs)
    ↓
Database (MySQL / H2)
    ↑
View Layer (Thymeleaf templates ← ModelAndView from Controller)
```

| Layer | Classes |
|---|---|
| Controller | `DashboardController`, `StudentController`, `CourseController`, `RegistrationController` |
| Service | `StudentService`, `CourseService`, `RegistrationService` |
| Repository | `StudentRepository`, `CourseRepository`, `RegistrationRepository` |
| Model | `Student`, `Course`, `Registration` |
| Exception | `ResourceNotFoundException`, `GlobalExceptionHandler` |
| View | Thymeleaf HTML templates |

---

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Core language |
| Spring Boot 3.2 | Application framework |
| Spring MVC | Web layer / controllers |
| Spring Data JPA | ORM and database access |
| Hibernate | JPA provider |
| MySQL | Production database |
| H2 | In-memory DB for testing |
| Thymeleaf | Server-side templating |
| Bootstrap 5 | Responsive UI framework |
| Maven | Build tool |

---

## Project Setup

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+ (or use H2 for instant startup)
- IntelliJ IDEA / Eclipse
- Visual Studio Code

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/sij-ey/group4-student-management-system.git
   cd student-management-system
   ```

2. **Configure the database**

   Open `src/main/resources/application.properties` and set:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/student_db?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```

   > **Quick start without MySQL:** Comment the MySQL block and uncomment the H2 block in `application.properties`.

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   Or open `StudentManagementApplication.java` in your IDE and click **Run**.

4. **Open in browser**
   ```
   http://localhost:8080  
   ```
   <!-- or use port:9090 if port:8080 in use -->

---

## Innovation & Unique Aspects

- **Shared layout template** (`layout.html`) using Thymeleaf Layout Dialect — avoids HTML repetition across all pages.
- **Global exception handler** (`GlobalExceptionHandler`) catches all errors centrally and renders a user-friendly error page.
- **Animated stat counters** on the dashboard (JavaScript count-up animation).
- **Avatar initials** generated dynamically from student names without any image upload.
- **Duplicate-safe logic** — the service layer checks for duplicate emails and course codes before saving.
- **Responsive design** — fully usable on mobile, tablet, and desktop.

---

## Challenges Encountered

1. **Thymeleaf Layout Dialect** required adding `thymeleaf-layout-dialect` dependency; learning `layout:decorate` vs `th:replace` took time.
2. **Bidirectional JPA relationships** (`@OneToMany` / `@ManyToOne`) caused infinite recursion during serialisation — solved by using `FetchType.LAZY`.
3. **Duplicate registration prevention** — required a composite unique constraint at the database level plus a service-layer guard.
4. **Form handling for add vs edit** — needed to distinguish the same form template for both create and update operations via `th:if="${student.id == null}"`.

---

## Lessons Learned

- The **Controller → Service → Repository** layered pattern makes code easy to test and maintain.
- **Constructor injection** (not field injection) is the Spring best practice and makes dependencies explicit.
- **Thymeleaf** integrates tightly with Spring MVC's `Model` and `BindingResult`, making form validation straightforward.
- **Spring Data JPA** removes almost all boilerplate SQL — derived query methods (`findByEmail`, `existsByEmailAndIdNot`) are powerful.
- Writing `GlobalExceptionHandler` early in the project saved hours of debugging blank error pages.

---

## Group Members

| Name | Student ID |
|---|---|
| Charles Ochieng | CS/M/1748/09/23 |
| Ian Kimutai Kirui | CS/MK/0904/09/23 |
| Levin Towet | CS/MK/1592/09/23 |
| Morgan Josiah | CS/MK/1929/09/23 |

---