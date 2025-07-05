const urlParams = new URLSearchParams(window.location.search);
const departamentoId = parseInt(urlParams.get('id'));

async function obtenerDepartamentos() {
  const response = await fetch('/departamentos/lista');
  if (!response.ok) {
    throw new Error('Error al obtener los departamentos');
  }
  return await response.json();
}

// Busca y muestra el detalle del departamento
async function mostrarDetalleDepartamento() {
  try {
    const departamentos = await obtenerDepartamentos();
    const departamento = departamentos.find(dep => dep.id === departamentoId);

    const detalleContainer = document.getElementById('departamento-detalle');
    if (departamento) {
      detalleContainer.innerHTML = `
        <img src="/img/list/p-5.png" alt="${departamento.nombre}">
        <h3>${departamento.nombre}</h3>
        <p><strong>Descripción:</strong> ${departamento.descripcion}</p>
        <p><strong>Precio:</strong> $${departamento.precio.toLocaleString()}</p>
        <p><strong>Dirección:</strong> aquí va dirección</p>
      `;
    } else {
      detalleContainer.innerHTML = "<p>Departamento no encontrado.</p>";
    }
  } catch (error) {
    document.getElementById('departamento-detalle').innerHTML = "<p>Error al cargar el departamento.</p>";
    console.error(error);
  }
}

window.onload = mostrarDetalleDepartamento;

// scripts/departamento_detalle.js
document.addEventListener('DOMContentLoaded', function() {
  const params = new URLSearchParams(window.location.search);
  const id = params.get('id');
  if (id) {
    // Elimina el select si existe
    const select = document.getElementById('alojamiento');
    if (select) select.remove();

    // Crea el input oculto
    const form = document.getElementById('reserva-form');
    const hidden = document.createElement('input');
    hidden.type = 'hidden';
    hidden.id = 'idAlojamiento';
    hidden.name = 'idAlojamiento';
    hidden.value = id;
    form.insertBefore(hidden, form.firstChild);

    const fechaInicial = new Date().toISOString().split('T')[0];
    document.getElementById('fecha inicio').setAttribute('min', fechaInicial);
    const fechaFinal = new Date().toISOString().split('T')[0];
    document.getElementById('fecha final').setAttribute('min', fechaFinal);
  }
});