const parametros = new URLSearchParams(window.location.search);
const noticia_Id = parametros.get('id');
const usuario_Id = localStorage.getItem('userId');
const token = localStorage.getItem('token');

// variables para mantener el estado actual 
let currentRating = null;
let ratingId = null;

// Referencia a los botónes 
const likeButton = document.getElementById('like-button');
const dislikeButton = document.getElementById('dislike-button');

// Consultar la calificación actual del usuario (si existe)
// 1. Buscamos la calificación al cargar la página
async function fetchCurrentRating() {
  try {
    const response = await fetch(`http://localhost:8080/api/calificacionNoticia/buscar`, {
      method: 'POST',
      headers: { 
        'Authorization': 'Bearer ' + token,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        usuarioId: usuario_Id,
        noticiaId: noticia_Id
      })
    });
    if(response.ok) {
      const data = await response.json();
      currentRating = data.valor;
      ratingId = data.id_calificacion;
    } else {
      // Si no existe, se asume 404
      currentRating = null;
      ratingId = null;
    }
    console.log("Estado tras buscar:", { currentRating, ratingId });
    updateRatingUI();
  } catch (error) {
    console.error("Error consultando la calificación:", error);
  }
}



// Función para crear una calificación nueva
async function createRating(valor) {
  try {
    const response = await fetch(`http://localhost:8080/api/calificacionNoticia/crear`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + token,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        usuarioId: usuario_Id,
        noticiaId: noticia_Id,
        valor: valor
      })
    });
    if (!response.ok) throw new Error('Error al crear la calificación');
    const data = await response.json();
    console.log("Calificación creada:", data);
    currentRating = data.valor;
    ratingId = data.id_calificacion;
  } catch (error) {
    console.error(error);
    alert(error.message);
  }
}

// Función para actualizar una calificación existente
async function updateRating(nuevoValor) {
  try {
    const response = await fetch(`http://localhost:8080/api/calificacionNoticia/actualizar/${ratingId}`, {
      method: 'PUT',
      headers: {
        'Authorization': 'Bearer ' + token,
        'Content-Type': 'application/json'
      },
      // Enviamos el nuevo valor en el body
      body: JSON.stringify({ valor: nuevoValor })
    });
    if (!response.ok) throw new Error('Error al actualizar la calificación');
    const data = await response.json();
    currentRating = data.valor;
  } catch (error) {
    console.error(error);
    alert(error.message);
  }
}

// Función para eliminar una calificación existente
// 4. Eliminar calificación (DELETE)
async function deleteRating() {
  try {
    const response = await fetch(`http://localhost:8080/api/calificacionNoticia/eliminar/${ratingId}`, {
      method: "DELETE",
      headers: {
        "Authorization": "Bearer " + token
      }
    });
    if (!response.ok) throw new Error("Error al eliminar la calificación");
    // decir que la calificacion se elimino 
    alert("Calificación eliminada con éxito");
    // Reiniciar el estado de la calificación actual
    currentRating = null;
    ratingId = null;
  } catch (error) {
    console.error(error);
    alert(error.message);
  }
}

// Función para actualizar la UI según la calificación actual

// 5. UI: activa o desactiva los botones
function updateRatingUI() {
  if (currentRating === 1) {
    likeButton.classList.add("active");
    dislikeButton.classList.remove("active");
  } else if (currentRating === -1) {
    dislikeButton.classList.add("active");
    likeButton.classList.remove("active");
  } else {
    likeButton.classList.remove("active");
    dislikeButton.classList.remove("active");
  }
}

// 6. Manejo de eventos en los botones
likeButton.addEventListener("click", async () => {
  if (currentRating === 1) {
    await deleteRating();      // Elimina la calificación
  } else if (currentRating === -1) {
    await updateRating(1);     // Cambia a like
  } else {
    await createRating(1);     // Crea un like
  }
  await fetchCurrentRating();  // Refresca el estado
  updateRatingUI();
});

dislikeButton.addEventListener("click", async () => {
  if (currentRating === -1) {
    await deleteRating();      // Elimina la calificación
  } else if (currentRating === 1) {
    await updateRating(-1);    // Cambia a dislike
  } else {
    await createRating(-1);    // Crea un dislike
  }
  await fetchCurrentRating();
  updateRatingUI();
});

// Al cargar la página, consultar la calificación actual del usuario
fetchCurrentRating();


