import java.util.*;
import java.time.LocalDate;

public class Vista {
    private final Scanner sc = new Scanner(System.in);
    private final ControladorSistema controlador = new ControladorSistema();

    public static void main(String[] args) { new Vista().iniciar(); }

    private void iniciar() {
        int op;
        do {
            mostrarMenu();
            op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> registrarUsuario();
                case 2 -> iniciarSesion();
                case 3 -> crearViaje();
                case 4 -> gestionarActividades();
                case 5 -> calcularDuracion();
                case 6 -> calcularPresupuesto();
                case 7 -> generarResumen();
            }
        } while (op != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n=== MENÚ ===");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Crear viaje");
        System.out.println("4. Gestionar actividades");
        System.out.println("5. Calcular duración");
        System.out.println("6. Calcular presupuesto");
        System.out.println("7. Generar resumen");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private void registrarUsuario() {
        System.out.print("Nombre: "); String n = sc.nextLine();
        System.out.print("Correo: "); String c = sc.nextLine();
        System.out.print("Contraseña: "); String p = sc.nextLine();
        System.out.print("Tipo: "); String t = sc.nextLine();
        controlador.registrarUsuario(new Usuario(n, c, p, t));
        System.out.println("Usuario registrado.");
    }

    private void iniciarSesion() {
        System.out.print("Correo: "); String c = sc.nextLine();
        System.out.print("Contraseña: "); String p = sc.nextLine();
        boolean ok = controlador.iniciarSesion(c, p);
        System.out.println(ok ? "Sesión iniciada." : "Error de inicio.");
    }

    private void crearViaje() {
        if (controlador.getUsuarioActual() == null) { System.out.println("Inicia sesión."); return; }
        System.out.print("Destino: "); String d = sc.nextLine();
        System.out.print("Inicio (YYYY-MM-DD): "); LocalDate i = LocalDate.parse(sc.nextLine());
        System.out.print("Fin (YYYY-MM-DD): "); LocalDate f = LocalDate.parse(sc.nextLine());
        System.out.print("Presupuesto: "); double p = Double.parseDouble(sc.nextLine());
        System.out.print("Personas: "); int pe = Integer.parseInt(sc.nextLine());
        controlador.crearViajeParaActual(new Viaje(d, i, f, p, pe));
        System.out.println("Viaje creado.");
    }

    private Actividad pedirActividad() {
        System.out.print("Nombre: "); String n = sc.nextLine();
        System.out.print("Tipo: "); String t = sc.nextLine();
        System.out.print("Inicio (HH:MM): "); String hi = sc.nextLine();
        System.out.print("Fin (HH:MM): "); String hf = sc.nextLine();
        System.out.print("Costo: "); double c = Double.parseDouble(sc.nextLine());
        return new Actividad(n, t, hi, hf, c);
    }

    private void gestionarActividades() {
        if (controlador.getUsuarioActual() == null) { System.out.println("Inicia sesión."); return; }
        System.out.print("Destino: "); String d = sc.nextLine();
        int op;
        do {
            System.out.println("\n1. Agregar 2. Editar 3. Eliminar 4. Ver 0. Salir");
            op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> controlador.agregarActividadAViajeV2(d, pedirActividad());
                case 2 -> {
                    List<Actividad> lista = controlador.verItinerarioDeViajeV2(d);
                    for (int i = 0; i < lista.size(); i++) System.out.println(i + ": " + lista.get(i));
                    System.out.print("Índice: "); int idx = Integer.parseInt(sc.nextLine());
                    if (idx >= 0 && idx < lista.size())
                        controlador.editarActividadDeViajeV2(d, idx, pedirActividad());
                }
                case 3 -> {
                    List<Actividad> lista = controlador.verItinerarioDeViajeV2(d);
                    for (int i = 0; i < lista.size(); i++) System.out.println(i + ": " + lista.get(i));
                    System.out.print("Índice: "); int idx = Integer.parseInt(sc.nextLine());
                    controlador.eliminarActividadDeViajeV2(d, idx);
                }
                case 4 -> controlador.verItinerarioDeViajeV2(d).forEach(System.out::println);
            }
        } while (op != 0);
    }

    private void calcularDuracion() {
        System.out.print("Destino: "); String d = sc.nextLine();
        System.out.println("Duración: " + controlador.calcularDuracionDeViajeV2(d) + " días.");
    }

    private void calcularPresupuesto() {
        System.out.print("Destino: "); String d = sc.nextLine();
        System.out.println("Presupuesto: Q " + controlador.calcularPresupuestoDeViajeV2(d));
    }

    private void generarResumen() {
        System.out.print("Destino: "); String d = sc.nextLine();
        System.out.println(controlador.generarResumenDeViajeV2(d));
    }
}
