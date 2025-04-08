// --- Funcionalidad de cierre de sesión ---
// Primero, obtenemos el botón de cerrar sesión usando su ID
const btnCerrarSesion = document.getElementById('btn-cerrar-sesion');

// Añadimos un evento de clic al botón
btnCerrarSesion.addEventListener('click', () => {
    // Al hacer clic en el botón, eliminamos el 'token' y 'userId' del localStorage
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    // Mostramos una alerta para informar al usuario que la sesión ha sido cerrada
    alert("Sesión cerrada con éxito.");
    // Redirigimos al usuario a la página de login
    window.location.href = '../Login/Login.html';
});

// --- Crear noticia ---
// Añadimos un evento 'submit' al formulario de creación de noticias
document.getElementById('formulario-noticia').addEventListener('submit', async function (e) {
    e.preventDefault(); // Prevenimos el comportamiento por defecto del formulario (enviar)
    
    // Obtenemos el ID del usuario y el token de localStorage
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');
    
    // Obtenemos los valores del título y contenido de la noticia desde los campos del formulario
    const titulo = document.getElementById('noticeTitle').value;
    const descripcion = document.getElementById('noticeContent').value;
    
    // Creamos un objeto con los datos de la noticia
    const noticia = { titulo, descripcion, usuarioId: userId };

    try {
        // Enviamos una solicitud POST al backend para crear la noticia
        const response = await fetch('http://localhost:8080/api/Noticia/crear', {
            method: 'POST', // Método de la solicitud
            headers: { 
                'Content-Type': 'application/json', // Indicamos que enviamos JSON
                'Authorization': 'Bearer ' + token // Añadimos el token de autenticación en los headers
            },
            body: JSON.stringify(noticia) // Enviamos el objeto noticia como cuerpo de la solicitud
        });
        
        // Si la respuesta no es exitosa, lanzamos un error
        if (!response.ok) throw new Error("Error al crear la noticia");
        
        // Si todo sale bien, obtenemos los datos de la noticia creada
        const noticiaCreada = await response.json();
        
        // Mostramos la noticia creada en la consola
        console.log("Noticia creada:", noticiaCreada);
        
        // Llamamos a la función para agregar la noticia al DOM
        agregarNoticiaAlDOM(noticiaCreada);
        
        // Cerramos el modal de creación de noticia
        $('#modalNuevaNoticia').modal('hide');
    } catch (error) {
        // Si ocurre un error, lo mostramos en consola y alertamos al usuario
        console.error("Error al crear la noticia:", error);
        alert("No se pudo crear la noticia. Intenta nuevamente.");
    }
});

