# Individual CURL Commands for Testing Manga Endpoints

## Prerequisites

# Make sure your backend is running on port 9090

# Replace the email/password with actual test accounts in your database

# Install jq for JSON formatting: sudo apt install jq (Linux) or brew install jq (Mac)

## 1. Login as Admin

curl -X POST "http://localhost:9090/auth/login" \
 -H "Content-Type: application/json" \
 -d '{
"email": "admin@example.com",
"password": "admin123"
}'

## 2. Login as Regular User

curl -X POST "http://localhost:9090/auth/login" \
 -H "Content-Type: application/json" \
 -d '{
"email": "user@example.com",
"password": "user123"
}'

# Replace YOUR_ADMIN_TOKEN and YOUR_USER_TOKEN with actual tokens from login responses

## 3. Get all manga as Admin (should see all statuses)

curl -X GET "http://localhost:9090/manga?offset=0&limit=10&sortby=title&isAsc=true" \
 -H "Authorization: Bearer YOUR_ADMIN_TOKEN" \
 -H "Content-Type: application/json"

## 4. Get all manga as Regular User (should only see APPROVED)

curl -X GET "http://localhost:9090/manga?offset=0&limit=10&sortby=title&isAsc=true" \
 -H "Authorization: Bearer YOUR_USER_TOKEN" \
 -H "Content-Type: application/json"

## 5. Get all manga without authentication (should only see APPROVED)

curl -X GET "http://localhost:9090/manga?offset=0&limit=10&sortby=title&isAsc=true" \
 -H "Content-Type: application/json"

## 6. Search manga as Admin with status filter (should work)

curl -X GET "http://localhost:9090/manga/search?offset=0&limit=10&title=&author=&status=PENDING&status=APPROVED" \
 -H "Authorization: Bearer YOUR_ADMIN_TOKEN" \
 -H "Content-Type: application/json"

## 7. Search manga as Regular User with status filter (should ignore status filter)

curl -X GET "http://localhost:9090/manga/search?offset=0&limit=10&title=&author=&status=PENDING&status=APPROVED" \
 -H "Authorization: Bearer YOUR_USER_TOKEN" \
 -H "Content-Type: application/json"

## 8. Search manga by title as Admin

curl -X GET "http://localhost:9090/manga/search?offset=0&limit=10&title=naruto&author=" \
 -H "Authorization: Bearer YOUR_ADMIN_TOKEN" \
 -H "Content-Type: application/json"

## 9. Search manga by title as Regular User

curl -X GET "http://localhost:9090/manga/search?offset=0&limit=10&title=naruto&author=" \
 -H "Authorization: Bearer YOUR_USER_TOKEN" \
 -H "Content-Type: application/json"

## 10. Search manga by author as Admin

curl -X GET "http://localhost:9090/manga/search?offset=0&limit=10&title=&author=kishimoto" \
 -H "Authorization: Bearer YOUR_ADMIN_TOKEN" \
 -H "Content-Type: application/json"

## 11. Search manga by genre (example with ACTION genre)

curl -X GET "http://localhost:9090/manga/search?offset=0&limit=10&genres=ACTION" \
 -H "Authorization: Bearer YOUR_ADMIN_TOKEN" \
 -H "Content-Type: application/json"

## 12. Test with different sorting options

curl -X GET "http://localhost:9090/manga?offset=0&limit=5&sortby=createdAt&isAsc=false" \
 -H "Authorization: Bearer YOUR_USER_TOKEN" \
 -H "Content-Type: application/json"

## 13. Test pagination

curl -X GET "http://localhost:9090/manga?offset=1&limit=3&sortby=title&isAsc=true" \
 -H "Authorization: Bearer YOUR_USER_TOKEN" \
 -H "Content-Type: application/json"

## Expected Results:

# - Admin should see all manga regardless of status (PENDING, APPROVED, DELETED, etc.)

# - Regular users should only see APPROVED manga

# - Unauthenticated requests should only see APPROVED manga

# - Status filters in search should only work for admins

# - Regular users trying to filter by status should still only see APPROVED manga

## Testing Tips:

# 1. Check the response JSON for the "status" field in each manga object

# 2. Compare results between admin and user requests

# 3. Verify that unauthenticated requests behave like regular user requests

# 4. Test edge cases like invalid tokens, malformed requests, etc.
