package org.staygo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuTerminal {

    private List<Alojamiento> alojamientos;
    private List<Usuario> usuarios;
    private Scanner sc;

    public MenuTerminal() {
        alojamientos = new ArrayList<>();
        usuarios = new ArrayList<>();
        sc = new Scanner(System.in);
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        // Datos iniciales para pruebas
        Departamento dep1 = new Departamento("Av. Central 123", 45000f, "Depto moderno", 3, true);
        Hotel hotel1 = new Hotel("Calle Real 456", 80000f, "Hotel con spa", 5, 20);
        hotel1.setServicios(List.of("WiFi", "Piscina", "Spa", "Desayuno"));

        alojamientos.add(dep1);
        alojamientos.add(hotel1);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n==== Menú Principal ====");
            System.out.println("1. Ver alojamientos disponibles");
            System.out.println("2. Realizar una reserva");
            System.out.println("3. Ver reservas de un usuario");
            System.out.println("4. Agregar alojamiento");
            System.out.println("5. Eliminar alojamiento");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = obtenerEntradaNumerica();
            switch (opcion) {
                case 1 -> mostrarAlojamientos();
                case 2 -> realizarReserva();
                case 3 -> verReservas();
                case 4 -> agregarAlojamiento();
                case 5 -> eliminarAlojamiento();
                case 6 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 6);
    }

    private int obtenerEntradaNumerica() {
        while (!sc.hasNextInt()) {
            sc.nextLine(); // Limpiar el buffer
            System.out.print("Por favor, ingrese un número válido: ");
        }
        return sc.nextInt();
    }

    private void mostrarAlojamientos() {
        System.out.println("\n--- ALOJAMIENTOS ---");
        for (int i = 0; i < alojamientos.size(); i++) {
            System.out.println("\n[" + (i + 1) + "]\n" + alojamientos.get(i).verDetalles());
        }
    }

    private void realizarReserva() {
        System.out.print("\nNombre de usuario: ");
        String nombre = sc.nextLine();
        Usuario usuario = buscarUsuario(nombre);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        mostrarAlojamientos();
        System.out.print("\nSeleccione el número del alojamiento a reservar: ");
        int index = obtenerEntradaNumerica() - 1;
        sc.nextLine();

        if (index < 0 || index >= alojamientos.size()) {
            System.out.println("Alojamiento inválido.");
            return;
        }

        System.out.print("Fecha inicio (YYYY-MM-DD): ");
        LocalDate inicio = LocalDate.parse(sc.nextLine());

        System.out.print("Fecha fin (YYYY-MM-DD): ");
        LocalDate fin = LocalDate.parse(sc.nextLine());

        try {
            Reserva reserva = new Reserva(usuario, alojamientos.get(index), inicio, fin);
            usuario.realizarReserva(reserva);
            alojamientos.get(index).setOcupado(true);
            System.out.println("Reserva realizada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al crear reserva: " + e.getMessage());
        }
    }

    private void verReservas() {
        System.out.print("\nNombre de usuario: ");
        String nombre = sc.nextLine();
        Usuario usuario = buscarUsuario(nombre);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        List<Reserva> reservas = usuario.obtenerReservas();

        if (reservas.isEmpty()) {
            System.out.println("Este usuario no tiene reservas.");
        } else {
            System.out.println("\n--- RESERVAS ---");
            for (Reserva r : reservas) {
                System.out.println(r.obtenerDetallesReserva());
                System.out.println();
            }
        }
    }

    private Usuario buscarUsuario(String nombre) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        return null;
    }

    private void agregarAlojamiento() {
        System.out.println("\n¿Desea agregar un Departamento o un Hotel?");
        System.out.println("1. Departamento");
        System.out.println("2. Hotel");
        System.out.print("Seleccione una opción: ");
        int opcion = obtenerEntradaNumerica();

        if (opcion == 1) {
            agregarDepartamento();
        } else if (opcion == 2) {
            agregarHotel();
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private void agregarDepartamento() {
        System.out.print("\nDirección del departamento: ");
        String direccion = sc.nextLine();

        System.out.print("Precio por noche: ");
        float precio = sc.nextFloat();
        sc.nextLine(); // Limpiar el buffer

        System.out.print("Descripción del departamento: ");
        String descripcion = sc.nextLine();

        System.out.print("Número de habitaciones: ");
        int numHabitaciones = sc.nextInt();

        System.out.print("¿Es el departamento moderno (true/false)? ");
        boolean moderno = sc.nextBoolean();
        sc.nextLine(); // Limpiar el buffer

        Departamento nuevoDepartamento = new Departamento(direccion, precio, descripcion, numHabitaciones, moderno);
        alojamientos.add(nuevoDepartamento);
        System.out.println("Departamento agregado con éxito.");
    }

    private void agregarHotel() {
        System.out.print("\nDirección del hotel: ");
        String direccion = sc.nextLine();

        System.out.print("Precio por noche: ");
        float precio = sc.nextFloat();
        sc.nextLine(); // Limpiar el buffer

        System.out.print("Descripción del hotel: ");
        String descripcion = sc.nextLine();

        System.out.print("Número de estrellas: ");
        int numEstrellas = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer

        System.out.print("Número de habitaciones: ");
        int numHabitaciones = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer

        System.out.print("Servicios disponibles (separados por coma): ");
        String serviciosInput = sc.nextLine();
        List<String> servicios = List.of(serviciosInput.split(","));

        Hotel nuevoHotel = new Hotel(direccion, precio, descripcion, numEstrellas, numHabitaciones);
        nuevoHotel.setServicios(servicios);
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
}


