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

public boolean iniciarSesion(String correo, String contraseña) {
    for (Usuario u : usuariosRegistrados) {
        if (u.autenticarse(correo, contraseña)) {
            usuarioActual = u;
            return true;
            }
        }
        return false;
    }
