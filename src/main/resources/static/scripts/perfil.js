document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('formDepartamento');
    const mensaje = document.getElementById('mensajeConfirmacion');
    const lista = document.getElementById('listaDepartamentos');

    async function cargarPerfilUsuario() {
        try {
            const respuesta = await fetch('/perfil/info');
            if (!respuesta.ok) throw new Error();
            const usuario = await respuesta.json();
            document.getElementById('username').textContent = usuario.username || '';
            document.getElementById('firstname').textContent = usuario.firstname || '';
            document.getElementById('lastname').textContent = usuario.lastname || '';
            document.getElementById('celular').textContent = usuario.celular || '';
            document.getElementById('correo').textContent = usuario.correo || '';
        } catch (error) {
            document.getElementById('perfilDatos').innerHTML = '<p style="color:red">No se pudo cargar el perfil.</p>';
        }
    }

    form.addEventListener('submit', async function(event) {
        event.preventDefault();

        const formData = new FormData(form);

        try {
            const respuesta = await fetch('/departamentos/crear', {
                method: 'POST',
                body: formData
            });

            if (respuesta.ok) {
                mensaje.textContent = "Departamento creado exitosamente.";
                mensaje.style.color = "green";
                form.reset();
                await cargarDepartamentos();
            } else {
                mensaje.textContent = "Error al crear el departamento.";
                mensaje.style.color = "red";
            }
        } catch (error) {
            mensaje.textContent = "Error de conexión con el servidor.";
            mensaje.style.color = "red";
        }
    });

    async function cargarDepartamentos() {
        try {
            const respuesta = await fetch('/departamentos/lista/user');
            const departamentos = await respuesta.json();

            lista.innerHTML = '';
            departamentos.forEach(dep => {
                const div = document.createElement('div');
                div.innerHTML = `
                    <strong>${dep.nombre}</strong><br>
                    ${dep.descripcion}<br>
                    Precio: $${dep.precio.toLocaleString()}<br>
                    Habitaciones: ${dep.numHabitaciones}<br><hr>
                `;
                lista.appendChild(div);
            });
        } catch (error) {
            lista.innerHTML = '<p style="color:red">Error al cargar departamentos.</p>';
        }
    }

    cargarPerfilUsuario();
    cargarDepartamentos().then(() => {
        console.log("Departamentos recargados.");
    });
});