// --- Listar noticias ---
// Función para obtener las noticias del backend
async function obtenerNoticias() {
    // Obtenemos el ID del usuario y el token de localStorage
    const usuarioId = localStorage.getItem('userId');  
    const token = localStorage.getItem('token');
    
    try {
        // Enviamos una solicitud GET al backend para obtener las noticias del usuario
        const response = await fetch(`http://localhost:8080/api/Noticia/usuario/${usuarioId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json', // Indicamos que esperamos JSON en la respuesta
                'Authorization': 'Bearer ' + token // Añadimos el token de autenticación
            }
        });
        
        // Si la respuesta no es exitosa, lanzamos un error
        if (!response.ok) throw new Error("Error al obtener las noticias");
        
        // Si todo sale bien, obtenemos las noticias
        const noticias = await response.json();
        
        // Mostramos las noticias obtenidas en la consola
        console.log("Noticias obtenidas:", noticias);
        
        // Llamamos a la función para actualizar el DOM con las noticias obtenidas
        actualizarDOMNoticias(noticias);
    } catch (error) {
        // Si ocurre un error, lo mostramos en consola y alertamos al usuario
        console.error("Error al obtener las noticias:", error);
        alert("No se pudieron obtener las noticias. Intenta nuevamente.");
    }
}

// Función para actualizar el DOM con las noticias obtenidas
function actualizarDOMNoticias(noticias) {
    // Buscamos el contenedor donde se deben mostrar las noticias
    const container = document.getElementById('noticias-container');
    
    // Limpiamos el contenedor antes de agregar nuevas noticias
    container.innerHTML = '';
    
    // Iteramos sobre cada noticia y la agregamos al contenedor
    noticias.forEach(noticia => {
        // Añadimos una tarjeta para cada noticia con su título y descripción
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

// Función para agregar una noticia al DOM
function agregarNoticiaAlDOM(noticia) {
  // Seleccionamos el contenedor donde se mostrarán las noticias
  const container = document.getElementById('noticias-container');
  
  // Insertamos una nueva tarjeta de noticia al principio del contenedor
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
// Detectamos si el clic fue en un botón de edición
const btnEditar = e.target.closest('.btn-warning');
if (btnEditar) {
  // Si el clic fue en el botón de editar, obtenemos los datos de la noticia
  const card = btnEditar.closest('.card');
  const id = card.getAttribute('data-id');
  const titulo = card.getAttribute('data-titulo');
  const descripcion = card.getAttribute('data-descripcion');
  
  // Llamamos a la función para abrir el modal de edición y cargar los datos
  abrirModalEdicion(id, titulo, descripcion);
}
});

// Función que abre el modal de edición y carga los datos de la noticia
function abrirModalEdicion(id, titulo, descripcion) {
// Asignamos los valores de la noticia al formulario de edición
document.getElementById('editarNoticiaId').value = id;
document.getElementById('editarTitle').value = titulo;
document.getElementById('editarContent').value = descripcion;

// Mostramos el modal de edición
$('#modalEdicion').modal('show');
}

// Al enviar el formulario de edición se hace la actualización al backend
document.getElementById('formulario-editar-noticia').addEventListener('submit', async function(e) {
e.preventDefault(); // Prevenimos el envío por defecto del formulario

// Obtenemos los valores del formulario de edición
const id = document.getElementById('editarNoticiaId').value;
const nuevoTitulo = document.getElementById('editarTitle').value;
const nuevaDescripcion = document.getElementById('editarContent').value;
const token = localStorage.getItem('token');

// Creamos el objeto que enviamos al backend
const payload = { titulo: nuevoTitulo, descripcion: nuevaDescripcion };

try {
  // Enviamos la solicitud PUT al backend para actualizar la noticia
  const response = await fetch(`http://localhost:8080/api/Noticia/actualizar/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token // Añadimos el token de autenticación
    },
    body: JSON.stringify(payload) // Enviamos el cuerpo con los nuevos datos
  });
  
  // Si la respuesta no es exitosa, lanzamos un error
  if (!response.ok) throw new Error("Error en la actualización de la noticia");
  
  // Obtenemos los datos de la noticia actualizada
  const noticiaActualizada = await response.json();
  console.log("Noticia actualizada:", noticiaActualizada);

  // Actualizamos la tarjeta de la noticia en el DOM con los nuevos datos
  actualizarCardNoticia(noticiaActualizada);
  
  // Cerramos el modal de edición
  $('#modalEdicion').modal('hide');

  // Limpiamos el formulario de edición
  e.target.reset();
} catch (error) {
  // Si ocurre un error, mostramos un mensaje en consola y alertamos al usuario
  console.error("Error actualizando la noticia:", error);
  alert("No se pudo actualizar la noticia. Intenta nuevamente.");
}
});

// Función para actualizar la tarjeta en el DOM con los nuevos datos
function actualizarCardNoticia(noticia) {
// Buscamos la tarjeta correspondiente usando el ID de la noticia
const card = document.querySelector(`.card[data-id="${noticia.id_noticia}"]`);
if (card) {
  // Actualizamos los atributos y contenido de la tarjeta con los nuevos datos
  card.setAttribute('data-titulo', noticia.titulo);
  card.setAttribute('data-descripcion', noticia.descripcion);
  card.querySelector('.card-title a').textContent = noticia.titulo;
  card.querySelector('.card-text').textContent = noticia.descripcion;
} else {
  // Si no se encuentra la tarjeta, obtenemos todas las noticias
  obtenerNoticias();
}
}

