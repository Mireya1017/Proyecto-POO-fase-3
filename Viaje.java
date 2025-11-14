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
        for (Actividad a : itinerario) if (a != null) total += a.getCostoEstimado();
        return total;
    }

    public boolean agregarActividadSeguro(Actividad nueva) {
        if (nueva == null) return false;
        for (Actividad a : itinerario) if (a.chocaCon(nueva)) return false;
        itinerario.add(nueva);
        ordenarItinerario();
        return true;
    }

    public void ordenarItinerario() {
        itinerario.sort(Comparator.comparingInt(a -> Actividad.parseHoraMin(a.getHoraInicio())));
    }

    public List<Actividad> getItinerario() { return itinerario; }
    public String getNombreDestino() { return nombreDestino; }
    public double getPresupuesto() { return presupuesto; }
    public List<Actividad> getActividades() { return itinerario; }

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
        int diaIni = 6 * 60;
        int diaFin = 22 * 60;

        List<Actividad> ord = new ArrayList<>(itinerario);
        ord.sort(Comparator.comparingInt(a -> Actividad.parseHoraMin(a.getHoraInicio())));

        int cursor = diaIni;
        for (Actividad a : ord) {
            int ini = Actividad.parseHoraMin(a.getHoraInicio());
            if (ini - cursor >= duracionMin) huecos.add(formatoHueco(cursor, ini));
            cursor = Math.max(cursor, Actividad.parseHoraMin(a.getHoraFin()));
        }
        if (diaFin - cursor >= duracionMin) huecos.add(formatoHueco(cursor, diaFin));
        return huecos;
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