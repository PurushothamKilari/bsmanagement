#!/bin/bash

# JWT Authentication Test Script for Bookstore Management System
# This script tests the JWT authentication endpoints

echo "=== JWT Authentication Test Script ==="
echo "Testing Bookstore Management System JWT endpoints..."
echo ""

# Configuration
BASE_URL="http://localhost:8080"
CONTENT_TYPE="Content-Type: application/json"

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to test login
test_login() {
    local username=$1
    local password=$2
    local expected_role=$3
    
    echo "Testing login with $username/$password..."
    
    response=$(curl -s -X POST "$BASE_URL/api/auth/login" \
        -H "$CONTENT_TYPE" \
        -d "{\"username\":\"$username\",\"password\":\"$password\"}")
    
    token=$(echo $response | grep -o '"token":"[^"]*' | cut -d'"' -f4)
    
    if [ -n "$token" ]; then
        echo -e "${GREEN}✓ Login successful for $username${NC}"
        echo "Token: ${token:0:20}..."
        
        # Test accessing protected endpoint
        echo "Testing protected endpoint access..."
        protected_response=$(curl -s -X GET "$BASE_URL/api/books" \
            -H "Authorization: Bearer $token")
        
        if [[ $protected_response == *"Book"* ]] || [[ $protected_response == *"["* ]]; then
            echo -e "${GREEN}✓ Protected endpoint accessible${NC}"
        else
            echo -e "${RED}✗ Protected endpoint not accessible${NC}"
            echo "Response: $protected_response"
        fi
        
        echo ""
    else
        echo -e "${RED}✗ Login failed for $username${NC}"
        echo "Response: $response"
        echo ""
    fi
}

# Function to test invalid login
test_invalid_login() {
    local username=$1
    local password=$2
    
    echo "Testing invalid login with $username/$password..."
    
    response=$(curl -s -X POST "$BASE_URL/api/auth/login" \
        -H "$CONTENT_TYPE" \
        -d "{\"username\":\"$username\",\"password\":\"$password\"}")
    
    if [[ $response == *"Invalid"* ]] || [[ $response == *"401"* ]]; then
        echo -e "${GREEN}✓ Invalid login correctly rejected${NC}"
    else
        echo -e "${RED}✗ Invalid login not rejected properly${NC}"
        echo "Response: $response"
    fi
    echo ""
}

# Function to test access without token
test_no_token() {
    echo "Testing access without token..."
    
    response=$(curl -s -X GET "$BASE_URL/api/books")
    
    if [[ $response == *"Access Denied"* ]] || [[ $response == *"403"* ]] || [[ $response == *"Unauthorized"* ]]; then
        echo -e "${GREEN}✓ Access correctly denied without token${NC}"
    else
        echo -e "${RED}✗ Access not denied without token${NC}"
        echo "Response: $response"
    fi
    echo ""
}

# Function to test Swagger access
test_swagger_access() {
    echo "Testing Swagger UI access (should be public)..."
    
    response=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/swagger-ui.html")
    
    if [ $response -eq 200 ]; then
        echo -e "${GREEN}✓ Swagger UI accessible without authentication${NC}"
    else
        echo -e "${RED}✗ Swagger UI not accessible${NC}"
        echo "HTTP Status: $response"
    fi
    echo ""
}

# Main test execution
echo "Starting JWT authentication tests..."
echo ""

# Check if server is running
echo "Checking if server is running..."
server_response=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/actuator/health" || echo "000")

if [ $server_response -eq 200 ] || [ $server_response -eq 404 ]; then
    echo -e "${GREEN}✓ Server is running${NC}"
    echo ""
    
    # Run tests
    test_login "admin" "admin123" "ADMIN"
    test_login "user" "user123" "USER"
    test_login "manager" "manager123" "MANAGER"
    
    test_invalid_login "admin" "wrongpassword"
    test_invalid_login "nonexistent" "password"
    
    test_no_token
    
    test_swagger_access
    
    echo "=== Test Summary ==="
    echo "All tests completed. Check the results above."
    echo ""
    echo "To test manually:"
    echo "1. Login: curl -X POST $BASE_URL/api/auth/login -H \"$CONTENT_TYPE\" -d '{\"username\":\"admin\",\"password\":\"admin123\"}'"
    echo "2. Use token: curl -X GET $BASE_URL/api/books -H \"Authorization: Bearer <token>\""
    
else
    echo -e "${RED}✗ Server is not running on $BASE_URL${NC}"
    echo "Please start the Spring Boot application first:"
    echo "mvn spring-boot:run"
fi