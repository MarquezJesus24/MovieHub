# 🎬 MovieHub Backend — Microservicios Spring Boot

Arquitectura backend para **MovieHub**, una plataforma de catálogo de películas construida con **Spring Boot 3**, **Spring Cloud**, y **Eureka**.  
Incluye servicios independientes para gestión, descubrimiento, configuración y gateway de API.

> 🔗 El frontend Angular correspondiente se encuentra en [`moviehub-frontend/`](https://github.com/MarquezJesus24/MovieHub-Frontend/blob/main/README.md)

---

## 🧱 Tabla de contenidos
- [Arquitectura general](#arquitectura-general)
- [Requisitos](#requisitos)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Puertos usados](#puertos-usados)
- [Configuración](#configuración)
- [Ejecución paso a paso](#ejecución-paso-a-paso)
- [Endpoints principales](#endpoints-principales)
- [Problemas frecuentes](#problemas-frecuentes)
- [Notas de desarrollo](#notas-de-desarrollo)
- [Enlace al frontend](#enlace-al-frontend)

---

## 🏗️ Arquitectura general

Microservicios Spring Boot conectados mediante:
- **Spring Cloud Config Server** → configuración centralizada  
- **Eureka Server** → descubrimiento de servicios  
- **Spring Cloud Gateway** → punto único de entrada  
- **Microservicio de Películas** → CRUD + búsqueda  

---

## ⚙️ Requisitos

| Dependencia | Versión recomendada |
|--------------|--------------------|
| Java | 21 |
| Maven | 3.9+ |
| Git | Última versión |

Verifica tus versiones:
```bash
java -version
mvn -v
```

---

## 📂 Estructura del proyecto

```text
.
├─ api-gateway/                # Spring Cloud Gateway (WebFlux)
├─ microservice-config/        # Config Server
├─ microservice-eureka/        # Eureka Server
├─ microservice-movie/         # Servicio de películas
├─ moviehub-frontend/          # Frontend Angular 20
└─ pom.xml                     # Multi-módulo Maven (si aplica)
```

---

## 🌐 Puertos usados (por defecto)

| Servicio | Puerto |
|-----------|--------|
| Config Server | 8888 |
| Eureka Server | 8761 |
| API Gateway | 8080 |
| Microservice Movie | 8090 |
| Frontend Angular | 4200 |

---

## 🔧 Configuración

### 1️⃣ Config Server

Cada servicio debe importar la configuración centralizada:

```yaml
spring:
  config:
    import: optional:configserver:http://localhost:8888
```

Ejemplo de `config/api-gateway.yml`:

```yaml
server:
  port: 8080

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
  instance:
    preferIpAddress: true

spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
        - id: movies
          uri: lb://MICROSERVICE-MOVIE
          predicates:
            - Path=/api/movies/**
```

Verifica:  
👉 [http://localhost:8888/api-gateway/default](http://localhost:8888/api-gateway/default)

---

### 2️⃣ CORS en API Gateway

Archivo: `api-gateway/src/main/java/.../config/CorsConfig.java`

```java
@Configuration
public class CorsConfig {
  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration cors = new CorsConfiguration();
    cors.setAllowedOrigins(List.of("http://localhost:4200"));
    cors.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS","PATCH"));
    cors.setAllowedHeaders(List.of("*"));
    cors.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cors);
    return new CorsWebFilter(source);
  }
}
```

---

## 🚀 Ejecución paso a paso

> ⚠️ El orden es **crítico** para el arranque.

1️⃣ **Config Server**
```bash
cd microservice-config
mvn spring-boot:run
```

2️⃣ **Eureka Server**
```bash
cd microservice-eureka
mvn spring-boot:run
# http://localhost:8761
```

3️⃣ **Microservice Movie**
```bash
cd microservice-movie
mvn spring-boot:run
```

4️⃣ **API Gateway**
```bash
cd api-gateway
mvn clean package
mvn spring-boot:run
```

---

## 📡 Endpoints principales

A través del Gateway (`http://localhost:8080/api`):

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| GET | `/movies` | Listar todas las películas |
| GET | `/movies/movie/{id}` | Detalle de película |
| GET | `/movies/all-movies-by-status/publicada` | Filtrar por estado |
| GET | `/movies/all-movies-by-name/{name}` | Buscar por nombre |
| POST | `/movies` | Crear película |
| PUT | `/movies/{id}` | Actualizar |
| DELETE | `/movies/{id}` | Eliminar |

---

## 🧩 Troubleshooting

| Problema | Causa/Solución |
|-----------|----------------|
| 🚫 *503 Service Unavailable* | Microservicio no registrado en Eureka o nombre distinto en `uri: lb://SERVICE-NAME`. |
| ⚠️ *Error CORS* | Agrega `http://localhost:4200` en `CorsConfig`. |
| ❌ *404 desde Angular* | Verifica que el `apiUrl` no tenga barra final (`/`). |
| 🔎 *Campos nulos (fecha, etc.)* | Verifica nombres de campos. |

---

## 🧠 Notas de desarrollo

- Centraliza configuración en el **Config Server**.
- Todos los servicios deben registrarse en **Eureka**.
- Controla CORS únicamente desde el **Gateway**.
- Usa `@EnableDiscoveryClient` en cada microservicio.
- Para nuevos microservicios, añade su configuración en `config/`.

---

## 🧱 Stack tecnológico

- **Spring Boot 3 + Java 21**
- **Spring Cloud (Eureka, Config, Gateway)**
- **Maven 3.9+**
- **PostgreSQL (sugerido)**
- **Frontend Angular 20 (ver enlace abajo)**

---

## 🔗 Enlace al frontend

➡️ [MovieHub Frontend — Angular 20](https://github.com/MarquezJesus24/MovieHub-Frontend/blob/main/README.md)
