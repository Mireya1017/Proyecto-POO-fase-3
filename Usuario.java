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
