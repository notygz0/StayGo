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
  img.src = "/img/list/p-5.png";
  img.alt = departamento.nombre;

  const info = document.createElement('div');
  info.className = 'departamento-info';

  const nombre = document.createElement('h3');
  nombre.textContent = departamento.nombre;
  const boton = document.createElement('button');
  boton.textContent = 'Ver reserva';

  const link = document.createElement('a');
  link.href = `/departamentos/detalle?id=${departamento.id}`;
  link.classList.add('departamento-card-link');

  card.appendChild(img);
  card.appendChild(info);
  info.appendChild(nombre);
  info.appendChild(boton);
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

window.onload = async function () {
  try {
    departamentos = await obtenerDepartamentos();
    cargarDepartamentos();
  } catch (error) {
    console.error(error);
    document.getElementById('departamentos-container').innerHTML = '<p>Error al cargar los departamentos</p>';
  }
};