#!/bin/bash

echo "🏦 Starting Simple Core Banking Service..."
#
## Check if Docker and Docker Compose are installed
#if ! command -v docker &> /dev/null; then
#    echo "❌ Docker is not installed. Please install Docker first."
#    exit 1
#fi
#
#if ! command -v docker-compose &> /dev/null; then
#    echo "❌ Docker Compose is not installed. Please install Docker Compose first."
#    exit 1
#fi

# Create necessary directories
mkdir -p init-scripts

# Stop any existing containers
echo "🛑 Stopping existing containers..."
docker compose down

# Build and start services
echo "🚀 Building and starting services..."
docker compose up --build -d

# Wait for services to be healthy
echo "⏳ Waiting for services to be ready..."
sleep 30

# Check service health
echo "🔍 Checking service health..."
docker compose ps

echo "✅ Core Banking service is ready!"
echo "📍 User Service: http://localhost:8080"
echo "📍 Database: localhost:5432"
echo "📍 Redis: localhost:6379"
echo ""
echo "🔧 Useful commands:"
echo "  View logs: docker-compose logs -f"
echo "  Stop services: docker-compose down"
echo "  Restart: docker-compose restart"
