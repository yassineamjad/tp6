# TP - Spring Boot, Hibernate et MySQL

## Organisation

- `DemoApplication` démarre l'application Spring Boot.
- `StudentController` expose les endpoints REST des étudiants.
- `StudentService` contient la logique métier et délimite les opérations de service.
- `StudentRepository` exécute les opérations d'accès aux données avec Spring Data JPA.
- `Student` représente l'entité persistée par Hibernate.
- `StudentRequest` valide les données reçues lors de la création d'un étudiant.
- `StudentsByYear` représente le résultat du regroupement par année de naissance.
- `GlobalExceptionHandler` retourne les erreurs de validation au format JSON.
- `OpenApiConfig` configure les informations affichées par Swagger.

## Démarrage

Avec la configuration H2 par défaut :

```bash
./mvnw spring-boot:run
```

L'application démarre sur `http://localhost:8080`.

## Documentation Swagger

- Swagger UI : http://localhost:8080/swagger-ui.html
- Spécification OpenAPI JSON : http://localhost:8080/v3/api-docs

## Endpoints disponibles

| Méthode | URL | Description |
|---|---|---|
| `POST` | `/students` | Enregistrer un étudiant |
| `DELETE` | `/students/{id}` | Supprimer un étudiant |
| `GET` | `/students` | Lire tous les étudiants |
| `GET` | `/students/count` | Compter les étudiants |
| `GET` | `/students/by-year` | Compter les étudiants par année de naissance |

## Tests de l'API avec cURL

Les commandes suivantes doivent être exécutées avec l'application démarrée.

### Ajouter un étudiant

```bash
curl -i -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Mido",
    "prenom": "Ali",
    "dateNaissance": "2000-01-01"
  }'
```

Une réponse `201 Created` est attendue.

### Lire tous les étudiants

```bash
curl -i http://localhost:8080/students
```

### Compter les étudiants

```bash
curl -i http://localhost:8080/students/count
```

### Compter les étudiants par année de naissance

```bash
curl -i http://localhost:8080/students/by-year
```

### Supprimer un étudiant

Remplacer `1` par l'identifiant retourné lors de la création :

```bash
curl -i -X DELETE http://localhost:8080/students/1
```

Une suppression réussie retourne `204 No Content`. Si l'identifiant n'existe
pas, l'API retourne `404 Not Found`.

### Vérifier la validation

Les champs `nom`, `prenom` et `dateNaissance` sont obligatoires. La date de
naissance doit être antérieure à la date actuelle.

```bash
curl -i -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "",
    "prenom": "Ali",
    "dateNaissance": "2030-01-01"
  }'
```

Une réponse `400 Bad Request` est attendue avec le détail des champs invalides.

## Résultats obtenus dans ce TP

Les tests unitaires du contrôleur utilisent JUnit 5 et Mockito. Le service est
simulé afin de tester le contrôleur sans connexion à une base de données.

Exécuter tous les tests :

```bash
./mvnw test
```

Les tests couvrent notamment :

- l'enregistrement d'un étudiant avec le statut `201 Created` ;
- la suppression réussie avec le statut `204 No Content` ;
- la réponse `404 Not Found` pour un étudiant inexistant ;
- la lecture de la liste des étudiants ;
- le comptage des étudiants ;
- le regroupement des étudiants par année de naissance.

## Exemple d'erreur de connexion MySQL

Si MySQL n'est pas démarré, une erreur de connexion peut apparaître :

```text
org.hibernate.exception.JDBCConnectionException:
Unable to obtain isolated JDBC connection

Caused by: com.mysql.cj.jdbc.exceptions.CommunicationsException:
Communications link failure

Caused by: java.net.ConnectException: Connection refused
```

Vérifier que MySQL est démarré, que la base `studentdb` existe et que les
identifiants fournis par `MYSQL_USERNAME` et `MYSQL_PASSWORD` sont corrects,
puis relancer l'application avec le profil `mysql`.
