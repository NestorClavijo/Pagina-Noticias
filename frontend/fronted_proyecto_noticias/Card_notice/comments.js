// funcionalidad cerrar sesion 

const btnCerrarSesion = document.getElementById('btn-cerrar-sesion-2');
btnCerrarSesion.addEventListener('click', () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    alert("Sesión cerrada con éxito.");
    window.location.href = '../Login/Login.html';
});

const params = new URLSearchParams(window.location.search);
const noticiaId = params.get('id');
const titulo = params.get('titulo');
const descripcion = params.get('descripcion');

document.getElementById('titulo-noticia').textContent = titulo;
document.getElementById('contenido-noticia').textContent = descripcion;


//Crear comentario
document.getElementById('form-comentario').addEventListener('submit', async function(event) {
    event.preventDefault(); // Evitar el envío del formulario
    const params = new URLSearchParams(window.location.search);
    const usuarioId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');
    const noticiaId = params.get('id');
    const descripcion = document.getElementById('descripcion-comentario').value;
    try {
        const response = await fetch(`http://localhost:8080/api/Comentario/crear`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify({
                descripcion: descripcion,
                noticiaId: noticiaId,
                usuarioId: usuarioId
            })
        });
        if (!response.ok) throw new Error("Error al crear el comentario");
        const comentarioCreado = await response.json();
        console.log("Comentario creado:", comentarioCreado);
        document.getElementById('descripcion-comentario').value = '';
        agregarComentarioAlDOM(comentarioCreado,parseInt(usuarioId));
    } catch (error) {
        console.error("Error al crear el comentario:", error);
        alert("No se pudo crear el comentario. Intenta nuevamente.");
    }
});

// --- Listar comentarios ---
async function obtenerComentarios() {
    const params = new URLSearchParams(window.location.search);
    const token = localStorage.getItem('token');
    const noticiaId = params.get('id');
    try {
        const response = await fetch(`http://localhost:8080/api/Comentario/noticia/${noticiaId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });
        if (!response.ok) throw new Error("Error al obtener las noticias");
        const comentarios = await response.json();
        //console.log("Noticias obtenidas:", noticias);
        actualizarDOMComentarios(comentarios);
    } catch (error) {
        console.error("Error al obtener los comentarios:", error);
        alert("No se pudieron obtener los comentarios. Intenta nuevamente.");
    }
}

function actualizarDOMComentarios(comentarios) {
    const usuarioId = parseInt(localStorage.getItem('userId'));
    const container = document.getElementById('comentarios-lista');
    container.innerHTML = ''; // Limpiar el contenedor
    comentarios.forEach(comentario => {
        if(usuarioId === comentario.usuario.id_usuario){
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
        }
        else{
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

function agregarComentarioAlDOM(comentario,usuarioId)  {
    const container = document.getElementById('comentarios-lista');
    if(usuarioId === comentario.usuario.id_usuario){
        container.insertAdjacentHTML('afterbegin',`
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
                `) ;
    }
    else{
        container.insertAdjacentHTML('afterbegin', `<div class="media comentario-item mb-3 p-3 border rounded">
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
    obtenerComentarios();
});

// -- Funcionalidad de eliminar comentario
document.getElementById('comentarios-lista').addEventListener('click', async function(e) {
    const boton = e.target.closest('.btn-outline-danger');
    if (!boton) return;

    const idComentario = boton.getAttribute('data-id');
    const token = localStorage.getItem('token');

    if (!confirm("¿Estás seguro de que deseas eliminar este comentario?")) return;

    try {
        const response = await fetch(`http://localhost:8080/api/Comentario/eliminar/${idComentario}`, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });

        if (!response.ok) throw new Error("Error al eliminar el comentario");

        // Eliminar del DOM
        const comentarioDOM = boton.closest('.comentario-item');
        if (comentarioDOM) comentarioDOM.remove();

        console.log(`Comentario ${idComentario} eliminado con éxito.`);
    } catch (error) {
        console.error("Error al eliminar el comentario:", error);
        alert("No se pudo eliminar el comentario.");
    }
});


