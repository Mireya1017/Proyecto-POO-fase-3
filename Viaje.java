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
        this.nombreDestino = nombreDestino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
        this.cantidadPersonas = cantidadPersonas;
    }

    public int calcularDuracionDiasV2() {
        if (fechaInicio == null || fechaFin == null) return 0;
        long d = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
        return (int) Math.max(d, 0);
    }

    public double calcularPresupuestoActividadesV2() {
        double total = 0.0;
        for (Actividad a : itinerario) if (a != null) total += a.getCostoEstimado();
        return total;
    }

    public boolean agregarActividadV2(Actividad act) { return itinerario.add(act); }
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

    public List<Actividad> verItinerarioInmutableV2() { return Collections.unmodifiableList(itinerario); }

    public String generarResumenV2() {
        StringBuilder sb = new StringBuilder();
        sb.append("Destino: ").append(nombreDestino).append("\n");
        sb.append("Duración (días): ").append(calcularDuracionDiasV2()).append("\n");
        sb.append("Presupuesto total: Q ").append(calcularPresupuestoActividadesV2()).append("\n");
        for (int i = 0; i < itinerario.size(); i++) {
            Actividad a = itinerario.get(i);
            sb.append(i).append(": ").append(a.toString()).append("\n");
        }
        return sb.toString();
    }

    public String getNombreDestino() { return nombreDestino; }
}
