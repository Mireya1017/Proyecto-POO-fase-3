import java.util.List;
import java.util.Scanner;

public class Vista {

    private Scanner sc = new Scanner(System.in);
    private ControladorSistema controlador;

    public Vista(ControladorSistema controlador) {
        this.controlador = controlador;
    }

    public static void main(String[] args) {
        ControladorSistema controlador = new ControladorSistema();
        Vista vista = new Vista(controlador);
        vista.iniciar();
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
                case 1: registrarUsuario(); break;
                case 2: iniciarSesion(); break;
                case 3: crearViaje(); break;
                case 4: gestionarActividades(); break;
                case 5: calcularDuracion(); break;
                case 6: calcularPresupuesto(); break;
                case 7: generarResumen(); break;
                case 8: mostrarRecomendaciones(); break;
                case 9: mostrarResumenRapido(); break;
                case 0: System.out.println("Saliendo..."); return;
                default: System.out.println("Opción inválida.");
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
        System.out.println("6. Calcular presupuesto del viaje");
        System.out.println("7. Generar resumen del viaje");
        System.out.println("8. Mostrar recomendaciones");
        System.out.println("9. Mostrar resumen rápido del usuario");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private void registrarUsuario() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = sc.nextLine();
        System.out.print("Tipo de usuario (Estudiante/Familia/Individual): ");
        String tipo = sc.nextLine();

        Usuario u = new Usuario(nombre, correo, contrasena, tipo);
        controlador.registrarUsuario(u);
        System.out.println("Usuario registrado.");
    }

    private void iniciarSesion() {
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        if (controlador.iniciarSesion(correo, pass)) {
            System.out.println("Sesión iniciada correctamente.");
        } else {
            System.out.println("Usuario no encontrado o contraseña incorrecta.");
        }
    }

    private void crearViaje() {
        if (controlador.getUsuarioActual() == null) {
            System.out.println("Inicia sesión primero.");
            return;
        }

        try {
            System.out.print("Destino: ");
            String destino = sc.nextLine();
            System.out.print("Fecha inicio (YYYY-MM-DD): ");
            String inicio = sc.nextLine();
            System.out.print("Fecha fin (YYYY-MM-DD): ");
            String fin = sc.nextLine();
            System.out.print("Presupuesto: ");
            double pres = Double.parseDouble(sc.nextLine());
            System.out.print("Cantidad de personas: ");
            int cant = Integer.parseInt(sc.nextLine());

            Viaje v = new Viaje(destino, java.time.LocalDate.parse(inicio),
                    java.time.LocalDate.parse(fin), pres, cant);

            controlador.crearViajeParaActual(v);
            System.out.println("Viaje creado correctamente.");
        } catch (Exception e) {
            System.out.println("Datos inválidos, no se pudo crear el viaje.");
        }
    }

