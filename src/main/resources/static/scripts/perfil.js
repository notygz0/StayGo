document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('formDepartamento');
    const mensaje = document.getElementById('mensajeConfirmacion');
    const lista = document.getElementById('listaDepartamentos');

    // Cargar datos del usuario
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
                div.className = "departamento-item";

                // Info departamento
                const info = document.createElement('div');
                info.style.flex = "1";
                info.innerHTML = `
                    <strong>${dep.nombre}</strong>
                    <br>Dirección: ${dep.direccion}<br> Descripcion: ${dep.descripcion}<br>
                    Precio: $${dep.precio.toLocaleString()}<br>
                    Habitaciones: ${dep.numHabitaciones}
                `;

                // Botones
                const botones = document.createElement('div');
                botones.className = "departamento-botones";

                // Botón Ver Reservas
                const btnReservas = document.createElement('button');
                btnReservas.textContent = "Ver reserva";
                btnReservas.className = "departamento-boton reservas";
                btnReservas.onclick = async () => {
                    try {
                        const resp = await fetch(`/reservas/departamento/${dep.id}`);
                        if (!resp.ok) throw new Error();
                        const reserva = await resp.json();
                        if (reserva && reserva.id) {
                            mostrarPopupReserva(reserva);
                        } else {
                            mostrarPopupSinReserva(dep.nombre);
                        }
                    } catch {
                        mostrarPopupSinReserva(dep.nombre);
                    }
                };

                // Botón Eliminar
                const btnEliminar = document.createElement('button');
                btnEliminar.textContent = "Eliminar";
                btnEliminar.className = "departamento-boton eliminar";
                btnEliminar.onclick = async () => {
                    if (confirm("¿Seguro que deseas eliminar este departamento?")) {
                        try {
                            const resp = await fetch(`/departamentos/borrar/${dep.id}`, { method: 'DELETE' });
                            if (resp.ok) {
                                await cargarDepartamentos();
                            } else {
                                alert("Error al eliminar el departamento.");
                            }
                        } catch {
                            alert("Error de conexión al eliminar.");
                        }
                    }
                };

                botones.appendChild(btnReservas);
                botones.appendChild(btnEliminar);

                div.appendChild(info);
                div.appendChild(botones);
                lista.appendChild(div);
            });
        } catch (error) {
            lista.innerHTML = '<p style="color:red">Error al cargar departamentos.</p>';
        }
    }

    // Popup para mostrar reserva
    function mostrarPopupReserva(reserva) {
        const modalBg = document.createElement('div');
        modalBg.className = 'modal-bg';

        const modal = document.createElement('div');
        modal.className = 'modal-contenido';
        modal.innerHTML = `
            <h2>${reserva.alojamiento || ''}</h2>
            ${reserva.imagen ? `<img src="data:image/jpeg;base64,${reserva.imagen}" alt="Imagen departamento" style="max-width:100%;margin:10px 0;border-radius:6px;">` : ''}
            <p><strong>Usuario:</strong> ${reserva.name}</p>
            <p><strong>Fecha de inicio:</strong> ${reserva.fechaInicio}</p>
            <p><strong>Fecha de fin:</strong> ${reserva.fechaFin}</p>
            <p><strong>Estado:</strong> <span id="estado-actual">${reserva.estado}</span></p>
            <label for="select-estado" style="margin-top:10px;">Cambiar estado:</label>
            <select id="select-estado" style="margin-bottom:10px;">
                <option value="PENDIENTE">PENDIENTE</option>
                <option value="CONFIRMADO">CONFIRMADO</option>
                <option value="CANCELADA">CANCELADA</option>
            </select>
            <div id="mensaje-estado" style="color:green;font-size:14px;margin-bottom:6px;"></div>
            <button id="cerrar-modal">Cerrar</button>
        `;

        modalBg.appendChild(modal);
        document.body.appendChild(modalBg);

        // Selecciona el estado actual en el select
        const selectEstado = document.getElementById('select-estado');
        selectEstado.value = reserva.estado;

        selectEstado.addEventListener('change', async function() {
            const nuevoEstado = this.value;
            try {
                const resp = await fetch(`/reservas/cambiarEstado/${reserva.id}?estado=${nuevoEstado}`);
                if (resp.ok) {
                    document.getElementById('estado-actual').textContent = nuevoEstado;
                    document.getElementById('mensaje-estado').textContent = "Estado actualizado correctamente.";
                    document.getElementById('mensaje-estado').style.color = "green";
                } else {
                    document.getElementById('mensaje-estado').textContent = "Error al actualizar el estado.";
                    document.getElementById('mensaje-estado').style.color = "red";
                }
            } catch {
                document.getElementById('mensaje-estado').textContent = "Error de conexión.";
                document.getElementById('mensaje-estado').style.color = "red";
            }
        });

        document.getElementById('cerrar-modal').onclick = () => {
            document.body.removeChild(modalBg);
        };
    }

    // Popup para cuando no hay reserva
    function mostrarPopupSinReserva(nombreDepartamento) {
        const modalBg = document.createElement('div');
        modalBg.className = 'modal-bg';

        const modal = document.createElement('div');
        modal.className = 'modal-contenido';
        modal.innerHTML = `
            <h2>${nombreDepartamento}</h2>
            <p>No hay reservas para este departamento.</p>
            <button id="cerrar-modal">Cerrar</button>
        `;

        modalBg.appendChild(modal);
        document.body.appendChild(modalBg);

        document.getElementById('cerrar-modal').onclick = () => {
            document.body.removeChild(modalBg);
        };
    }

    cargarPerfilUsuario();
    cargarDepartamentos().then(() => {
        console.log("Departamentos recargados.");
    });
});