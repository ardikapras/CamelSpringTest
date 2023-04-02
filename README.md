# CamelSpringTest
Sample project to implement Spring Boot and Apache Camel REST

## Pre-requisites
1. Install Java
2. Install Maven

## How to start
1. Execute `mvn spring-boot:run` in the root folder
2. Once started, we can try curl to the server

## Curl

### GET method
```
curl --location 'http://localhost:8080/api/customers/1?filter=testFilter&sort=anc'
```

### PATCH method
```
curl --location --request PATCH 'http://localhost:8080/api/customers?filter=filter-by-age&sort=ascending' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Ardika",
    "lastName": "Prasetyo",
    "email": "test-email@gmail.com"
}'
```