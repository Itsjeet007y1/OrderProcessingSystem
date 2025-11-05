# OrderProcessingSystem

This repository contains a small Spring Boot microservices demo for an Order Processing System with the following services:

- discovery-server — Eureka discovery server (port 8761)
- api-gateway — Zuul API gateway / proxy (port 9393)
- customer-service — Customer CRUD service (port 8181)
- order-service — Order management service (port 7272)

Each service is a Maven module under the repository root. The project uses MongoDB for persistence and Eureka for service discovery. Swagger (springfox) is enabled in the services for API exploration.

---

Contents

- Overview
- Prerequisites
- Project structure
- Configuration (ports, Mongo URIs)
- Build & run (per-service and all together)
- APIs (endpoints, payload examples)
- Swagger / UI
- Troubleshooting & tips
- Next steps

---

Overview

This demo implements basic customer and order services. The discovery server (Eureka) registers services so the API gateway (Zuul) can route requests to them. MongoDB stores customers and orders.

Prerequisites

- Java 8 (project pom properties target Java 1.8)
- Maven (or use the included mvnw / mvnw.cmd wrappers)
- MongoDB running locally (default URIs point to localhost:27017)
- (Optional) Docker if you prefer to run Mongo in a container

If you don't have system-installed Maven, use the wrapper scripts included in each module (mvnw for *nix, mvnw.cmd for Windows).

Project structure (important modules)

- discovery-server/ — Eureka server
- api-gateway/ — Zuul proxy intended to front the services
- customer-service/ — Customer CRUD microservice
- order-service/ — Order management microservice

Configuration (defaults)

These values are read from each module's `src/main/resources/application.properties`. The defaults in this workspace are:

- discovery-server: spring.application.name=DISCOVERY-SERVER, server.port=8761
- api-gateway: spring.application.name=api-gateway, server.port=9393
- customer-service: spring.application.name=CUSTOMER-SERVICE, server.port=8181
  - MongoDB: spring.data.mongodb.uri=mongodb://localhost:27017/customer_db
- order-service: spring.application.name=order-service, server.port=7272
  - MongoDB: spring.data.mongodb.uri=mongodb://localhost:27017/order_db

Note: You can override server ports and Mongo URIs with environment variables or command-line properties when starting each Spring Boot app.

Building

From the repo root you can build each module independently or build all modules with Maven.

Examples (Windows cmd.exe):

- Build a single module (e.g., customer-service):

```cmd
cd customer-service
mvnw.cmd clean package
```

- Build from repository root (builds each module separately):

```cmd
mvnw.cmd -T 1C clean package
```

Running the services (recommended order)

1) Start MongoDB (if not running). On Windows with a service install:

```cmd
net start MongoDB
```

Or run mongod manually (if you installed it manually):

```cmd
"C:\Program Files\MongoDB\Server\4.4\bin\mongod.exe"
```

2) Start the discovery server (Eureka) first so other services can register:

```cmd
cd discovery-server
mvnw.cmd spring-boot:run
```

Open Eureka dashboard: http://localhost:8761

3) Start the api-gateway (optional; you can also call services directly):

```cmd
cd api-gateway
mvnw.cmd spring-boot:run
```

API gateway dashboard (if any): http://localhost:9393 (it will proxy to registered services)

4) Start customer-service and order-service (separate terminals):

```cmd
cd customer-service
mvnw.cmd spring-boot:run

cd ..\order-service
mvnw.cmd spring-boot:run
```

Alternatively run packaged jars (after mvn package):

```cmd
cd customer-service\target
java -jar customer-service-0.0.1-SNAPSHOT.jar

cd ..\order-service\target
java -jar order-service-0.0.1-SNAPSHOT.jar
```

Overriding ports or Mongo URI

You can override the Spring properties at startup. Examples:

```cmd
:: Change server port
mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8282"

:: Override Mongo URI using environment variable (Windows)
set SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/custom_db
mvnw.cmd spring-boot:run
```

API documentation and endpoints

Customer service (direct):

- POST /savecustomer — Create a new customer
  - Request JSON (example):

```json
{
  "customerId": "c123",
  "firstName": "Alice",
  "sirName": "Smith",
  "dob": "1990-01-01",
  "title": "Ms"
}
```
- POST /updatecustomer — Update an existing customer (same payload shape)
- GET /getcustomers — List all customers
- GET /getcustomer/{customerId} — Get a customer (returns a CustomerResponse wrapper)
- DELETE /deletecustomer/{id} — Delete a customer by id

Customer service responses are wrapped in a `ResponseDefObject<T>` that contains a status code, message, and data object.

Examples (Windows cmd):

- Create customer (direct to service):

