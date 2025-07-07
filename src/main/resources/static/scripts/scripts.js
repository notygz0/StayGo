async function obtenerAlojamientos() {
  const response = await fetch('/departamentos/lista');
  if (!response.ok) {
    throw new Error('Error al obtener los alojamientos');
  }
  return await response.json();
}

function crearTarjetaAlojamiento(alojamiento) {
  const card = document.createElement('div');
  card.className = 'alojamiento-card';
  const img = document.createElement('img');
  if (alojamiento.imagen) {
    // Si la imagen viene en base64, arma la URL data
    img.src = `data:image/jpeg;base64,${alojamiento.imagen}`;
  } else {
    img.src = "/img/list/p-5.png";
  }
  img.alt = alojamiento.nombre;
  const info = document.createElement('div');
  info.className = 'alojamiento-info';
  const nombre = document.createElement('h3');
  nombre.textContent = alojamiento.nombre;
  const precio = document.createElement('p');
  precio.textContent = `Precio por noche: $${alojamiento.precio.toLocaleString()}`;

  const link = document.createElement('a');
  link.href = `/departamentos/detalle?id=${alojamiento.id}`;
  link.classList.add('alojamiento-card');

  info.append(nombre, precio);
  card.append(img, info);
  link.appendChild(card);
  return link;
}
async function cargarAlojamientos() {
  const container = document.getElementById('alojamientos-container');
  container.innerHTML = '';
  try {
    const alojamientos = await obtenerAlojamientos();
    alojamientos.forEach(alojamiento => {
      container.appendChild(crearTarjetaAlojamiento(alojamiento));
    });
  } catch (error) {
    container.innerHTML = '<p>Error al cargar los alojamientos</p>';
    console.error(error);
  }
}

window.onload = function() {
  cargarAlojamientos();

  const userButton   = document.getElementById('user-menu-button');
  const userDropdown = document.getElementById('user-dropdown');
  userButton.addEventListener('click', e => {
    e.stopPropagation();
    userDropdown.classList.toggle('show');
  });
  document.addEventListener('click', e => {
    if (!userDropdown.contains(e.target) && e.target !== userButton) {
      userDropdown.classList.remove('show');
    }
  });
};
