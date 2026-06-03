# GAC Backend — Architecture

## Package layout

```
com.gac.api
├── ApiApplication
├── core
│   ├── domain          # User, Projector, Key, Movement, enums
│   ├── gateway         # Port interfaces
│   └── usecase         # Application services (execute())
│       ├── user/
│       ├── projector/
│       ├── key/
│       └── movement/
└── infrastructure
    ├── configuration   # Spring @Bean wiring for use cases
    └── persistence     # JPA adapters per aggregate
        ├── user/
        ├── projector/
        ├── key/
        └── movement/
```

## Domain model (v1.3 alignment)

### Roles
`ADMIN`, `ATTENDANT`, `PROFESSOR`

### Item status
`AVAILABLE`, `RESERVED`, `ON_LOAN`, `MAINTENANCE`

### Movement types
`RESERVATION`, `LOAN`, `RETURN`, `EXCHANGE`

### Movement lifecycle
Each movement references **one asset** (`assetType` + `assetId`).

| Status | Meaning |
|--------|---------|
| `OPEN` | Active reservation or loan |
| `COMPLETED` | Finished (e.g. reservation converted to loan) |
| `CANCELLED` | Cancelled reservation |

**RN08:** movements are never deleted — only status transitions and new records.

### Asset fields (Projector / Key)
- `reservedRegistrationNumber` — set when status is `RESERVED`
- `defectDescription` — set when returned with defect or exchanged
- Projector: `serialNumber`
- Key: `spareKey`, optional `assetTag`

## Movement flow (reservation → loan)

```
Professor                    System                         Attendant
    |                           |                                |
    |-- CreateReservation ----->|                                |
    |                           | asset → RESERVED               |
    |                           | movement RESERVATION / OPEN    |
    |<-- confirmation code -----|                                |
    |                           |                                |
    |                           |<-------- ConfirmLoan ----------|
    |                           | validate code (RN12)           |
    |                           | RESERVATION → COMPLETED        |
    |                           | LOAN / OPEN                    |
    |                           | asset → ON_LOAN                |
```

### Use cases
| Use case | Actor | Description |
|----------|-------|-------------|
| `CreateReservationUseCase` | Professor | UC11 — reserve available asset, generate 4-digit code |
| `ConfirmLoanUseCase` | Attendant | UC03 — validate code, close reservation, open loan |
| `CancelReservationUseCase` | Professor | UC11 alt — cancel own open reservation |

## Presentation layer (Sprint 1)

```
com.gac.api.presentation
├── controller/     AuthController, UserController
├── dto/request/    LoginRequest, ChangePasswordRequest
├── dto/response/   LoginResponse, UserResponse
├── mapper/         UserMapper
└── exception/      GlobalExceptionHandler, ApiErrorResponse
```

### Security
- Stateless JWT (`Authorization: Bearer <token>`)
- BCrypt password hashing via `PasswordHasher` port
- `@PreAuthorize` on protected routes

### API endpoints

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/api/auth/login` | Public | Login with registration number + password |
| GET | `/api/users/me` | JWT | Current user profile |
| PATCH | `/api/users/me/password` | JWT | Change password (UC18) |

### Dev seed user
| Registration | Password | Role |
|--------------|----------|------|
| `admin` | `admin123` | ADMIN |

Configure JWT in `application.properties`: `gac.jwt.secret`, `gac.jwt.expiration-ms`.

## Local run

```bash
./gradlew bootRun
```

H2 console: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:gac`)

### Example login

```bash
curl -s -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"registrationNumber":"admin","password":"admin123"}'
```
