package org.staygo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuTerminal {

    private List<Alojamiento> alojamientos;
    private List<Usuario> usuarios;
    private Scanner leer;
    private Usuario usuarioActivo;

    public MenuTerminal() {
        alojamientos = new ArrayList<>();
        usuarios = new ArrayList<>();
        leer = new Scanner(System.in);
        cargarDatosIniciales();
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
                System.out.println("\n==== Menú de Inicio ====");
                System.out.println("1. Iniciar sesión");
                System.out.println("2. Registrarse");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = obtenerEntradaNumerica();
                switch (opcion) {
                    case 1 -> iniciarSesion();
                    case 2 -> registrarUsuario();
                    case 3 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } else {
                System.out.println("\n==== Menú Principal ====");
                System.out.println("1. Ver alojamientos disponibles");
                System.out.println("2. Realizar una reserva");
                System.out.println("3. Ver reservas de un usuario");
                System.out.println("4. Agregar alojamiento");
                System.out.println("5. Eliminar alojamiento");
                System.out.println("6. Crear usuario");
                System.out.println("7. Eliminar usuario");
                System.out.println("8. Cerrar sesión");
                System.out.print("Seleccione una opción: ");

                opcion = obtenerEntradaNumerica();
                switch (opcion) {
                    case 1 -> mostrarAlojamientos();
                    case 2 -> realizarReserva();
                    case 3 -> verReservas();
                    case 4 -> agregarAlojamiento();
                    case 5 -> eliminarAlojamiento();
                    case 6 -> crearUsuario();
                    case 7 -> eliminarUsuario();
                    case 8 -> cerrarSesion();
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            }
        } while (opcion != 3);
    }

    private void iniciarSesion() {
        System.out.print("\nIngrese su nombre de usuario: ");
        String nombre = leer.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = leer.nextLine();

        for (Usuario u : usuarios) {
            if (u.iniciarSesion(nombre, contrasena)) {
                usuarioActivo = u;
                System.out.println("Bienvenido, " + usuarioActivo.getNombre());
                return;
            }
        }

        System.out.println("Usuario o contraseña incorrectos.");
    }

    private void registrarUsuario() {
        System.out.print("\nIngrese su nombre de usuario: ");
        String nombre = leer.nextLine();

        System.out.print("Ingrese la contraseña: ");
        String contrasena = leer.nextLine();

        System.out.print("Seleccione el rol (1. CLIENTE, 2. ARRENDATARIO): ");
        int rolOption = obtenerEntradaNumerica();
        Roles rol = (rolOption == 1) ? Roles.CLIENTE : Roles.ARRENDATARIO;

        Long idUsuario = (long) (usuarios.size() + 1);
        Usuario nuevoUsuario = new Usuario(idUsuario, nombre, contrasena, rol);
        usuarios.add(nuevoUsuario);
        System.out.println("Usuario registrado con éxito.");
    }

    private void cerrarSesion() {
        usuarioActivo = null;
        System.out.println("Has cerrado sesión.");
    }

    private int obtenerEntradaNumerica() {
        while (!leer.hasNextInt()) {
            leer.nextLine();
            System.out.print("Por favor, ingrese un número válido: ");
        }
        return leer.nextInt();
    }

    private void mostrarAlojamientos() {
        System.out.println("\n--- ALOJAMIENTOS ---");
        for (int i = 0; i < alojamientos.size(); i++) {
            System.out.println("\n[" + (i + 1) + "]\n" + alojamientos.get(i).verDetalles());
        }
    }

    private void realizarReserva() {
        if (usuarioActivo.getRol() != Roles.CLIENTE) {
            System.out.println("Solo los clientes pueden realizar reservas.");
            return;
        }

        System.out.print("\nSeleccione el número del alojamiento a reservar: ");
        int index = obtenerEntradaNumerica() - 1;

        if (index < 0 || index >= alojamientos.size()) {
            System.out.println("Alojamiento inválido.");
            return;
        }

        System.out.print("Fecha inicio (YYYY-MM-DD): ");
        LocalDate inicio = LocalDate.parse(leer.nextLine());

        System.out.print("Fecha fin (YYYY-MM-DD): ");
        LocalDate fin = LocalDate.parse(leer.nextLine());

        Reserva reserva = new Reserva(usuarioActivo, alojamientos.get(index), inicio, fin);
        usuarioActivo.realizarReserva(reserva);
        alojamientos.get(index).setOcupado(true);
        System.out.println("Reserva realizada con éxito.");
    }

    private void verReservas() {
        if (usuarioActivo.getRol() != Roles.CLIENTE) {
            System.out.println("Solo los clientes pueden ver sus reservas.");
            return;
        }

        List<Reserva> reservas = usuarioActivo.obtenerReservas();

        if (reservas.isEmpty()) {
            System.out.println("Este usuario no tiene reservas.");
        } else {
            System.out.println("\n--- RESERVAS ---");
            for (Reserva r : reservas) {
                System.out.println(r.obtenerDetallesReserva());
            }
        }
    }



    private void agregarAlojamiento() {
        if (usuarioActivo.getRol() == Roles.ARRENDATARIO) {
            agregarDepartamento();
        } else if (usuarioActivo.getRol() == Roles.ADMIN) {
            agregarHotel();
        } else {
            System.out.println("No tiene permisos para agregar alojamiento.");
        }
    }

    private void agregarDepartamento() {
        if (usuarioActivo.getRol() != Roles.ARRENDATARIO) {
            System.out.println("Solo los arrendatarios pueden agregar departamentos.");
            return;
        }

        System.out.print("\nDirección del departamento: ");
        String direccion = leer.nextLine();

        System.out.print("Precio por noche: ");
        float precio = leer.nextFloat();
        leer.nextLine(); // Limpiar buffer

        System.out.print("Descripción del departamento: ");
        String descripcion = leer.nextLine();

        System.out.print("Número de habitaciones: ");
        int numHabitaciones = leer.nextInt();

        System.out.print("¿Es el departamento moderno (true/false)? ");
        boolean moderno = leer.nextBoolean();
        leer.nextLine(); // Limpiar buffer

        Departamento nuevoDepartamento = new Departamento(direccion, precio, descripcion, numHabitaciones, moderno, usuarioActivo);  // Pasar el usuarioActivo como dueño
        alojamientos.add(nuevoDepartamento);
        System.out.println("Departamento agregado con éxito.");
    }

    private void agregarHotel() {
        System.out.print("\nDirección del hotel: ");
        String direccion = leer.nextLine();

        System.out.print("Precio por noche: ");
        float precio = leer.nextFloat();
        leer.nextLine();

        System.out.print("Descripción del hotel: ");
        String descripcion = leer.nextLine();

        System.out.print("Número de estrellas: ");
        int numEstrellas = leer.nextInt();
        leer.nextLine();

        Hotel nuevoHotel = new Hotel(direccion, precio, descripcion, numEstrellas, 0);
        alojamientos.add(nuevoHotel);
        System.out.println("Hotel agregado con éxito.");
    }

    private void eliminarAlojamiento() {
        System.out.println("\nSeleccione el alojamiento que desea eliminar:");
        mostrarAlojamientos();

        System.out.print("\nSeleccione el número del alojamiento a eliminar: ");
        int index = obtenerEntradaNumerica() - 1;

        if (index < 0 || index >= alojamientos.size()) {
            System.out.println("Alojamiento inválido.");
            return;
        }

        alojamientos.remove(index);
        System.out.println("Alojamiento eliminado con éxito.");
    }

    private void crearUsuario() {
        if (usuarioActivo.getRol() != Roles.ADMIN) {
            System.out.println("Solo los administradores pueden crear usuarios.");
            return;
        }

        System.out.print("\nIngrese el nombre de usuario: ");
        String nombre = leer.nextLine();

        System.out.print("Ingrese la contraseña: ");
        String contrasena = leer.nextLine();

        System.out.print("Seleccione el rol (1. CLIENTE, 2. ARRENDATARIO, 3. ADMIN): ");
        int rolOption = obtenerEntradaNumerica();
        Roles rol = Roles.values()[rolOption - 1];

        Long idUsuario = (long) (usuarios.size() + 1);
        Usuario nuevoUsuario = new Usuario(idUsuario, nombre, contrasena, rol);
        usuarios.add(nuevoUsuario);
        System.out.println("Usuario creado con éxito.");
    }

    private void eliminarUsuario() {
        if (usuarioActivo.getRol() != Roles.ADMIN) {
            System.out.println("Solo los administradores pueden eliminar usuarios.");
            return;
        }

        System.out.print("\nIngrese el ID del usuario a eliminar: ");
        Long idUsuario = leer.nextLong();
        leer.nextLine();

        Usuario usuario = buscarUsuarioPorId(idUsuario);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        if (usuario.getRol() == Roles.ADMIN) {
            System.out.println("No se puede eliminar al usuario ADMIN.");
            return;
        }

        usuarios.remove(usuario);
        System.out.println("Usuario eliminado con éxito.");
    }

    private Usuario buscarUsuarioPorId(Long idUsuario) {
        for (Usuario u : usuarios) {
            if (u.getId_usuario().equals(idUsuario)) {
                return u;
            }
        }
        return null;
    }
}





