# Backend Mobile - Plataforma CORPALIV / Alba Lab
> **Asignatura:** DSY1105 - Desarrollo de Aplicaciones Móviles  
> **Institución:** Duoc UC  
> **Proyecto:** Sistema de Inclusión Social, Catálogo Solidario y Gestión de Alianzas Corporativas  

---

## 📌 Descripción del Proyecto
Este repositorio contiene el ecosistema completo de **microservicios Backend** desarrollado en **Java Spring Boot**, diseñado para dar soporte a la aplicación móvil Android de **CORPALIV** (Corporación de Padres y Amigos por la Limitación Visual).

La arquitectura desacoplada implementa:
- **Base de Datos Relacional:** MySQL 8.0 en contenedor Docker.
- **Autenticación y Seguridad:** Microservicio `auth-service` con contraseñas hasheadas en SHA-1 y emisión de tokens **JWT** (JSON Web Tokens).
- **Punto de Entrada Unificado:** `api-gateway` que centraliza todas las rutas y simplifica la conexión de la app móvil a un solo puerto (`8080`).
- **Orquestación Multi-contenedor:** Docker Compose con redes puente internas y scripts de arranque automático multiplataforma (`.sh` y `.bat`).

---

## 🏗️ Arquitectura del Sistema

```
Backend-Mobile/
├── docker-compose.yml          # Orquestación de MySQL 8.0 + 4 Microservicios en Docker
├── iniciar_servicios.sh        # Script automatizado de inicio Docker (Linux)
├── detener_servicios.sh        # Script de parada y limpieza (Linux)
├── iniciar_servicios.bat       # Script automatizado de inicio Docker (Windows)
├── detener_servicios.bat       # Script de parada y limpieza (Windows)
│
├── api-gateway/                # API Gateway Central (Puerto 8080)
│   ├── Dockerfile
│   ├── src/main/resources/application.yaml
│   └── pom.xml
│
├── auth-service/               # Autenticación y Emisión de JWT (Puerto 8083)
│   ├── Dockerfile
│   ├── src/main/java/cl/duoc/corpaliv/auth/
│   │   ├── Model/UserModel.java
│   │   ├── Repository/UserRepository.java
│   │   ├── Service/HashService.java
│   │   ├── Service/JwtService.java
│   │   ├── Service/UserService.java
│   │   ├── Controller/AuthController.java
│   │   └── AuthServiceApplication.java
│   └── pom.xml
│
├── alianza-service/            # Gestión de Solicitudes y Alianzas (Puerto 8081)
│   ├── Dockerfile
│   ├── src/main/java/cl/duoc/corpaliv/alianza/
│   │   ├── Model/AlianzaModel.java
│   │   ├── Repository/AlianzaRepository.java
│   │   ├── Service/AlianzaService.java
│   │   ├── Controller/AlianzaController.java
│   │   └── AlianzaServiceApplication.java
│   └── pom.xml
│
└── catalogo-service/           # Catálogo de Productos Solidarios (Puerto 8082)
    ├── Dockerfile
    ├── src/main/java/cl/duoc/corpaliv/catalogo/
    │   ├── Model/ProductoModel.java
    │   ├── Repository/ProductoRepository.java
    │   ├── Service/ProductoService.java
    │   ├── Controller/ProductoController.java
    │   └── CatalogoServiceApplication.java
    └── pom.xml
```

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 21 (OpenJDK LTS)
- **Framework:** Spring Boot 4.x
  - Spring Web (RESTful API controllers)
  - Spring Data JPA & Hibernate
  - Spring Cloud Gateway (Ruteo reactivo unificado)
  - Lombok (Generación automática de constructores, getters y setters)
  - JJWT 0.12.5 (Generación y validación de tokens JWT)
- **Base de Datos:** MySQL 8.0
- **Contenedores:** Docker & Docker Compose (Imágenes ligeras con Alpine Linux)

---

## 🚀 Puesta en Marcha Rápida

### Prerrequisitos
- Tener instalado y corriendo **Docker** / **Docker Desktop**.

### En Linux
```bash
chmod +x iniciar_servicios.sh detener_servicios.sh
./iniciar_servicios.sh
```
*Para detener todos los contenedores:*
```bash
./detener_servicios.sh
```

### En Windows
Hacer doble clic en:
```cmd
iniciar_servicios.bat
```
*Para detener:*
```cmd
detener_servicios.bat
```

*Comandos útiles de Docker:*
- Ver contenedores activos: `docker compose ps`
- Ver logs en vivo: `docker compose logs -f`
- Recompilar imágenes si hiciste cambios de código: `docker compose up --build -d`

---

## 📡 Catálogo Completo de Endpoints (Vía API Gateway :8080)

