# EmployeeManagementSystem-ems
API for employee management system create and update their state using state machine.

## Built With
* Java 11
* Spring Boot
* Spring StateMachine
* H2 in-memory DB
* Docker
* Maven
* Swagger

### Build and Run
* Build with Maven.

Download the app. go inside project folder and run this maven commend.

`$ mvn clean install`
`$ mvn clean package spring-boot:run`

* Build with docker file.

Download the app. go inside project folder and run this docker-compose commend.

`$ docker build -t ems-api .`
`$ docker run -p 8080:8080 ems-api`


* Build with docker-compose.

Download the app. go inside project folder and run this docker-compose commend.

`$ docker-compose build`
`$ docker-compose up`

## Using the endpoints
* Once the app is up and running. you can check API documentation from this url
[Swagger API Documentation](http://localhost:8080/swagger-ui.html)
* You can check EmployeeController for endpoints
* Ex: http://localhost:8080/api/v1/employees      				GET  List All Employees 
* Ex: http://localhost:8080/api/v1/employees  	  				POST create new Employee
* Ex: http://localhost:8080/api/v1/employees/check/{empId}   	PUT  update employee state to check
* Ex: http://localhost:8080/api/v1/employees/approve/{empId}  	PUT  update employee state to approved
* Ex: http://localhost:8080/api/v1/employees/activate/{empId}  	PUT  update employee state to activated

## Unit/Integeration Test
* The project having integeration test for all endpoint with Junit, Mokito and WebMvc.

## Actuator
* The project uses spring actuator for monitoring.
* [actuator metrics](http://localhost:8080/actuator/metrics)
* [actuator env](http://localhost:8080/actuator/env)
* [actuator health](http://localhost:8080/actuator/health)



