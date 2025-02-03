# Spring Cloud Microservices Architecture

This project demonstrates a microservices architecture using Spring Cloud components, including Eureka Server, API Gateway, User Service, and Spring Boot Admin for monitoring.

## Project Structure

- **Eureka Server**: Service discovery for all microservices.
- **API Gateway**: Load balancer and request router for microservices.
- **User Service**: A simple service registered with Eureka Server.
- **Spring Boot Admin**: Monitoring and managing Spring Boot applications.

---

## Prerequisites

Before running the project, ensure you have the following installed:

- Java 11 or later
- Maven 3.6+
- Docker (optional, for containerized deployment)

---

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/your-repo/microservices-architecture.git
cd microservices-architecture
```

### 2. Build All Services
Build all services using Maven:
```bash
mvn clean install
```

### 3. Start the Eureka Server
Navigate to the `eureka-server` directory and run:
```bash
cd eureka-server
mvn spring-boot:run
```
Eureka Server will be available at [http://localhost:8761](http://localhost:8761).

### 4. Start the Spring Boot Admin Server
Navigate to the `spring-admin` directory and run:
```bash
cd spring-admin
mvn spring-boot:run
```
Spring Boot Admin will be available at [http://localhost:9090](http://localhost:9090).

### 5. Start the API Gateway
Navigate to the `api-gateway` directory and run:
```bash
cd api-gateway
mvn spring-boot:run
```
API Gateway will be available at [http://localhost:8080](http://localhost:8080).

### 6. Start the User Service
Navigate to the `user-service` directory and run:
```bash
cd user-service
mvn spring-boot:run
```
User Service will be available at [http://localhost:8081](http://localhost:8081).

---

## Configuration

### Eureka Server
`application.yml` for Eureka Server:
```yaml
server:
  port: 8761

eureka:
  server:
    eviction-interval-timer-in-ms: 5000
  client:
    register-with-eureka: false
    fetch-registry: false
```

### Spring Boot Admin
`application.yml` for Spring Boot Admin:
```yaml
server:
  port: 9090

spring:
  application:
    name: spring-admin

  boot:
    admin:
      discovery:
        enabled: true

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    register-with-eureka: true
    fetch-registry: true
```

### API Gateway
`application.yml` for API Gateway:
```yaml
server:
  port: 8080

spring:
  application:
    name: api-gateway

  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/users/**

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    register-with-eureka: true
    fetch-registry: true
```

### User Service
`application.yml` for User Service:
```yaml
server:
  port: 8081

spring:
  application:
    name: user-service

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    register-with-eureka: true
    fetch-registry: true

  instance:
    prefer-ip-address: true
```

---

## How to Access the Services

- **Eureka Dashboard**: [http://localhost:8761](http://localhost:8761)
- **Spring Boot Admin**: [http://localhost:9090](http://localhost:9090)
- **API Gateway**: [http://localhost:8080](http://localhost:8080)
- **User Service**: [http://localhost:8081](http://localhost:8081)

---

## Common Issues

### 1. Offline Instances in Spring Boot Admin
- Ensure all services are correctly registered in Eureka.
- Verify `management.endpoints.web.exposure.include` includes `health` and `info`.
- Restart services if stale instances are present in the registry.

### 2. Failed to Resolve Hostname
- Add `prefer-ip-address: true` in the `application.yml` of services.
- Replace any hardcoded hostnames with `localhost`.

---

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
