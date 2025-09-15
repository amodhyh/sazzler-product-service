# Sazzler Product Service

## Overview
Handles product catalog management, integrates with Eureka, and exposes product-related APIs.

## Features
- CRUD operations for products
- Database integration (MongoDB recommended)
- Eureka service registration
- Docker support

## Setup
1. Java 21+
2. Gradle
3. Configure database URI in `application.yaml`
4. Set Eureka URL

## Build & Run
```bash
./gradlew build
./gradlew bootRun
```

## Configuration
- Database: Set URI and database name
- Eureka: Set service registry URL

## API Endpoints
- `/products` - Product management

## Docker
- Build: `docker build -t sazzler-product-service .`
- Run: `docker run -p 8083:8083 sazzler-product-service`

## Troubleshooting
- Ensure DB is running
- Check Eureka registration
- Validate API endpoints

