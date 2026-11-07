# Student API


## Requirements

- Java 17 or later
- Maven, or the included Maven Wrapper

## Run the application

The default configuration uses an in-memory H2 database, so no external database is required:

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080`.

## Swagger

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/students` | Save a student |
| `DELETE` | `/students/{id}` | Delete a student |
| `GET` | `/students` | List all students |
| `GET` | `/students/count` | Count students |
| `GET` | `/students/by-year` | Count students by birth year |

The create endpoint expects JSON in this format:

```json
{
  "nom": "Mido",
  "prenom": "Ali",
  "dateNaissance": "2000-01-01"
}
```

## Test the API with cURL

With the application running, create a student:

```bash
curl -i -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Mido",
    "prenom": "Ali",
    "dateNaissance": "2000-01-01"
  }'
```

List all students:

```bash
curl -i http://localhost:8080/students
```

Count students:

```bash
curl -i http://localhost:8080/students/count
```

Count students by birth year:

```bash
curl -i http://localhost:8080/students/by-year
```

Delete a student by ID:

```bash
curl -i -X DELETE http://localhost:8080/students/1
```

An invalid request returns `400 Bad Request`. For example:

```bash
curl -i -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "",
    "prenom": "Ali",
    "dateNaissance": "2030-01-01"
  }'
```

## Use MySQL

MySQL is available through the `mysql` Spring profile:

```bash
MYSQL_USERNAME=root MYSQL_PASSWORD=your_password \
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

The MySQL database `studentdb` must already exist on `localhost:3306`.

## Run tests

```bash
./mvnw test
```
