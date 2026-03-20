#!/bin/bash
echo "========================================"
echo "  Restaurant Reservation System"
echo "========================================"
echo ""
echo "Starting backend..."
cd "$(dirname "$0")"
./mvnw spring-boot:run &
BACKEND_PID=$!
echo "Waiting for backend to start..."
sleep 15
echo ""
echo "Starting frontend..."
cd frontend
npm install
npm start