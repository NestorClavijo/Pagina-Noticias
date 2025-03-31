// Seleccionar los elementos del DOM
const likeButton = document.getElementById('like-button');
const dislikeButton = document.getElementById('dislike-button');
const likeCount = document.getElementById('like-count');
const dislikeCount = document.getElementById('dislike-count');

// Convertir los contadores a números
let likes = parseInt(likeCount.textContent, 10);
let dislikes = parseInt(dislikeCount.textContent, 10);

// Evento para el botón de "like"
likeButton.addEventListener('click', () => {
  likes += 1; // Incrementar el contador de likes
  likeCount.textContent = likes; // Actualizar el texto del contador
});

// Evento para el botón de "dislike"
dislikeButton.addEventListener('click', () => {
  dislikes += 1; // Incrementar el contador de dislikes
  dislikeCount.textContent = dislikes; // Actualizar el texto del contador
});