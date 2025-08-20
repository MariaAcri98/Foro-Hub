# ForoHub API REST
*Una API REST para la gestión de tópicos y respuestas, que simula un foro de discusión en línea.
Permite a los usuarios crear, leer, actualizar y eliminar tópicos, con autenticación y seguridad basada en JSON Web Tokens (JWT).*

### Características principales:
- **Gestión de Tópicos:** CRUD (Crear, Leer, Actualizar, Eliminar) de tópicos.
- **Autenticación y Seguridad:** Implementación de JWT para proteger los endpoints y garantizar que solo los usuarios autorizados puedan interactuar con la API.
- **Validación de Datos:** Uso de jakarta.validation para asegurar la integridad de los datos de entrada.
- **Control de Acceso:** Filtros de seguridad para verificar la validez del token en cada petición.
- **Exclusividad de Tópicos:** Lógica de negocio para evitar la duplicación de tópicos (validando que no exista un tópico con el mismo título y mensaje).

## Tecnologías Utilizadas
* **Java 17:** Lenguaje de programación.
* **Spring Boot 3:** Framework principal para la construcción de la API.
* **Spring Security:** Módulo para la autenticación y autorización.
* **JWT (JSON Web Token) con Auth0:** Biblioteca para la generación y validación de tokens.
* **MySQL:** Base de datos relacional para la persistencia de datos.
* **Maven:** Herramienta de gestión y construcción del proyecto.
* **Lombok:** Para reducir el código repetitivo (getters, setters, constructores, etc.).

## Requisitos del sistema
*Para ejecutar este proyecto, necesitas tener instalado:*

- JDK 17 o superior.
- Maven 3.6 o superior.
- Un servidor de base de datos MySQL.
- Una herramienta para probar APIs como Postman o Insomnia.

## Instrucciones de Configuración y Ejecución

1. **Clonar el repositorio:**

`git clone https://www.youtube.com/watch?v=3GymExBkKjE
cd forohub`

2. **Configurar la base de datos:**

Crea una base de datos en MySQL con el nombre forohub.
Abre el archivo
`src/main/resources/application.properties` y configura las credenciales de tu base de datos:

`spring.datasource.url=jdbc:mysql://localhost:3306/forohub`
`spring.datasource.username=tu_usuario_de_mysql`
`spring.datasource.password=tu_contraseña_de_mysql`

3. **Ejecutar la aplicación:**
   * Desde la terminal, navega a la raíz del proyecto y ejecuta:
     
     `mvn spring-boot:run`
     
     La aplicación se ejecutará en `http://localhost:8080.`

## Endpoints de la API

*Aquí se describen los endpoints principales.*

**Autenticación**

* `POST /login`: Autentica a un usuario y retorna un token JWT.
  
* **Body:**
  
`{`

`"login"`: `"usuario1"`,

`"password"`: `"contraseña123"`

`}`
* **Respuesta:**
  
  `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`
  
**Nota:** Este token debe ser enviado en el encabezado `Authorization:
Bearer <token_jwt>` para acceder a los endpoints protegidos.

* **Tópicos**
  
* `POST /topicos`: Crea un nuevo tópico.
* **Headers:** Authorization: `Bearer <token_jwt>`
* **Body:**
  
`{`

`"titulo": "Mi primer tópico",`
  
`"mensaje": "Este es el mensaje de mi primer tópico...",`
 
`"autor": "nombre_de_usuario",`

`"curso": "Programacion"`

`}`

* `GET /topicos:` Obtiene la lista de todos los tópicos.
* **Headers:** `Authorization: Bearer <token_jwt>`
* `GET /topicos/{id}`: Obtiene un tópico por su ID.
* **Headers:** `Authorization: Bearer <token_jwt>`
* `PUT /topicos/{id}` Actualizar tópico existente.
* **Headers:** `Authorization: Bearer <token_jwt>`
* **Body:** `JSON` con los datos a actualizar (por ejemplo, el título o el mensaje).
* `DELETE /topicos/{id}` Eliminar el tópico pos su ID.
* **Headers:** `Authorization: Bearer <token_jwt>`

  ## AUTOR:

  * Maria Soledad Acri
  * Linkedln: https://www.linkedin.com/in/maria-acri-75a4a0343/
  * GitHub: https://github.com/MariaAcri98
  


