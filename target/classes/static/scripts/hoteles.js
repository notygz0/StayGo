const hoteles = [
  {
    nombre: "Hotel Playa",
    descripcion: "Frente a la playa con excelente vista al mar.",
    imagen: "/img/list/hotel-1.png",
    precio: 450000
  },
  {
    nombre: "Hotel Casino Dreams",
    descripcion: "Ubicado en la concurrida Av Alemania de Temuco con todas las comodidades",
    imagen: "/img/list/hotel-2.png",
    precio: 600000
  }
];

// funcion para crear tarjeta de hotel
function crearTarjetaHotel(hotel) {
  const card = document.createElement('div');
  card.className = 'hotel-card';

  const img = document.createElement('img');
  img.src = hotel.imagen;
  img.alt = hotel.nombre;

  const info = document.createElement('div');
  info.className = 'hotel-info';

  const nombre = document.createElement('h3');
  nombre.textContent = hotel.nombre;

  const descripcion = document.createElement('p');
  descripcion.textContent = hotel.descripcion;

  const precio = document.createElement('p');
  precio.textContent = `Precio: $${Number(hotel.precio).toLocaleString()}`;

  info.appendChild(nombre);
  info.appendChild(descripcion);
  info.appendChild(precio);

  card.appendChild(img);
  card.appendChild(info);

  return card;
}

// Carga inicial de hoteles
function cargarHoteles() {
  const container = document.getElementById('hoteles-container');
  container.innerHTML = '';

  hoteles.forEach(hotel => {
    const card = crearTarjetaHotel(hotel);
    container.appendChild(card);
  });
}

// Filtro por nombre y precio
function filtrarHoteles() {
  const searchNameInput = document.getElementById('search-name');
  const searchPriceInput = document.getElementById('price-range');
  const searchName = searchNameInput ? searchNameInput.value.toLowerCase() : '';
  const searchPrice = searchPriceInput ? parseFloat(searchPriceInput.value) : Infinity;

  const container = document.getElementById('hoteles-container');
  container.innerHTML = '';

  const filteredHoteles = hoteles.filter(hotel => {
    const matchesName = hotel.nombre.toLowerCase().includes(searchName);
    const matchesPrice = hotel.precio <= searchPrice;
    return matchesName && matchesPrice;
  });

  if (filteredHoteles.length > 0) {
    filteredHoteles.forEach(hotel => {
      const card = crearTarjetaHotel(hotel);
      container.appendChild(card);
    });
  } else {
    const noResultsMessage = document.createElement('p');
    noResultsMessage.textContent = "No se encontraron hoteles que coincidan con los filtros";
    container.appendChild(noResultsMessage);
  }
  const priceRangeValue = document.getElementById('price-range-value');
  if (priceRangeValue) {
    priceRangeValue.textContent = Number(searchPrice).toLocaleString();
  }

  document.getElementById('price-range-value').textContent = searchPrice.toLocaleString();
}

// Evento onload
window.onload = cargarHoteles;