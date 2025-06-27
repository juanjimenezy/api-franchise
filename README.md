# API Franchise - Sistema de Gestión de Franquicias

## Descripción

API REST para la gestión de franquicias, sucursales y productos implementada con Clean Architecture y programación reactiva usando Spring Boot y Project Reactor.

## Cómo Ejecutar la Aplicación

### Prerrequisitos
- Java 17 o superior
- Gradle (incluido wrapper)

### Ejecución

```bash  
# Usando Gradle Wrapper (recomendado)  [header-1](#header-1)
./gradlew bootRun  
  
# O compilar y ejecutar el JAR  [header-2](#header-2)
./gradlew build  
java -jar applications/app-service/build/libs/app-service.jar

```

# Estructura de la API

## Arquitectura: Clean Architecture

La aplicación sigue los principios de **Clean Architecture**, con una separación estricta de responsabilidades entre capas.

---

## Capas de la Arquitectura

### 1. Domain (Dominio)

- **Entities:**  
  Modelos de negocio como:
    - `Franchise`
    - `Branch`
    - `Product`

- **Use Cases:**  
  Contiene la lógica de aplicación y los casos de uso.

- **Repository Gateways:**  
  Interfaces o contratos para el acceso a los datos.

---

### 2. Infrastructure (Infraestructura)

- **Entry Points:**  
  Controladores REST (API Controllers).

- **Driven Adapters:**  
  Implementaciones concretas de los repositorios definidos en el dominio.

- **Helpers:**  
  Clases utilitarias y funciones compartidas.

---

### 3. Application (Aplicación)

- **Configuración de Spring Boot:**  
  Manejo de propiedades y configuración del framework.

- **Inyección de dependencias automática:**  
  Gestión automática de beans y componentes.

- **Punto de entrada principal:**  
  Clase principal que inicia la aplicación.


## Estructura de modulos
```bash  
api-franchise/  
├── domain/  
│   ├── model/           # Entidades y gateways  
│   └── usecase/         # Casos de uso  
├── infrastructure/  
│   ├── driven-adapters/ # Implementaciones de repositorios  
│   ├── entry-points/    # Controladores REST  
│   └── helpers/         # Utilidades  
└── applications/  
    └── app-service/     # Aplicación Spring Boot  

```


# Programación Reactiva

La API utiliza **Project Reactor** para realizar operaciones no bloqueantes:

- Todos los repositorios retornan `Mono<T>` para operaciones individuales.
- Uso de **streams reactivos** para el manejo de **backpressure**.
- Operaciones **asíncronas de extremo a extremo (end-to-end)**.

---

# Casos de Uso Implementados

- **FranchiseUseCase:**  
  Gestión de franquicias.

- **BranchUseCase:**  
  Gestión de sucursales.

- **ProductUseCase:**  
  Gestión de productos.

---

# Inyección de Dependencias

Los casos de uso utilizan **inyección por constructor** con **Lombok**:

- Uso de `@RequiredArgsConstructor` para la generación automática de constructores.
- **Descubrimiento automático de beans** mediante `@ComponentScan`.
- Dependencias **inmutables** y **testeables**.

---

# Endpoints de la API

## Franquicias

- `GET /franchises` - Listar franquicias
- `POST /franchises` - Crear franquicia
- `PUT /franchises/{id}` - Actualizar franquicia
- `DELETE /franchises/{id}` - Eliminar franquicia

## Sucursales

- `GET /branches` - Listar sucursales
- `POST /branches` - Crear sucursal
- `PUT /branches/{id}` - Actualizar sucursal
- `DELETE /branches/{id}` - Eliminar sucursal

## Productos

- `GET /products` - Listar productos
- `POST /products` - Crear producto
- `PUT /products/{id}` - Actualizar producto
- `DELETE /products/{id}` - Eliminar producto

---

# Tecnologías Utilizadas

- **Spring Boot:** Framework principal.
- **Project Reactor:** Programación reactiva.
- **Lombok:** Reducción de código boilerplate.
- **Gradle:** Gestión de dependencias y construcción.
- **Clean Architecture:** Patrón arquitectónico base.
