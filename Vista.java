import java.util.*;
import java.time.LocalDate;


public class Vista {

    private final Scanner sc = new Scanner(System.in);
    private final ControladorSistema controlador = new ControladorSistema();

    public void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Crear nuevo viaje (requiere sesión)");
        System.out.println("4. Ver/Editar/Eliminar viajes (requiere sesión)");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private LocalDate parseFecha(String s) {
        try { return LocalDate.parse(s); } catch (Exception e) { return null; }
    }
    private double parseDouble(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return 0.0; }
    }
    private int parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }