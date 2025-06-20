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