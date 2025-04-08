//manejar la etiqueta de mostrar contraseña
// Selector para alternar la visualización de la contraseña
const togglePassword = document.querySelector('.toggle-password');

// Evento que cambia el tipo del campo de contraseña entre "password" y "text"
togglePassword.addEventListener('click', function () {
    // Seleccionamos el campo de contraseña
    const passwordInput = document.querySelector('input[name="password"]');

    // Si la contraseña está oculta, la mostramos, y si está visible, la ocultamos
    if (passwordInput.type === "password") {
        passwordInput.type = "text"; // Cambia la contraseña de oculta a visible
        this.innerHTML = '<i class="fas fa-eye-slash"></i>'; // Cambia el ícono a un ojo cerrado
    } else {
        passwordInput.type = "password"; // Cambia de visible a oculta
        this.innerHTML = '<i class="fas fa-eye"></i>'; // Cambia el ícono a un ojo abierto
    }
});

// implementar el registro de usuario
async function registerUser(event) {
    event.preventDefault(); // Detiene el envío tradicional del formulario

    // Extraemos los valores de los campos del formulario
    const name = document.querySelector('input[name="nombre"]').value.trim(); // Nombre del usuario
    const lastName = document.querySelector('input[name="apellido"]').value.trim(); // Apellido del usuario
    const description = document.querySelector('input[name="descripcion"]').value.trim(); // Descripción del usuario
    const email = document.querySelector('input[name="email"]').value.trim(); // Correo electrónico del usuario
    const phone = document.querySelector('input[name="telefono"]').value.trim(); // Teléfono del usuario
    const username = document.querySelector('input[name="username"]').value.trim(); // Nombre de usuario
    const password = document.querySelector('input[name="password"]').value; // Contraseña

    // Validamos que todos los campos estén completos
    if (!name || !lastName || !description || !email || !phone || !username || !password) {
        alert("Completa todos los campos");
        return; // Detiene el registro si falta algún campo
    }

    // Validación del correo electrónico usando una expresión regular
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    // Validación de la contraseña, debe tener al menos 8 caracteres, una letra, un número y un carácter especial
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@!%*?&])[A-Za-z\d$@!%*?&]{8,}$/;

    if (!emailRegex.test(email)) {
        alert("El correo electrónico no es válido"); // Si el correo no es válido, mostramos un mensaje
        return;
    }

    if (!passwordRegex.test(password)) {
        alert("La contraseña debe tener al menos 8 caracteres, una letra y un número"); // Si la contraseña no cumple con los requisitos, mostramos un mensaje
        return;
    }

    // Crear el objeto que vamos a enviar con los datos del usuario
    const payload = {
        nombre: name,
        apellido: lastName,
        descripcion: description,
        email,
        telefono: phone,
        username,
        password
    };

    // Realizamos la solicitud al backend para registrar al usuario
    try {
        // Enviamos los datos al backend usando fetch y el método POST
        const response = await fetch('http://localhost:8080/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload) // Enviamos los datos del usuario en formato JSON
        });

        // Si la respuesta no es correcta, lanzamos un error
        if (!response.ok) {
            throw new Error("Error en el registro");
        }

        // Si el registro es exitoso, recibimos la respuesta y la mostramos
        const data = await response.json();
        console.log("Registro exitoso:", data);
        
        // Redirigimos a la página de inicio de sesión después de un registro exitoso
        window.location.href = '../Login/Login.html';

    } catch (error) {
        // Si ocurre un error, lo mostramos en la consola y alertamos al usuario
        console.error("Error durante el registro", error);
        alert("No se pudo registrar, verifica tus datos.");
    }
}

// Evento que se dispara cuando el contenido de la página está completamente cargado
document.addEventListener("DOMContentLoaded", () => {
    // Seleccionamos el formulario de registro
    const form = document.querySelector(".register-form");
    console.log("Formulario encontrado:", form);
    // Si encontramos el formulario, le añadimos el listener para el evento submit
    if (form) {
      form.addEventListener("submit", registerUser);
    } else {
      console.error("El formulario no fue encontrado en el DOM.");
    }
});
