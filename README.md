# Network Asset Manager

A full-stack Java web application built to inventory, audit, and monitor network devices (hosts) along with their open ports and services.

Developed as a mini-project for **Advance Java Technology (01CE0308)** at **Marwadi University** (Department of Computer Engineering).

---

## Features

- **Authentication & Session Security:** User registration and login backed by salted **PBKDF2-HMAC-SHA256** password hashing (120,000 iterations), session fixation protection (`request.changeSessionId()`), `AuthFilter` route protection, cache-control headers (`no-store`), and a 15-minute idle session timeout.
- **Interactive Dashboard:** Real-time metrics displaying total hosts, active vs. inactive counts, high-risk host alerts, total logged ports, visual risk distribution bars, and recently added assets.
- **Host Inventory Management (CRUD):** Add, view, edit, and delete network hosts with IPv4 address, hostname, operating system, status (`Active`/`Inactive`), risk level (`Low`/`Medium`/`High`), and audit notes.
- **Search & Risk Filtering:** Filter devices dynamically by risk level or search across IP addresses, hostnames, operating systems, and notes.
- **Open Ports & Vulnerability Logging:** Attach open port numbers (`1–65535`), service names, and vulnerability notes to individual hosts with cascading deletion (`ON DELETE CASCADE`).
- **Dual-Layer Validation & XSS Defense:** Client-side validation (`validate.js`) paired with strict server-side regex validation (`ValidationUtil.java`), parameterized `PreparedStatement` queries against SQL injection, and JSTL `<c:out>` escaping against XSS.

---

## Tech Stack

| Layer | Technologies |
| :--- | :--- |
| **Backend** | Java 17 (Temurin), Java Servlets 4.0, Servlet Filters, `HttpSession`, JDBC |
| **Frontend** | JSP, JSTL 1.2, Expression Language (EL), Custom Dark-Theme CSS, Vanilla JS |
| **Database** | MySQL 8.0 (`mysql-connector-java 8.0.33`) |
| **Build & Server** | Apache Maven 3.10+, Apache Tomcat 9.0 |

---

## Project Structure

```text
network-asset-manager/
├── pom.xml                                  # Maven dependencies & WAR build config
├── schema.sql                               # MySQL database and table definitions
├── sample_data.sql                          # Demo user and sample host/port records
├── .gitignore                               # Excludes target/, IDE files, and local db.properties
├── README.md                                # Project documentation
└── src/main/
    ├── java/
    │   ├── db/DBConnection.java             # Loads db.properties & opens JDBC connections
    │   ├── model/                           # POJO models (User, Host, Port)
    │   ├── dao/                             # Data Access Objects (UserDAO, HostDAO, PortDAO)
    │   ├── util/                            # PasswordUtil, SessionUtil, ValidationUtil
    │   ├── filter/AuthFilter.java           # Protects private routes & sets no-cache headers
    │   └── servlets/                        # 11 MVC Controllers (Auth, Dashboard, Hosts, Ports)
    ├── resources/
    │   └── db.properties.example            # Template for database credentials
    └── webapp/
        ├── css/style.css                    # Dark UI styling
        ├── js/validate.js                   # Client-side form validation
        └── WEB-INF/
            ├── web.xml                      # Session timeout & error page mappings
            └── views/                       # JSTL/EL JSP views (dashboard, hosts, hostDetail, etc.)
```

---

## Setup & Local Deployment

### 1. Prerequisites
- **JDK 17**
- **Apache Maven 3.8+**
- **Apache Tomcat 9**
- **MySQL Server 8.0+**

### 2. Configure the Database
Run the schema and sample data scripts in MySQL:
```bash
mysql -u root -p < schema.sql
mysql -u root -p < sample_data.sql
```
*(Demo login included in `sample_data.sql`: Username `test` / Password `password123`)*

### 3. Configure Database Properties
Copy the example properties file and update your MySQL credentials if needed:
```bash
cp src/main/resources/db.properties.example src/main/resources/db.properties
```

### 4. Build & Deploy on Tomcat 9
```bash
mvn clean package
```
Copy `target/netAssetManager.war` into your Tomcat `webapps/` folder and start Tomcat:
```bash
cp target/netAssetManager.war /path/to/tomcat9/webapps/
/path/to/tomcat9/bin/catalina.sh run
```
Open `http://localhost:8080/netAssetManager/` in your browser.

---

## Authors

**Shaik Asgar**
**Lakshya Kaushik**
**Pratik Raj Ansh**
B.Tech Computer Science & Engineering (Cybersecurity)  
Marwadi University, Rajkot
