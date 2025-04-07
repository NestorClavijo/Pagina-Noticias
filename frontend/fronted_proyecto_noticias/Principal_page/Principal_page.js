// --- Funcionalidad de cierre de sesión ---
const btnCerrarSesion = document.getElementById('btn-cerrar-sesion');
btnCerrarSesion.addEventListener('click', () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    alert("Sesión cerrada con éxito.");
    window.location.href = '../Login/Login.html';
});

// --- Crear noticia ---
document.getElementById('formulario-noticia').addEventListener('submit', async function (e) {
    e.preventDefault();
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');
    const titulo = document.getElementById('noticeTitle').value;
    const descripcion = document.getElementById('noticeContent').value;
    const noticia = { titulo, descripcion, usuarioId: userId };

    try {
        const response = await fetch('http://localhost:8080/api/Noticia/crear', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(noticia)
        });
        if (!response.ok) throw new Error("Error al crear la noticia");
        const noticiaCreada = await response.json();
        console.log("Noticia creada:", noticiaCreada);
        agregarNoticiaAlDOM(noticiaCreada);
        $('#modalNuevaNoticia').modal('hide');
    } catch (error) {
        console.error("Error al crear la noticia:", error);
        alert("No se pudo crear la noticia. Intenta nuevamente.");
    }
});

// --- Listar noticias ---
async function obtenerNoticias() {
    const usuarioId = localStorage.getItem('userId');  
    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`http://localhost:8080/api/Noticia/usuario/${usuarioId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });
        if (!response.ok) throw new Error("Error al obtener las noticias");
        const noticias = await response.json();
        console.log("Noticias obtenidas:", noticias);
        actualizarDOMNoticias(noticias);
    } catch (error) {
        console.error("Error al obtener las noticias:", error);
        alert("No se pudieron obtener las noticias. Intenta nuevamente.");
    }
}

function actualizarDOMNoticias(noticias) {
    const container = document.getElementById('noticias-container');
    container.innerHTML = ''; // Limpiar el contenedor
    noticias.forEach(noticia => {
        container.innerHTML += `
      <div class="col-md-4 mb-4">
        <div class="card shadow-sm h-100" data-id="${noticia.id_noticia}" data-titulo="${noticia.titulo}" data-descripcion="${noticia.descripcion}">
          <div class="card-body d-flex flex-column">
            <h5 class="card-title">
              <a href="../Card_notice/notice_detail.html?id=${noticia.id_noticia}&titulo=${encodeURIComponent(noticia.titulo)}&descripcion=${encodeURIComponent(noticia.descripcion)}" class="text-dark text-decoration-none font-weight-bold">
                ${noticia.titulo}
              </a>
            </h5>
            <p class="card-text flex-grow-1 text-muted">
              ${noticia.descripcion}
            </p>
            <div class="d-flex justify-content-between">
              <button class="btn btn-warning btn-sm mr-2" data-toggle="modal" data-target="#modalEdicion">
                <i class="fas fa-edit"></i> Editar
              </button>
              <button class="btn btn-danger btn-sm">
                <i class="fas fa-trash-alt"></i> Eliminar
              </button>
            </div>
          </div>
        </div>
      </div>
    `;
    });
}

function agregarNoticiaAlDOM(noticia) {
    const container = document.getElementById('noticias-container');
    container.insertAdjacentHTML('afterbegin', `
      <div class="col-md-4 mb-4">
        <div class="card shadow-sm h-100" data-id="${noticia.id_noticia}" data-titulo="${noticia.titulo}" data-descripcion="${noticia.descripcion}">
          <div class="card-body d-flex flex-column">
            <h5 class="card-title">
              <a href="../Card_notice/notice_detail.html?id=${noticia.id_noticia}&titulo=${encodeURIComponent(noticia.titulo)}&descripcion=${encodeURIComponent(noticia.descripcion)}" class="text-dark text-decoration-none font-weight-bold">
                ${noticia.titulo}
              </a>
            </h5>
            <p class="card-text flex-grow-1 text-muted">
              ${noticia.descripcion}
            </p>
            <div class="d-flex justify-content-between">
              <button class="btn btn-warning btn-sm mr-2" data-toggle="modal" data-target="#modalEdicion">
                <i class="fas fa-edit"></i> Editar
              </button>
              <button class="btn btn-danger btn-sm">
                <i class="fas fa-trash-alt"></i> Eliminar
              </button>
            </div>
          </div>
        </div>
      </div>
    `);
}

// --- Funcionalidad de edición ---

// Usamos event delegation para capturar clics en botones "Editar"
document.getElementById('noticias-container').addEventListener('click', function(e) {
  const btnEditar = e.target.closest('.btn-warning');
  if (btnEditar) {
    const card = btnEditar.closest('.card');
    const id = card.getAttribute('data-id');
    const titulo = card.getAttribute('data-titulo');
    const descripcion = card.getAttribute('data-descripcion');
    abrirModalEdicion(id, titulo, descripcion);
  }
});

// Función que abre el modal de edición y carga los datos de la noticia
function abrirModalEdicion(id, titulo, descripcion) {
  document.getElementById('editarNoticiaId').value = id;
  document.getElementById('editarTitle').value = titulo;
  document.getElementById('editarContent').value = descripcion;
  $('#modalEdicion').modal('show');
}

// Al enviar el formulario de edición se hace la actualización al backend
document.getElementById('formulario-editar-noticia').addEventListener('submit', async function(e) {
  e.preventDefault();
  
  const id = document.getElementById('editarNoticiaId').value;
  const nuevoTitulo = document.getElementById('editarTitle').value;
  const nuevaDescripcion = document.getElementById('editarContent').value;
  const token = localStorage.getItem('token');
  const payload = 
  { titulo: nuevoTitulo, 
    descripcion: nuevaDescripcion };

  try {
    const response = await fetch(`http://localhost:8080/api/Noticia/actualizar/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token
      },
      body: JSON.stringify(payload)
    });
    if (!response.ok) throw new Error("Error en la actualización de la noticia");
    
    const noticiaActualizada = await response.json();
    console.log("Noticia actualizada:", noticiaActualizada);

    // Actualiza la tarjeta en el DOM con los nuevos datos
    actualizarCardNoticia(noticiaActualizada);
    $('#modalEdicion').modal('hide');

    // Limpiar el formulario de edición
    e.target.reset();
  } catch (error) {
    console.error("Error actualizando la noticia:", error);
    alert("No se pudo actualizar la noticia. Intenta nuevamente.");
  }
});

