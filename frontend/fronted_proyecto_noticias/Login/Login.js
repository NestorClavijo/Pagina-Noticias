// Selecciona el ícono que alterna la visualización de la contraseña
document.querySelector('.toggle-password').addEventListener('click', function () {
    const passwordInput = document.querySelector('input[name="password"]');
    
    if (passwordInput.type === "password") {
      passwordInput.type = "text";
      this.innerHTML = '<i class="fas fa-eye-slash"></i>';
    } else {
      passwordInput.type = "password";
      this.innerHTML = '<i class="fas fa-eye"></i>';
    }
  });
  
// Seleccionar el botón de inicio de sesión
const loginButton = document.getElementById('login-button');

// Agregar un evento de clic al botón
loginButton.addEventListener('click', () => {
  // Aquí puedes agregar validaciones si es necesario
  // Por ejemplo, verificar si los campos de usuario y contraseña están llenos

  // Redirigir a la página principal
  window.location.href = '../Principal_page/Principal_page.html';
});