    private void gestionarActividades() {
        if (controlador.getUsuarioActual() == null) {
            System.out.println("Inicia sesión primero.");
            return;
        }

        System.out.print("Destino del viaje: ");
        String destino = sc.nextLine();

        int op;
        do {
            System.out.println("\n=== Gestión de Actividades ===");
            System.out.println("1. Agregar actividad");
            System.out.println("2. Editar actividad");
            System.out.println("3. Eliminar actividad");
            System.out.println("4. Ver actividades");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            try { op = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { op = -1; }

            switch (op) {
                case 1:
                    Actividad a = pedirActividad();
                    if (a != null) {
                        boolean ok = controlador.agregarActividadAViajeV2(destino, a);
                        if (ok) System.out.println("Actividad agregada correctamente.");
                        else System.out.println("Conflicto de horario o viaje no encontrado.");
                    }
                    break;
                case 2:
                    List<Actividad> lista = controlador.verItinerarioDeViajeV2(destino);
                    mostrarListaActividades(lista);
                    System.out.print("Índice a editar: ");
                    try {
                        int idx = Integer.parseInt(sc.nextLine());
                        if (idx >= 0 && idx < lista.size()) {
                            Actividad nueva = pedirActividad();
                            if (controlador.editarActividadDeViajeV2(destino, idx, nueva)) {
                                System.out.println("Actividad editada correctamente.");
                            } else System.out.println("Error al editar actividad.");
                        } else System.out.println("Índice inválido.");
                    } catch (Exception e) { System.out.println("Índice inválido."); }
                    break;
                case 3:
                    lista = controlador.verItinerarioDeViajeV2(destino);
                    mostrarListaActividades(lista);
                    System.out.print("Índice a eliminar: ");
                    try {
                        int idx = Integer.parseInt(sc.nextLine());
                        if (controlador.eliminarActividadDeViajeV2(destino, idx)) {
                            System.out.println("Actividad eliminada.");
                        } else System.out.println("Error al eliminar.");
                    } catch (Exception e) { System.out.println("Índice inválido."); }
                    break;
                case 4:
                    lista = controlador.verItinerarioDeViajeV2(destino);
                    mostrarListaActividades(lista);
                    break;
                case 0: return;
                default: System.out.println("Opción inválida.");
            }

        } while (true);
    }

    private void mostrarListaActividades(List<Actividad> lista) {
        for (int i = 0; i < lista.size(); i++)
            System.out.println(i + ": " + lista.get(i));
    }

    private Actividad pedirActividad() {
        try {
            System.out.print("Nombre actividad: ");
            String nom = sc.nextLine();
            System.out.print("Hora inicio (HH:MM): ");
            String hi = sc.nextLine();
            System.out.print("Hora fin (HH:MM): ");
            String hf = sc.nextLine();
            System.out.print("Costo estimado: ");
            double c = Double.parseDouble(sc.nextLine());
            return new Actividad(nom, "General", hi, hf, c);
        } catch (Exception e) {
            System.out.println("Datos inválidos.");
            return null;
        }
    }

    private void calcularDuracion() {
        if (controlador.getUsuarioActual() == null) {
            System.out.println("Inicia sesión primero.");
            return;
        }
        System.out.print("Destino del viaje: ");
        String destino = sc.nextLine();
        int dur = controlador.calcularDuracionDeViajeV2(destino);
        System.out.println("Duración del viaje: " + dur + " días.");
    }

    private void calcularPresupuesto() {
        if (controlador.getUsuarioActual() == null) {
            System.out.println("Inicia sesión primero.");
            return;
        }
        System.out.print("Destino del viaje: ");
        String destino = sc.nextLine();
        double pres = controlador.calcularPresupuestoDeViajeV2(destino);
        System.out.println("Presupuesto total del viaje: Q " + pres);
    }

    private void generarResumen() {
        if (controlador.getUsuarioActual() == null) {
            System.out.println("Inicia sesión primero.");
            return;
        }
        System.out.print("Destino del viaje: ");
        String destino = sc.nextLine();
        String res = controlador.generarResumenDeViajeV2(destino);
        System.out.println(res);
    }

    private void mostrarRecomendaciones() {
        try {
            System.out.print("Máximo presupuesto (Q): ");
            double maxP = Double.parseDouble(sc.nextLine());
            System.out.print("Máxima duración (días): ");
            int maxD = Integer.parseInt(sc.nextLine());
            List<Viaje> recs = controlador.recomendarViajes(maxP, maxD);
            System.out.println("Recomendaciones:");
            for (Viaje v : recs) {
                System.out.println("- " + v.getNombreDestino() + " | Duración: " + v.calcularDuracion()
                        + " | Presupuesto: Q " + v.getPresupuesto());
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida.");
        }
    }

    private void mostrarResumenRapido() {
        if (controlador.getUsuarioActual() == null) {
            System.out.println("Inicia sesión primero.");
            return;
        }
        System.out.print("Correo del usuario: ");
        String correo = sc.nextLine();
        String resumen = controlador.resumenRapidoUsuario(correo);
        System.out.println(resumen);
    }

}
