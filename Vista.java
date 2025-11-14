import java.util.List;
import java.util.Scanner;

public class Vista {

    private Scanner sc = new Scanner(System.in);
    private Controlador controlador;

    public Vista(Controlador controlador) {
        this.controlador = controlador;
    }

    public void iniciar() {
        int op;
        do {
            mostrarMenu();
            String line = sc.nextLine();
            if (line.isBlank()) continue;

            try { op = Integer.parseInt(line); }
            catch (Exception e) { op = -1; }

            switch (op) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    crearViaje();
                    break;
                case 4:
                    gestionarActividades();
                    break;
                case 5:
                    calcularDuracion();
                    break;
                case 6:
                    calcularPresupuesto();
                    break;
                case 7:
                    generarResumen();
                    break;
                case 8:
                    mostrarRecomendaciones();
                    break;
                case 9:
                    mostrarResumenRapido();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (true);
    }

    private void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Crear viaje");
        System.out.println("4. Gestionar actividades");
        System.out.println("5. Calcular duración del viaje");
        System.out.println("6. Calcular presupuesto");
        System.out.println("7. Generar resumen del viaje");
        System.out.println("8. Mostrar recomendaciones");
        System.out.println("9. Mostrar resumen rápido");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private void registrarUsuario() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Perfil (Estudiante/Familia/Individual): ");
        String perfil = sc.nextLine();
        System.out.print("Presupuesto: ");
        double presupuesto = Double.parseDouble(sc.nextLine());

        controlador.registrarUsuario(nombre, perfil, presupuesto);
        System.out.println("Usuario registrado.");
    }

    private void iniciarSesion() {
        System.out.print("Nombre de usuario: ");
        String nombre = sc.nextLine();

        if (controlador.iniciarSesion(nombre)) {
            System.out.println("Sesión iniciada.");
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private void crearViaje() {
        if (controlador.getUsuarioActual() == null) {
            System.out.println("Inicia sesión primero.");
            return;
        }

        System.out.print("Destino del viaje: ");
        String destino = sc.nextLine();

        controlador.crearViaje(destino);
        System.out.println("Viaje creado.");
    }

    private void gestionarActividades() {
        if (controlador.getUsuarioActual() == null) {
            System.out.println("Inicia sesión.");
            return;
        }

        System.out.print("Destino: ");
        String d = sc.nextLine();

        int op;
        do {
            System.out.println("\n1. Agregar  2. Editar  3. Eliminar  4. Ver  0. Salir");

            try { op = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { op = -1; }

            switch (op) {

                case 1: {
                    Actividad nueva = pedirActividad();
                    if (nueva == null) break;

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

                                List<String> huecos = controlador.sugerirHuecosPara(d, dur);

                                if (huecos.isEmpty()) {
                                    System.out.println("No se encontraron huecos disponibles.");
                                } else {
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
                    break;
                }

                case 2: {
                    List<Actividad> lista = controlador.verItinerarioDeViajeV2(d);
                    for (int i = 0; i < lista.size(); i++)
                        System.out.println(i + ": " + lista.get(i));

                    System.out.print("Índice: ");
                    try {
                        int idx = Integer.parseInt(sc.nextLine());
                        if (idx >= 0 && idx < lista.size()) {
                            Actividad nueva = pedirActividad();
                            System.out.println(
                                controlador.editarActividadDeViajeV2(d, idx, nueva)
                                ? "Ok" : "Error"
                            );
                        } else {
                            System.out.println("Índice inválido.");
                        }
                    } catch (Exception e) {
                        System.out.println("Índice inválido.");
                    }
                    break;
                }

                case 3: {
                    List<Actividad> lista = controlador.verItinerarioDeViajeV2(d);
                    for (int i = 0; i < lista.size(); i++)
                        System.out.println(i + ": " + lista.get(i));

                    System.out.print("Índice: ");
                    try {
                        int idx = Integer.parseInt(sc.nextLine());
                        System.out.println(
                            controlador.eliminarActividadDeViajeV2(d, idx) ? "Ok" : "Error"
                        );
                    } catch (Exception e) {
                        System.out.println("Índice inválido.");
                    }
                    break;
                }

                case 4:
                    controlador.verItinerarioDeViajeV2(d).forEach(System.out::println);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Inválido");
            }

        } while (true);
    }

    private Actividad pedirActividad() {
        try {
            System.out.print("Nombre actividad: ");
            String nom = sc.nextLine();

            System.out.print("Hora inicio (0–23): ");
            int h = Integer.parseInt(sc.nextLine());

            System.out.print("Minuto inicio (0–59): ");
            int m = Integer.parseInt(sc.nextLine());

            System.out.print("Duración (minutos): ");
            int dur = Integer.parseInt(sc.nextLine());

            return new Actividad(nom, h, m, dur);

        } catch (Exception e) {
            System.out.println("Datos inválidos.");
            return null;
        }
    }

    private void calcularDuracion() {
        controlador.calcularDuracion();
    }

    private void calcularPresupuesto() {
        controlador.calcularPresupuesto();
    }

    private void generarResumen() {
        controlador.generarResumen();
    }

    private void mostrarRecomendaciones() {
        controlador.mostrarRecomendaciones();
    }

    private void mostrarResumenRapido() {
        controlador.mostrarResumenRapido();
    }
}