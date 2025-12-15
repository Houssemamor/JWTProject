# Test Register Admin User
$registerUrl = "http://localhost:8080/api/auth/signup"
$registerBody = @{
    username = "admin"
    email = "admin@admin.com"
    password = "admin123"
    role = @("admin")
} | ConvertTo-Json

Write-Host "Signing up admin user..."
$response = Invoke-WebRequest -Uri $registerUrl -Method POST -ContentType "application/json" -Body $registerBody
Write-Host "Response: " $response.Content
Write-Host ""

# Test Login (Signin)
$loginUrl = "http://localhost:8080/api/auth/signin"
$loginBody = @{
    username = "admin"
    password = "admin123"
} | ConvertTo-Json

Write-Host "Signing in admin user..."
$loginResponse = Invoke-WebRequest -Uri $loginUrl -Method POST -ContentType "application/json" -Body $loginBody
Write-Host "Response: " $loginResponse.Content