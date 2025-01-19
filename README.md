
# <img src="https://raw.githubusercontent.com/agentnovax/www.agentnovax.com/main/assets/banners/AgentNovaX-Banner.png" alt="AgentNovaX Banner" height="300" width="100%">

---

## AgentNovaX

Welcome to **AgentNovaX**! 🌍✨

At [AgentNovaX](https://www.agentnovax.com/), our vision is to create a world where **innovation**, **collaboration**, **technology**, and **sustainability** work hand-in-hand to empower communities. We aim to provide tools that **simplify tasks**, **increase productivity**, and contribute to a **better planet**. Through **creativity**, **inclusivity**, and **environmental consciousness**, we strive to inspire a global movement toward shared success and continuous growth. 🌱💡

---

## **AgentNovaXSpringAuthAPI** - Spring Boot Project with PostgreSQL and Firebase Authentication 🚀

**NovaAuthAPI** is a robust **Spring Boot API application** that integrates **Firebase Authentication** and **PostgreSQL** to provide secure and scalable user authentication solutions. The project supports seamless user registration, validation of Firebase ID tokens, and integrates with multiple authentication providers like **Google**, **Microsoft**, **Apple**, and **Facebook**. Additionally, it includes **JWT token generation** for secure session management, offering a simple yet powerful solution for building modern authentication systems.

Perfect for developers looking to integrate **Firebase authentication** into their **Spring Boot** applications with **PostgreSQL** as the backend data store, providing a complete **OAuth solution**.

---

## Features 🔑

- User registration with **Firebase Authentication** 🔐
- Validates ID tokens using **Firebase Admin APIs** ✅
- Stores user information in a **PostgreSQL** database 💾
- Supports multiple authentication providers (Google, Microsoft, Apple, Facebook) 🌐
- Returns **JWT Token** and session for the application 🔑

---

## Prerequisites ⚙️

### Tools and Technologies 🛠️
- **Java** (23)
- **Spring Boot** (3.4.1)
- **PostgreSQL** (17)
- **Firebase Account**
- **Postman** (for testing APIs)

---

## Installation 🏗️

### Step 1: Set Up PostgreSQL 🗃️

#### 1. Install PostgreSQL
- **Windows**:
    - Download the installer from the [official site](https://www.postgresql.org/download/).
    - Follow the installation instructions.
    - Set up a password for the `postgres` user during installation.
- **Linux**:
  ```bash
  sudo apt update
  sudo apt install postgresql postgresql-contrib
  ```
- **macOS**:
  ```bash
  brew install postgresql
  ```

#### 2. Start PostgreSQL Service
- **Windows**: Use **pgAdmin** or start the service from the services tab.
- **Linux/macOS**:
  ```bash
  sudo service postgresql start
  ```

#### 3. Create a Database
- Access the PostgreSQL shell:
  ```bash
  psql -U postgres
  ```
- Create a database and tables using the script in the `init-db` folder:
  ```postgresql
  CREATE DATABASE "Auth"
  WITH OWNER = auth ENCODING = 'UTF8';
  ```

- Exit the shell:
  ```sql
  \q
  ```

### Step 2: Set Up Firebase 🔥

#### 1. Create a Google Account

#### 2. Create a Firebase Account
- Go to [Firebase Console](https://console.firebase.google.com/).
- Log in with your Google account and create a new Firebase project.

#### 3. Enable Authentication 🔑
- Navigate to the **Authentication** section.
- Enable the desired authentication providers (Google, Microsoft, Apple, Facebook).

#### 4. Generate Firebase Admin SDK 🌐
- Go to **Project Settings** > **Service Accounts** and generate a new private key.
- Download the **JSON** file and place it in the project's `resources` folder.

---

## Running the Project ⚡

### 1. Clone the Repository
```bash
git clone <repository_url>
cd <repository_name>
```

### 2. Update Configuration 🛠️
Update the `application.properties` or `application.yml` file:
```properties
spring.application.name=firebase-authentication-api
server.servlet.context-path=/auth

spring.datasource.url=jdbc:postgresql://localhost:5432/Auth
spring.datasource.username=auth
spring.datasource.password=auth
spring.jpa.hibernate.ddl-auto=update

security.jwt.secret-key=<your-jwt-secret>
```

### 3. Build and Run 🚀
- **Using Maven**:
  ```bash
  mvn spring-boot:run
  ```
- **Using Docker (Optional)**:
  ```bash
  docker build -t springboot-firebase .
  docker run -p 8080:8080 springboot-firebase
  ```

---

## API Usage 📡

Import the **Postman collection** and environment to test the login API. Use **username** and **password** in the pre-request script for login.

---

## Adding Authentication Providers 🌍

### Google Authentication
- Go to Firebase Console > **Authentication** > **Sign-in Method**.
- Enable **Google** and provide the OAuth 2.0 client ID.

### Microsoft Authentication
- Enable **Microsoft** in Firebase Console and provide OAuth credentials from **Azure Portal**.

### Apple Authentication
- Set up **Apple** authentication with the credentials from your **Apple Developer account**.

### Facebook Authentication
- Enable **Facebook** authentication and provide the credentials from **Facebook Developer Console**.

---

## Contributions ✨

We welcome contributions! Feel free to **open a pull request** or raise an **issue** for enhancements or bug fixes.

---

## Additional Notes 📋

- **Testing**: Use tools like **Postman** or **cURL** to test API endpoints.
- **Scaling**: Consider containerization with **Docker** for consistent deployment across environments.
- **Providers**: Refer to the Firebase documentation for detailed setup for other authentication providers.
- For more information, check out the [Spring Boot Documentation](https://spring.io/projects/spring-boot) and [Firebase Authentication Docs](https://firebase.google.com/docs/auth/).

---

## License 📄

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

---

## Contact 📧

For any queries, feel free to reach out via [agentnovaxp@gmail.com](mailto:agentnovaxp@gmail.com).
