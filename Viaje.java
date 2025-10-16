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
    public int calcularDuracion() {
        if (fechaInicio == null || fechaFin == null) return 0;
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        return (int) Math.max(0, dias);
    }
    public double calcularPresupuestoTotal() {
        double suma = 0.0;
        for (Actividad a : itinerario) suma += a.getCosto();
        return suma;
    }
    public void agregarActividad(Actividad actividad) {
        if (actividad != null) itinerario.add(actividad);
    }
    
     public boolean editarActividad(String nombre) {
        for (Actividad a : itinerario) {
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                a.setTipo("Actualizada");
                return true;
            }
        }
        return false;
    }
    public boolean eliminarActividad(String nombre) {
        Iterator<Actividad> it = itinerario.iterator();
        while (it.hasNext()) {
            Actividad a = it.next();
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                it.remove();
                return true;
            }
        }
        return false;
    }
    public List<Actividad> getItinerario() { 
        return itinerario; 
    }
    public String getNombreDestino() { 
        return nombreDestino; 
    }
    public void setNombreDestino(String nombreDestino) { 
        this.nombreDestino = nombreDestino; 
    }

    public LocalDate getFechaInicio() { 
        return fechaInicio; 
    }
    public void setFechaInicio(LocalDate fechaInicio) { 
        this.fechaInicio = fechaInicio; 
    }

    public LocalDate getFechaFin() { 
        return fechaFin; 
    }
    public void setFechaFin(LocalDate fechaFin) { 
        this.fechaFin = fechaFin; 
    }

    public double getPresupuesto() { 
        return presupuesto; 
    }
    public void setPresupuesto(double presupuesto) { 
        this.presupuesto = presupuesto; 
    }

    public int getCantidadPersonas() { 
        return cantidadPersonas; 
    }
    public void setCantidadPersonas(int cantidadPersonas) { 
        this.cantidadPersonas = cantidadPersonas;
     }
}
public void agregarActividad(Actividad actividad) {
    if (actividades == null) {
        actividades = new ArrayList<>();
    }
    actividades.add(actividad);
    System.out.println("Actividad agregada correctamente: " + actividad.getNombre());
}
public void editarActividad(String nombreAntiguo, Actividad nuevaActividad) {
    for (int i = 0; i < actividades.size(); i++) {
        if (actividades.get(i).getNombre().equalsIgnoreCase(nombreAntiguo)) {
            actividades.set(i, nuevaActividad);
            System.out.println("Actividad actualizada correctamente.");
            return;
        }
    }
    System.out.println("No se encontró la actividad con ese nombre.");
}

public double calcularDuracionTotal() {
    double total = 0;
    if (actividades != null) {
        for (Actividad a : actividades) {
            total += a.getDuracion();
        }
    }
    return total;
}


