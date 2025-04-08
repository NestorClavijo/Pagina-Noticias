// funcionalidad cerrar sesion 
// Este bloque de código permite cerrar sesión cuando el usuario hace clic en el botón de cierre de sesión.
// El token y el ID del usuario se eliminan de localStorage y luego se redirige al usuario a la página de login.
const btnCerrarSesion = document.getElementById('btn-cerrar-sesion-2');
btnCerrarSesion.addEventListener('click', () => {
    localStorage.removeItem('token'); // Elimina el token de autenticación
    localStorage.removeItem('userId'); // Elimina el ID del usuario
    alert("Sesión cerrada con éxito."); // Muestra un mensaje al usuario
    window.location.href = '../Login/Login.html'; // Redirige al usuario a la página de login
});

// Obtención de parámetros de la URL para mostrar el título y la descripción de la noticia
const params = new URLSearchParams(window.location.search);
const noticiaId = params.get('id'); // Obtiene el ID de la noticia desde la URL
const titulo = params.get('titulo'); // Obtiene el título de la noticia desde la URL
const descripcion = params.get('descripcion'); // Obtiene la descripción de la noticia desde la URL

// Se actualizan los elementos HTML con la información de la noticia
document.getElementById('titulo-noticia').textContent = titulo;
document.getElementById('contenido-noticia').textContent = descripcion;

// Crear comentario
// Este bloque se encarga de crear un comentario cuando el usuario lo envía a través de un formulario.
document.getElementById('form-comentario').addEventListener('submit', async function(event) {
    event.preventDefault(); // Evita que el formulario se envíe de forma tradicional
    const params = new URLSearchParams(window.location.search);
    const usuarioId = localStorage.getItem('userId'); // Obtiene el ID del usuario de localStorage
    const token = localStorage.getItem('token'); // Obtiene el token JWT de localStorage
    const noticiaId = params.get('id'); // Obtiene el ID de la noticia desde la URL
    const descripcion = document.getElementById('descripcion-comentario').value; // Obtiene el contenido del comentario del formulario

    try {
        // Se realiza una petición POST al backend para crear el comentario
        const response = await fetch(`http://localhost:8080/api/Comentario/crear`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // Se indica que el contenido es JSON
                'Authorization': 'Bearer ' + token // Se incluye el token JWT para la autenticación
            },
            body: JSON.stringify({
                descripcion: descripcion,
                noticiaId: noticiaId,
                usuarioId: usuarioId
            })
        });

        // Si la respuesta no es exitosa, lanza un error
        if (!response.ok) throw new Error("Error al crear el comentario");

        const comentarioCreado = await response.json(); // Convierte la respuesta en JSON
        console.log("Comentario creado:", comentarioCreado); // Muestra el comentario creado en la consola
        document.getElementById('descripcion-comentario').value = ''; // Limpia el campo de descripción
        agregarComentarioAlDOM(comentarioCreado, parseInt(usuarioId)); // Añade el comentario al DOM
    } catch (error) {
        console.error("Error al crear el comentario:", error); // Muestra el error en la consola
        alert("No se pudo crear el comentario. Intenta nuevamente."); // Muestra un mensaje de error al usuario
    }
});

