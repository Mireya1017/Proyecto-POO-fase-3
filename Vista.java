import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Vista {
    private final Scanner sc = new Scanner(System.in);
    private final ControladorSistema controlador = new ControladorSistema();

    public static void main(String[] args) { new Vista().iniciar(); }

    private void iniciar() {
        int op;
        do {
            mostrarMenu();
            String line = sc.nextLine();
            if (line.isBlank()) continue;
            try { op = Integer.parseInt(line); } catch (Exception e) { op = -1; }
            switch (op) {
                case 1 -> registrarUsuario();
                case 2 -> iniciarSesion();
                case 3 -> crearViaje();
                case 4 -> gestionarActividades();
                case 5 -> calcularDuracion();
                case 6 -> calcularPresupuesto();
                case 7 -> generarResumen();
                case 9 -> mostrarResumenRapido();
                case 0 -> { System.out.println("Saliendo..."); return; }
                default -> System.out.println("Opción inválida.");
            }
        } while (true);
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
        System.out.println("8. Recomendaciones de viajes");
        System.out.println("9. Resumen rápido de viajes del usuario");
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
        try {
            System.out.print("Destino: "); String d = sc.nextLine();
            System.out.print("Inicio (YYYY-MM-DD): "); LocalDate i = LocalDate.parse(sc.nextLine());
            System.out.print("Fin (YYYY-MM-DD): "); LocalDate f = LocalDate.parse(sc.nextLine());
            System.out.print("Presupuesto: "); double p = Double.parseDouble(sc.nextLine());
            System.out.print("Personas: "); int pe = Integer.parseInt(sc.nextLine());
            controlador.crearViajeParaActual(new Viaje(d, i, f, p, pe));
            System.out.println("Viaje creado.");
        } catch (Exception e) {
            System.out.println("Entrada inválida. No se creó el viaje.");
        }
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
            try { op = Integer.parseInt(sc.nextLine()); } catch (Exception e) { op = -1; }
            switch (op) {
                case 1 -> { // agregar con validación y sugerencia de huecos
                    Actividad nueva;
                    try { nueva = pedirActividad(); } catch (Exception ex) { System.out.println("Entrada inválida."); break; }
                    boolean ok = controlador.agregarActividadAViajeV2(d, nueva);
                    if (ok) {
                        System.out.println("Actividad agregada correctamente. Itinerario ordenado.");
                    } else {
                        System.out.println("Conflicto de horario o destino no encontrado. ¿Ver sugerencias de huecos? (s/n)");
                        String r = sc.nextLine().trim().toLowerCase();
                        if (r.equals("s")) {
                            try {
                                System.out.print("Duración deseada en minutos: ");
                                int dur = Integer.parseInt(sc.nextLine());
                                // buscar viaje
                                Viaje v = controlador.getUsuarioActual().getViajes().stream()
                                        .filter(x -> x.getNombreDestino().equalsIgnoreCase(d)).findFirst().orElse(null);
                                if (v == null) { System.out.println("Viaje no encontrado."); break; }
                                List<String> huecos = controlador.sugerirHuecosPara(v, dur);
                                if (huecos.isEmpty()) System.out.println("No se encontraron huecos disponibles.");
                                else {
                                    System.out.println("Huecos sugeridos:");
                                    for (String h : huecos) System.out.println(" - " + h);
                                }
                            } catch (Exception ex) {
                                System.out.println("Entrada inválida.");
                            }
                        } else {
                            System.out.println("No se agregó la actividad.");
                        }
                    }
                }
                case 2 -> {
                    List<Actividad> lista = controlador.verItinerarioDeViajeV2(d);
                    for (int i = 0; i < lista.size(); i++) System.out.println(i + ": " + lista.get(i));
                    System.out.print("Índice: ");
                    int idx = Integer.parseInt(sc.nextLine());
                    if (idx >= 0 && idx < lista.size()) {
                        Actividad nueva = pedirActividad();
                        System.out.println(controlador.editarActividadDeViajeV2(d, idx, nueva) ? "Ok" : "Error");
                    } else {
                        System.out.println("Índice inválido.");
                    }
                }
                case 3 -> {
                    List<Actividad> lista = controlador.verItinerarioDeViajeV2(d);
                    for (int i = 0; i < lista.size(); i++) System.out.println(i + ": " + lista.get(i));
                    System.out.print("Índice: "); int idx = Integer.parseInt(sc.nextLine());
                    System.out.println(controlador.eliminarActividadDeViajeV2(d, idx) ? "Ok" : "Error");
                }
                case 4 -> controlador.verItinerarioDeViajeV2(d).forEach(System.out::println);
                case 0 -> { return; }
                default -> System.out.println("Inválido");
            }
        } while (true);
    }

    private void calcularDuracion() {
        if (controlador.getUsuarioActual() == null) { System.out.println("Inicia sesión."); return; }
        System.out.print("Destino: "); String d = sc.nextLine();
        System.out.println("Duración: " + controlador.calcularDuracionDeViajeV2(d) + " días.");
    }

    private void calcularPresupuesto() {
        if (controlador.getUsuarioActual() == null) { System.out.println("Inicia sesión."); return; }
        System.out.print("Destino: "); String d = sc.nextLine();
        System.out.println("Presupuesto: Q " + controlador.calcularPresupuestoDeViajeV2(d));
    }

    private void generarResumen() {
        if (controlador.getUsuarioActual() == null) { System.out.println("Inicia sesión."); return; }
        System.out.print("Destino: "); String d = sc.nextLine();
        System.out.println(controlador.generarResumenDeViajeV2(d));
    }
    private void mostrarResumenRapido() {
    if (controlador.getUsuarioActual() == null) { System.out.println("Inicia sesión."); return; }
    String correo = controlador.getUsuarioActual().getCorreo();
    System.out.println(controlador.resumenRapidoUsuario(correo));
    }   
    
}