// Actualiza la tarjeta en el DOM con los nuevos datos
function actualizarCardNoticia(noticia) {
  // Busca la tarjeta correspondiente por su ID en el DOM
  // y actualiza los atributos y el contenido con los nuevos datos.
  const card = document.querySelector(`.card[data-id="${noticia.id_noticia}"]`);
  if (card) {
    // Si encontramos la tarjeta, actualizamos los atributos 'data-titulo' y 'data-descripcion'
    // y el contenido de la tarjeta con los nuevos datos de la noticia.
    card.setAttribute('data-titulo', noticia.titulo);
    card.setAttribute('data-descripcion', noticia.descripcion);
    card.querySelector('.card-title a').textContent = noticia.titulo;
    card.querySelector('.card-text').textContent = noticia.descripcion;
  } else {
    // Si no encontramos la tarjeta, obtenemos todas las noticias nuevamente desde el backend.
    obtenerNoticias();
  }
}

// Al cargar la página, obtenemos la lista de noticias
document.addEventListener("DOMContentLoaded", () => {
    // Llamamos a la función obtenerNoticias() para cargar las noticias al iniciar.
    obtenerNoticias();
});

// Opcional: Al cerrar el modal de nueva noticia, removemos el foco para accesibilidad
$('#modalNuevaNoticia').on('hidden.bs.modal', function () {
  // Remueve el foco del modal cuando se cierra para mejorar la accesibilidad.
  $(this).find(':focus').blur();
});

// -- Funcionalidad de eliminar noticia -- 

// Agregar un listener para el botón eliminar en cada tarjeta de noticia
// y usar event delegation para manejar el evento de clic
document.getElementById('noticias-container').addEventListener('click', function(e) {
  const btnEliminar = e.target.closest('.btn-danger');
  if (btnEliminar) {
    // Cuando el usuario hace clic en el botón de eliminar,
    // obtenemos el ID de la noticia desde el atributo 'data-id' de la tarjeta.
    const card = btnEliminar.closest('.card');
    const id = card.getAttribute('data-id');
    
    // Confirmamos con el usuario si desea eliminar la noticia
    if (confirm("¿Estás seguro de que deseas eliminar esta noticia?")) {
      // Si el usuario confirma, llamamos a la función eliminarNoticia() pasando el ID.
      eliminarNoticia(id);
    }
  }
});

// Función para eliminar la noticia
async function eliminarNoticia(id) {
  const token = localStorage.getItem('token'); // Obtenemos el token de autenticación desde el almacenamiento local
  try {
    // Realizamos una solicitud DELETE al backend para eliminar la noticia por su ID
    const response = await fetch(`http://localhost:8080/api/Noticia/eliminar/${id}`, {
      method: 'DELETE',
      headers: {
        'Authorization': 'Bearer ' + token  // Enviamos el token en el encabezado para autenticación
      }
    });

    // Si la respuesta no es exitosa, lanzamos un error
    if (!response.ok) {
      throw new Error("Error al eliminar la noticia");
    }

    // Si la eliminación es exitosa, eliminamos la tarjeta de la noticia del DOM
    const card = document.querySelector(`.card[data-id="${id}"]`);
    if (card) {
      card.parentElement.remove(); // Eliminamos la tarjeta del contenedor
    }
    console.log("Noticia eliminada:", id); // Mostramos el ID de la noticia eliminada
  } catch (error) {
    // Si ocurre un error en el proceso de eliminación, mostramos un mensaje en consola y alertamos al usuario
    console.error("Error al eliminar la noticia:", error);
    alert("No se pudo eliminar la noticia. Intenta nuevamente.");
  }
}




