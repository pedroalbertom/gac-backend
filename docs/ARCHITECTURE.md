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

## Naming conventions

- Use cases: `Create*`, `List*`, `Update*`, `Delete*`, `Find*`
- Gateway methods: `save`, `findAll`, `findById`, `deleteById`
- Domain method on use cases: `execute(...)`
- Roles: `ADMIN`, `ATTENDANT`
- Item status: `AVAILABLE`, `ON_LOAN`, `MAINTENANCE`
- Movement type: `LOAN`, `RETURN` (legacy prototype; will change with requirements v1.3)

## Local run

```bash
./gradlew bootRun
```

H2 console: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:gac`)
