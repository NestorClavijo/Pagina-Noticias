//manejar la etiqueta de mostrar contraseña
// Selector para alternar la visualización de la contraseña
const togglePassword = document.querySelector('.toggle-password');
togglePassword.addEventListener('click', function () {
    // Cambiar el tipo de input de password a text y viceversa
    const passwordInput = document.querySelector('input[name="password"]');
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        this.innerHTML = '<i class="fas fa-eye-slash"></i>';
    } else {
        passwordInput.type = "password";
        this.innerHTML = '<i class="fas fa-eye"></i>';
    }
});

// implementar el registro de usuario
async function registerUser(event) {
    event.preventDefault(); // Detiene el envío tradicional

    // Extraer valores del formulario
    //extraer el nombre
    const name = document.querySelector('input[name="nombre"]').value.trim();
    //extraer el apellido 
    const lastName = document.querySelector('input[name="apellido"]').value.trim();
    //extraer la descripcion
    const description = document.querySelector('input[name="descripcion"]').value.trim();
    //extraer el correo electrónico 
    const email = document.querySelector('input[name="email"]').value.trim();
    //extraer el telefono
    const phone = document.querySelector('input[name="telefono"]').value.trim();
    //extraer el nombre de usuario
    const username = document.querySelector('input[name="username"]').value.trim();
    //extraer la contraseña
    const password = document.querySelector('input[name="password"]').value;

    // Validar que todos los campos estén completos
    if (!name || !lastName || !description || !email || !phone || !username || !password) {
        alert("Completa todos los campos");
        return;
    }

    //validar que el correo electrónico sea válido
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    //validar la contraseña
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@!%*?&])[A-Za-z\d$@!%*?&]{8,}$/; // Al menos 8 caracteres, al menos una letra y un número, acepta carateres especiales 

    if (!emailRegex.test(email)) {
        alert("El correo electrónico no es válido");
        return;
    }

    if (!passwordRegex.test(password)) {
        alert("La contraseña debe tener al menos 8 caracteres, una letra y un número");
        return;
    }

    // Crear el objeto de datos a enviar
    const payload = {
        nombre: name,
        apellido: lastName,
        descripcion: description,
        email,
        telefono: phone,
        username,
        password
    };

    // Realizar la solicitud al backend
    try{
        const response = await fetch('http://localhost:8080/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload) 
        });

        if (!response.ok) {
            throw new Error("Error en el registro");
        }

        const data = await response.json();
        console.log("Registro exitoso:", data);
        
        // si el registro es exitoso redirige a la página de inicio de sesión
        window.location.href = '../Login/Login.html';

    }catch(error){
        console.error("Error durante el registro", error);
        alert("No se pudo registrar, verifica tus datos.");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".register-form");
    console.log("Formulario encontrado:", form);
    if (form) {
      form.addEventListener("submit", registerUser);
    } else {
      console.error("El formulario no fue encontrado en el DOM.");
    }
  });