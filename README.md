# Restaurant Demo - REST API with Spring Boot, JWT, h2, JPA and JMS 

## Steps to Setup

**1. Clone the application**

```bash
https://github.com/pablojunin/spring-boot-rest-api-restaurant.git
```

**2. Run application**

```bash
mvn spring-boot:run
```

**3. Run test**

```bash
mvn test
```

**4. Username and password for to get token**

```bash
{
	"username":"restaurant",
	"password":"password"
}
```

The app will start running at <http://localhost:8080> on the context 'restaurant' <http://localhost:8080/restaurant>


## Explore Rest APIs

The app defines following APIs.

    GET /restaurant/api/v1/sales
    
    GET /restaurant/api/v1/sales/current
    
    POST /restaurant/authenticate
    
    POST /restaurant/api/v1/sales
    
## Execute with CURL

Get all sales:

```bash
curl -X GET "http://localhost:8080/restaurant/api/v1/sales" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZXN0YXVyYW50IiwiZXhwIjoxNjExMDQ3Nzg0LCJpYXQiOjE2MTEwMjk3ODR9.6HSBNHjNIAds8JgZSaXhHygp1l9QcpGbYlY0ocFpj9PHfgWQx-SAPvQ6ZxYJhsacMJ4XmF4QFDyW4Y_HMB--BA"
```

Get all current sales
 
```bash
curl -X GET "http://localhost:8080/restaurant/api/v1/sales/current" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZXN0YXVyYW50IiwiZXhwIjoxNjExMDQ3Nzg0LCJpYXQiOjE2MTEwMjk3ODR9.6HSBNHjNIAds8JgZSaXhHygp1l9QcpGbYlY0ocFpj9PHfgWQx-SAPvQ6ZxYJhsacMJ4XmF4QFDyW4Y_HMB--BA"
```

Create a new sale

```bash
curl -X POST "http://localhost:8080/restaurant/api/v1/sales" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZXN0YXVyYW50IiwiZXhwIjoxNjExMDE5NjYxLCJpYXQiOjE2MTEwMDE2NjF9.dlr-korT8Lqmlz5iyqYJunCF4AcOKQUqZ9Bbv2ISdO7RkK6UaZvJEB7ekoZYsMkAm0gQ5YHusHe4xPO6eeu48g" -H "Content-Type: application/json" -d "{ \"amount\": 0, \"billNumber\": 0, \"date\": \"2021-01-19\", \"description\": \"string\", \"itemsQuantity\": 0}"
```

## Explore with swagger

To see the documentation with swagger: <http://localhost:8080/restaurant/swagger-ui/>


