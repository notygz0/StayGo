const alojamientosDummy = [
  { id: 1, nombre: 'Hotel Central', precio: 80, imagen: 'img/list/p-1.png' },
  { id: 2, nombre: 'Departamento Playa', precio: 100, imagen: 'img/list/p-2.png' },
  { id: 3, nombre: 'Cabaña Montaña', precio: 120, imagen: 'img/list/p-4.png' },
  { id: 4, nombre: 'Apartamento Moderno', precio: 90, imagen: 'img/list/p-5.png' },
  { id: 5, nombre: 'Casa Rural', precio: 110, imagen: 'img/list/p-6.png' },
  { id: 6, nombre: 'Villa de Lujo', precio: 150, imagen: 'img/list/p-7.png' },
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

  info.appendChild(nombre);
  info.appendChild(precio);

  card.appendChild(img);
  card.appendChild(info);

  return card;
}

function cargarAlojamientos() {
  const container = document.getElementById('alojamientos-container');
  alojamientosDummy.forEach(a => {
    container.appendChild(crearTarjetaAlojamiento(a));
  });
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
  const nombreInput = document.getElementById('nombre');
  const fechaInput = document.getElementById('fecha');
  const mensaje = document.getElementById('mensaje-reserva');

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

function scrollSuave() {
  document.querySelectorAll('.nav a').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();
      const targetId = this.getAttribute('href').substring(1);
      const targetSection = document.getElementById(targetId);
      if (targetSection) {
        window.scrollTo({
          top: targetSection.offsetTop - 60,
          behavior: 'smooth'
        });
      }
    });
  });
}

function activarSeccionMenu() {
  const sections = document.querySelectorAll('section');
  const navLinks = document.querySelectorAll('.nav a');
  let scrollPos = window.scrollY + 70;

  sections.forEach((section, index) => {
    if (
      scrollPos >= section.offsetTop &&
      scrollPos < section.offsetTop + section.offsetHeight
    ) {
      navLinks.forEach(link => link.classList.remove('active'));
      if (navLinks[index]) navLinks[index].classList.add('active');
    }
  });
}

function configurarMenuUsuario() {
  const authButton = document.getElementById('auth-button');
  const authDropdown = document.getElementById('auth-dropdown');

  authButton.addEventListener('click', () => {
    authDropdown.classList.toggle('hidden');
  });

  window.addEventListener('click', (e) => {
    if (!authButton.contains(e.target) && !authDropdown.contains(e.target)) {
      authDropdown.classList.add('hidden');
    }
  });

  // Eventos en opciones del menú
  document.getElementById('login-link').addEventListener('click', (e) => {
    e.preventDefault();
    alert('Aquí iría la funcionalidad de Iniciar Sesión');
    authDropdown.classList.add('hidden');
  });

  document.getElementById('register-link').addEventListener('click', (e) => {
    e.preventDefault();
    alert('Aquí iría la funcionalidad de Registrarse');
    authDropdown.classList.add('hidden');
  });
}

window.onload = function () {
  cargarAlojamientos();
  cargarOpcionesReserva();
  document.getElementById('reserva-form').addEventListener('submit', manejarReserva);
  scrollSuave();
  window.addEventListener('scroll', activarSeccionMenu);
  configurarMenuUsuario();
};
