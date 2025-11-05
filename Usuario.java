iimport java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private String nombre;
    private String correo;
    private String contrasena;
    private String tipoUsuario;
    private List<Viaje> viajes = new ArrayList<>();

    public Usuario(String nombre, String correo, String contrasena, String tipoUsuario) {
        this.nombre = nombre == null ? "" : nombre;
        this.correo = correo == null ? "" : correo;
        this.contrasena = contrasena == null ? "" : contrasena;
        this.tipoUsuario = tipoUsuario == null ? "" : tipoUsuario;
    }

    public void registrarse() {
        // placeholder: si se quiere lógica extra (enviar correo, setear fecha), va aquí.
    }

    public boolean autenticarse(String correo, String contrasena) {
        if (correo == null || contrasena == null) return false;
        return this.correo.equalsIgnoreCase(correo.trim()) && this.contrasena.equals(contrasena);
    }

    public void agregarViaje(Viaje v) { if (v != null) viajes.add(v); }
    public boolean eliminarViaje(String nombreDestino) {
        return viajes.removeIf(v -> v.getNombreDestino().equalsIgnoreCase(nombreDestino));
    }

    public List<Viaje> getViajes() { return viajes; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario u = (Usuario) o;
        return Objects.equals(correo, u.correo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(correo);
    }
}
