const alojamientosDummy = [
  { id: 1, nombre: 'Hotel Central',       precio:  80, imagen: 'img/list/p-1.png' },
  { id: 2, nombre: 'Departamento Playa',  precio: 100, imagen: 'img/list/p-2.png' },
  { id: 3, nombre: 'Cabaña Montaña',      precio: 120, imagen: 'img/list/p-4.png' },
  { id: 4, nombre: 'Apartamento Moderno', precio:  90, imagen: 'img/list/p-5.png' },
  { id: 5, nombre: 'Casa Rural',          precio: 110, imagen: 'img/list/p-6.png' },
  { id: 6, nombre: 'Villa de Lujo',       precio: 150, imagen: 'img/list/p-7.png' },
];

function crearTarjetaAlojamiento(alojamiento) {
  const card = document.createElement('div');
  card.className = 'alojamiento-card';
  const img = document.createElement('img');
  img.src = alojamiento.imagen;
  img.alt = alojamiento.nombre;
  const info = document.createElement('div');
  info.className = 'alojamiento-info';
  const nombre = document.createElement('h3');
  nombre.textContent = alojamiento.nombre;
  const precio = document.createElement('p');
  precio.textContent = `Precio por noche: $${alojamiento.precio}`;
  info.append(nombre, precio);
  card.append(img, info);
  return card;
}

function cargarAlojamientos() {
  const container = document.getElementById('alojamientos-container');
  alojamientosDummy.forEach(a => container.appendChild(crearTarjetaAlojamiento(a)));
}

function cargarOpcionesReserva() {
  const select = document.getElementById('alojamiento');
  alojamientosDummy.forEach(a => {
    const option = document.createElement('option');
    option.value = a.id;
    option.textContent = a.nombre;
    select.appendChild(option);
  });
}

function manejarReserva(event) {
  event.preventDefault();
  const alojamientoSelect = document.getElementById('alojamiento');
  const nombreInput       = document.getElementById('nombre');
  const fechaInput        = document.getElementById('fecha');
  const mensaje           = document.getElementById('mensaje-reserva');

  if (!alojamientoSelect.value || !nombreInput.value || !fechaInput.value) {
    mensaje.textContent = 'Por favor completa todos los campos.';
    mensaje.style.color = 'red';
    return;
  }

  const alojamiento = alojamientosDummy.find(a => a.id == alojamientoSelect.value);
  mensaje.style.color = 'green';
  mensaje.textContent = `Reserva exitosa para ${nombreInput.value} en ${alojamiento.nombre} el día ${fechaInput.value}. ¡Gracias!`;
  event.target.reset();
}

window.onload = function() {
  cargarAlojamientos();
  cargarOpcionesReserva();
  document.getElementById('reserva-form').addEventListener('submit', manejarReserva);

  const userButton   = document.getElementById('user-menu-button');
  const userDropdown = document.getElementById('user-dropdown');

  userButton.addEventListener('click', e => {
    e.stopPropagation();
    userDropdown.classList.toggle('show');
  });

  document.addEventListener('click', e => {
    if (!userDropdown.contains(e.target)) {
      userDropdown.classList.remove('show');
    }
  });
};