> 💡 **Nota para el equipo de Frontend:** La app móvil **solo debe comunicarse con el puerto `8080`**. El Gateway se encarga de dirigir cada petición al microservicio correspondiente.

### 1. Autenticación y Usuarios (`auth-service`)

#### Registro de Usuario
- **Método:** `POST`
- **URL:** `http://localhost:8080/auth/register`
- **Body:**
```json
{
  "email": "estudiante@duocuc.cl",
  "password": "miPasswordSeguro123"
}
```
- **Respuesta:**
```json
{
  "message": "Usuario registrado exitosamente!"
}
```

#### Inicio de Sesión (Login) y Obtención de Token JWT
- **Método:** `POST`
- **URL:** `http://localhost:8080/auth/login`
- **Body:**
```json
{
  "email": "estudiante@duocuc.cl",
  "password": "miPasswordSeguro123"
}
```
- **Respuesta Exitosa:**
```json
{
  "status": "ok",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlc3R1ZGlhbnRlQGR1b2N1Yy5jbCIsImlhdCI6MTc5NTE3ODkyOCwiZXhwIjoxNzk1MTgyNTI4fQ..."
}
```

---

### 2. Solicitudes de Alianzas (`alianza-service`)

| Método | Endpoint | Descripción | Body Requerido |
| :--- | :--- | :--- | :--- |
| **GET** | `http://localhost:8080/api/solicitudes` | Listar todas las solicitudes | No |
| **GET** | `http://localhost:8080/api/solicitudes/{id}` | Obtener una solicitud por ID | No |
| **POST** | `http://localhost:8080/api/solicitudes` | Crear nueva solicitud desde app | Sí (Ver abajo) |
| **PUT** | `http://localhost:8080/api/solicitudes/{id}` | Actualizar datos/estado | Sí |
| **DELETE**| `http://localhost:8080/api/solicitudes/{id}` | Eliminar una solicitud | No |

#### Ejemplo Payload `POST /api/solicitudes`:
```json
{
  "nombreEmpresa": "Fundación Inclusiva Chile",
  "emailContacto": "contacto@fundacion.cl",
  "telefono": "+56912345678",
  "cantidadTrabajadores": 45,
  "fechaReunion": "2026-12-15",
  "requiereCapacitacion": true,
  "aceptaTerminos": true
}
```

---

### 3. Catálogo de Productos Solidarios (`catalogo-service`)

| Método | Endpoint | Descripción | Parámetros |
| :--- | :--- | :--- | :--- |
| **GET** | `http://localhost:8080/api/productos` | Listar catálogo completo | Ninguno |
| **GET** | `http://localhost:8080/api/productos?soloActivos=true` | Filtrar solo productos en stock/activos | `soloActivos=true` |
| **GET** | `http://localhost:8080/api/productos/{id}` | Detalle de un producto | Ninguno |
| **POST** | `http://localhost:8080/api/productos` | Agregar nuevo producto solidario | Sí (Ver abajo) |
| **PUT** | `http://localhost:8080/api/productos/{id}` | Actualizar stock, precio o datos | Sí |
| **DELETE**| `http://localhost:8080/api/productos/{id}` | Dar de baja un producto | Ninguno |

#### Ejemplo Payload `POST /api/productos`:
```json
{
  "nombre": "Pulsera Trenzada Inclusiva",
  "descripcion": "Elaborada artesanalmente por jóvenes en los talleres sensoriales de CORPALIV",
  "precio": 3500.0,
  "stock": 30,
  "categoria": "ARTESANIA",
  "activo": true
}
```

---

## 📱 Guía de Integración con Android (Retrofit)

Para que la aplicación móvil se comunique con este backend:

1. **Permitir tráfico HTTP en `AndroidManifest.xml`:**
   ```xml
   <application
       android:usesCleartextTraffic="true"
       ... >
   ```

2. **Base URL en la instancia de Retrofit:**
   * **Si pruebas en el emulador de Android Studio:**
     ```kotlin
     val BASE_URL = "http://10.0.2.2:8080/"
     ```
   * **Si pruebas en tu teléfono físico (conectado al mismo Wi-Fi que el PC):**
     ```kotlin
     val BASE_URL = "http://192.168.X.X:8080/" // Reemplazar por la IP local de tu computador
     ```

3. **Cómo enviar el Token JWT en las peticiones autenticadas:**
   Agrega el interceptor o header HTTP:
   ```
   Authorization: Bearer <TOKEN_OBTENIDO_EN_LOGIN>
   ```

---

## 👥 Equipo de Trabajo
- **Backend & DevOps:** Joaquín Aenishanslins (`@aenismerino`)
- **Desarrollo Móvil Android:** Equipo DSY1105 - Duoc UC
