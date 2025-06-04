package org.staygo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import org.staygo.entity.Alojamiento;
import org.staygo.entity.Departamento;
import org.staygo.entity.EstadoReserva;
import org.staygo.entity.Hotel;
import org.staygo.entity.Pago;
import org.staygo.entity.Reserva;
import org.staygo.entity.Role;
import org.staygo.entity.User;

/**
 * clase que permite al usuario usar el programa mediante terminal.
 * <p>
 * actua como suplente de lo que seria despues el sitio web,
 * por lo que eventualmente quedara obsoleta.
 * todos los system.in fueron reemplazados por logger por sugerencia de sonarqube.
 * </p>
 *
 * @author lorenzo lopez
 */
public class MenuTerminal {

    /**
     * lista que contiene los alojamientos disponibles en el sistema.
     */
    private List<Alojamiento> alojamientos;

    /**
     * lista que contiene los usuarios registrados en el sistema.
     */
    private List<User> usuarios;

    /**
     * scanner para leer entradas de usuario desde la terminal.
     */
    private Scanner leer;

    /**
     * usuario que esta actualmente activo (logueado).
     */
    private User usuarioActivo;

    /**
     * gestor de datos para cargar y guardar usuarios y alojamientos.
     */
    private GestorDeDatos gestorDeDatos;

    /**
     * logger para mostrar mensajes informativos y de error en la consola.
     */
    private static final Logger logger = Logger.getLogger(MenuTerminal.class.getName());

    /**
     * constructor que inicializa las listas de alojamientos y usuarios
     * y carga los datos iniciales en el sistema.
     */
    public MenuTerminal() {
        gestorDeDatos = new GestorDeDatos();

        usuarios = gestorDeDatos.cargarUsuarios();
        alojamientos = gestorDeDatos.cargarAlojamientos();

        if (usuarios.isEmpty()) {
            cargarDatosIniciales();
            gestorDeDatos.guardarUsuarios(usuarios);
        }

        leer = new Scanner(System.in);
    }

    /**
     * carga usuarios iniciales por defecto (admin, arrendatario y cliente)
     * en caso de que la lista de usuarios este vacia.
     */
    private void cargarDatosIniciales() {
        usuarios.add(new User(1L, "admin", "admin123", Role.ADMIN));
        usuarios.add(new User(2L, "arrendatario", "arrenda123", Role.ARRENDATARIO));
        usuarios.add(new User(3L, "cliente", "cliente123", Role.CLIENTE));
    }

    /**
     * muestra el menu principal o el menu de inicio dependiendo
     * de si hay un usuario activo o no, y maneja la navegacion del menu.
     */
    public void mostrarMenu() {
        int opcion;
        do {
            if (usuarioActivo == null) {
                logger.info("\n==== Menu de Inicio ====");
                logger.info("1. Iniciar sesion");
                logger.info("2. Registrarse");
                logger.info("3. Salir");
                logger.info("Seleccione una opcion: ");
                opcion = obtenerEntradaNumerica();
                switch (opcion) {
                    case 1 -> iniciarSesion();
                    case 2 -> registrarUsuario();
                    case 3 -> logger.info("Saliendo del sistema...");
                    default -> logger.warning("Opcion invalida. Intente nuevamente.");
                }
            } else {
                logger.info("\n==== Menu Principal ====");
                logger.info("1. Ver alojamientos disponibles");
                logger.info("2. Buscar alojamientos (por tipo y precio)");
                logger.info("3. Realizar una reserva");
                logger.info("4. Ver reservas de un usuario");
                logger.info("5. Realizar pago");
                logger.info("6. Ver pagos realizados");
                logger.info("7. Agregar alojamiento");
                logger.info("8. Eliminar alojamiento");
                logger.info("9. Cerrar sesion");
                logger.info("Seleccione una opcion: ");

                opcion = obtenerEntradaNumerica();

                switch (opcion) {
                    case 1 -> mostrarAlojamientos();
                    case 2 -> buscarAlojamientosFiltrados();
                    case 3 -> realizarReserva();
                    case 4 -> verReservas();
                    case 5 -> realizarPago();
                    case 6 -> verPagos();
                    case 7 -> agregarAlojamiento();
                    case 8 -> eliminarAlojamiento();
                    case 9 -> cerrarSesion();
                    default -> logger.warning("Opcion invalida. Intente nuevamente.");
                }
            }
        } while (opcion != 3 && !(usuarioActivo != null && opcion == 9));
    }

