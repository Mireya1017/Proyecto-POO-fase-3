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
