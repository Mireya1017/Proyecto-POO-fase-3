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

    public Viaje(String nombreDestino, LocalDate fechaInicio, LocalDate fechaFin,
                 double presupuesto, int cantidadPersonas) {
        this.nombreDestino = nombreDestino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
        this.cantidadPersonas = cantidadPersonas;
    }
    public void agregarActividad(Actividad actividad) {
        if (actividad != null) itinerario.add(actividad);
    }
    public int calcularDuracion() {
        if (fechaInicio == null || fechaFin == null) return 0;
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        return (int) Math.max(0, dias);
    }

}
