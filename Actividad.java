import java.util.*;

/*
 * Clase 3: Actividad (Modelo) — Soporte sin commits asignados
 */
public class Actividad {
    private String nombre;
    private String tipo;
    private String horaInicio;
    private String horaFin;
    private double costoEstimado;

    public Actividad(String nombre, String tipo, String horaInicio, String horaFin, double costoEstimado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.costoEstimado = costoEstimado;
    }
    public int calcularDuracion() {
        try {
            String[] hi = horaInicio.split(":");
            String[] hf = horaFin.split(":");
            int minutosIni = Integer.parseInt(hi[0]) * 60 + Integer.parseInt(hi[1]);
            int minutosFin = Integer.parseInt(hf[0]) * 60 + Integer.parseInt(hf[1]);
            return Math.max(0, minutosFin - minutosIni);
        } catch (Exception e) {
            return 0;
        }
    }
}
