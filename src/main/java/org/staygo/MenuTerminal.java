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
    private GestorDeDatos gestorDeDatos;
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

    private void cargarDatosIniciales() {
        usuarios.add(new Usuario(1L, "admin", "admin123", Roles.ADMIN));
        usuarios.add(new Usuario(2L, "arrendatario", "arrenda123", Roles.ARRENDATARIO));
        usuarios.add(new Usuario(3L, "cliente", "cliente123", Roles.CLIENTE));
    }

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
                logger.info("2. Buscar alojamientos (por tipo y precio)");
                logger.info("3. Realizar una reserva");
                logger.info("4. Ver reservas de un usuario");
                logger.info("5. Agregar alojamiento");
                logger.info("6. Eliminar alojamiento");
                logger.info("7. Cerrar Sesion");
                logger.info("Seleccione una opcion: ");

                opcion = obtenerEntradaNumerica();
                switch (opcion) {
                    case 1 -> mostrarAlojamientos();
                    case 2 -> buscarAlojamientosFiltrados();
                    case 3 -> realizarReserva();
                    case 4 -> verReservas();
                    case 5 -> agregarAlojamiento();
                    case 6 -> eliminarAlojamiento();
                    case 7 -> cerrarSesion();
                    default -> logger.warning("Opcion invalida. Intente de nuevo");
                }
            }
        } while (opcion != 3 && !(usuarioActivo != null && opcion == 7));
    }

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

    public void registrarUsuario() {
        logger.info("\nIngrese su nombre de usuario: ");
        String nombre = leer.nextLine();

        logger.info("Ingrese la contraseña: ");
        String contrasena = leer.nextLine();

        logger.info("Seleccione el rol (1. CLIENTE, 2. ARRENDATARIO): ");
        int rolOption = obtenerEntradaNumerica();
        Roles rol = (rolOption == 1) ? Roles.CLIENTE : Roles.ARRENDATARIO;

        Long idUsuario = (long) (usuarios.size() + 1);
        Usuario nuevoUsuario = new Usuario(idUsuario, nombre, contrasena, rol);
        usuarios.add(nuevoUsuario);

        gestorDeDatos.guardarUsuarios(usuarios);

        logger.info("Usuario registrado con éxito.");
    }

    private int obtenerEntradaNumerica() {
        while (!leer.hasNextInt()) {
            leer.nextLine();
            logger.info("Por favor, ingrese un número válido: ");
        }
        int num = leer.nextInt();
        leer.nextLine();
        return num;
    }

    private void cerrarSesion() {
        usuarioActivo = null;
        logger.info("Has cerrado sesión.");
    }

    private void mostrarAlojamientos() {
        logger.info("\n--- ALOJAMIENTOS ---");
        for (int i = 0; i < alojamientos.size(); i++) {
            logger.info(String.format("[%d]\n%s", i + 1, alojamientos.get(i).verDetalles()));
        }
    }

    private void buscarAlojamientosFiltrados() {
        logger.info("\nSeleccione el tipo de alojamiento:");
        logger.info("1. Todos");
        logger.info("2. Hotel");
        logger.info("3. Departamento");
        int tipo = obtenerEntradaNumerica();

        logger.info("Ingrese precio minimo: ");
        float precioMin = leer.nextFloat();
        logger.info("Ingrese precio maximo: ");
        float precioMax = leer.nextFloat();
        leer.nextLine();

        List<Alojamiento> resultados = new ArrayList<>();

        for (Alojamiento alojamiento : alojamientos) {
            boolean tipoOk = false;
            switch (tipo) {
                case 1 -> tipoOk = true;
                case 2 -> tipoOk = alojamiento instanceof Hotel;
                case 3 -> tipoOk = alojamiento instanceof Departamento;
                default -> logger.warning("Tipo invalido, mostrando todos");
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

            logger.info("Reserva realizada con éxito.");
        } catch (Exception e) {
            logger.warning("Error al realizar la reserva: " + e.getMessage());
        }
    }

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

    private void agregarAlojamiento() {
        if (usuarioActivo.getRol() == Roles.ARRENDATARIO) {
            agregarDepartamento();
        } else if (usuarioActivo.getRol() == Roles.ADMIN) {
            agregarHotel();
        } else {
            logger.warning("No tiene permisos para agregar alojamiento.");
        }
    }

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
        leer.nextLine();

        Departamento nuevoDepartamento = new Departamento(direccion, precio, descripcion, numHabitaciones, usuarioActivo);
        alojamientos.add(nuevoDepartamento);

        gestorDeDatos.guardarAlojamientos(alojamientos);

        logger.info("Departamento agregado con éxito.");
    }

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

        gestorDeDatos.guardarAlojamientos(alojamientos);

        logger.info("Hotel agregado con éxito.");
    }

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

        gestorDeDatos.guardarAlojamientos(alojamientos);

        logger.info("Alojamiento eliminado con éxito.");
    }
}
