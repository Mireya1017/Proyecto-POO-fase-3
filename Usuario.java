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
