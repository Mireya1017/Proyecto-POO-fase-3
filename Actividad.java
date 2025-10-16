import java.util.*;


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
    
     @Override
    public String toString() {
        return nombre + " (" + tipo + ") " + horaInicio + "-" + horaFin + " | Q" + costoEstimado;
    }
    
    public double getCosto() { 
        return costoEstimado; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getTipo() { 
        return tipo; 
    }
    public void setTipo(String tipo) { 
        this.tipo = tipo; 
    }
    public String getHoraInicio() { 
        return horaInicio; 
    }
    public void setHoraInicio(String horaInicio) { 
        this.horaInicio = horaInicio; 
    }

    public String getHoraFin() { 
        return horaFin; 
    }
    public void setHoraFin(String horaFin) { 
        this.horaFin = horaFin; 
    }
    public double getCostoEstimado() { 
        return costoEstimado; 
    }
    public void setCostoEstimado(double costoEstimado) { 
        this.costoEstimado = costoEstimado; 
    }
}

    
        
    
