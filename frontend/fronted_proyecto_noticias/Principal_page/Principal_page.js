// Seleccionar el botón de "Cerrar Sesión"
const btnCerrarSesion = document.getElementById('btn-cerrar-sesion');

// Agregar un evento de clic al botón
btnCerrarSesion.addEventListener('click', () => {
  // Redirigir al usuario a la página de login
  window.location.href = '../Login/Login.html';
});