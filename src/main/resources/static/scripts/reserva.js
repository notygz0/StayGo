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
  if (reserva.imagen) {
    // Si la imagen viene en base64, arma la URL data
    img.src = `data:image/jpeg;base64,${reserva.imagen}`;
  } else {
    img.src = "/img/list/p-5.png";
  }
  img.alt = reserva.alojamiento;

  const info = document.createElement('div');
  info.className = 'reserva-info';

  const nombreAlojamiento = document.createElement('h3');
  nombreAlojamiento.textContent = reserva.alojamiento;

  const boton = document.createElement('button');
  boton.textContent = 'Ver reserva';
  boton.onclick = () => mostrarPopupReserva(reserva);

  card.appendChild(img);
  card.appendChild(info);
  info.appendChild(nombreAlojamiento);
  info.appendChild(boton);

  return card;
}

function cargarReservas() {
  const container = document.getElementById('reservas-container');
  container.innerHTML = '';

  reservas.forEach(reserva => {
    const card = crearTarjetaReserva(reserva);
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
    <h2>${reserva.alojamiento}</h2>
    ${reserva.imagen ? `<img src="data:image/jpeg;base64,${reserva.imagen}" alt="Imagen reserva">` : ''}
    <p>Fecha de inicio: ${reserva.fechaInicio}</p>
    <p>Fecha de fin: ${reserva.fechaFin}</p>
    <p>Estado: ${reserva.estado}</p>
    <button id="cerrar-modal">Cerrar</button>
  `;

  modalBg.appendChild(modal);
  document.body.appendChild(modalBg);

  document.getElementById('cerrar-modal').onclick = () => {
    document.body.removeChild(modalBg);
  };
}