    /**
     * metodo que permite iniciar sesion solicitando nombre y contrasena,
     * y asigna el usuario activo si las credenciales son correctas.
     */
    public void iniciarSesion() {
        logger.info("\nIngrese su nombre de usuario: ");
        String nombre = leer.nextLine();
        logger.info("Ingrese su contrasena: ");
        String contrasena = leer.nextLine();

        for (User u : usuarios) {
            if (u.iniciarSesion(nombre, contrasena)) {
                usuarioActivo = u;
                logger.info("Bienvenido, " + usuarioActivo.getNombre());
                return;
            }
        }

        logger.warning("Usuario o contrasena incorrectos.");
    }

    /**
     * metodo que permite registrar un nuevo usuario solicitando
     * nombre, contrasena y rol, luego guarda el usuario en el sistema.
     */
    public void registrarUsuario() {
        logger.info("\nIngrese su nombre de usuario: ");
        String nombre = leer.nextLine();

        logger.info("Ingrese la contrasena: ");
        String contrasena = leer.nextLine();

        logger.info("Seleccione el rol (1. CLIENTE, 2. ARRENDATARIO): ");
        int rolOption = obtenerEntradaNumerica();
        leer.nextLine();
        Role rol = (rolOption == 1) ? Role.CLIENTE : Role.ARRENDATARIO;

        Long idUsuario = (long) (usuarios.size() + 1);
        User nuevoUsuario = new User(idUsuario, nombre, contrasena, rol);
        usuarios.add(nuevoUsuario);

        gestorDeDatos.guardarUsuarios(usuarios);

        logger.info("Usuario registrado con exito.");
    }

    /**
     * obtiene y valida una entrada numerica del usuario desde la terminal,
     * asegurandose que sea un numero valido.
     *
     * @return el numero ingresado por el usuario.
     */
    private int obtenerEntradaNumerica() {
        while (!leer.hasNextInt()) {
            leer.nextLine();
            logger.info("Por favor, ingrese un numero valido: ");
        }
        int num = leer.nextInt();
        leer.nextLine();
        return num;
    }

    /**
     * metodo para cerrar la sesion del usuario activo.
     */
    private void cerrarSesion() {
        usuarioActivo = null;
        logger.info("Has cerrado sesion.");
    }

    /**
     * muestra en consola la lista completa de alojamientos disponibles.
     */
    private void mostrarAlojamientos() {
        logger.info("\n--- ALOJAMIENTOS ---");
        for (int i = 0; i < alojamientos.size(); i++) {
            logger.info(String.format("[%d]\n%s", i + 1, alojamientos.get(i).verDetalles()));
        }
    }

    /**
     * permite buscar alojamientos filtrando por tipo y rango de precio,
     * y muestra los resultados en consola.
     */
    private void buscarAlojamientosFiltrados() {
        logger.info("\nSeleccione el tipo de alojamiento:");
        logger.info("1. Todos");
        logger.info("2. Hotel");
        logger.info("3. Departamento");
        int tipo = obtenerEntradaNumerica();

        logger.info("Ingrese precio minimo (0 para sin minimo): ");
        float precioMin = leer.nextFloat();
        logger.info("Ingrese precio maximo (0 para sin maximo): ");
        float precioMax = leer.nextFloat();
        leer.nextLine();

        List<Alojamiento> resultados = new ArrayList<>();

        for (Alojamiento alojamiento : alojamientos) {
            boolean tipoOk = false;
            switch (tipo) {
                case 1 -> tipoOk = true;
                case 2 -> tipoOk = alojamiento instanceof Hotel;
                case 3 -> tipoOk = alojamiento instanceof Departamento;
                default -> logger.warning("Tipo invalido, mostrando todos.");
            }

            boolean precioOk = true;
            if (precioMin > 0 && alojamiento.getPrecio() < precioMin) {
                precioOk = false;
            }
            if (precioMax > 0 && alojamiento.getPrecio() > precioMax) {
                precioOk = false;
            }

            if (tipoOk && precioOk) {
                resultados.add(alojamiento);
            }
        }

        if (resultados.isEmpty()) {
            logger.info("No se encontraron alojamientos que coincidan con los criterios.");
        } else {
            logger.info("\n--- RESULTADOS DE LA BUSQUEDA ---");
            for (int i = 0; i < resultados.size(); i++) {
                logger.info(String.format("[%d]\n%s", i + 1, resultados.get(i).verDetalles()));
            }
        }
    }

