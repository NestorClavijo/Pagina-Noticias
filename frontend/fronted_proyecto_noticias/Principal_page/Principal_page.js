// Seleccionar el botón de "Cerrar Sesión"
const btnCerrarSesion = document.getElementById('btn-cerrar-sesion');
const btnCrearNoticia = document.getElementById('btn-crear-noticia');

// Agregar un evento de clic al botón
btnCerrarSesion.addEventListener('click', () => {
  // Redirigir al usuario a la página de login
  window.location.href = '../Login/Login.html';
});

btnCrearNoticia.addEventListener('click', () => {
  window.location.href = '../Crear_noticia/Crear_noticia.html';
});