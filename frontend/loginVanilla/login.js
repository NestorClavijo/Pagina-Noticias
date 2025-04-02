const handleSubmit = async (e) => {
    e.preventDefault();

    const data = {
        username: e.target[0].value, 
        password: e.target[1].value
    };

    try {
        const response = await fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }

        const result = await response.json();
        console.log("Respuesta del servidor:", result);

        localStorage.setItem("token", result.token);
        localStorage.setItem("userId", result.id);

        alert("Inicio de sesión exitoso");

    } catch (error) {
        console.error("Error en la autenticación:", error);
        alert("Error en la autenticación, verifica tus credenciales.");
    }
};

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".formulario_login");

    // console.log("Formulario encontrado:", form); 

    if (form) {
        form.addEventListener("submit", handleSubmit);
    } else {
        console.error("El formulario no fue encontrado en el DOM.");
    }
});