// Actualiza la tarjeta en el DOM con los nuevos datos
function actualizarCardNoticia(noticia) {
  // Busca la tarjeta correspondiente por su ID
  // y actualiza los atributos y el contenido
  // de la tarjeta con los nuevos datos
  const card = document.querySelector(`.card[data-id="${noticia.id_noticia}"]`);
  if (card) {
    card.setAttribute('data-titulo', noticia.titulo);
    card.setAttribute('data-descripcion', noticia.descripcion);
    card.querySelector('.card-title a').textContent = noticia.titulo;
    card.querySelector('.card-text').textContent = noticia.descripcion;
  } else {
    obtenerNoticias();
  }
}

// Al cargar la página, obtenemos la lista de noticias
document.addEventListener("DOMContentLoaded", () => {
    obtenerNoticias();
});

// Opcional: Al cerrar el modal de nueva noticia, remover el foco (para accesibilidad)
$('#modalNuevaNoticia').on('hidden.bs.modal', function () {
  $(this).find(':focus').blur();
});

// -- Funcionalidad de eliminar noticia -- 

// Agregar un listener para el botón eliminar en cada tarjeta de noticia
// y usar event delegation para manejar el evento de clic
document.getElementById('noticias-container').addEventListener('click', function(e) {
  const btnEliminar = e.target.closest('.btn-danger');
  if (btnEliminar) {

    // Obtener el ID de la noticia desde el atributo data-id de la tarjeta
    const card = btnEliminar.closest('.card');
    const id = card.getAttribute('data-id');
    
    // Confirmar la eliminación
    if (confirm("¿Estás seguro de que deseas eliminar esta noticia?")) {
      eliminarNoticia(id);
    }
  }
});

// Función para eliminar la noticia

async function eliminarNoticia(id) {

  const token = localStorage.getItem('token');
  try {
    const response = await fetch(`http://localhost:8080/api/Noticia/eliminar/${id}`, {
      method: 'DELETE',
      headers: {
        'Authorization': 'Bearer ' + token  
      }
  });
    if (!response.ok) {
      throw new Error("Error al eliminar la noticia");
    }

    // Eliminar la tarjeta del DOM
    const card = document.querySelector(`.card[data-id="${id}"]`);
    if (card) {
      card.parentElement.remove();
    }
    console.log("Noticia eliminada:", id);
  }catch(error) {
    console.error("Error al eliminar la noticia:", error);
    alert("No se pudo eliminar la noticia. Intenta nuevamente.");
  }
}



