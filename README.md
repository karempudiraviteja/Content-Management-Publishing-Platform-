# 🚀 Full Stack Web Application

This is a Full Stack Web Application built using:

- 🔹 Spring Boot (Backend)
- 🔹 JWT Authentication
- 🔹 Role-Based Authorization
- 🔹 React (Vite) Frontend
- 🔹 REST APIs
- 🔹 MySQL / PostgreSQL Database

---

## 📁 Project Structure
content-management-publishing-platform/
│
├── CMS/ # Spring Boot Backend
├── cms-frontend/ # React + Vite Frontend
└── README.md

---

# 🛠️ Backend - Spring Boot

## 🔧 Technologies Used

- Java 17+
- Spring Boot
- Spring Security
- JWT Authentication
- JPA / Hibernate
- MySQL / PostgreSQL
- Maven

## ▶️ How To Run Backend

1. Go to backend folder:
cd CMS

2. Run application:
mvn spring-boot:run

OR run using STS / IntelliJ.

Backend runs at:
http://localhost:9632

---

# 💻 Frontend - React (Vite)

## 🔧 Technologies Used

- React
- Vite
- Axios
- React Router
- CSS

## ▶️ How To Run Frontend

1. Go to frontend folder:
cd cms-frontend

2. Install dependencies:
npm install

3. Run application:
npm run dev

Frontend runs at:
http://localhost:5173

---

# 🔐 Features

- ✅ User Registration
- ✅ Login with JWT
- ✅ Role-Based Access (Admin / User)
- ✅ Secure REST APIs
- ✅ Logout Functionality
- ✅ Protected Routes
- ✅ Responsive UI
- ✅ Dashboard

---

# 🔗 API Communication

Frontend communicates with backend using Axios.

Make sure backend is running before starting frontend.

---

# 📌 Environment Configuration

Update backend `application.properties`:
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password

---

# 🚀 Future Improvements

- Dockerization
- CI/CD Pipeline
- Deployment (Render / Vercel)
- Microservices Architecture


---

# 👨‍💻 Author

Raviteja Karempudi  
Full Stack Developer  
