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

public String getNombreDestino() { 
    return nombreDestino;
}

public java.time.LocalDate getFechaInicio() { return fechaInicio; }
public java.time.LocalDate getFechaFin() { return fechaFin; }
public double getPresupuesto() { return presupuesto; }
public int getCantidadPersonas() { return cantidadPersonas; }
public List<Actividad> getActividades() { return itinerario; }

public void agregarActividad(Actividad a) {
    if (a != null) itinerario.add(a);
}


public List<String> toStorageLines() {
    List<String> out = new ArrayList<>();
    out.add("VIAJE|" + nombreDestino);
    out.add("FECHAS|" + fechaInicio.toString() + "|" + fechaFin.toString());
    out.add("PRESUPUESTO|" + presupuesto + "|PERSONAS|" + cantidadPersonas);
    out.add("ACTIVIDADES|" + itinerario.size());
    for (Actividad a : itinerario) out.add(a.toStorageLine());
    out.add("FINVIAJE");
    return out;
}
public static Viaje fromStorage(Iterator<String> it) {
    if (it == null || !it.hasNext()) return null;
    String header = it.next(); 
    if (header == null || !header.startsWith("VIAJE|")) return null;
    String destino = header.substring("VIAJE|".length()).trim();

    String fechas = it.hasNext() ? it.next() : null; 
    String[] pf = fechas.split("\\|");
    java.time.LocalDate ini = java.time.LocalDate.parse(pf[1].trim());
    java.time.LocalDate fin = java.time.LocalDate.parse(pf[2].trim());

    String presLine = it.hasNext() ? it.next() : null; 
    String[] pp = presLine.split("\\|");
    double pres = Double.parseDouble(pp[1].trim());
    int personas = Integer.parseInt(pp[3].trim());

    String actsLine = it.hasNext() ? it.next() : null; 
    int nActs = Integer.parseInt(actsLine.split("\\|")[1].trim());

    Viaje v = new Viaje(destino, ini, fin, pres, personas);
    for (int i = 0; i < nActs; i++) {
        String actLine = it.hasNext() ? it.next() : null;
        Actividad a = Actividad.fromStorageLine(actLine);
        if (a != null) v.agregarActividad(a);
    }

    if (it.hasNext()) {
        String finv = it.next();
        if (!"FINVIAJE".equals(finv)) {
           
        }
    }
    return v;
}

public void ordenarItinerario() {
    itinerario.sort(Comparator.comparingInt(a -> Actividad.parseHoraMin(a.getHoraInicio())));
}

public boolean hayConflicto(Actividad nueva) {
    for (Actividad a : itinerario) {
        if (a.chocaCon(nueva)) return true;
    }
    return false;
}

}


