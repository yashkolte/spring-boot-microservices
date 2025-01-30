# Spring Boot Microservices with Eureka & API Gateway

## 📌 Overview
This project demonstrates a **microservices architecture** using **Spring Boot**, **Spring Cloud Eureka**, and **Spring Cloud Gateway**. The system consists of:

1. **Eureka Server** (Service Registry) - Centralized registry for service discovery.
2. **User Service** - A microservice that provides user-related endpoints.
3. **API Gateway** (Load Balancer) - Routes requests to microservices and provides load balancing.

---

## 🚀 Tech Stack
- **Java 17+**
- **Spring Boot**
- **Spring Cloud Eureka (Service Discovery)**
- **Spring Cloud Gateway (API Gateway & Load Balancer)**
- **Maven**

---

## 🏗️ Project Structure
```plaintext
microservices-project/
│── eureka-server/      # Eureka Service Registry
│── user-service/       # User microservice
│── api-gateway/        # API Gateway (Load Balancer)
```

---

## 🔧 Setup Instructions
### 1️⃣ Clone the Repository
```bash
git clone https://github.com/yashkolte/SpringBootMicroservices.git
cd SpringBootMicroservices
```

### 2️⃣ Build the Project
Run the following command in the **root directory**:
```bash
mvn clean package
```

---

## 📌 Microservices Setup
### **1. Start Eureka Server**
Navigate to the `eureka-server` directory and run:
```bash
cd eureka-server
mvn spring-boot:run
```
- ✅ Check Eureka Dashboard: [http://localhost:8761](http://localhost:8761)

### **2. Start Eureka Client**
Navigate to `eureka-client` and run:
```bash
cd ../eureka-client
mvn spring-boot:run
```
- ✅ Check API: [http://localhost:8081/users/all](http://localhost:8081/users/all)

### **3. Start API Gateway**
Navigate to `api-gateway` and run:
```bash
cd ../api-gateway
mvn spring-boot:run
```
- ✅ Access API through Gateway: [http://localhost:8080/users/all](http://localhost:8080/users/all)

---

## 🛠️ API Endpoints
### **User Service API** (Direct)
- `GET /users/all` → Fetch all users
- `POST /users/add?name=John` → Add a user

### **API Gateway (Load Balanced Routes)**
- `GET http://localhost:8080/users/all` → **(via API Gateway)**
- `POST http://localhost:8080/users/add?name=John` → **(via API Gateway)**

---

## 🔥 Troubleshooting
### ❌ **API Gateway Error: No servers available for service: user-service**
✅ Ensure User Service is registered in Eureka:
- Check **http://localhost:8761** to see registered services.
- Restart services in this order:
  1. **Eureka Server**
  2. **User Service**
  3. **API Gateway**

### ❌ **Spring Cloud Gateway Error (WebApplicationType)**
✅ Ensure `api-gateway` does NOT have `spring-boot-starter-web` in `pom.xml`.
✅ Ensure `application.yml` contains:
```yaml
spring:
  main:
    web-application-type: reactive
```

---

## 🎯 Next Steps
- ✅ Add more microservices (e.g., Order Service, Payment Service)
- ✅ Secure APIs with **Spring Security & JWT**
- ✅ Implement **Circuit Breaker (Resilience4j)**

---

## 📜 License
This project is licensed under the **MIT License**.

---

🚀 **Happy Coding!**

