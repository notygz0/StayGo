package org.staygo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuTerminal {

    private List<Alojamiento> alojamientos;
    private List<Usuario> usuarios;

    public MenuTerminal() {
        alojamientos = new ArrayList<>();
        usuarios = new ArrayList<>();
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() { //para testear el menu unicamente, despues borrar
        Departamento dep1 = new Departamento("Av. Central 123", 45000f, "Depto moderno", 3, true);
        Hotel hotel1 = new Hotel("Calle Real 456", 80000f, "Hotel con spa", 5, 20);
        hotel1.setServicios(List.of("WiFi", "Piscina", "Spa", "Desayuno"));

        alojamientos.add(dep1);
        alojamientos.add(hotel1);

        Usuario user1 = new Usuario(1L, "juan", "1234", Roles.CLIENTE);
        usuarios.add(user1);
    }

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n==== Menú Principal ====");
            System.out.println("1. Ver alojamientos disponibles");
            System.out.println("2. Realizar una reserva");
            System.out.println("3. Ver reservas de un usuario");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();
            sc.nextLine(); //sin esto se salta una linea y la siguiente variable recibe una cadena vacia

            switch (opcion) {
                case 1 -> mostrarAlojamientos();
                case 2 -> realizarReserva(sc);
                case 3 -> verReservas(sc);
                case 4 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 4);
    }

    private void mostrarAlojamientos() {
        System.out.println("\n--- ALOJAMIENTOS ---");
        for (int i = 0; i < alojamientos.size(); i++) {
            System.out.println("\n[" + (i + 1) + "]\n" + alojamientos.get(i).verDetalles());
        }
    }

    private void realizarReserva(Scanner sc) {
        System.out.print("\nNombre de usuario: ");
        String nombre = sc.nextLine();
        Usuario usuario = buscarUsuario(nombre);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        mostrarAlojamientos();
        System.out.print("\nSeleccione el número del alojamiento a reservar: ");
        int index = sc.nextInt() - 1;
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

    private void verReservas(Scanner sc) {
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
}

