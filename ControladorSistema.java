import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

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

  
    public boolean iniciarSesion(String correo, String contrasena) {
        for (Usuario u : usuariosRegistrados) {
            if (u.autenticarse(correo, contrasena)) {
                usuarioActual = u;
                return true;
            }
        }
        return false;
    }

    public void crearViajeParaActual(Viaje viaje) {
        if (usuarioActual != null && viaje != null) {
            usuarioActual.agregarViaje(viaje);
            System.out.println("Viaje creado y agregado al usuario actual.");
        } else {
            System.out.println("Debe iniciar sesión antes de crear un viaje.");
        }
    }

    
    public List<Viaje> verViajes() {
        if (usuarioActual == null) return new ArrayList<>();
        return usuarioActual.getViajes();
    }

    public boolean editarViaje(String nombreDestino) {
        if (usuarioActual == null) return false;
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return true;
            }
        }
        return false;
    }

    public boolean eliminarViaje(String nombreDestino) {
        if (usuarioActual == null) return false;
        return usuarioActual.eliminarViaje(nombreDestino);
    }

    public void mostrarPresupuesto(Viaje viaje) {
        if (viaje == null) { System.out.println("Viaje nulo."); return; }
        System.out.println("El presupuesto total estimado es: Q" + viaje.calcularPresupuestoTotal());
    }


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

    public List<Viaje> recomendarViajesPorPresupuesto(double maxPresupuesto) {
        List<Viaje> recomendaciones = new ArrayList<>();
        for (Usuario u : usuariosRegistrados) {
            for (Viaje v : u.getViajes()) {
                if (v.getPresupuesto() <= maxPresupuesto) recomendaciones.add(v);
            }
        }
        return recomendaciones;
    }

    public List<Viaje> recomendarViajesPorDuracion(int maxDias) {
        List<Viaje> recomendaciones = new ArrayList<>();
        for (Usuario u : usuariosRegistrados) {
            for (Viaje v : u.getViajes()) {
                if (v.calcularDuracion() <= maxDias) recomendaciones.add(v);
            }
        }
        return recomendaciones;
    }

    public List<Viaje> recomendarViajes(double maxPresupuesto, int maxDias) {
        List<Viaje> recomendaciones = new ArrayList<>();
        for (Usuario u : usuariosRegistrados) {
            for (Viaje v : u.getViajes()) {
                if (v.getPresupuesto() <= maxPresupuesto && v.calcularDuracion() <= maxDias) {
                    recomendaciones.add(v);
                }
            }
        }
        return recomendaciones;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    public void setUsuarioActual(Usuario u) {
        this.usuarioActual = u;
    }

    public void pruebaDuracion(Viaje viaje) {
        System.out.println("Duración calculada correctamente: " + viaje.calcularDuracion() + " días.");
    }

   
    public boolean agregarActividadAViajeV2(String nombreDestino, Actividad act) {
        if (usuarioActual == null || nombreDestino == null || act == null) return false;
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.agregarActividadSeguro(act);
            }
        }
        return false;
    }

    public List<Actividad> verItinerarioDeViajeV2(String nombreDestino) {
        if (usuarioActual == null) return new ArrayList<>();
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.getItinerario();
            }
        }
        return new ArrayList<>();
    }

    public boolean editarActividadDeViajeV2(String nombreDestino, int indice, Actividad nueva) {
        if (usuarioActual == null) return false;
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.editarActividadV2(indice, nueva);
            }
        }
        return false;
    }

    public boolean eliminarActividadDeViajeV2(String nombreDestino, int indice) {
        if (usuarioActual == null) return false;
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.eliminarActividadV2(indice);
            }
        }
        return false;
    }

    public int calcularDuracionDeViajeV2(String nombreDestino) {
        if (usuarioActual == null) return 0;
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.calcularDuracion();
            }
        }
        return 0;
    }

    public double calcularPresupuestoDeViajeV2(String nombreDestino) {
        if (usuarioActual == null) return 0.0;
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.calcularPresupuestoTotal();
            }
        }
        return 0.0;
    }

    public String generarResumenDeViajeV2(String nombreDestino) {
        if (usuarioActual == null) return "Inicie sesión.";
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.generarResumenV2();
            }
        }
        return "Viaje no encontrado.";
    }

    
    public List<String> sugerirHuecosPara(String nombreDestino, int duracionMin) {
        if (usuarioActual == null) return new ArrayList<>();
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.sugerirHuecos(duracionMin);
            }
        }
        return new ArrayList<>();
    }
  
    public List<String> sugerirHuecosPara(Viaje v, int duracionMin) {
        if (v == null) return new ArrayList<>();
        return v.sugerirHuecos(duracionMin);
    }

    
    public String resumenRapidoUsuario(String correoUsuario) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getCorreo().equalsIgnoreCase(correoUsuario)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Resumen rápido de viajes para ").append(u.getNombre()).append(":\n");
                for (Viaje v : u.getViajes()) {
                    sb.append("- ").append(v.getNombreDestino())
                      .append(" | Duración: ").append(v.calcularDuracion()).append(" días")
                      .append(" | Presupuesto: Q ").append(v.getPresupuesto())
                      .append(" | Actividades: ").append(v.getActividades().size()).append("\n");
                }
                return sb.toString();
            }
        }
        return "Usuario no encontrado.";
    }

    public String resumenRapidoUsuarioPorPresupuesto(String correoUsuario, double maxPresupuesto) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getCorreo().equalsIgnoreCase(correoUsuario)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Resumen rápido de viajes (presupuesto <= Q ").append(maxPresupuesto)
                  .append(") para ").append(u.getNombre()).append(":\n");
                for (Viaje v : u.getViajes()) {
                    if (v.getPresupuesto() <= maxPresupuesto) {
                        sb.append("- ").append(v.getNombreDestino())
                          .append(" | Duración: ").append(v.calcularDuracion()).append(" días")
                          .append(" | Presupuesto: Q ").append(v.getPresupuesto())
                          .append(" | Actividades: ").append(v.getActividades().size()).append("\n");
                    }
                }
                return sb.toString();
            }
        }
        return "Usuario no encontrado o sin viajes dentro del presupuesto.";
    }

    public String resumenRapidoUsuarioPorDuracion(String correoUsuario, int maxDias) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getCorreo().equalsIgnoreCase(correoUsuario)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Resumen rápido de viajes (duración <= ").append(maxDias)
                  .append(" días) para ").append(u.getNombre()).append(":\n");
                for (Viaje v : u.getViajes()) {
                    if (v.calcularDuracion() <= maxDias) {
                        sb.append("- ").append(v.getNombreDestino())
                          .append(" | Duración: ").append(v.calcularDuracion()).append(" días")
                          .append(" | Presupuesto: Q ").append(v.getPresupuesto())
                          .append(" | Actividades: ").append(v.getActividades().size()).append("\n");
                    }
                }
                return sb.toString();
            }
        }
        return "Usuario no encontrado o sin viajes dentro de la duración.";
    }
}