    /**
     * permite a un usuario con rol CLIENTE realizar una reserva
     * para un alojamiento seleccionado y fechas indicadas.
     */
    private void realizarReserva() {
        if (usuarioActivo.getRol() != Role.CLIENTE) {
            logger.warning("Solo los clientes pueden realizar reservas.");
            return;
        }

        mostrarAlojamientos();

        logger.info("\nSeleccione el numero del alojamiento a reservar: ");
        int index = obtenerEntradaNumerica() - 1;

        if (index < 0 || index >= alojamientos.size()) {
            logger.warning("Alojamiento invalido.");
            return;
        }

        try {
            logger.info("Fecha inicio (YYYY-MM-DD): ");
            LocalDate inicio = LocalDate.parse(leer.nextLine());

            logger.info("Fecha fin (YYYY-MM-DD): ");
            LocalDate fin = LocalDate.parse(leer.nextLine());

            Reserva reserva = new Reserva(usuarioActivo, alojamientos.get(index), inicio, fin);
            usuarioActivo.realizarReserva(reserva);
            alojamientos.get(index).setOcupado(true);

            gestorDeDatos.guardarUsuarios(usuarios);
            gestorDeDatos.guardarAlojamientos(alojamientos);

            logger.info("Reserva realizada con exito.");
        } catch (Exception e) {
            logger.warning("Error al realizar la reserva: " + e.getMessage());
        }
    }

    /**
     * muestra las reservas realizadas por el usuario activo,
     * solo si su rol es CLIENTE.
     */
    private void verReservas() {
        if (usuarioActivo.getRol() != Role.CLIENTE) {
            logger.warning("Solo los clientes pueden ver sus reservas.");
            return;
        }

        List<Reserva> reservas = usuarioActivo.obtenerReservas();

        if (reservas.isEmpty()) {
            logger.info("Este usuario no tiene reservas.");
        } else {
            logger.info("\n--- RESERVAS ---");
            for (Reserva r : reservas) {
                logger.info(r.obtenerDetallesReserva());
            }
        }
    }

    /**
     * permite a un usuario con rol CLIENTE realizar un pago
     * para una reserva confirmada.
     */
    private void realizarPago() {
        if (usuarioActivo.getRol() != Role.CLIENTE) {
            logger.warning("Solo los clientes pueden realizar pagos");
            return;
        }

        List<Reserva> reservasPendientes = new ArrayList<>();
        for (Reserva r : usuarioActivo.obtenerReservas()) {
            if (r.getEstadoReserva() == EstadoReserva.CONFIRMADO) {
                reservasPendientes.add(r);
            }
        }

        if (reservasPendientes.isEmpty()) {
            logger.info("No tienes reservas confirmadas pendientes de pago");
            return;
        }

        logger.info("\nSeleccione la reserva que desea pagar:");
        for (int i = 0; i < reservasPendientes.size(); i++) {
            logger.info(String.format("[%d]\n%s", i + 1, reservasPendientes.get(i).obtenerDetallesReserva()));
        }

        int opcionPago = obtenerEntradaNumerica() - 1;
        if (opcionPago < 0 || opcionPago >= reservasPendientes.size()) {
            logger.warning("Opcion invalida.");
            return;
        }

        Reserva reservaSeleccionada = reservasPendientes.get(opcionPago);
        float monto = reservaSeleccionada.getAlojamiento().getPrecio();

        Pago pago = Pago.crearPago(usuarioActivo, monto);
        boolean exito = pago.realizarPago();

        if (exito) {
            usuarioActivo.agregarPago(pago);
            gestorDeDatos.guardarUsuarios(usuarios);
            logger.info("Pago realizado con exito. Monto: $" + monto);
        } else {
            logger.warning("El pago ya fue realizado anteriormente");
        }
    }

    /**
     * muestra los pagos realizados por el usuario activo,
     * solo si su rol es CLIENTE.
     */
    private void verPagos() {
        if (usuarioActivo.getRol() != Role.CLIENTE) {
            logger.warning("Solo los clientes pueden ver sus pagos");
            return;
        }

        List<Pago> pagos = usuarioActivo.getPagos();

        if (pagos.isEmpty()) {
            logger.info("No se encontraron pagos realizados");
            return;
        }

        logger.info("\n--- PAGOS REALIZADOS ---");
        for (Pago p : pagos) {
            logger.info(p.obtenerDetallesPago());
        }
    }

    /**
     * metodo para agregar un alojamiento nuevo,
     * dependiendo del rol del usuario activo (arrendatario o admin).
     */
    private void agregarAlojamiento() {
        if (usuarioActivo.getRol() == Role.ARRENDATARIO) {
            agregarDepartamento();
        } else if (usuarioActivo.getRol() == Role.ADMIN) {
            agregarHotel();
        } else {
            logger.warning("No tiene permisos para agregar alojamiento.");
        }
    }

    /**
     * agrega un departamento nuevo al sistema,
     * solo accesible para usuarios con rol ARRENDATARIO.
     */
    private void agregarDepartamento() {
        if (usuarioActivo.getRol() != Role.ARRENDATARIO) {
            logger.warning("Solo los arrendatarios pueden agregar departamentos.");
            return;
        }

        logger.info("\nDireccion del departamento: ");
        String direccion = leer.nextLine();

        logger.info("Precio por noche: ");
        float precio = leer.nextFloat();
        leer.nextLine();

        logger.info("Descripcion del departamento: ");
        String descripcion = leer.nextLine();

        logger.info("Numero de habitaciones: ");
        int numHabitaciones = leer.nextInt();
        leer.nextLine();

        Departamento nuevoDepartamento = new Departamento(direccion, precio, descripcion, numHabitaciones, usuarioActivo);
        alojamientos.add(nuevoDepartamento);

        gestorDeDatos.guardarAlojamientos(alojamientos);

        logger.info("Departamento agregado con exito.");
    }

    /**
     * agrega un hotel nuevo al sistema,
     * solo accesible para usuarios con rol ADMIN.
     */
    private void agregarHotel() {
        logger.info("\nDireccion del hotel: ");
        String direccion = leer.nextLine();

        logger.info("Precio por noche: ");
        float precio = leer.nextFloat();
        leer.nextLine();

        logger.info("Descripcion del hotel: ");
        String descripcion = leer.nextLine();

        logger.info("Numero de estrellas: ");
        int numEstrellas = leer.nextInt();
        leer.nextLine();

        Hotel nuevoHotel = new Hotel(direccion, precio, descripcion, numEstrellas, 0);
        alojamientos.add(nuevoHotel);

        gestorDeDatos.guardarAlojamientos(alojamientos);

        logger.info("Hotel agregado con exito.");
    }

    /**
     * elimina un alojamiento seleccionado de la lista de alojamientos.
     */
    private void eliminarAlojamiento() {
        logger.info("\nSeleccione el alojamiento que desea eliminar:");
        mostrarAlojamientos();

        logger.info("\nSeleccione el numero del alojamiento a eliminar: ");
        int index = obtenerEntradaNumerica() - 1;

        if (index < 0 || index >= alojamientos.size()) {
            logger.warning("Alojamiento invalido.");
            return;
        }

        alojamientos.remove(index);

        gestorDeDatos.guardarAlojamientos(alojamientos);

        logger.info("Alojamiento eliminado con exito.");
    }
}

