# Restaurant Reservation System

A web application for browsing, filtering, and reserving restaurant tables.

**Author:** Alex Kupper
**Development time:** 15-20 hours

---

## Requirements

Before running the application, install the following:

1. **Java 21** — download from [oracle.com/java](https://www.oracle.com/java/technologies/downloads/#java21)
2. **Node.js** (v18 or newer) — download from [nodejs.org](https://nodejs.org/)

To verify the installation, open Terminal (Mac/Linux) or Command Prompt (Windows) and run:

    java -version   # should show 21.x.x
    node -version   # should show v18 or newer

---

## Running the application

### Mac / Linux

1. Open Terminal
2. Type the following and press Enter:

   cd restaurant
   chmod +x start.sh
   ./start.sh

> `chmod +x start.sh` makes the script executable — you only need to do this once.

### Windows

1. Open the project folder in File Explorer
2. Double-click **`start.bat`**

✅ The browser will open automatically at `http://localhost:3000`

> **Note:** The first startup may take 1-2 minutes as dependencies are downloaded. The script will automatically stop any existing processes running on ports 8080 and 3000.

---

## How to use

1. **Set your filters** on the left panel — choose a date, time, number of guests and zone
2. **Click "Recommend a table"** — the system will highlight the best available table in gold
3. **Click on a table** to make a reservation
4. **Enter your name** in the popup and click "Reserve"

If your group is too large for a single table, the system will suggest two adjacent tables to merge.

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

## AI Usage

Claude AI was used extensively during development. The following parts were generated with AI assistance:

- Backend service classes (RecommendationService, TableMergeService)
- Frontend components (FloorPlan, FilterPanel, ReservationModal)
- Spring Boot configuration and setup
- Bug fixing (CORS, Lombok, Java version issues)

My own contributions:
- Overall application design and feature decisions
- Choosing filtering criteria and preference options
- Testing and verifying all functionality
- Writing this documentation

---

## Unresolved issues

All planned features were successfully implemented.