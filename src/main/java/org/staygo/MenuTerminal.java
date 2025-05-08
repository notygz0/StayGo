package org.staygo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * clase que permite al usuario usar el programa mediante terminal.
 * <p>
 *     actua como suplente de lo que seria despues el sitio web, por lo que eventualmente quedara obsoleta.
 *     todos los system.in fueron reemplazados por logger por sugerencia de sonarqube.
 * </p>
 *
 * @author Lorenzo Lopez
 */
public class MenuTerminal {

    private List<Alojamiento> alojamientos;
    private List<Usuario> usuarios;
    private Scanner leer;
    private Usuario usuarioActivo;
    private static final Logger logger = Logger.getLogger(MenuTerminal.class.getName());

    /**
     * constructor que inicializa las listas de alojamientos y usuarios
     * y carga los datos iniciales en el sistema.
     */
    public MenuTerminal() {
        alojamientos = new ArrayList<>();
        usuarios = new ArrayList<>();
        leer = new Scanner(System.in);
        cargarDatosIniciales();
    }

    /**
     * metodo que carga los datos iniciales de usuarios al sistema.
     * en este caso, agrega usuarios con diferentes roles como ADMIN, ARRENDATARIO y CLIENTE.
     */
    private void cargarDatosIniciales() {
        usuarios.add(new Usuario(1L, "admin", "admin123", Roles.ADMIN));
        usuarios.add(new Usuario(2L, "arrendatario", "arrenda123", Roles.ARRENDATARIO));
        usuarios.add(new Usuario(3L, "cliente", "cliente123", Roles.CLIENTE));
    }

    /**
     * metodo que muestra el menu principal del sistema y gestiona las opciones seleccionadas.
     * dependiendo del estado de sesion del usuario, muestra el menu de inicio o el menu principal.
     */
    public void mostrarMenu() {
        int opcion;
        do {
            if (usuarioActivo == null) {
                logger.info("\n==== Menú de Inicio ====");
                logger.info("1. Iniciar sesión");
                logger.info("2. Registrarse");
                logger.info("3. Salir");
                logger.info("Seleccione una opción: ");
                opcion = obtenerEntradaNumerica();
                switch (opcion) {
                    case 1 -> iniciarSesion();
                    case 2 -> registrarUsuario();
                    case 3 -> logger.info("Saliendo del sistema...");
                    default -> logger.warning("Opción inválida. Intente nuevamente.");
                }
            } else {
                logger.info("\n==== Menú Principal ====");
                logger.info("1. Ver alojamientos disponibles");
                logger.info("2. Realizar una reserva");
                logger.info("3. Ver reservas de un usuario");
                logger.info("4. Agregar alojamiento");
                logger.info("5. Eliminar alojamiento");
                logger.info("6. Cerrar Sesión");
                logger.info("Seleccione una opción: ");

                opcion = obtenerEntradaNumerica();
                switch (opcion) {
                    case 1 -> mostrarAlojamientos();
                    case 2 -> realizarReserva();
                    case 3 -> verReservas();
                    case 4 -> agregarAlojamiento();
                    case 5 -> eliminarAlojamiento();
                    case 6 -> cerrarSesion();
                    default -> logger.warning("Opción inválida. Intente nuevamente.");
                }
            }
        } while (opcion != 3);
    }

    /**
     * metodo que gestiona el inicio de sesion del usuario.
     * solicita el nombre de usuario y la contraseña, y valida las credenciales.
     */
    public void iniciarSesion() {
        logger.info("\nIngrese su nombre de usuario: ");
        String nombre = leer.nextLine();
        logger.info("Ingrese su contraseña: ");
        String contrasena = leer.nextLine();

        for (Usuario u : usuarios) {
            if (u.iniciarSesion(nombre, contrasena)) {
                usuarioActivo = u;
                logger.info("Bienvenido, " + usuarioActivo.getNombre());
                return;
            }
        }

        logger.warning("Usuario o contraseña incorrectos.");
    }

    /**
     * metodo que permite registrar un nuevo usuario.
     * el nuevo usuario puede ser un CLIENTE o un ARRENDATARIO.
     */
    public void registrarUsuario() {
        logger.info("\nIngrese su nombre de usuario: ");
        String nombre = leer.nextLine();

        logger.info("Ingrese la contraseña: ");
        String contrasena = leer.nextLine();

        logger.info("Seleccione el rol (1. CLIENTE, 2. ARRENDATARIO): ");
        int rolOption = obtenerEntradaNumerica();
        leer.nextLine();
        Roles rol = (rolOption == 1) ? Roles.CLIENTE : Roles.ARRENDATARIO;

        Long idUsuario = (long) (usuarios.size() + 1);
        Usuario nuevoUsuario = new Usuario(idUsuario, nombre, contrasena, rol);
        usuarios.add(nuevoUsuario);
        logger.info("Usuario registrado con éxito.");
    }

