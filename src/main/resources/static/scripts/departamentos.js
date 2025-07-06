async function obtenerDepartamentos() {
  const response = await fetch('/departamentos/lista');
  if (!response.ok) {
    throw new Error('Error al obtener los departamentos');
  }
  return await response.json();
}

let departamentos = [];

function crearTarjetaDepartamento(departamento) {
  const card = document.createElement('div');
  card.className = 'departamento-card';

  const img = document.createElement('img');
  if (departamento.imagen) {
    // Si la imagen viene en base64, arma la URL data
    img.src = `data:image/jpeg;base64,${departamento.imagen}`;
  } else {
    img.src = "/img/list/p-5.png";
  }
  img.alt = departamento.nombre;

  const info = document.createElement('div');
  info.className = 'departamento-info';

  const nombre = document.createElement('h3');
  nombre.textContent = departamento.nombre;

  const descripcion = document.createElement('p');
  descripcion.textContent = departamento.descripcion;

  const precio = document.createElement('p');
  precio.textContent = `Precio: $${departamento.precio.toLocaleString()}`;

  // redirige la pagina para ver los detalles del departamento
  const link = document.createElement('a');
  link.href = `/departamentos/detalle?id=${departamento.id}`; 
  link.classList.add('departamento-card-link'); 

  card.appendChild(img);
  card.appendChild(info);
  info.appendChild(nombre);
  info.appendChild(descripcion);
  info.appendChild(precio);
  link.appendChild(card);

  return link;
}


function cargarDepartamentos() {
  const container = document.getElementById('departamentos-container');
  container.innerHTML = '';

  departamentos.forEach(departamento => {
    const card = crearTarjetaDepartamento(departamento);
    container.appendChild(card);
  });
}
function filtrarDepartamentos() {
  const searchName = document.getElementById('search-name').value.toLowerCase();
  const searchPrice = parseFloat(document.getElementById('price-range').value);

  const container = document.getElementById('departamentos-container');
  container.innerHTML = '';

  const filteredDepartamentos = departamentos.filter(departamento => {
    const matchesName = departamento.nombre.toLowerCase().includes(searchName);
    const matchesPrice = departamento.precio <= searchPrice;
    return matchesName && matchesPrice;
  });

  if (filteredDepartamentos.length > 0) {
    filteredDepartamentos.forEach(departamento => {
      const card = crearTarjetaDepartamento(departamento);
      container.appendChild(card);
    });
  } else {
    const noResultsMessage = document.createElement('p');
    noResultsMessage.textContent = "No se encontraron departamentos que coincidan con los filtros";
    container.appendChild(noResultsMessage);
  }
  document.getElementById('price-range-value').textContent = searchPrice.toLocaleString();
}

window.onload = async function () {
  try {
    departamentos = await obtenerDepartamentos();
    cargarDepartamentos();

    document.getElementById('search-name').addEventListener('keyup', filtrarDepartamentos);
    document.getElementById('price-range').addEventListener('input', filtrarDepartamentos);
  } catch (error) {
    console.error(error);
    document.getElementById('departamentos-container').innerHTML = '<p>Error al cargar los departamentos</p>';
  }
};
