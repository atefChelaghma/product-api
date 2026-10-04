# Product API

A REST API for managing a product catalog. Products are stored in MongoDB and exposed through versioned JSON endpoints.

## Requirements

- Java 21
- Docker with Docker Compose, or a MongoDB instance you can connect to
- No separate Maven installation is required; this repository includes the Maven Wrapper

## Run locally

1. Start MongoDB from the project root:

   ```bash
   docker compose up -d mongodb
   ```

   The Compose configuration exposes MongoDB on `localhost:27017` and creates the `product_store` database.

2. Start the API in another terminal:

   ```bash
   ./mvnw spring-boot:run
   ```

   The application uses `mongodb://localhost:27017/product_store` by default and listens on port `8080`.

To use a different MongoDB instance, set `MONGODB_URI` before starting the application:

```bash
MONGODB_URI='mongodb://username:password@host:27017/product_store' ./mvnw spring-boot:run
```

## API documentation

With the application running, open Swagger UI at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html). The generated OpenAPI 3 JSON is available at [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs).

## Product endpoints

All endpoints are under `/api/v1/products`.

| Method | Path | Description | Success response |
| --- | --- | --- | --- |
| `GET` | `/api/v1/products` | List all products | `200 OK` |
| `GET` | `/api/v1/products/{id}` | Get one product | `200 OK` |
| `POST` | `/api/v1/products` | Create a product | `201 Created` |
| `PUT` | `/api/v1/products/{id}` | Replace a product's fields | `200 OK` |
| `DELETE` | `/api/v1/products/{id}` | Delete a product | `204 No Content` |

### Product request

The create and update endpoints accept the following JSON. `image` and `description` must not be blank, `feedback` must be zero or greater, and `price` must be greater than zero.

```json
{
  "image": "https://example.com/images/product.png",
  "description": "A sample product",
  "feedback": 5,
  "price": 12.50
}
```

### Product response

Successful create, read, and update operations return a product in this shape:

```json
{
  "id": "generated-mongodb-id",
  "image": "https://example.com/images/product.png",
  "description": "A sample product",
  "feedback": 5,
  "price": 12.50
}
```

An unknown product ID returns `404 Not Found`. Invalid request fields return `400 Bad Request` with a problem detail response containing an `errors` object keyed by field name.

## Run tests

```bash
./mvnw test
```
