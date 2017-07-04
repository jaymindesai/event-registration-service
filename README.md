# **event-registration-service**

## Backend API for a specific Regstraation System.

### Development Kit - jdk8
### Framework - Spring Boot
### Database - MySQL
 
#### Pre-requisites:
 - jdk8
 - Maven 3.5.0 or above
 - Lombok (heavily used to avoid boilerplate code)

#### Setup:
  ##### Populate DB:
   - create database registrationDB or change the datasource url in `src/main/resources/**application.yml**`
   - start server : **mvn spring-boot:run**
   - hit : **localhost:8090/api/db/populate**
 
