import java.util.*;
import java.time.LocalDate;


public class Vista {

    private final Scanner sc = new Scanner(System.in);
    private final ControladorSistema controlador = new ControladorSistema();

    public void mostrarMenuPrincipal() {
        System.out.println(" MENÚ PRINCIPAL");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Crear nuevo viaje (necesita ingresar sesión)");
        System.out.println("4. Ver/Editar/Eliminar viajes (necesita ingresar sesión)");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private LocalDate parseFecha(String s) {
        try { return LocalDate.parse(s); } catch (Exception e) { return null; }
    }
    private double parseDouble(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return 0.0; }
    }
    private int parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
    public Usuario pedirDatosUsuario() {
        System.out.println("\n=== Registro de Usuario ===");
        System.out.print("Nombre: "); String nombre = sc.nextLine().trim();
        System.out.print("Correo: "); String correo = sc.nextLine().trim();
        System.out.print("Contraseña: "); String contraseña = sc.nextLine().trim();
        System.out.print("Tipo de usuario: "); String tipo = sc.nextLine().trim();
        return new Usuario(nombre, correo, contraseña, tipo);
    }

    public boolean flujoLogin() {
        System.out.println("\n=== Iniciar Sesión ===");
        System.out.print("Correo: "); String correo = sc.nextLine().trim();
        System.out.print("Contraseña: "); String contraseña = sc.nextLine().trim();
        boolean ok = controlador.iniciarSesion(correo, contraseña);
        System.out.println(ok ? "Sesión iniciada correctamente." : "Credenciales inválidas.");
        return ok;
    }

    public Viaje pedirDatosViaje() {
        System.out.println("\n=== Crear Nuevo Viaje ===");
        System.out.print("Destino: "); String destino = sc.nextLine().trim();
        System.out.print("Fecha inicio (YYYY-MM-DD): "); LocalDate inicio = parseFecha(sc.nextLine().trim());
        System.out.print("Fecha fin (YYYY-MM-DD): "); LocalDate fin = parseFecha(sc.nextLine().trim());
        System.out.print("Presupuesto estimado: "); double presupuesto = parseDouble(sc.nextLine().trim());
        System.out.print("Cantidad de personas: "); int personas = parseInt(sc.nextLine().trim());
        return new Viaje(destino, inicio, fin, presupuesto, personas);
    }

    public Actividad pedirDatosActividad() {
        System.out.println("\n=== Agregar Actividad ===");
        System.out.print("Nombre: "); String nombre = sc.nextLine().trim();
        System.out.print("Tipo: "); String tipo = sc.nextLine().trim();
        System.out.print("Hora inicio (HH:MM): "); String hi = sc.nextLine().trim();
        System.out.print("Hora fin (HH:MM): "); String hf = sc.nextLine().trim();
        System.out.print("Costo estimado: "); double costo = parseDouble(sc.nextLine().trim());
        return new Actividad(nombre, tipo, hi, hf, costo);
    }

    public void mostrarViajes(List<Viaje> viajes) {
        System.out.println("\n=== Mis Viajes ===");
        if (viajes == null || viajes.isEmpty()) {
            System.out.println("No hay viajes registrados.");
            return;
        }
        int i = 1;
        for (Viaje v : viajes) {
            System.out.println(i + ". " + v.getNombreDestino() + " | " +
                "Inicio: " + v.getFechaInicio() + " | Fin: " + v.getFechaFin() +
                " | Personas: " + v.getCantidadPersonas() +
                " | PresupuestoActividades: " + v.calcularPresupuestoTotal());
            i++;
        }
    }

    public void mostrarItinerario(Viaje viaje) {
        System.out.println("\n=== Itinerario de " + viaje.getNombreDestino() + " ===");
        List<Actividad> acts = viaje.getItinerario();
        if (acts.isEmpty()) {
            System.out.println("Sin actividades.");
        } else {
            int i = 1;
            for (Actividad a : acts) {
                System.out.println(i + ". " + a.getNombre() + " (" + a.getTipo() + ") "
                        + a.getHoraInicio() + "-" + a.getHoraFin()
                        + " | Q" + a.getCostoEstimado());
                i++;
            }
        }
    }
    public void mostrarViajes(List<Viaje> viajes) {
        System.out.println("\n=== Mis Viajes ===");
        if (viajes == null || viajes.isEmpty()) {
            System.out.println("No hay viajes registrados.");
            return;
        }
        int i = 1;
        for (Viaje v : viajes) {
            System.out.println(i + ". " + v.getNombreDestino() + " | " +
                "Inicio: " + v.getFechaInicio() + " | Fin: " + v.getFechaFin() +
                " | Personas: " + v.getCantidadPersonas() +
                " | PresupuestoActividades: " + v.calcularPresupuestoTotal());
            i++;
        }
    }

    public void mostrarItinerario(Viaje viaje) {
        System.out.println("\n=== Itinerario de " + viaje.getNombreDestino() + " ===");
        List<Actividad> acts = viaje.getItinerario();
        if (acts.isEmpty()) {
            System.out.println("Sin viaje planeado.");
        } else {
            int i = 1;
            for (Actividad a : acts) {
                System.out.println(i + ". " + a.getNombre() + " (" + a.getTipo() + ") "
                        + a.getHoraInicio() + "-" + a.getHoraFin()
                        + " | Q" + a.getCostoEstimado());
                i++;
            }
        }
    }
private void flujoRegistro() {
        Usuario u = pedirDatosUsuario();
        if (u.getNombre().isEmpty() || u.getCorreo().isEmpty() || u.getContraseña().isEmpty()) {
            System.out.println("Datos incompletos. Intente de nuevo.");
            return;
        }
        controlador.registrarUsuario(u);
    }

    private void flujoCrearViaje() {
        Viaje v = pedirDatosViaje();
        if (v.getNombreDestino() == null || v.getNombreDestino().isEmpty()) {
            System.out.println("Destino inválido.");
            return;
        }
        controlador.crearViaje(v);

        System.out.print("¿Desea agregar actividades ahora? (s/n): ");
        String resp = sc.nextLine().trim().toLowerCase();
        if (resp.equals("s")) {
            boolean seguir = true;
            while (seguir) {
                Actividad a = pedirDatosActividad();
                v.agregarActividad(a);
                System.out.print("¿Agregar otra actividad? (s/n): ");
                String r2 = sc.nextLine().trim().toLowerCase();
                if (!r2.equals("s")) {
                    seguir = false; // sin break
                }
            }
            System.out.println("Actividades agregadas.");
        }
    }