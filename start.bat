@echo off
echo ========================================
echo   Restaurant Reservation System
echo ========================================
echo.
echo Starting backend...
cd /d "%~dp0"
start cmd /k "mvnw.cmd spring-boot:run"
echo Waiting for backend to start (15 seconds)...
timeout /t 15
echo.
echo Starting frontend...
cd frontend
npm install
npm start