    /**
     * metodo que obtiene la entrada numerica del usuario.
     * valida que el usuario ingrese un número válido.
     *
     * @return el número ingresado por el usuario.
     */
    private int obtenerEntradaNumerica() {
        while (!leer.hasNextInt()) {
            leer.nextLine();
            logger.info("Por favor, ingrese un número válido: ");
        }
        int num = leer.nextInt();
        leer.nextLine();
        return num;
    }

    /**
     * metodo que cierra la sesion del usuario activo.
     */
    private void cerrarSesion() {
        usuarioActivo = null;
        logger.info("Has cerrado sesión.");
    }

    /**
     * metodo que muestra todos los alojamientos disponibles.
     */
    private void mostrarAlojamientos() {
        logger.info("\n--- ALOJAMIENTOS ---");
        for (int i = 0; i < alojamientos.size(); i++) {
            logger.info(String.format("[%d]\n%s", i + 1, alojamientos.get(i).verDetalles()));
        }
    }

    /**
     * metodo que permite al usuario realizar una reserva.
     * solo los usuarios con rol CLIENTE pueden realizar reservas.
     */
    private void realizarReserva() {
        if (usuarioActivo.getRol() != Roles.CLIENTE) {
            logger.warning("Solo los clientes pueden realizar reservas.");
            return;
        }

        mostrarAlojamientos();

        logger.info("\nSeleccione el número del alojamiento a reservar: ");
        int index = obtenerEntradaNumerica() - 1;

        if (index < 0 || index >= alojamientos.size()) {
            logger.warning("Alojamiento inválido.");
            return;
        }

        logger.info("Fecha inicio (YYYY-MM-DD): ");
        LocalDate inicio = LocalDate.parse(leer.nextLine());

        logger.info("Fecha fin (YYYY-MM-DD): ");
        LocalDate fin = LocalDate.parse(leer.nextLine());

        Reserva reserva = new Reserva(usuarioActivo, alojamientos.get(index), inicio, fin);
        usuarioActivo.realizarReserva(reserva);
        alojamientos.get(index).setOcupado(true);
        logger.info("Reserva realizada con éxito.");
    }

    /**
     * metodo que muestra las reservas realizadas por el usuario activo.
     * solo los clientes pueden ver sus reservas.
     */
    private void verReservas() {
        if (usuarioActivo.getRol() != Roles.CLIENTE) {
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
     * metodo que permite agregar un nuevo alojamiento al sistema.
     * dependiendo del rol del usuario activo, se agrega un departamento o un hotel.
     */
    private void agregarAlojamiento() {
        if (usuarioActivo.getRol() == Roles.ARRENDATARIO) {
            agregarDepartamento();
        } else if (usuarioActivo.getRol() == Roles.ADMIN) {
            agregarHotel();
        } else {
            logger.warning("No tiene permisos para agregar alojamiento.");
        }
    }

    /**
     * metodo que permite agregar un departamento al sistema.
     * solo los arrendatarios pueden agregar departamentos.
     */
    private void agregarDepartamento() {
        if (usuarioActivo.getRol() != Roles.ARRENDATARIO) {
            logger.warning("Solo los arrendatarios pueden agregar departamentos.");
            return;
        }

        logger.info("\nDirección del departamento: ");
        String direccion = leer.nextLine();

        logger.info("Precio por noche: ");
        float precio = leer.nextFloat();
        leer.nextLine();

        logger.info("Descripción del departamento: ");
        String descripcion = leer.nextLine();

        logger.info("Número de habitaciones: ");
        int numHabitaciones = leer.nextInt();

        Departamento nuevoDepartamento = new Departamento(direccion, precio, descripcion, numHabitaciones, usuarioActivo);
        alojamientos.add(nuevoDepartamento);
        logger.info("Departamento agregado con éxito.");
    }

    /**
     * metodo que permite agregar un hotel al sistema.
     * solo los administradores pueden agregar hoteles.
     */
    private void agregarHotel() {
        logger.info("\nDirección del hotel: ");
        String direccion = leer.nextLine();

        logger.info("Precio por noche: ");
        float precio = leer.nextFloat();
        leer.nextLine();

        logger.info("Descripción del hotel: ");
        String descripcion = leer.nextLine();

        logger.info("Número de estrellas: ");
        int numEstrellas = leer.nextInt();
        leer.nextLine();

        Hotel nuevoHotel = new Hotel(direccion, precio, descripcion, numEstrellas, 0);
        alojamientos.add(nuevoHotel);
        logger.info("Hotel agregado con éxito.");
    }

    /**
     * metodo que permite eliminar un alojamiento del sistema.
     */
    private void eliminarAlojamiento() {
        logger.info("\nSeleccione el alojamiento que desea eliminar:");
        mostrarAlojamientos();

        logger.info("\nSeleccione el número del alojamiento a eliminar: ");
        int index = obtenerEntradaNumerica() - 1;

        if (index < 0 || index >= alojamientos.size()) {
            logger.warning("Alojamiento inválido.");
            return;
        }

        alojamientos.remove(index);
        logger.info("Alojamiento eliminado con éxito.");
    }
}
