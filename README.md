# ms-campuslab-audit

Microservicio encargado de registrar y consultar el historial de auditoría de eventos académicos: quién solicitó, aprobó, entregó o recibió de vuelta un equipo o recurso de laboratorio.

## Responsabilidades

- Recibir y persistir eventos de auditoría enviados por otros microservicios (`bookings`, `catalog`) vía REST.
- Exponer un endpoint de consulta para trazabilidad de reservas y acciones de usuarios.

## Por qué no usa Kafka

En el diseño original del curso, la auditoría se alimentaba de streaming con Kafka. En esta implementación se reemplazó por persistencia directa en MySQL más llamadas REST síncronas desde los servicios de dominio, simplificando la arquitectura para el alcance del proyecto.

## Stack técnico

- Java 21 + Spring Boot 3.3
- Spring Data JPA + MySQL
- Spring Security OAuth2 Resource Server (JWT de Azure AD)
- Flyway (migraciones de base de datos)

## Configuración

| Variable | Descripción | Default |
|---|---|---|
| `SPRING_DATASOURCE_URL` | URL de conexión a MySQL | `jdbc:mysql://localhost:3306/audit_db` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de MySQL | `root` |
| `SPRING_DATASOURCE_PASSWORD` | Password de MySQL | (vacío) |

Puerto por defecto: **8085**

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/audit/events` | Registra un nuevo evento de auditoría |
| `GET` | `/api/audit/events?bookingId=&actorId=` | Consulta eventos con filtros opcionales |

### Ejemplo: registrar un evento

```http
POST /api/audit/events
Content-Type: application/json

{
  "bookingId": "1",
  "action": "APROBADA",
  "actorId": "usr-456",
  "actorRole": "Operador",
  "detail": "Aprobado por operador de laboratorio"
}
```

### Ejemplo: consultar eventos de una reserva

```http
GET /api/audit/events?bookingId=1
```

Respuesta:

```json
[
  {
    "id": 1,
    "bookingId": "1",
    "action": "SOLICITADA",
    "actorId": "usr-123",
    "actorRole": "Cliente",
    "timestamp": "2026-09-13T18:00:00",
    "detail": null
  },
  {
    "id": 2,
    "bookingId": "1",
    "action": "APROBADA",
    "actorId": "usr-456",
    "actorRole": "Operador",
    "timestamp": "2026-09-13T18:05:00",
    "detail": "Aprobado por operador de laboratorio"
  }
]
```

## Modelo de datos

Tabla `audit_events`:

| Columna | Tipo | Descripción |
|---|---|---|
| `id` | BIGINT | PK autoincremental |
| `booking_id` | VARCHAR(50) | ID de la reserva relacionada |
| `action` | VARCHAR(50) | Acción/estado registrado |
| `actor_id` | VARCHAR(50) | Usuario que ejecutó la acción |
| `actor_role` | VARCHAR(30) | Rol del usuario (Admin, Operador, Cliente) |
| `event_timestamp` | DATETIME | Momento del evento |
| `detail` | VARCHAR(1000) | Metadata opcional |
| `created_at` | DATETIME | Momento de inserción en la base |

## Cómo correr localmente

```bash
mvn spring-boot:run
```

## Cómo correr con Docker

```bash
docker build -t ms-campuslab-audit .
docker run -p 8085:8085 ms-campuslab-audit
```

## Seguridad

**Nota:** este servicio recibe llamadas de servicio a servicio (desde `bookings`, `catalog`), no directamente de usuarios finales. Si se exige JWT en todas las rutas, esos microservicios deben incluir un token válido (client credentials flow) en sus llamadas. Alternativamente, se puede restringir el acceso a nivel de red interna/Security Group en vez de exigir JWT de usuario.