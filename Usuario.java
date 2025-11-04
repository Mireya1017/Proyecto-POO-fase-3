import java.util.*;

public class Usuario {
    private String nombre;
    private String correo;
    private String contraseña;
    private String tipoUsuario;
    private List<Viaje> viajes = new ArrayList<>();

    public Usuario(String nombre, String correo, String contraseña, String tipoUsuario) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
    }

    public void agregarViaje(Viaje v) { if (v != null) viajes.add(v); }
    public List<Viaje> getViajes() { return viajes; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}
public List<Usuario> getUsuariosRegistrados() { return usuariosRegistrados; }
public Usuario getUsuarioActual() { return usuarioActual; }

public boolean guardarEstado(String ruta) {
    try {
        List<String> out = new ArrayList<>();
        out.add("USUARIOS|" + usuariosRegistrados.size());
        for (Usuario u : usuariosRegistrados) {
            out.add("USUARIO|" + u.getNombre() + "|" + u.getCorreo() + "|" + u.getContraseña() + "|" + u.getTipoUsuario());
            List<Viaje> viajes = u.getViajes();
            out.add("VIAJES|" + viajes.size());
            for (Viaje v : viajes) out.addAll(v.toStorageLines());
            out.add("FINUSUARIO");
        }
        java.nio.file.Files.write(java.nio.file.Path.of(ruta), out, java.nio.charset.StandardCharsets.UTF_8);
        return true;
    } catch (Exception e) {
        return false;
    }
}

public boolean cargarEstado(String ruta) {
    try {
        List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Path.of(ruta), java.nio.charset.StandardCharsets.UTF_8);
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
            viaje.getFechaInicio() + "," +
            viaje.getFechaFin() + "," +
            viaje.getPresupuesto() + "," +
            viaje.getCantidadPersonas()
        );
        out.add("");
        out.add("Actividad,Tipo,Hora_inicio,Hora_fin,Costo");
        for (Actividad a : viaje.getActividades()) out.add(a.toCsvRow());
        java.nio.file.Files.write(java.nio.file.Path.of(rutaCsv), out, java.nio.charset.StandardCharsets.UTF_8);
        return true;
    } catch (Exception e) {
        return false;
    }
}

