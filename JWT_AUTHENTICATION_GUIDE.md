# JWT Authentication Guide - Bookstore Management System

## Overview
This Spring Boot application now uses JWT (JSON Web Token) based authentication for securing all API endpoints. This guide explains how to use the authentication system.

## Authentication Flow

### 1. Get JWT Token
To authenticate, send a POST request to the login endpoint:

**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
    "username": "admin",
    "password": "admin123"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY3MjUzNDQwMCwiZXhwIjoxNjcyNjIwODAwfQ.abc123...",
    "username": "admin",
    "role": "ADMIN"
}
```

### 2. Use JWT Token
Include the token in the Authorization header for all subsequent requests:

**Header:**
```
Authorization: Bearer <your-jwt-token>
```

## Sample Users
The application comes with pre-configured sample users:

| Username | Password | Role   | Description |
|----------|----------|--------|-------------|
| admin    | admin123 | ADMIN  | Full access |
| user     | user123  | USER   | Standard user |
| manager  | manager123 | MANAGER | Manager access |

## API Endpoints

### Public Endpoints (No Authentication Required)
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- Swagger UI: `/swagger-ui.html` - API documentation
- API Docs: `/v3/api-docs` - OpenAPI documentation

### Protected Endpoints (JWT Required)
All other endpoints require JWT authentication.

## Testing with Postman

### 1. Login to Get Token
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "admin123"
}
```

### 2. Use Token in Subsequent Requests
```
GET http://localhost:8080/api/books
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## Testing with cURL

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Access Protected Endpoint
```bash
curl -X GET http://localhost:8080/api/books \
  -H "Authorization: Bearer <your-token-here>"
```

## User Registration

### Register New User
**Endpoint:** `POST /api/auth/register`

**Request:**
```json
{
    "username": "newuser",
    "password": "password123",
    "email": "newuser@example.com",
    "role": "USER"
}
```

**Response:**
```json
{
    "id": 4,
    "username": "newuser",
    "email": "newuser@example.com",
    "role": "USER",
    "active": true
}
```

## Token Details
- **Expiration:** 24 hours from creation
- **Algorithm:** HS512
- **Secret:** Configured in application.properties

## Error Handling

### Invalid Credentials
```json
{
    "status": 401,
    "message": "Invalid username or password",
    "timestamp": "2023-01-01T12:00:00"
}
```

### Expired Token
```json
{
    "status": 401,
    "message": "JWT token is expired",
    "timestamp": "2023-01-01T12:00:00"
}
```

### Missing Token
```json
{
    "status": 403,
    "message": "Access Denied",
    "timestamp": "2023-01-01T12:00:00"
}
```

## Configuration

### application.properties
```properties
# JWT Configuration
jwt.secret=your-secret-key-here-should-be-at-least-256-bits-for-hs512
jwt.expiration=86400000
```

## Security Best Practices

1. **Token Storage:** Store tokens securely on the client side
2. **HTTPS:** Always use HTTPS in production
3. **Token Expiration:** Tokens expire after 24 hours
4. **Secret Key:** Use a strong, random secret key in production
5. **Password Encoding:** All passwords are encoded using BCrypt

## Troubleshooting

### Common Issues

1. **"Invalid username or password"**
   - Check if user exists in database
   - Verify password is correct
   - Ensure UserServiceImpl is encoding passwords

2. **"JWT token is expired"**
   - Login again to get new token
   - Check system time synchronization

3. **"Access Denied"**
   - Ensure Authorization header is present
   - Check token format: "Bearer <token>"
   - Verify token hasn't been tampered with

4. **Swagger UI Not Working**
   - Ensure you're accessing `/swagger-ui.html`
   - Check that security configuration allows Swagger endpoints

## Testing Checklist

- [ ] Login with admin/admin123 returns valid token
- [ ] Login with invalid credentials returns 401
- [ ] Access /api/books without token returns 403
- [ ] Access /api/books with valid token returns 200
- [ ] Token expires after 24 hours
- [ ] Swagger UI accessible without authentication
- [ ] User registration creates new user with encoded password
- [ ] Different user roles work correctly