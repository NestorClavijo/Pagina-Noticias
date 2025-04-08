// Obtiene los parámetros de la URL (ID de la noticia y del usuario) y el token de autenticación
const parametros = new URLSearchParams(window.location.search);
const noticia_Id = parametros.get('id'); // Obtiene el ID de la noticia desde la URL
const usuario_Id = localStorage.getItem('userId'); // Obtiene el ID del usuario desde el almacenamiento local (localStorage)
const token = localStorage.getItem('token'); // Obtiene el token JWT de localStorage

// Variables para mantener el estado actual de la calificación
let currentRating = null; // Guarda la calificación actual (puede ser 1 para "me gusta" o -1 para "no me gusta")
let ratingId = null; // Guarda el ID de la calificación si existe

// Referencias a los botones de "me gusta" y "no me gusta"
const likeButton = document.getElementById('like-button'); // Botón para dar "me gusta"
const dislikeButton = document.getElementById('dislike-button'); // Botón para dar "no me gusta"

// Función para consultar la calificación actual del usuario
async function fetchCurrentRating() {
  try {
    // Realiza una petición POST al backend para obtener la calificación actual del usuario
    const response = await fetch(`http://localhost:8080/api/calificacionNoticia/buscar`, {
      method: 'POST',
      headers: { 
        'Authorization': 'Bearer ' + token, // Se agrega el token JWT para autenticarse
        'Content-Type': 'application/json' // Se indica que el cuerpo de la solicitud es JSON
      },
      body: JSON.stringify({
        usuarioId: usuario_Id, // ID del usuario
        noticiaId: noticia_Id // ID de la noticia
      })
    });

    // Si la respuesta es exitosa, se obtiene la calificación
    if(response.ok) {
      const data = await response.json(); // Convierte la respuesta en un objeto JSON
      currentRating = data.valor; // Asigna el valor de la calificación
      ratingId = data.id_calificacion; // Asigna el ID de la calificación
    } else {
      // Si no se encuentra la calificación, se asigna null
      currentRating = null;
      ratingId = null;
    }
    console.log("Estado tras buscar:", { currentRating, ratingId }); // Muestra el estado en la consola
    updateRatingUI(); // Actualiza la interfaz de usuario según la calificación actual
  } catch (error) {
    console.error("Error consultando la calificación:", error); // Muestra el error si ocurre
  }
}

// Función para crear una nueva calificación (ya sea "me gusta" o "no me gusta")
async function createRating(valor) {
  try {
    const response = await fetch(`http://localhost:8080/api/calificacionNoticia/crear`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + token, // Se agrega el token JWT para autenticarse
        'Content-Type': 'application/json' // Se indica que el cuerpo de la solicitud es JSON
      },
      body: JSON.stringify({
        usuarioId: usuario_Id, // ID del usuario
        noticiaId: noticia_Id, // ID de la noticia
        valor: valor // El valor de la calificación (1 para "me gusta", -1 para "no me gusta")
      })
    });

    // Si la respuesta no es exitosa, lanza un error
    if (!response.ok) throw new Error('Error al crear la calificación');
    
    const data = await response.json(); // Convierte la respuesta en un objeto JSON
    console.log("Calificación creada:", data); // Muestra la calificación creada en la consola
    currentRating = data.valor; // Actualiza la calificación actual
    ratingId = data.id_calificacion; // Actualiza el ID de la calificación
  } catch (error) {
    console.error(error); // Muestra el error si ocurre
    alert(error.message); // Muestra un mensaje de alerta con el error
  }
}

// Función para actualizar una calificación existente (cuando el usuario cambia su opinión)
async function updateRating(nuevoValor) {
  try {
    const response = await fetch(`http://localhost:8080/api/calificacionNoticia/actualizar/${ratingId}`, {
      method: 'PUT', // Método PUT para actualizar la calificación
      headers: {
        'Authorization': 'Bearer ' + token, // Se agrega el token JWT para autenticarse
        'Content-Type': 'application/json' // Se indica que el cuerpo de la solicitud es JSON
      },
      body: JSON.stringify({ valor: nuevoValor }) // Se envía el nuevo valor de la calificación
    });

    // Si la respuesta no es exitosa, lanza un error
    if (!response.ok) throw new Error('Error al actualizar la calificación');
    
    const data = await response.json(); // Convierte la respuesta en un objeto JSON
    currentRating = data.valor; // Actualiza la calificación actual
  } catch (error) {
    console.error(error); // Muestra el error si ocurre
    alert(error.message); // Muestra un mensaje de alerta con el error
  }
}

// Función para eliminar una calificación existente (cuando el usuario decide quitar su calificación)
async function deleteRating() {
  try {
    const response = await fetch(`http://localhost:8080/api/calificacionNoticia/eliminar/${ratingId}`, {
      method: "DELETE", // Método DELETE para eliminar la calificación
      headers: {
        "Authorization": "Bearer " + token // Se agrega el token JWT para autenticarse
      }
    });

    // Si la respuesta no es exitosa, lanza un error
    if (!response.ok) throw new Error("Error al eliminar la calificación");
    
    // Informa al usuario que la calificación ha sido eliminada
    alert("Calificación eliminada con éxito");
    
    // Reinicia el estado de la calificación actual
    currentRating = null;
    ratingId = null;
  } catch (error) {
    console.error(error); // Muestra el error si ocurre
    alert(error.message); // Muestra un mensaje de alerta con el error
  }
}

// Función para actualizar la interfaz de usuario según la calificación actual
function updateRatingUI() {
  if (currentRating === 1) {
    likeButton.classList.add("active"); // Marca el botón de "me gusta" como activo
    dislikeButton.classList.remove("active"); // Desmarca el botón de "no me gusta"
  } else if (currentRating === -1) {
    dislikeButton.classList.add("active"); // Marca el botón de "no me gusta" como activo
    likeButton.classList.remove("active"); // Desmarca el botón de "me gusta"
  } else {
    likeButton.classList.remove("active"); // Desmarca el botón de "me gusta"
    dislikeButton.classList.remove("active"); // Desmarca el botón de "no me gusta"
  }
}

// Manejo de eventos cuando el usuario hace clic en el botón de "me gusta"
likeButton.addEventListener("click", async () => {
  if (currentRating === 1) {
    await deleteRating(); // Si ya se había dado "me gusta", elimina la calificación
  } else if (currentRating === -1) {
    await updateRating(1); // Si se había dado "no me gusta", cambia a "me gusta"
  } else {
    await createRating(1); // Si no se ha dado calificación, crea un "me gusta"
  }
  await fetchCurrentRating();  // Refresca la calificación actual
  updateRatingUI(); // Actualiza la interfaz de usuario
});

// Manejo de eventos cuando el usuario hace clic en el botón de "no me gusta"
dislikeButton.addEventListener("click", async () => {
  if (currentRating === -1) {
    await deleteRating(); // Si ya se había dado "no me gusta", elimina la calificación
  } else if (currentRating === 1) {
    await updateRating(-1); // Si se había dado "me gusta", cambia a "no me gusta"
  } else {
    await createRating(-1); // Si no se ha dado calificación, crea un "no me gusta"
  }
  await fetchCurrentRating(); // Refresca la calificación actual
  updateRatingUI(); // Actualiza la interfaz de usuario
});

// Al cargar la página, consulta la calificación actual del usuario para la noticia
fetchCurrentRating();
