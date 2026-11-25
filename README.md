# 🐾 VetCare — Sistema de Gestión Veterinaria

VetCare es un sistema web para administrar una clínica veterinaria: clientes, mascotas, personal, servicios, citas médicas y pagos. Está hecho con **Spring Boot** (backend y API REST) y **Angular** (frontend), con seguridad por roles.

## Requisitos

- Java 21
- Maven (o un IDE como IntelliJ / Eclipse / VS Code con soporte Spring)
- MySQL 8
- Node.js 22 y npm
- Angular CLI 21 (`npm install -g @angular/cli`)

## Cómo ejecutarlo

### 1. Base de datos

La base `BD_VetCare` se crea sola al iniciar el backend (`createDatabaseIfNotExist=true`), solo necesitas MySQL encendido.

Ejecuta el backend una vez (crea las tablas) y luego inserta el usuario administrador:

```sql
INSERT INTO usuario (username, password, rol, estado)
VALUES ('admin', '$2b$12$9Ldn6ZfCoGMtpXZjpWxquOxpSsmNMYjWZARHwBlLxJbc0nKQYljAe', 'ADMIN', 1);
```

### 2. Backend (Spring Boot)

1. Abre `src/main/resources/application.properties` y ajusta tus credenciales de MySQL:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=TU_PASSWORD
   ```
2. Ejecuta la clase principal como *Spring Boot App*, o desde la terminal:
   ```bash
   mvn spring-boot:run
   ```
3. El backend queda en **http://localhost:8080**.

### 3. Frontend (Angular)

```bash
cd vetcare-frontend
npm install
ng serve
```

Abre **http://localhost:4200** e inicia sesión con:
(usuario y contraseña solo para uso local)
| Usuario | Contraseña |
|---------|------------|
| `admin` | `admin123` |

> El backend y MySQL deben estar corriendo antes de abrir el frontend.

## Roles y permisos

| Rol | Qué puede hacer |
|---|---|
| **Administrador** | Acceso total: usuarios, personal, servicios, clientes, mascotas, citas, pagos |
| **Asistente** | Clientes, mascotas, especies, crear citas y registrar pagos |
| **Veterinario** | Ver clientes, mascotas y citas; iniciar y finalizar atención |

El menú se adapta al rol del usuario. Si intenta entrar a una URL sin permiso, ve una página de acceso denegado.

## Qué puede hacer

- **Clientes y mascotas:** registro, edición y búsqueda en vivo (por nombre o DNI del cliente, por mascota).
- **Personal:** gestión de veterinarios (con especialidad) y asistentes, con fecha de contratación.
- **Catálogo de servicios y especies:** precios validados (mayores a cero) y activación/desactivación.
- **Citas:** se agenda con mascota, veterinario y servicios marcados con checkbox; **el total lo calcula el backend**.
- **Flujo de estados:** `PENDIENTE → EN_ATENCION → FINALIZADA → PAGADA`, solo en ese orden.
- **Pagos y boleta:** el asistente registra el pago de una cita finalizada y puede **imprimir la boleta**; queda registrado quién cobró y cuándo.
- **Usuarios:** administración de cuentas con contraseñas cifradas (BCrypt).
- **Confirmaciones** con SweetAlert2 en acciones sensibles.

## Reglas de negocio principales

- Solo empleados activos participan en nuevas operaciones; solo veterinarios activos pueden asignarse a una cita.
- La mascota debe existir y pertenecer al cliente.
- Solo servicios activos pueden agregarse, y una cita debe tener al menos uno.
- Toda cita inicia como `PENDIENTE`; el estado solo avanza siguiendo el flujo permitido.
- Solo una cita `FINALIZADA` puede pagarse.
- Crear una cita y registrar un pago son operaciones `@Transactional`: o se guarda todo, o nada.

## Estructura del proyecto

```
vetcare/                      (backend)
└── src/main/java/edu/pe/cibertec/
    ├── config/               seguridad y CORS
    ├── controller/           controllers MVC y REST (/api/**)
    ├── dto/                  objetos de petición/respuesta
    ├── entity/               entidades JPA (herencia JOINED)
    ├── repository/           acceso a datos
    └── service/              lógica de negocio

vetcare-frontend/             (Angular)
└── src/app/
    ├── guards/               auth, rol e invitado
    ├── models/               interfaces
    ├── pages/                clientes, mascotas, citas, pagos, etc.
    └── services/             consumo de la API
```

## Tecnologías

Java 21 · Spring Boot 3 · Spring Data JPA / Hibernate · Spring Security · MySQL · Lombok · Thymeleaf · Angular 21 · Bootstrap 5 · SweetAlert2
