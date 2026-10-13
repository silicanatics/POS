# Run the POS application with Docker Compose

The Compose stack builds the Angular UI and Spring API, then starts them with
MySQL and RabbitMQ. Docker Compose provides the internal service discovery used
by the API and the UI's `/pos` reverse proxy.

## Start

From this directory, copy the example environment file and replace every
`replace-with-...` value with a strong, unique value:

```powershell
Copy-Item .env.example .env
docker compose up --build -d
```

Open <http://localhost:8080/POSUI/>. The API is also published at
<http://localhost:8091/pos>; the UI sends API traffic through its own `/pos`
path so the browser does not need to resolve Docker service names.

The UI and API ports can be changed with `POS_UI_PORT` and `POS_API_PORT` in
`.env`. Keep `API_URL=/pos` when using the included Nginx proxy.

## Database initialization and persistence

MySQL data is kept in the `mysql_data` volume. The default
`JPA_SCHEMA_MODE=update` preserves database contents across API container
restarts. It creates/updates tables, but does not execute the application's
Hibernate seed scripts.

For a new, empty database that should be populated using those seed scripts,
set `JPA_SCHEMA_MODE=create` before the first startup. Once the API has
successfully initialized the database, change it back to `update` and recreate
the API container:

```powershell
docker compose up --build -d
# Change JPA_SCHEMA_MODE to update in .env after initialization.
docker compose up -d --force-recreate pos-api
```

Do not use `create` or `create-drop` with a database containing data: Hibernate
will remove existing tables/data. `docker compose down` stops the stack and
keeps named volumes; `docker compose down -v` also deletes the MySQL and
RabbitMQ data.

## Operations

```powershell
docker compose ps
docker compose logs -f pos-ui pos-api mysql rabbitmq
docker compose down
```

The application still depends on any physical/network printers configured in
its data; those devices are external to this Compose stack.
