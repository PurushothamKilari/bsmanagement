1. Book Management:
• API Endpoints:
o GET /api/books: Retrieve a list of all books.
o GET /api/books/{id}: Retrieve details of a single book.
o POST /api/books: Add a new book to the inventory.
o PUT /api/books/{id}: Update an existing book's details (e.g., price, stock).
o DELETE /api/books/{id}: Delete a book from the inventory.
• Book Details:
o Title
o Author(s)
o Genre
o ISBN
o Price
o Description
o Stock Quantity
o Image URL (cover image)
2. User Authentication:
• API Endpoints:
o POST /api/register: Register a new user (Customer or Admin).
o POST /api/login: Authenticate an existing user and return a JWT token for secure API
access.
• User Details:
o Name
o Email
o Password (hashed and securely stored)
o Role (Customer or Admin)
• Security:
o Use JWT (JSON Web Token) for user authentication.
o Implement Spring Security to restrict access to certain endpoints (e.g., only Admins
can manage books).
3. Order Management:
• API Endpoints:
o GET /api/orders: Retrieve a list of all orders (for Admin users).
o GET /api/orders/{id}: Retrieve details of a single order.
o POST /api/orders: Place a new order (Customer only).
o PUT /api/orders/{id}/status: Update order status (Admin only).
• Order Details:
o User details (Customer Name, Email)
o List of ordered books (with quantity and total price)
o Order status (e.g., Pending, Shipped, Delivered)
o Payment status (mock or real integration depending on project scope)
4. Database Integration:
• Use MySQLto store book, user, and order data.
• Establish relationships between books and orders, and users and orders.
• Ensure that the database structure supports easy querying, such as filtering books by genre,
searching by title, and tracking order history.
5. Performance and Pagination:
• Implement pagination for the list of books and orders to ensure the API can efficiently handle
large datasets.
• Implement search functionality for books by title or author.
6. Error Handling:
• Provide meaningful error messages for failed requests (e.g., 404 Not Found for a nonexistent
book or order).
• Use appropriate HTTP status codes for success (200 OK), creation (201 Created), bad
requests (400 Bad Request), unauthorized access (401 Unauthorized), etc.








1. Domain Layer (Entities)
src/main/java/com/bookstore/bsmanagement/model/Book.java
src/main/java/com/bookstore/bsmanagement/model/User.java
src/main/java/com/bookstore/bsmanagement/model/Order.java
2. Data Access Layer (Repositories)
src/main/java/com/bookstore/bsmanagement/repository/BookRepository.java
src/main/java/com/bookstore/bsmanagement/repository/UserRepository.java
src/main/java/com/bookstore/bsmanagement/repository/OrderRepository.java
3. Service Layer
src/main/java/com/bookstore/bsmanagement/service/BookService.java
src/main/java/com/bookstore/bsmanagement/service/UserService.java
src/main/java/com/bookstore/bsmanagement/service/OrderService.java









4. Web Layer (Controllers)
src/main/java/com/bookstore/bsmanagement/controller/BookController.java
src/main/java/com/bookstore/bsmanagement/controller/UserController.java
src/main/java/com/bookstore/bsmanagement/controller/OrderController.java
src/main/java/com/bookstore/bsmanagement/controller/AuthController.java
5. Security Layer
src/main/java/com/bookstore/bsmanagement/security/SecurityConfig.java
src/main/java/com/bookstore/bsmanagement/security/JwtTokenProvider.java
src/main/java/com/bookstore/bsmanagement/security/JwtAuthenticationFilter.java
src/main/java/com/bookstore/bsmanagement/security/CustomUserDetailsService.java




6. DTOs and Mappers
src/main/java/com/bookstore/bsmanagement/dto/BookDto.java
src/main/java/com/bookstore/bsmanagement/dto/UserDto.java
src/main/java/com/bookstore/bsmanagement/dto/OrderDto.java
src/main/java/com/bookstore/bsmanagement/mapper/BookMapper.java
src/main/java/com/bookstore/bsmanagement/mapper/UserMapper.java
src/main/java/com/bookstore/bsmanagement/mapper/OrderMapper.java
7. Exception Handling
src/main/java/com/bookstore/bsmanagement/exception/GlobalExceptionHandler.java
src/main/java/com/bookstore/bsmanagement/exception/ResourceNotFoundException.java
src/main/java/com/bookstore/bsmanagement/exception/BadRequestException.java


Add MySQL configuration and Maven wrapper setup

- Created .gitattributes to manage line endings for different files.
- Added .gitignore to exclude build artifacts and IDE-specific files.
- Introduced Maven wrapper properties and scripts for easier Maven management.
- Implemented mvnw and mvnw.cmd scripts for cross-platform Maven execution.
- Added MySQL driver dependency in pom.xml for database connectivity.
- Created application.properties with database connection settings and JPA configurations.
- Developed entity classes: Book, Order, User with appropriate JPA annotations.
- Added ServletInitializer for Spring Boot application deployment.
- Updated tests to ensure application context loads correctly.
- Documented MySQL configuration plan and API updates in markdown files."