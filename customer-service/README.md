# customer-service

This service requires a running MongoDB instance. By default the service uses the connection URI set in `src/main/resources/application.properties`:

    spring.data.mongodb.uri=mongodb://localhost:27017/customer_db

If your MongoDB is running on a different host or port, update `application.properties` accordingly, or set the `SPRING_DATA_MONGODB_URI` environment variable.

Quick start (local MongoDB on default port 27017):

1. Start MongoDB locally (on Windows with the default installation):

   - Start the MongoDB service: `net start MongoDB`
   - Or run `mongod` if you use a manual install.

2. Build and run the service (requires Java and Maven installed):

   - Set JAVA_HOME to your JDK installation directory.
   - Build: `./mvnw package` (on Windows use `mvnw.cmd`)
   - Run: `java -jar target/customer-service-<version>.jar`

If you intentionally want to use port 27016, set `spring.data.mongodb.uri=mongodb://localhost:27016/customer_db` and ensure Mongo is listening on that port.
