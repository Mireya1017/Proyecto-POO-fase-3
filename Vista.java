import java.util.List;
import java.util.Scanner;

public class Vista {

    private Scanner sc = new Scanner(System.in);
    private ControladorSistema controlador;

    public Vista(ControladorSistema controlador) {
        this.controlador = controlador;
    }

    public void iniciar() {
        int op;
        do {
            mostrarMenu();
            String line = sc.nextLine();
            if (line.isBlank()) continue;

            try { op = Integer.parseInt(line); }
            catch (Exception e) { op = -1; }

            switch (op) {
                case 0: System.out.println("Saliendo..."); return;
                default: System.out.println("Funcionalidades de ejemplo"); // Simplificado
            }
        } while (true);
    }

    private void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private Actividad pedirActividad() {
        try {
            System.out.print("Nombre actividad: ");
            String nom = sc.nextLine();
            System.out.print("Hora inicio (0-23): ");
            int h = Integer.parseInt(sc.nextLine());
            System.out.print("Minuto inicio (0-59): ");
            int m = Integer.parseInt(sc.nextLine());
            System.out.print("Duración (minutos): ");
            int dur = Integer.parseInt(sc.nextLine());

            String horaInicio = String.format("%02d:%02d", h, m);
            int finMin = h*60 + m + dur;
            String horaFin = String.format("%02d:%02d", finMin/60, finMin%60);

            return new Actividad(nom, "General", horaInicio, horaFin, 0.0);

        } catch (Exception e) {
            System.out.println("Datos inválidos.");
            return null;
        }
    }
}