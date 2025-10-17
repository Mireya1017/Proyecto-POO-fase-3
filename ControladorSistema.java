import java.util.*;

/*
Controlador
 */
public class ControladorSistema {
    private List<Usuario> usuariosRegistrados = new ArrayList<>();
    private Usuario usuarioActual;
    
public void registrarUsuario(Usuario usuario) {
        if (usuario == null) return;
        boolean existe = false;
        for (Usuario u : usuariosRegistrados) {
            if (u.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
                existe = true;
            }
        }
        if (!existe) {
            usuariosRegistrados.add(usuario);
            usuario.registrarse();
        } else {
            System.out.println("El correo ya está registrado.");
        }
    }
// iniciar Sesion con valores)
public boolean iniciarSesion(String correo, String contraseña) {
    for (Usuario u : usuariosRegistrados) {
        if (u.autenticarse(correo, contraseña)) {
            usuarioActual = u;
            return true;
            }
        }
        return false;
    }
//Consructor objeto viajes
public void crearViaje(Viaje viaje) {
        if (usuarioActual != null && viaje != null) {
            usuarioActual.agregarViaje(viaje);
            System.out.println("Viaje creado y agregado al usuario actual.");
        } else {
            System.out.println("Debe iniciar sesión antes de crear un viaje.");
        }
    }
 // acciones a objeto viaje
public List<Viaje> verViajes() {
        if (usuarioActual == null) return new ArrayList<>();
        return usuarioActual.getViajes();
    }

    public boolean editarViaje(String nombreDestino) {
        if (usuarioActual == null) return false;
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return true; // Vista aplicará setters concretos
            }
        }
        return false;
    }

    public boolean eliminarViaje(String nombreDestino) {
        if (usuarioActual == null) return false;
        return usuarioActual.eliminarViaje(nombreDestino);
    }

// Info sobre viajes y usuario

 public String obtenerResumenViaje(String nombreDestino) {
        if (usuarioActual == null) return "Inicie sesión.";
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return "Destino: " + v.getNombreDestino()
                        + " | Duración (días): " + v.calcularDuracion()
                        + " | Actividades: " + v.getItinerario().size()
                        + " | PresupuestoActividades: " + v.calcularPresupuestoTotal();
            }
        }
        return "Viaje no encontrado.";
    }

    public Usuario getUsuarioActual() { return usuarioActual; }
    public void setUsuarioActual(Usuario u) { this.usuarioActual = u; }

    public void pruebaDuracion(Viaje viaje) {
        System.out.println("Duración calculada correctamente: " + viaje.calcularDuracionTotal() + " horas.");
    }
    public void gestionarActividades(Viaje viaje) {
    Scanner sc = new Scanner(System.in);
    int opcion;
    do {
        System.out.println("\n--- GESTIÓN DE ACTIVIDADES ---");
        System.out.println("1. Agregar actividad");
        System.out.println("2. Editar actividad");
        System.out.println("3. Eliminar actividad");
        System.out.println("4. Ver actividades");
        System.out.println("0. Salir");
        System.out.print("Elija una opción: ");
        opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Costo: ");
                double costo = sc.nextDouble();
                sc.nextLine();
                System.out.print("Duración (horas): ");
                double duracion = sc.nextDouble();
                sc.nextLine();
                viaje.agregarActividad(new Actividad(nombre, costo, duracion));
                break;
            case 2:
                System.out.print("Nombre de la actividad a editar: ");
                String viejo = sc.nextLine();
                System.out.print("Nuevo nombre: ");
                String nuevoNombre = sc.nextLine();
                System.out.print("Nuevo costo: ");
                double nuevoCosto = sc.nextDouble();
                sc.nextLine();
                System.out.print("Nueva duración: ");
                double nuevaDuracion = sc.nextDouble();
                sc.nextLine();
                viaje.editarActividad(viejo, new Actividad(nuevoNombre, nuevoCosto, nuevaDuracion));
                break;
            case 3:
                System.out.print("Nombre de la actividad a eliminar: ");
                String eliminar = sc.nextLine();
                viaje.eliminarActividad(eliminar);
                break;
            case 4:
                for (Actividad a : viaje.getActividades()) {
                    System.out.println(a);
                }
                break;
        }
    } while (opcion != 0);
}
public void generarResumen(Viaje viaje) {
    System.out.println("\n--- RESUMEN DEL VIAJE ---");
    System.out.println("Destino: " + viaje.getDestino());
    System.out.println("Duración total: " + viaje.calcularDuracionTotal() + " horas");
    System.out.println("Presupuesto total: Q" + viaje.calcularPresupuestoTotal());
    System.out.println("Actividades:");
    if (viaje.getActividades().isEmpty()) {
        System.out.println("No hay actividades registradas.");
    } else {
        for (Actividad a : viaje.getActividades()) {
            System.out.println("- " + a);
        }
    }
}
}
public void mostrarDuracionViaje(Viaje viaje) {
    System.out.println("La duración total del viaje es: " + viaje.calcularDuracionTotal() + " horas.");
