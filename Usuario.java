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

    public void registrarse() {
        System.out.println("Usuario registrado: " + nombre + " (" + correo + ")");
    }

    public boolean autenticarse(String correo, String contraseña) {
        return this.correo.equalsIgnoreCase(correo) && this.contraseña.equals(contraseña);
    }

    public void agregarViaje(Viaje viaje) {
        if (viaje != null) viajes.add(viaje);

    public List<Viaje> getViajes() { return viajes; }

    public boolean eliminarViaje(String nombreDestino) {
        Iterator<Viaje> it = viajes.iterator();
        boolean eliminado = false;
        while (it.hasNext()) {
            Viaje v = it.next();
            if (v.getNombreDestino().equalsIgnoreCase(nombreDestino)) {
                it.remove();
                eliminado = true;
            }
        }
        return eliminado;
    }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipo) { this.tipoUsuario = tipo; }
