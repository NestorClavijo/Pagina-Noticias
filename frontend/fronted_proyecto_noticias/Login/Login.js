// Selector para alternar la visualización de la contraseña
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

// Función para manejar el envío del formulario y consumir el servicio de login
async function handleSubmit(event) {
  event.preventDefault(); // Detiene el envío tradicional

  // Extraer valores del formulario
  const username = document.querySelector('input[name="username"]').value.trim();
  const password = document.querySelector('input[name="password"]').value;

  if (!username || !password) {
    alert("Completa todos los campos");
    return;
  }

  const payload = { username, password };

  try {
    const response = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (!response.ok) {
      throw new Error("Error en el inicio de sesión");
    }

    const data = await response.json();
    console.log("Login exitoso:", data);

    // Guarda el token y el id del usuario, por ejemplo en localStorage
    localStorage.setItem("token", data.token);
    localStorage.setItem("userId", data.id);

    // Redirige a la página principal
    window.location.href = '../Principal_page/Principal_page.html';
  } catch (err) {
    console.error("Error durante el login", err);
    alert("No se pudo iniciar sesión, verifica tus credenciales.");
  }
}

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector(".login-container");
  console.log("Formulario encontrado:", form);
  if (form) {
    form.addEventListener("submit", handleSubmit);
  } else {
    console.error("El formulario no fue encontrado en el DOM.");
  }
});