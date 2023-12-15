# Project: User Demonstration REST Endpoint

This project is a technical challenge to implement CRUD operations through the development of a REST API for user management, it serves as a practical exercise to demonstrate proficiency in REST API development.

## Index
- [Summary](#summary)
- [Features](#features)
- [Entities](#entities)
- [Requirements](#requirements)
- [Issues](#issues)
- [How to Run](#how-to-run)

## Summary

This project is a technical challenge to implement CRUD operations through the development of a REST API for user management.  
The API receives and returns values in JSON format. It allows users to create, read, update, and delete User entities. Each User entity has a name, surname, and birthdate.  
The API is built using Java 11, Spring Boot 2.7, and an in-memory database. It is designed to be easy to set up and run, with detailed instructions provided in the "[How to Run](#how-to-run)" section. 

## Features

The system provides the following features:

- Get all users
- Get user by ID
- Create new user
- Modify user
- Delete user

## Entities

- User
  - ID
  - Name
  - Surname
  - Birthdate

## Requirements

The API is built with Java 11, Spring Boot 2.7, Spring JPA, and H2 Database.

Please refer to the "[How to Run](#how-to-run)" section for instructions on how to run the project and call the routes.

## Issues

The development of this project was organized into the following tasks, each represented by a GitHub issue:

1. [Implement User Entity with Basic Attributes](https://github.com/talesmousinho/user-demo/issues/1)
2. [Create Endpoint to Retrieve All Users](https://github.com/talesmousinho/user-demo/issues/2)
3. [Create Endpoint to Retrieve a User by ID](https://github.com/talesmousinho/user-demo/issues/3)
4. [Create Endpoint to Add a New User](https://github.com/talesmousinho/user-demo/issues/4)
5. [Create Endpoint to Update User Information](https://github.com/talesmousinho/user-demo/issues/5)
6. [Create Endpoint to Delete a User](https://github.com/talesmousinho/user-demo/issues/6)
7. [Create JUnit Tests for User Controller Endpoints](https://github.com/talesmousinho/user-demo/issues/7)
8. [Add Swagger UI for API Documentation](https://github.com/talesmousinho/user-demo/issues/8)

## How to Run

### Prerequisites

- Java 11

1. Clone the repository to your local machine.
```bash
git clone git@github.com:talesmousinho/user-demo.git
```

2. Navigate to the project directory.
```bash
cd user-demo/
```

3. Build the project using Maven.
```bash
./mvnw clean install
```

4. Run the Spring Boot application.
```bash
java -jar target/user-demo-server-0.0.1-SNAPSHOT.jar
```

The application will start running at http://localhost:8000.
