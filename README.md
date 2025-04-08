
# Página-Noticias

## Descripción

**Página-Noticias** es una aplicación web que permite a los usuarios interactuar con noticias, creando, comentando y calificando noticias. Los usuarios pueden registrarse, iniciar sesión y gestionar su contenido a través de una API RESTful.

### Funcionalidades principales:
- **Inicio de sesión y registro** de usuarios utilizando **JWT** para autenticación.
- Los usuarios pueden **crear, editar y eliminar noticias**.
- Los usuarios pueden **comentar y calificar** noticias.
- **Interacción en tiempo real** para la calificación de noticias (me gusta/no me gusta).

## Tecnologías utilizadas

### Front-End:
- **HTML**: Para la estructura de las páginas web.
- **CSS**: Para los estilos y diseño de la interfaz.
- **JavaScript**: Para la lógica interactiva en las páginas (manejo de formularios, interacción con la API).
- **Vanilla JavaScript** (sin frameworks adicionales).

### Back-End:
- **Spring Boot**: Para la creación de la API RESTful.
- **JWT**: Para la autenticación y autorización de los usuarios.
- **JPA/Hibernate**: Para la gestión de la base de datos.
- **MySQL**: Base de datos relacional para almacenar usuarios, noticias, comentarios y calificaciones.

## Base de datos

La base de datos utilizada es **`noticias`**, donde se almacenan los datos relacionados con los usuarios, noticias, comentarios y calificaciones.

## Estructura del Proyecto

### 1. **Front-End**:
   - **`loginVanilla`**: Contiene la página de inicio de sesión, con sus estilos y lógica en JavaScript.
   - **`fronted_proyecto_noticias`**:
     - **`Card_notice`**: Componentes relacionados con las noticias (detalles, interacciones como comentarios y likes).
     - **`Login`**: Formulario de inicio de sesión.
     - **`Principal_page`**: Página principal que muestra las noticias disponibles.
     - **`Register`**: Página de registro de nuevos usuarios.

### 2. **Back-End**:
   - **Controladores**: Gestionan las rutas para el acceso a las noticias, comentarios, y usuarios.
   - **Servicios**: Implementan la lógica de negocio para la gestión de noticias, comentarios, y autenticación.
   - **Modelos**: Definen las entidades que interactúan con la base de datos (Noticia, Comentario, Usuario, Calificación).
   - **Repositorios**: Interactúan con la base de datos para obtener, crear, actualizar o eliminar registros.

## Instalación

### Requisitos:
- **Java 21** o superior.
- **Maven** para la construcción y gestión del proyecto.
- **MySQL** para la base de datos.

### 1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/Pagina-Noticias.git
   cd Pagina-Noticias
   ```

### 2. **Configurar la base de datos**:
   - Crea una base de datos **`noticias`** en MySQL.
   - Configura las credenciales en el archivo **`application.properties`**.

### 3. **Ejecutar el back-end**:
   - Navega a la carpeta del proyecto y ejecuta:
   ```bash
   mvn spring-boot:run
   ```

### 4. **Ejecutar el front-end**:
   - Abre el archivo `index.html` en un navegador o configura un servidor para servir los archivos estáticos.

## Uso

### Endpoints disponibles en la API (Back-End):

#### **Calificación de Noticias**:
- **PUT** `/api/calificacionNoticia/actualizar/{id}`: Actualizar una calificación existente.
- **POST** `/api/calificacionNoticia/crear`: Crear una nueva calificación para una noticia.
- **POST** `/api/calificacionNoticia/buscar`: Obtener la calificación de una noticia.
- **DELETE** `/api/calificacionNoticia/eliminar/{id}`: Eliminar una calificación.

#### **Noticias**:
- **PUT** `/api/Noticia/actualizar/{id}`: Actualizar una noticia existente.
- **POST** `/api/Noticia/crear`: Crear una nueva noticia.
- **GET** `/api/Noticia/usuario/{usuarioId}`: Obtener noticias de un usuario.
- **DELETE** `/api/Noticia/eliminar/{id}`: Eliminar una noticia.

#### **Comentarios**:
- **PUT** `/api/Comentario/actualizar/{id}`: Actualizar un comentario.
- **POST** `/api/Comentario/crear`: Crear un nuevo comentario en una noticia.
- **GET** `/api/Comentario/noticia/{noticiaId}`: Obtener los comentarios de una noticia.
- **DELETE** `/api/Comentario/eliminar/{id}`: Eliminar un comentario.

#### **Autenticación**:
- **POST** `/auth/register`: Registro de usuario.
- **POST** `/auth/login`: Login de usuario.

### Documentación de las APIs

La documentación completa de las APIs se puede visualizar en Swagger, a través del siguiente enlace:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