```cmd
curl -X POST -H "Content-Type: application/json" -d "{
  \"customerId\": \"c123\",
  \"firstName\": \"Alice\",
  \"sirName\": \"Smith\",
  \"dob\": \"1990-01-01\",
  \"title\": \"Ms\"
}" http://localhost:8181/savecustomer
```

- Create customer (through API gateway):

```cmd
curl -X POST -H "Content-Type: application/json" -d "{ ... }" http://localhost:9393/customer-service/savecustomer
```

Order service (base path '/orders')

- POST /orders — Create an order
  - Request JSON example:

```json
{
  "customerId": "c123",
  "items": [
    { "productId": "p1", "quantity": 2, "price": 10.5 },
    { "productId": "p2", "quantity": 1, "price": 7.25 }
  ]
}
```

- GET /orders/{id} — Get order by id
- GET /orders?status=PROCESSING — List orders, optional status filter (statuses: PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED)
- PUT /orders/{id}/status?status=SHIPPED — Update order status
- POST /orders/{id}/cancel — Cancel order (returns 200 OK or 400 BAD REQUEST if cancel failed)

Examples (direct to order-service):

```cmd
curl -X POST -H "Content-Type: application/json" -d "{
  \"customerId\": \"c123\",
  \"items\": [
    { \"productId\": \"p1\", \"quantity\": 2, \"price\": 10.5 }
  ]
}" http://localhost:7272/orders

curl http://localhost:7272/orders/{orderId}

curl -X PUT "http://localhost:7272/orders/{orderId}/status?status=SHIPPED"

curl -X POST http://localhost:7272/orders/{orderId}/cancel
```

Using the API gateway proxy (Zuul)

Zuul is configured as an API gateway and will register with Eureka. By default Zuul will route requests by service id (e.g., `/customer-service/**` and `/order-service/**`) to the respective services. Example through gateway:

- Create customer via gateway:
  - POST http://localhost:9393/customer-service/savecustomer
- Create order via gateway:
  - POST http://localhost:9393/order-service/orders

If you add explicit Zuul routes in `api-gateway` application properties, those routes will change accordingly.

Swagger (API UI)

Both customer-service and order-service enable springfox Swagger. After starting a service, their Swagger UI is normally available at:

- Customer service: http://localhost:8181/swagger-ui.html
- Order service: http://localhost:7272/swagger-ui.html

And via API gateway (if gateway is running and proxies swagger paths):

- http://localhost:9393/customer-service/swagger-ui.html
- http://localhost:9393/order-service/swagger-ui.html

Note: springfox versions used in the pom may show different URL patterns for Swagger UI depending on the version (swagger-ui.html or /swagger-ui/index.html). If you cannot reach the UI, check the console logs and the actual mapping printed by Spring Boot at startup.

Database

- customer-service uses MongoDB database `customer_db` by default (URI: mongodb://localhost:27017/customer_db)
- order-service uses MongoDB database `order_db` by default (URI: mongodb://localhost:27017/order_db)

Tests and embedded MongoDB

- The services include `de.flapdoodle.embed.mongo` as a test dependency. Unit tests can spin up an embedded MongoDB for fast local tests.

Troubleshooting

- Connection refused to MongoDB: Ensure MongoDB is running and the URI in `application.properties` matches the host/port. On Windows check `net start MongoDB` or run `mongod` manually.
- Services not showing in Eureka: Confirm the discovery server is started before the services. Check each service logs for Eureka registration errors.
- Gateway 404s: Ensure the gateway is up and the target service is registered with Eureka. Review gateway routes in its configuration if present.
- Swagger UI not available: springfox can be sensitive to Spring Boot and springfox versions; consult the service logs to see whether Swagger is enabled and which path is exposed.

Quality gates / quick checks

- Build: `mvnw.cmd -T 1C clean package` (from repo root or per-module)
- Run Eureka: `cd discovery-server && mvnw.cmd spring-boot:run`
- Run services: start api-gateway (optional), customer-service, order-service
- Hit sample endpoints above and check MongoDB collections (`customer` and `orders`)

Security & notes

- This demo does not include authentication/authorization; don't expose it to untrusted networks.
- Some pom files include Kafka dependencies — Kafka is not configured in this README. If you plan to enable Kafka, add broker configuration and topic setup.

Next steps / Improvements

- Add Docker Compose to orchestrate MongoDB + Eureka + Gateway + services for one-command startup
- Add health checks and readiness/liveness endpoints
- Add integration tests that boot multiple services and test end-to-end flows
- Add OpenAPI generation (springdoc-openapi) for a more modern Swagger UI experience

Credits

This is a small microservices demo using Spring Boot, Eureka, and Zuul.

---

If you want, I can also:
- Add a Docker Compose file to bring up Mongo + Eureka + services
- Add example Postman collection
- Generate a minimal script to start all modules in separate terminal windows on Windows

