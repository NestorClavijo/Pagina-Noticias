// Selector para alternar la visualización de la contraseña
document.querySelector('.toggle-password').addEventListener('click', function () {
  // Selecciona el campo de entrada de la contraseña (input de tipo 'password')
  const passwordInput = document.querySelector('input[name="password"]');
  
  // Verifica si el tipo de campo es 'password' para cambiarlo a 'text' y mostrar la contraseña
  if (passwordInput.type === "password") {
    passwordInput.type = "text"; // Cambia el tipo de campo a texto (muestra la contraseña)
    this.innerHTML = '<i class="fas fa-eye-slash"></i>'; // Cambia el ícono a un ojo tachado
  } else {
    passwordInput.type = "password"; // Cambia el tipo de campo de nuevo a 'password' (oculta la contraseña)
    this.innerHTML = '<i class="fas fa-eye"></i>'; // Cambia el ícono a un ojo normal
  }
});

// Función para manejar el envío del formulario y consumir el servicio de login
async function handleSubmit(event) {
  event.preventDefault(); // Detiene el comportamiento por defecto del formulario (evita que se recargue la página)

  // Extrae los valores de los campos de nombre de usuario y contraseña
  const username = document.querySelector('input[name="username"]').value.trim();
  const password = document.querySelector('input[name="password"]').value;

  // Verifica si los campos no están vacíos
  if (!username || !password) {
    alert("Completa todos los campos"); // Muestra una alerta si algún campo está vacío
    return;
  }

  // Crea el objeto 'payload' con los datos del formulario para enviarlos al servidor
  const payload = { username, password };

  try {
    // Realiza una solicitud POST al endpoint de login con el 'payload' como cuerpo de la solicitud
    const response = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    // Si la respuesta no es exitosa (status diferente a 200), lanza un error
    if (!response.ok) {
      throw new Error("Error en el inicio de sesión");
    }

    // Si la respuesta es exitosa, se convierte la respuesta en un objeto JSON
    const data = await response.json();
    console.log("Login exitoso:", data);

    // Guarda el token y el ID del usuario en el almacenamiento local (localStorage)
    localStorage.setItem("token", data.token);
    localStorage.setItem("userId", data.id);

    // Redirige a la página principal después del login exitoso
    window.location.href = '../Principal_page/Principal_page.html';
  } catch (err) {
    console.error("Error durante el login", err);
    alert("No se pudo iniciar sesión, verifica tus credenciales."); // Muestra un error si algo sale mal
  }
}

// Este código se ejecuta una vez que el contenido de la página ha sido completamente cargado
document.addEventListener("DOMContentLoaded", () => {
  // Selecciona el formulario de login
  const form = document.querySelector(".login-container");
  console.log("Formulario encontrado:", form);

  // Si el formulario se encuentra en el DOM, se le añade un evento para manejar el envío
  if (form) {
    form.addEventListener("submit", handleSubmit);
  } else {
    console.error("El formulario no fue encontrado en el DOM."); // Muestra un error si no encuentra el formulario
  }
});