// --- Listar comentarios ---
// Función para obtener todos los comentarios asociados a una noticia
async function obtenerComentarios() {
    const params = new URLSearchParams(window.location.search);
    const token = localStorage.getItem('token'); // Obtiene el token JWT de localStorage
    const noticiaId = params.get('id'); // Obtiene el ID de la noticia desde la URL

    try {
        // Se realiza una petición GET para obtener los comentarios de la noticia
        const response = await fetch(`http://localhost:8080/api/Comentario/noticia/${noticiaId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json', // Se indica que el contenido es JSON
                'Authorization': 'Bearer ' + token // Se incluye el token JWT para la autenticación
            }
        });

        // Si la respuesta no es exitosa, lanza un error
        if (!response.ok) throw new Error("Error al obtener los comentarios");

        const comentarios = await response.json(); // Convierte la respuesta en JSON
        actualizarDOMComentarios(comentarios); // Actualiza el DOM con los comentarios obtenidos
    } catch (error) {
        console.error("Error al obtener los comentarios:", error); // Muestra el error en la consola
        alert("No se pudieron obtener los comentarios. Intenta nuevamente."); // Muestra un mensaje de error al usuario
    }
}

// Función para actualizar el DOM con los comentarios obtenidos
function actualizarDOMComentarios(comentarios) {
    const usuarioId = parseInt(localStorage.getItem('userId')); // Obtiene el ID del usuario desde localStorage
    const container = document.getElementById('comentarios-lista'); // Obtiene el contenedor de comentarios
    container.innerHTML = ''; // Limpia el contenedor de comentarios

    comentarios.forEach(comentario => {
        // Añade un comentario al DOM, diferenciando si es del usuario logueado
        if (usuarioId === comentario.usuario.id_usuario) {
            container.innerHTML += `
                <div class="media comentario-item mb-3 p-3 border rounded">
                    <i class="fas fa-user-circle fa-2x text-primary mr-3"></i>
                    <div class="media-body">
                        <h6 class="mt-0 mb-1 font-weight-bold text-dark">${comentario.usuario.nombre} ${comentario.usuario.apellido}</h6>
                        <p class="text-secondary">${comentario.descripcion}</p>
                        <div class="acciones-comentario">
                        <button class="btn btn-sm btn-outline-warning">
                            <i class="fas fa-edit"></i> Editar
                        </button>
                        <button class="btn btn-sm btn-outline-danger" data-id="${comentario.id_comentario}">
                            <i class="fas fa-trash-alt"></i> Eliminar
                        </button>
                        </div>
                    </div>
                </div>
            `;
        } else {
            container.innerHTML += `
                <div class="media comentario-item mb-3 p-3 border rounded">
                    <i class="fas fa-user-circle fa-2x text-secondary mr-3"></i>
                    <div class="media-body">
                        <h6 class="mt-0 mb-1 font-weight-bold text-dark">${comentario.usuario.nombre} ${comentario.usuario.apellido}</h6>
                        <p class="text-secondary">${comentario.descripcion}</p>
                    </div>
                </div>
            `;
        }
    });
}

// Función para agregar un comentario al DOM después de ser creado
function agregarComentarioAlDOM(comentario, usuarioId) {
    const container = document.getElementById('comentarios-lista');
    // Añade el comentario al principio de la lista, diferenciando si es del usuario logueado
    if (usuarioId === comentario.usuario.id_usuario) {
        container.insertAdjacentHTML('afterbegin', `
            <div class="media comentario-item mb-3 p-3 border rounded">
                <i class="fas fa-user-circle fa-2x text-primary mr-3"></i>
                <div class="media-body">
                    <h6 class="mt-0 mb-1 font-weight-bold text-dark">${comentario.usuario.nombre} ${comentario.usuario.apellido}</h6>
                    <p class="text-secondary">${comentario.descripcion}</p>
                    <div class="acciones-comentario">
                        <button class="btn btn-sm btn-outline-warning">
                            <i class="fas fa-edit"></i> Editar
                        </button>
                        <button class="btn btn-sm btn-outline-danger" data-id="${comentario.id_comentario}">
                            <i class="fas fa-trash-alt"></i> Eliminar
                        </button>
                    </div>
                </div>
            </div>
        `);
    } else {
        container.insertAdjacentHTML('afterbegin', `
            <div class="media comentario-item mb-3 p-3 border rounded">
                <i class="fas fa-user-circle fa-2x text-secondary mr-3"></i>
                <div class="media-body">
                    <h6 class="mt-0 mb-1 font-weight-bold text-dark">${comentario.usuario.nombre} ${comentario.usuario.apellido}</h6>
                    <p class="text-secondary">${comentario.descripcion}</p>
                </div>
            </div>
        `);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    obtenerComentarios(); // Llama a la función para cargar los comentarios cuando se carga la página
});

// Funcionalidad para eliminar un comentario
document.getElementById('comentarios-lista').addEventListener('click', async function(e) {
    const boton = e.target.closest('.btn-outline-danger'); // Obtiene el botón de eliminar
    if (!boton) return; // Si no se ha hecho clic en un botón de eliminar, no hace nada

    const idComentario = boton.getAttribute('data-id'); // Obtiene el ID del comentario a eliminar
    const token = localStorage.getItem('token'); // Obtiene el token JWT

    if (!confirm("¿Estás seguro de que deseas eliminar este comentario?")) return; // Muestra una confirmación antes de eliminar

    try {
        // Realiza una solicitud DELETE para eliminar el comentario
        const response = await fetch(`http://localhost:8080/api/Comentario/eliminar/${idComentario}`, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + token // Se incluye el token JWT para autenticar la petición
            }
        });

        if (!response.ok) throw new Error("Error al eliminar el comentario");

        // Elimina el comentario del DOM
        const comentarioDOM = boton.closest('.comentario-item');
        if (comentarioDOM) comentarioDOM.remove();

        console.log(`Comentario ${idComentario} eliminado con éxito.`);
    } catch (error) {
        console.error("Error al eliminar el comentario:", error);
        alert("No se pudo eliminar el comentario.");
    }
});

// Funcionalidad para editar un comentario
let comentarioIdSeleccionado = null;

document.getElementById('comentarios-lista').addEventListener('click', function(e) {
    const btnEditar = e.target.closest('.btn-outline-warning'); // Obtiene el botón de editar
    if (!btnEditar) return; // Si no se ha hecho clic en un botón de editar, no hace nada

    // Obtiene el contenido del comentario actual
    const comentarioDOM = btnEditar.closest('.comentario-item');
    const contenido = comentarioDOM.querySelector('p.text-secondary').textContent;

    // Guarda el ID y el contenido del comentario
    comentarioIdSeleccionado = comentarioDOM.querySelector('button.btn-outline-danger').getAttribute('data-id');
    document.getElementById('input-editar-comentario').value = contenido; // Muestra el contenido en el campo de edición

    // Muestra el modal para editar el comentario
    const modalElement = document.getElementById('modalEditarComentario');
    const modal = new bootstrap.Modal(modalElement);
    modal.show();
});

// Guarda la edición del comentario y actualiza el DOM
document.getElementById('guardar-edicion').addEventListener('click', async () => {
    const nuevoTexto = document.getElementById('input-editar-comentario').value; // Obtiene el nuevo texto del comentario
    const token = localStorage.getItem('token'); // Obtiene el token JWT

    try {
        // Realiza una solicitud PUT para actualizar el comentario
        const response = await fetch(`http://localhost:8080/api/Comentario/actualizar/${comentarioIdSeleccionado}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token // Incluye el token JWT para la autenticación
            },
            body: JSON.stringify({
                descripcion: nuevoTexto // Se envía el nuevo texto del comentario
            })
        });

        if (!response.ok) throw new Error('Error al editar comentario');

        // Cierra el modal después de editar
        const modal = bootstrap.Modal.getInstance(document.getElementById('modalEditarComentario'));
        modal.hide();

        // Refresca los comentarios o actualiza solo el comentario editado en el DOM
        obtenerComentarios();
    } catch (error) {
        console.error('Error al editar comentario:', error);
        alert('No se pudo editar el comentario.');
    }
});

