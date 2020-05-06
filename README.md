Simple CRM Service
===
## Build from
```Spring Boot + H2```
## Maven Plugin
1. spring-boot-starter-data-jpa
```Use for database ORM```
2. spring-boot-starter-security
```Use for token issue & AA```
3. spring-boot-starter-test
```Use for auto testing```
## APIs
All is in postman, import all json into postman then you can see.

## Request Format
Build with RESTFUL, put every params in request body with 

``` Content-Type: application/json ```

And Remember to login before using API

``` Authorization: Bearer {token}```