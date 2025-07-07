document.addEventListener('DOMContentLoaded', () => {
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
    cargarPerfilUsuario();
});