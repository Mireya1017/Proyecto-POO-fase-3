import java.util.*;
import java.time.LocalDate;

public class Vista {
    private final Scanner sc = new Scanner(System.in);
    private final ControladorSistema controlador = new ControladorSistema();

    public void mostrarMenuPrincipal() {
        System.out.println("=== MENÚ PRINCIPAL ===");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Crear nuevo viaje (requiere sesión)");
        System.out.println("4. Ver/Editar/Eliminar viajes (requiere sesión)");
        System.out.println("6. Calcular presupuesto total del viaje");
        System.out.println("7. Generar resumen del viaje");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
        opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    controlador.iniciarSesion();
                    break;
                case 2:
                    controlador.crearViaje();
                    break;
                case 3:
                    controlador.gestionarActividades(controlador.getViajeActual());
                    break;
                case 4:
                    controlador.verViajes(controlador.getUsuarioActual());
                    break;
                case 5:
                    controlador.mostrarDuracionViaje(controlador.getViajeActual());
                    break;
                case 6:
                    controlador.mostrarPresupuesto(controlador.getViajeActual()); // ← AQUÍ VA
                    break;
                case 7:
                    controlador.generarResumen(controlador.getViajeActual());
                    break;
                case 0:
                    System.out.println("Gracias por usar el sistema.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    public static void main(String[] args) {
        Vista vista = new Vista();
        vista.mostrarMenu(); // aquí se ejecuta el menú al correr el programa
    }
}
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
        System.out.println("=== Registro de Usuario ===");
        System.out.print("Nombre: "); String nombre = sc.nextLine().trim();
        System.out.print("Correo: "); String correo = sc.nextLine().trim();
        System.out.print("Contraseña: "); String contraseña = sc.nextLine().trim();
        System.out.print("Tipo de usuario: "); String tipo = sc.nextLine().trim();
        return new Usuario(nombre, correo, contraseña, tipo);
    }

    public boolean flujoLogin() {
        System.out.println("=== Iniciar Sesión ===");
        System.out.print("Correo: "); String correo = sc.nextLine().trim();
        System.out.print("Contraseña: "); String contraseña = sc.nextLine().trim();
        boolean ok = controlador.iniciarSesion(correo, contraseña);
        System.out.println(ok ? "Sesión iniciada correctamente." : "Credenciales inválidas.");
        return ok;
    }

    public Viaje pedirDatosViaje() {
        System.out.println("=== Crear Nuevo Viaje ===");
        System.out.print("Destino: "); String destino = sc.nextLine().trim();
        System.out.print("Fecha inicio (YYYY-MM-DD): "); LocalDate inicio = parseFecha(sc.nextLine().trim());
        System.out.print("Fecha fin (YYYY-MM-DD): "); LocalDate fin = parseFecha(sc.nextLine().trim());
        System.out.print("Presupuesto estimado: "); double presupuesto = parseDouble(sc.nextLine().trim());
        System.out.print("Cantidad de personas: "); int personas = parseInt(sc.nextLine().trim());
        return new Viaje(destino, inicio, fin, presupuesto, personas);
    }

    public Actividad pedirDatosActividad() {
        System.out.println("=== Agregar Actividad ===");
        System.out.print("Nombre: "); String nombre = sc.nextLine().trim();
        System.out.print("Tipo: "); String tipo = sc.nextLine().trim();
        System.out.print("Hora inicio (HH:MM): "); String hi = sc.nextLine().trim();
        System.out.print("Hora fin (HH:MM): "); String hf = sc.nextLine().trim();
        System.out.print("Costo estimado: "); double costo = parseDouble(sc.nextLine().trim());
        return new Actividad(nombre, tipo, hi, hf, costo);
    }

    public void mostrarViajes(List<Viaje> viajes) {
        System.out.println("=== Mis Viajes ===");
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
        System.out.println("=== Itinerario de " + viaje.getNombreDestino() + " ===");
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
                    seguir = false;
                }
            }
            System.out.println("Actividades agregadas.");
        }
    }

    private void flujoGestionViajes() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("=== GESTIÓN DE VIAJES ===");
            System.out.println("1. Ver viajes");
            System.out.println("2. Editar viaje");
            System.out.println("3. Eliminar viaje");
            System.out.println("4. Ver resumen de viaje");
            System.out.println("5. Volver");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();

            if (op.equals("1")) {
                mostrarViajes(controlador.verViajes());
            } else if (op.equals("2")) {
                editarViajeUI();
            } else if (op.equals("3")) {
                System.out.print("Destino a eliminar: ");
                String nombre = sc.nextLine().trim();
                boolean ok = controlador.eliminarViaje(nombre);
                System.out.println(ok ? "Viaje eliminado." : "No se encontró el viaje.");
            } else if (op.equals("4")) {
                System.out.print("Destino para resumen: ");
                String nombre = sc.nextLine().trim();
                Viaje v = controlador.buscarViajePorDestino(nombre);
                if (v != null) {
                    controlador.generarResumen(v);
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    private void editarViajeUI() {
        List<Viaje> lista = controlador.verViajes();
        if (lista.isEmpty()) {
            System.out.println("No hay viajes para editar.");
            return;
        }
        mostrarViajes(lista);
        System.out.print("Escriba el nombre del destino que desea editar: ");
        String nombre = sc.nextLine().trim();
        boolean existe = controlador.editarViaje(nombre);
        if (!existe) {
            System.out.println("No se encontró ese viaje.");
            return;
        }
        Viaje objetivo = null;
        for (Viaje v : lista) {
            if (v.getNombreDestino().equalsIgnoreCase(nombre)) {
                objetivo = v;
            }
        }
        if (objetivo == null) {
            System.out.println("No se pudo cargar el viaje.");
            return;
        }
        System.out.println("¿Qué desea editar?");
        System.out.println("1. Nombre del destino");
        System.out.println("2. Fechas (inicio/fin)");
        System.out.println("3. Presupuesto");
        System.out.println("4. Cantidad de personas");
        System.out.println("5. Itinerario (agregar actividad)");
        System.out.println("6. Calcular duración total del viaje");
case 6:
    controlador.mostrarDuracionViaje(viajeActual);
    break;
        System.out.print("Opción: ");
        String opcion = sc.nextLine().trim();

        if (opcion.equals("1")) {
            System.out.print("Nuevo nombre de destino: ");
            objetivo.setNombreDestino(sc.nextLine().trim());
            System.out.println("Destino actualizado.");
        } else if (opcion.equals("2")) {
            System.out.print("Nueva fecha inicio (YYYY-MM-DD): ");
            objetivo.setFechaInicio(parseFecha(sc.nextLine().trim()));
            System.out.print("Nueva fecha fin (YYYY-MM-DD): ");
            objetivo.setFechaFin(parseFecha(sc.nextLine().trim()));
            System.out.println("Fechas actualizadas.");
        } else if (opcion.equals("3")) {
            System.out.print("Nuevo presupuesto: ");
            objetivo.setPresupuesto(parseDouble(sc.nextLine().trim()));
            System.out.println("Presupuesto actualizado.");
        } else if (opcion.equals("4")) {
            System.out.print("Nueva cantidad de personas: ");
            objetivo.setCantidadPersonas(parseInt(sc.nextLine().trim()));
            System.out.println("Cantidad de personas actualizada.");
        } else if (opcion.equals("5")) {
            Actividad a = pedirDatosActividad();
            objetivo.agregarActividad(a);
            System.out.println("Actividad agregada al itinerario.");
        } else {
            System.out.println("Opción inválida.");
        }
    }

    public static void main(String[] args) {
        Vista vista = new Vista();
        boolean corriendo = true;
        do {
            vista.mostrarMenuPrincipal();
            String opcion = vista.sc.nextLine().trim();

            if (opcion.equals("1")) {
                vista.flujoRegistro();
            } else if (opcion.equals("2")) {
                vista.flujoLogin();
            } else if (opcion.equals("3")) {
                vista.flujoCrearViaje();
            } else if (opcion.equals("4")) {
                vista.flujoGestionViajes();
            } else if (opcion.equals("0")) {
                corriendo = false;
            } else {
                System.out.println("Opción inválida.");
            }
        } while (corriendo);
        System.out.println("¡Hasta pronto!");
    }
}





