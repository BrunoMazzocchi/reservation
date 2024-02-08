# Spring Boot Java Gradle Project Structure

The directory structure provided is a typical representation of a Java project using the Spring Boot framework. This structure follows the principles of a layered architecture, with clear separation of concerns. 
## Project Structure:

## Project Architecture Overview:

This structure follows a layered architecture with the following components:

- **Controller:** Handles incoming HTTP requests and manages the flow of data between the client and the service layer.

- **Repository:** Manages the data access and encapsulates the interaction with the database.

- **Dto (Data Transfer Object):** Represents the data that is transferred between layers.

- **Exception:** Handles global exceptions to provide consistent error handling throughout the application.

- **Model:** Represents the entities or objects used by the application.

- **Service:** Contains business logic and serves as an intermediary between the controller and the repositories.

- **Resources:** Contains configuration files such as `application.properties`.

- **Test:** Houses test classes for respective components.

### Advantage of the Implemented Architecture:

The layered architecture brings several advantages:

- **Separation of Concerns:** Each layer has a distinct responsibility, making the codebase modular, maintainable, and easy to understand.

- **Testability:** The structure supports the creation of unit tests for individual components, promoting robust testing practices.

- **Scalability:** The layered approach allows for the addition or modification of components without affecting the entire system.

- **Code Reusability:** Well-defined components facilitate code reuse, reducing redundancy in the codebase.

- **Ease of Maintenance:** The organized structure simplifies maintenance tasks, debugging, and troubleshooting.

- **Readability:** The directory structure provides a clear and intuitive layout, aiding developers in quickly locating and understanding code.

- **Consistent Naming Convention:** The naming convention used in packages and classes enhances consistency, making it easier for developers to follow coding standards.

By adhering to these architectural principles, the project aims to be scalable, maintainable, and extensible, providing a solid foundation for building and evolving Java Spring Boot applications.

## How to run
The app contains various dockerfile related to launch the app almost immediately. To do so, open the project and build them as follows: 
- docker pull openjdk:17-jdk-slim
- docker pull gradle:7.4-jdk17
- docker-compose build
- docker-compose up

If crashes, cancel the process and run it again.

     
      
