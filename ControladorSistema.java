import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ControladorSistema {
    private List<Usuario> usuariosRegistrados = new ArrayList<>();
    private Usuario usuarioActual;

    public void registrarUsuario(Usuario usuario) {
        if (usuario == null) return;
        boolean existe = usuariosRegistrados.stream()
            .anyMatch(u -> u.getCorreo().equalsIgnoreCase(usuario.getCorreo()));
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
                return true; // La vista realizará setters/ediciones específicas
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
                        + " | PresupuestoActividades: Q " + v.calcularPresupuestoTotal();
            }
        }
        return "Viaje no encontrado.";
    }

    public Usuario getUsuarioActual() { return usuarioActual; }
    public void setUsuarioActual(Usuario u) { this.usuarioActual = u; }

    // --- Actividades / Viaje (APIs usadas por la Vista) ---

    public boolean agregarActividadAViajeV2(String nombreDestino, Actividad a) {
        if (usuarioActual == null || nombreDestino == null || a == null) return false;
        Optional<Viaje> opt = usuarioActual.getViajes().stream()
                .filter(v -> v.getNombreDestino().equalsIgnoreCase(nombreDestino))
                .findFirst();
        if (opt.isEmpty()) return false;
        Viaje v = opt.get();
        return v.agregarActividadSeguro(a);
    }

    public List<Actividad> verItinerarioDeViajeV2(String nombreDestino) {
        if (usuarioActual == null || nombreDestino == null) return Collections.emptyList();
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.getItinerario();
            }
        }
        return Collections.emptyList();
    }

    public boolean editarActividadDeViajeV2(String nombreDestino, int indice, Actividad nueva) {
        if (usuarioActual == null || nombreDestino == null) return false;
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.editarActividadV2(indice, nueva);
            }
        }
        return false;
    }

    public boolean eliminarActividadDeViajeV2(String nombreDestino, int indice) {
        if (usuarioActual == null || nombreDestino == null) return false;
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.eliminarActividadV2(indice);
            }
        }
        return false;
    }

    public int calcularDuracionDeViajeV2(String nombreDestino) {
        if (usuarioActual == null || nombreDestino == null) return 0;
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.calcularDuracion();
            }
        }
        return 0;
    }

    public double calcularPresupuestoDeViajeV2(String nombreDestino) {
        if (usuarioActual == null || nombreDestino == null) return 0.0;
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.calcularPresupuestoTotal();
            }
        }
        return 0.0;
    }

    public String generarResumenDeViajeV2(String nombreDestino) {
        if (usuarioActual == null || nombreDestino == null) return "Inicie sesión.";
        for (Viaje v : usuarioActual.getViajes()) {
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                return v.generarResumenV2();
            }
        }
        return "Viaje no encontrado.";
    }

    // Guardar / cargar estado (simple)
    public boolean guardarEstado(String ruta) {
        try {
            List<String> out = new ArrayList<>();
            out.add("USUARIOS|" + usuariosRegistrados.size());
            for (Usuario u : usuariosRegistrados) {
                out.add("USUARIO|" + u.getNombre() + "|" + u.getCorreo() + "|" + u.getContrasena() + "|" + u.getTipoUsuario());
                List<Viaje> viajes = u.getViajes();
                out.add("VIAJES|" + viajes.size());
                for (Viaje v : viajes) out.addAll(v.toStorageLines());
                out.add("FINUSUARIO");
            }
            Files.write(Path.of(ruta), out, StandardCharsets.UTF_8);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cargarEstado(String ruta) {
        try {
            List<String> lines = Files.readAllLines(Path.of(ruta), StandardCharsets.UTF_8);
            Iterator<String> it = lines.iterator();
            usuariosRegistrados.clear();

            String header = it.hasNext() ? it.next() : null;
            if (header == null || !header.startsWith("USUARIOS|")) return false;
            int nUsuarios = Integer.parseInt(header.split("\\|")[1].trim());

            for (int i = 0; i < nUsuarios; i++) {
                String uline = it.hasNext() ? it.next() : null; // USUARIO|...
                String[] pu = uline.split("\\|");
                Usuario u = new Usuario(pu[1].trim(), pu[2].trim(), pu[3].trim(), pu[4].trim());

                String vline = it.hasNext() ? it.next() : null; // VIAJES|n
                int nViajes = Integer.parseInt(vline.split("\\|")[1].trim());

                for (int j = 0; j < nViajes; j++) {
                    Viaje v = Viaje.fromStorage(it);
                    if (v != null) u.agregarViaje(v);
                }
                // consumir FINUSUARIO
                if (it.hasNext()) it.next();
                usuariosRegistrados.add(u);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportarResumenCSV(Viaje viaje, String rutaCsv) {
        if (viaje == null) return false;
        try {
            List<String> out = new ArrayList<>();
            out.add("Destino,Fecha_inicio,Fecha_fin,Presupuesto,Cant_personas");
            out.add(
                "\"" + viaje.getNombreDestino().replace("\"","\"\"") + "\"," +
                (viaje.getFechaInicio() == null ? "" : viaje.getFechaInicio()) + "," +
                (viaje.getFechaFin() == null ? "" : viaje.getFechaFin()) + "," +
                viaje.getPresupuesto() + "," +
                viaje.getCantidadPersonas()
            );
            out.add("");
            out.add("Actividad,Tipo,Hora_inicio,Hora_fin,Costo");
            for (Actividad a : viaje.getActividades()) out.add(a.toCsvRow());
            Files.write(Path.of(rutaCsv), out, StandardCharsets.UTF_8);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Wrappers útiles
    public boolean agregarActividadValidada(Viaje v, Actividad nueva) {
        if (v == null || nueva == null) return false;
        return v.agregarActividadSeguro(nueva);
    }

    public List<String> sugerirHuecosPara(Viaje v, int duracionMin) {
        if (v == null) return Collections.emptyList();
        return v.sugerirHuecos(duracionMin);
    }

    // Exposición de usuarios (lectura)
    public List<Usuario> getUsuariosRegistrados() { return usuariosRegistrados; }
}
