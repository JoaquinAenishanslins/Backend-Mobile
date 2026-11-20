# Backend Mobile - Plataforma CORPALIV / Alba Lab
> **Asignatura:** DSY1105 - Desarrollo de Aplicaciones Móviles  
> **Institución:** Duoc UC  
> **Proyecto:** Sistema de Inclusión Social y Gestión de Alianzas Corporativas  

---

## 📌 Descripción del Proyecto
Este repositorio contiene el ecosistema de **microservicios Backend** desarrollado en **Java Spring Boot**, diseñado para dar soporte a la aplicación móvil Android de **CORPALIV** (Corporación de Padres y Amigos por la Limitación Visual).

La arquitectura desacoplada implementa persistencia relacional con **MySQL** orquestado vía **Docker**, endpoints RESTful con soporte CORS y scripts automatizados de compilación y despliegue multiplataforma (Linux / Windows).

---

## 🏗️ Arquitectura de Microservicios

```
Backend-Mobile/
├── docker-compose.yml          # Orquestación de MySQL 8.0 en contenedor Docker
├── iniciar_servicios.sh        # Script automatizado de build y arranque (Linux)
├── detener_servicios.sh        # Script de parada y limpieza (Linux)
├── iniciar_servicios.bat       # Script automatizado de build y arranque (Windows)
├── detener_servicios.bat       # Script de parada y limpieza (Windows)
├── alianza-service/            # Microservicio de Solicitudes y Alianzas (Puerto 8081)
│   ├── src/main/java/cl/duoc/corpaliv/alianza/
│   │   ├── AlianzaModel.java
│   │   ├── AlianzaRepository.java
│   │   ├── AlianzaService.java
│   │   ├── AlianzaController.java
│   │   └── AlianzaServiceApplication.java
│   └── pom.xml
└── catalogo-service/           # Microservicio de Catálogo de Productos Solidarios (Puerto 8082)
    ├── src/main/java/cl/duoc/corpaliv/catalogo/
    │   ├── ProductoModel.java
    │   ├── ProductoRepository.java
    │   ├── ProductoService.java
    │   ├── ProductoController.java
    │   └── CatalogoServiceApplication.java
    └── pom.xml
```

---

## 🛠️ Tecnologías y Herramientas

- **Lenguaje:** Java 21 (OpenJDK LTS)
- **Framework:** Spring Boot 4.x / 3.x
  - Spring Web (Controladores REST y MVC)
  - Spring Data JPA (Capa de persistencia con Hibernate)
  - Lombok (Boilerplate reduction: Getters, Setters, Builders)
- **Base de Datos:** MySQL 8.0
- **Contenedores:** Docker & Docker Compose
- **Gestor de Dependencias:** Apache Maven (Maven Wrapper `mvnw`)

---

## 🚀 Puesta en Marcha Rápida

### Prerrequisitos
- Tener instalado **Docker Desktop** (o Docker Engine en Linux).
- Tener instalado **Java JDK 21**.

### En Linux
Dar permisos y ejecutar el script principal:
```bash
chmod +x iniciar_servicios.sh detener_servicios.sh
./iniciar_servicios.sh
```
*Para detener todo:*
```bash
./detener_servicios.sh
```

### En Windows
Ejecutar haciendo doble clic o desde terminal CMD / PowerShell:
```cmd
iniciar_servicios.bat
```
*Para detener todo:*
```cmd
detener_servicios.bat
```

---

## 📡 Catálogo de Endpoints RESTful

### 1. `alianza-service` (Puerto `8081`)
Gestiona el flujo de solicitudes de alianzas corporativas y capacitaciones enviadas desde el formulario de la app móvil.

| Método | Endpoint | Descripción | Body (JSON) |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/solicitudes` | Lista todas las solicitudes registradas | Ninguno |
| **GET** | `/api/solicitudes/{id}` | Obtiene el detalle de una solicitud por ID | Ninguno |
| **POST** | `/api/solicitudes` | Registra una nueva solicitud de alianza | Ver estructura abajo |
| **PUT** | `/api/solicitudes/{id}` | Actualiza datos o estado de una solicitud | Estructura completa |
| **DELETE**| `/api/solicitudes/{id}` | Elimina un registro de solicitud | Ninguno |

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

### 2. `catalogo-service` (Puerto `8082`)
Gestiona los productos solidarios y talleres elaborados por la comunidad de CORPALIV.

| Método | Endpoint | Descripción | Parámetros |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/productos` | Lista todo el catálogo de productos | Ninguno |
| **GET** | `/api/productos?soloActivos=true` | Filtra solo productos habilitados para venta | `soloActivos=true` |
| **GET** | `/api/productos/{id}` | Obtiene el detalle de un producto por ID | Ninguno |
| **POST** | `/api/productos` | Registra un nuevo producto solidario | Ver estructura abajo |
| **PUT** | `/api/productos/{id}` | Actualiza precio, stock o datos del producto | Estructura completa |
| **DELETE**| `/api/productos/{id}` | Elimina un producto del catálogo | Ninguno |

#### Ejemplo Payload `POST /api/productos`:
```json
{
  "nombre": "Pulsera Trenzada Inclusiva",
  "descripcion": "Elaborada artesanalmente en taller sensorial por jóvenes de CORPALIV",
  "precio": 3500.0,
  "stock": 25,
  "categoria": "ARTESANIA",
  "activo": true
}
```

---

## 📱 Guía de Integración con la App Móvil (Android / Retrofit)

Para conectar la aplicación Android desarrollada en Kotlin/Jetpack Compose:

1. **Configurar el manifiesto (`AndroidManifest.xml`):**
   ```xml
   <application
       android:usesCleartextTraffic="true"
       ... >
   ```
2. **Definir la URL Base en Retrofit según el entorno de prueba:**
   - **Emulador de Android Studio:**
     - Alianzas: `http://10.0.2.2:8081/`
     - Catálogo: `http://10.0.2.2:8082/`
   - **Dispositivo Físico (mismo Wi-Fi):**
     - Alianzas: `http://<IP_LOCAL_PC>:8081/`
     - Catálogo: `http://<IP_LOCAL_PC>:8082/`

---

## 👥 Equipo de Desarrollo
- **Backend Developer & DevOps:** Joaquín Aenishanslins (`@aenismerino`)
- **Frontend & Android Integration:** Equipo DSY1105 - Duoc UC
