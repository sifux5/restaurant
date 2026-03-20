# Restaurant Reservation System

**Author:** Alex Kupper  
**Development time:** 15-20 hours

## Requirements

Before running the application, install the following:

1. **Java 21** — download from [oracle.com/java](https://www.oracle.com/java/technologies/downloads/#java21)
2. **Node.js** (v18 or newer) — download from [nodejs.org](https://nodejs.org/)

Verify in terminal:
```bash
java -version   # should show 21.x.x
node -version   # should show v18 or newer
```

---

## Running the application

### Mac / Linux
1. Open Terminal
2. Navigate to the project folder:
```bash
cd restaurant
```
3. Make the script executable and run:
```bash
chmod +x start.sh
./start.sh
```

### Windows
1. Open the project folder in File Explorer
2. Double-click **`start.bat`**

✅ The browser will open automatically at `http://localhost:3000`

> **Note:** The first startup may take 1-2 minutes as dependencies are downloaded.

---

## Features

- Floor plan with three zones: Indoor, Terrace, Private room
- Filter tables by date, time, number of guests and zone
- Preferences: quiet corner, window seat, near playground
- Recommendation algorithm based on available space and preferences
- Best recommendation highlighted with a gold border
- Table reservation via modal with a random daily dish (TheMealDB API)
- Dynamic table merging — if the group is too large for one table, the system suggests combining adjacent tables
- Occupied tables show when they will become available
- Random reservations generated on startup

---

## Challenges

**Java version:** Initially used Java 25 where Lombok did not work. After confirming with CGI that Java 21 is acceptable, switching versions resolved the issue.

**Lombok annotation processor:** The `@Data` annotation did not generate getters/setters during Maven compilation. Fixed by adding explicit `maven-compiler-plugin` configuration with Lombok annotation processor path in `pom.xml`.

**CORS:** Frontend (port 3000) could not communicate with backend (port 8080). Fixed by adding `spring.mvc.cors.allowed-origins=http://localhost:3000` to `application.properties`.

---

## Help

Claude AI was used during development — for applying Spring Boot and React concepts and resolving errors.

## Unresolved issues

All planned features were successfully implemented.