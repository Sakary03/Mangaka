#!/bin/bash

# Test script for Manga endpoints with role-based filtering
# Make sure your backend is running on port 9090

BASE_URL="http://localhost:9090"

echo "=== Testing Manga Endpoints with Role-Based Filtering ==="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}Step 1: Login as Admin${NC}"
echo "Logging in as admin..."
ADMIN_LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "password": "admin123"
  }')

echo "Admin login response: $ADMIN_LOGIN_RESPONSE"

# Extract admin token (assuming the response contains accessToken field)
ADMIN_TOKEN=$(echo $ADMIN_LOGIN_RESPONSE | grep -o '"accessToken":"[^"]*' | cut -d'"' -f4)
echo -e "${GREEN}Admin Token: $ADMIN_TOKEN${NC}"
echo ""

echo -e "${BLUE}Step 2: Login as Regular User${NC}"
echo "Logging in as regular user..."
USER_LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "user123"
  }')

echo "User login response: $USER_LOGIN_RESPONSE"

# Extract user token
USER_TOKEN=$(echo $USER_LOGIN_RESPONSE | grep -o '"accessToken":"[^"]*' | cut -d'"' -f4)
echo -e "${GREEN}User Token: $USER_TOKEN${NC}"
echo ""

echo -e "${YELLOW}=== Testing GET /manga endpoint ===${NC}"
echo ""

echo -e "${BLUE}Test 3: Get all manga as Admin (should see all statuses)${NC}"
curl -s -X GET "$BASE_URL/manga?offset=0&limit=10&sortby=title&isAsc=true" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" | jq '.'
echo ""

echo -e "${BLUE}Test 4: Get all manga as Regular User (should only see APPROVED)${NC}"
curl -s -X GET "$BASE_URL/manga?offset=0&limit=10&sortby=title&isAsc=true" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" | jq '.'
echo ""

echo -e "${BLUE}Test 5: Get all manga without authentication (should only see APPROVED)${NC}"
curl -s -X GET "$BASE_URL/manga?offset=0&limit=10&sortby=title&isAsc=true" \
  -H "Content-Type: application/json" | jq '.'
echo ""

echo -e "${YELLOW}=== Testing GET /manga/search endpoint ===${NC}"
echo ""

echo -e "${BLUE}Test 6: Search manga as Admin with status filter${NC}"
curl -s -X GET "$BASE_URL/manga/search?offset=0&limit=10&title=&author=&status=PENDING,APPROVED" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" | jq '.'
echo ""

echo -e "${BLUE}Test 7: Search manga as Regular User with status filter (should ignore status filter)${NC}"
curl -s -X GET "$BASE_URL/manga/search?offset=0&limit=10&title=&author=&status=PENDING,APPROVED" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" | jq '.'
echo ""

echo -e "${BLUE}Test 8: Search manga by title as Admin${NC}"
curl -s -X GET "$BASE_URL/manga/search?offset=0&limit=10&title=naruto&author=" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" | jq '.'
echo ""

echo -e "${BLUE}Test 9: Search manga by title as Regular User${NC}"
curl -s -X GET "$BASE_URL/manga/search?offset=0&limit=10&title=naruto&author=" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" | jq '.'
echo ""

echo -e "${BLUE}Test 10: Search manga by author as Admin${NC}"
curl -s -X GET "$BASE_URL/manga/search?offset=0&limit=10&title=&author=kishimoto" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" | jq '.'
echo ""

echo -e "${BLUE}Test 11: Search manga without authentication${NC}"
curl -s -X GET "$BASE_URL/manga/search?offset=0&limit=10&title=&author=" \
  -H "Content-Type: application/json" | jq '.'
echo ""

echo -e "${GREEN}=== Testing Complete ===${NC}"
echo ""
echo -e "${YELLOW}Expected Results:${NC}"
echo "- Admin should see all manga regardless of status"
echo "- Regular users should only see APPROVED manga"
echo "- Unauthenticated requests should only see APPROVED manga"
echo "- Status filters in search should only work for admins"
