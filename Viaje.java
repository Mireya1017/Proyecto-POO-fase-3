import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Viaje {
    private String nombreDestino;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<Actividad> itinerario = new ArrayList<>();
    private double presupuesto;
    private int cantidadPersonas;

    public Viaje(String nombreDestino, LocalDate fechaInicio, LocalDate fechaFin, double presupuesto, int cantidadPersonas) {
        this.nombreDestino = nombreDestino == null ? "" : nombreDestino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
        this.cantidadPersonas = cantidadPersonas;
    }

    public int calcularDuracion() {
        if (fechaInicio == null || fechaFin == null) return 0;
        long d = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
        return (int) Math.max(d, 0);
    }

    public double calcularPresupuestoTotal() {
        double total = 0.0;
        for (Actividad a : itinerario) {
            if (a != null) {
                total += a.getCostoEstimado();
            }
        }
        return total;
    }

    public boolean agregarActividadSeguro(Actividad nueva) {
        if (nueva == null) return false;
        for (Actividad a : itinerario) {
            if (a.chocaCon(nueva)) {
                return false;
            }
        }
        itinerario.add(nueva);
        ordenarItinerario();
        return true;
    }

    public void ordenarItinerario() {
        itinerario.sort(Comparator.comparingInt(a -> Actividad.parseHoraMin(a.getHoraInicio())));
    }

    public List<Actividad> getItinerario() {
        return itinerario;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public List<Actividad> getActividades() {
        return itinerario;
    }

    public boolean editarActividadV2(int indice, Actividad nueva) {
        if (indice < 0 || indice >= itinerario.size() || nueva == null) return false;
        itinerario.set(indice, nueva);
        return true;
    }

    public boolean eliminarActividadV2(int indice) {
        if (indice < 0 || indice >= itinerario.size()) return false;
        itinerario.remove(indice);
        return true;
    }

    public List<String> sugerirHuecos(int duracionMin) {
        List<String> huecos = new ArrayList<>();
        int diaIni = 6 * 60;   // 06:00
        int diaFin = 22 * 60;  // 22:00

        List<Actividad> ord = new ArrayList<>(itinerario);
        ord.sort(Comparator.comparingInt(a -> Actividad.parseHoraMin(a.getHoraInicio())));

        int cursor = diaIni;
        for (Actividad a : ord) {
            int ini = Actividad.parseHoraMin(a.getHoraInicio());
            if (ini - cursor >= duracionMin) {
                huecos.add(formatoHueco(cursor, ini));
            }
            cursor = Math.max(cursor, Actividad.parseHoraMin(a.getHoraFin()));
        }

        if (diaFin - cursor >= duracionMin) {
            huecos.add(formatoHueco(cursor, diaFin));
        }
        return huecos;
    }

    public String generarResumenV2() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Resumen del viaje ===\n");
        sb.append("Destino: ").append(nombreDestino).append("\n");
        sb.append("Fechas: ").append(fechaInicio).append(" a ").append(fechaFin).append("\n");
        sb.append("Duración: ").append(calcularDuracion()).append(" días\n");
        sb.append("Presupuesto: Q ").append(presupuesto).append("\n");
        sb.append("Personas: ").append(cantidadPersonas).append("\n\n");

        double totalActividades = calcularPresupuestoTotal();
        sb.append("Costo estimado de actividades: Q ").append(totalActividades).append("\n");
        sb.append("Saldo de presupuesto: Q ").append(presupuesto - totalActividades).append("\n\n");

        sb.append("Itinerario:\n");
        if (itinerario.isEmpty()) {
            sb.append("  (No hay actividades registradas)\n");
        } else {
            for (int i = 0; i < itinerario.size(); i++) {
                Actividad a = itinerario.get(i);
                if (a == null) continue;
                sb.append(i).append(") ")
                  .append(a.getNombre())
                  .append(" [").append(a.getTipo()).append("] ")
                  .append(a.getHoraInicio()).append(" - ").append(a.getHoraFin())
                  .append(" | Q ").append(a.getCostoEstimado())
                  .append("\n");
            }
        }

        return sb.toString();
    }

    private static String formatoHueco(int m1, int m2) {
        return aHHMM(m1) + " - " + aHHMM(m2);
    }

    private static String aHHMM(int m) {
        int h = m / 60;
        int mm = m % 60;
        return String.format("%02d:%02d", h, mm);
    }
}
        int mm = m % 60;
        return String.format("%02d:%02d", h, mm);
    }

}
