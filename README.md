# Service Queue

A full-stack web application for managing a first-come, first-served queue. It supports service desks, clinics, barbershops, and other businesses that serve people in order of arrival.

## Features

- Register a person with a name, phone number, and service type.
- View all entries in arrival order and call the next waiting person.
- Mark a called person as served or remove an entry from the queue.
- Track each entry through three states: Waiting, Called, and Served.

## Technology

- **Backend:** Java 17, Spring Boot 3.5, Spring Web, Spring Data JPA, and Bean Validation
- **Database:** H2 in memory
- **Frontend:** HTML, CSS, and vanilla JavaScript using the Fetch API
- **Build:** Maven

The Spring Boot app serves both the web interface and the REST API. The backend separates the controller, queue rules, and database access into controller, service, and repository layers.

## Run locally

Install JDK 17 or later, Maven 3.8 or later, and Git. Then run:

```bash
git clone https://github.com/rafaeldev-05/Fullstack-java-springboot.git
cd Fullstack-java-springboot
mvn spring-boot:run
```

Open [localhost:8080](http://localhost:8080). To run the existing tests, use `mvn test`.

In IntelliJ IDEA, open the folder containing `pom.xml` and load the Maven project if prompted. You can then run `FilaSolidariaApplication` from the editor.

## Use the queue

1. Enter a name and phone number, choose a service, and click **Register**. The new entry starts as **Waiting**.
2. Click **Call next** to move the earliest waiting entry to **Called**.
3. Click **Mark as served** to complete a called entry.
4. Use **Remove** to delete an entry or **Refresh** to reload the list.

## REST API

The API retains its existing routes, request fields, and status values. The web interface displays English labels for them.

| Method | Path | Operation |
| --- | --- | --- |
| POST | `/api/fila` | Register a person |
| GET | `/api/fila` | List all entries in arrival order |
| GET | `/api/fila/aguardando` | List waiting entries in arrival order |
| PATCH | `/api/fila/chamar-proximo` | Call the next waiting person |
| PATCH | `/api/fila/{id}/atendido` | Mark a called person as served |
| DELETE | `/api/fila/{id}` | Remove an entry |

Example registration request:

```json
{
  "nome": "Alex Morgan",
  "telefone": "(555) 010-1234",
  "tipoAtendimento": "Clinica"
}
```

The API returns `AGUARDANDO` (Waiting), `CHAMADO` (Called), or `ATENDIDO` (Served). A person must be called before being marked as served.

## Local database

H2 stores entries in memory, so they are cleared when the application stops. To inspect the database while the app is running, open [the H2 console](http://localhost:8080/h2-console). Use the JDBC URL `jdbc:h2:mem:filasolidaria`, username `sa`, and an empty password.

## Author

Rafael Freitas · [GitHub](https://github.com/rafaeldev-05)
