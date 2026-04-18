# 💼 Job Portal Application

A Full Stack Job Portal web application built with Spring Boot and React.

## 🚀 Tech Stack

### Backend
- Java 17
- Spring Boot 3.5
- Spring Security
- JWT Authentication
- MySQL
- JPA/Hibernate
- Maven

### Frontend
- React.js
- React Router
- Axios
- Bootstrap 5
- Context API

## ✨ Features

### Candidate
- Register and Login
- Browse all jobs
- Search jobs by title, company, location
- Apply for jobs
- Track application status

### Recruiter
- Register and Login
- Post new jobs
- View all posted jobs
- View applications for each job
- Accept or Reject applications

### Security
- JWT token based authentication
- Role based access control
- Password encryption with BCrypt

## 🛠️ Setup Instructions

### Backend Setup
1. Clone the repository
2. Open job-portal-backend in IntelliJ IDEA
3. Create MySQL database:
```sql
CREATE DATABASE jobportal;
```
4. Update application.properties with your MySQL password
5. Run JobPortalApplication.java

### Frontend Setup
1. Open job-portal-frontend in VS Code
2. Run the following commands:
```bash
npm install
npm run dev
```
3. Open http://localhost:5173

## 📸 Project Structure
job-portal/
│
├── job-portal-backend/
│   ├── src/main/java/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   ├── security/
│   │   └── config/
│   └── pom.xml
│
└── job-portal-frontend/
├── src/
│   ├── components/
│   ├── pages/
│   ├── services/
│   └── context/
└── package.json

## 👨‍💻 Developer

**G Manoj Kumar**
- ECE Graduate — MRCET
- Full Stack Java Developer
- Certifications: Java, Spring Boot, React, DBMS, DSA — Infosys Springboard & Udemy