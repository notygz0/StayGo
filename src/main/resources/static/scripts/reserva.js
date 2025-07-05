async function obtenerReservas() {
  const response = await fetch('/reservas/lista');
  if (!response.ok) {
    throw new Error('Error al obtener los reservas');
  }
  return await response.json();
}

let reservas = [];

function crearTarjetaReserva(reserva) {
  const card = document.createElement('div');
  card.className = 'reserva-card';

  const img = document.createElement('img');
  img.src = "/img/list/p-5.png";
  img.alt = reserva.name;

  const info = document.createElement('div');
  info.className = 'reserva-info';

  const nombre = document.createElement('h3');
  nombre.textContent = reserva.name;

  const boton = document.createElement('button');
  boton.textContent = 'Ver reserva';

  boton.onclick = () => mostrarPopupReserva(reserva);

  card.appendChild(img);
  card.appendChild(info);
  info.appendChild(nombre);
  info.appendChild(boton);

  return card;
}

function cargarReservas() {
  const container = document.getElementById('reservas-container');
  container.innerHTML = '';

  reservas.forEach(departamento => {
    const card = crearTarjetaReservas(reservas);
    container.appendChild(card);
  });
}

window.onload = async function () {
  try {
    reservas = await obtenerReservas();
    cargarReservas();
  } catch (error) {
    console.error(error);
    document.getElementById('reservas-container').innerHTML = '<p>Error al cargar los reservas</p>';
  }

};

function mostrarPopupReserva(reserva) {
  // Crea el fondo del modal
  const modalBg = document.createElement('div');
  modalBg.className = 'modal-bg';
  // Crea el contenido del modal
  const modal = document.createElement('div');
  modal.className = 'modal-contenido';
  modal.innerHTML = `
    <h2>${reserva.name}</h2>
    <p>ID: ${reserva.id}</p>
    <p>Fecha de inicio: ${reserva.fechaInicio}</p>
    <p>Fecha de fin: ${reserva.fechaFin}</p>
    <p>Estado: ${reserva.estado}</p>
    <!-- Agrega aquí más detalles si tienes -->
    <button id="cerrar-modal">Cerrar</button>
  `;

  modalBg.appendChild(modal);
  document.body.appendChild(modalBg);

  document.getElementById('cerrar-modal').onclick = () => {
    document.body.removeChild(modalBg);
  };
}