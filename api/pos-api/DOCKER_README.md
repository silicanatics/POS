# Running POS-API in Docker

## Prerequisites
- Docker and Docker Compose installed
- The WAR file built: `target/pos-api-0.0.1-SNAPSHOT.war`

## Quick Start

### Option 1: Using Docker Compose (Recommended)
This will start both MySQL and the POS-API application together.

```powershell
# Navigate to the project directory
cd C:\development\NEW\api\pos-api

# Build the Docker image and start all services
docker-compose up --build

# Or run in detached mode (background)
docker-compose up -d --build
```

The application will be available at: `http://localhost:8080/pos`

### Option 2: Using Docker Build and Run Manually

1. **First, ensure the WAR file is built:**
   ```powershell
   mvn clean package
   ```

2. **Build the Docker image:**
   ```powershell
   docker build -t pos-api:latest .
   ```

3. **Run with Docker (requires MySQL running separately):**
   ```powershell
   docker run -d `
     -p 8080:8080 `
     -e SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/posdb?allowPublicKeyRetrieval=false&useSSL=true&serverTimezone=UTC `
     -e SPRING_DATASOURCE_USERNAME=root `
     -e SPRING_DATASOURCE_PASSWORD=Sondeck@123 `
     --name pos-api `
     pos-api:latest
   ```

## Configuration

All application properties can be overridden via environment variables when running the container. The docker-compose.yml already includes all the necessary configurations.

### Key Environment Variables:
- `SPRING_DATASOURCE_URL` - Database URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `SPRING_SECURITY_USER_NAME` - Default security username
- `SPRING_SECURITY_USER_PASSWORD` - Default security password
- `JAVA_OPTS` - JVM options

## Useful Commands

### Check running containers:
```powershell
docker ps
```

### View logs:
```powershell
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f pos-api
docker-compose logs -f mysql
```

### Stop services:
```powershell
docker-compose down
```

### Stop and remove all data:
```powershell
docker-compose down -v
```

### Rebuild the image:
```powershell
docker-compose build --no-cache
docker-compose up -d
```

### Access MySQL from host:
```powershell
docker exec -it pos-mysql mysql -u root -pSondeck@123 posdb
```

### View container details:
```powershell
docker inspect pos-api
docker inspect pos-mysql
```

## Troubleshooting

### Application won't connect to database:
1. Check if MySQL container is healthy: `docker ps` (look for "(healthy)" status)
2. Wait a few seconds for MySQL to fully initialize
3. Check logs: `docker-compose logs mysql`

### Port already in use:
Change the port mapping in docker-compose.yml:
```yaml
services:
  pos-api:
    ports:
      - "8081:8080"  # Changed from 8080
```

### Application crashes on startup:
1. Check logs: `docker-compose logs pos-api`
2. Ensure all environment variables are properly set
3. Verify the WAR file exists: `ls target/pos-api-0.0.1-SNAPSHOT.war`

## Development Workflow

For development with hot reload:
1. Build the WAR: `mvn clean package`
2. Restart the container: `docker-compose restart pos-api`

## Network Communication

- **MySQL** is accessible from the app container at `mysql:3306`
- **POS-API** is accessible from the host at `http://localhost:8080/pos`
- Both services are on the `pos-network` bridge network

## Credentials

- **Database**: 
  - Username: `root`
  - Password: `Sondeck@123`
  - Database: `posdb`

- **API Security**:
  - Username: `shahiem`
  - Password: `test123`

## Production Considerations

For production deployments:
1. Use environment variables for sensitive data (don't hardcode credentials)
2. Configure proper health checks
3. Set appropriate resource limits in docker-compose.yml
4. Use docker secrets or external configuration management
5. Set `SPRING_JPA_HIBERNATE_DDL_AUTO=validate` to prevent accidental data loss
6. Configure persistent volumes for database data
