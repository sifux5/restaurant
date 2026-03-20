#!/bin/bash
echo "========================================"
echo "  Restaurant Reservation System"
echo "========================================"
echo ""
echo "Stopping any existing processes..."
lsof -ti:8080 | xargs kill -9 2>/dev/null
lsof -ti:3000 | xargs kill -9 2>/dev/null
sleep 2
echo "Starting backend..."
cd "$(dirname "$0")"
./mvnw spring-boot:run &
echo "Waiting for backend to start..."
sleep 15
echo ""
echo "Starting frontend..."
cd frontend
npm install
npm start