import java.util.Objects;

public class Actividad {
    private String nombre;
    private String tipo;
    private String horaInicio; // formato "HH:MM"
    private String horaFin;    // formato "HH:MM"
    private double costoEstimado;

    public Actividad(String nombre, String tipo, String horaInicio, String horaFin, double costoEstimado) {
        this.nombre = nombre == null ? "" : nombre;
        this.tipo = tipo == null ? "" : tipo;
        this.horaInicio = horaInicio == null ? "00:00" : horaInicio;
        this.horaFin = horaFin == null ? "00:00" : horaFin;
        this.costoEstimado = costoEstimado;
    }

    // Copia con opciones (si argumento es null se mantiene el original)
    public static Actividad copiaEditadaV2(Actividad base, String nombre, String tipo, String inicio, String fin, Double costo) {
        if (base == null) return null;
        return new Actividad(
            nombre != null ? nombre : base.getNombre(),
            tipo != null ? tipo : base.getTipo(),
            inicio != null ? inicio : base.getHoraInicio(),
            fin != null ? fin : base.getHoraFin(),
            costo != null ? costo : base.getCostoEstimado()
        );
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }
    public double getCostoEstimado() { return costoEstimado; }
    public void setCostoEstimado(double costoEstimado) { this.costoEstimado = costoEstimado; }

    @Override
    public String toString() {
        return nombre + " | " + tipo + " | " + horaInicio + "-" + horaFin + " | Q " + costoEstimado;
    }

    // Convierte "HH:MM" a minutos desde medianoche. Validación sencilla.
    public static int parseHoraMin(String hhmm) {
        if (hhmm == null || !hhmm.contains(":")) return 0;
        String[] p = hhmm.split(":");
        try {
            int h = Integer.parseInt(p[0].trim());
            int m = Integer.parseInt(p[1].trim());
            return h * 60 + m;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Formato estable para almacenamiento en líneas
    public String toStorageLine() {
        return nombre + " | " + tipo + " | " + horaInicio + " | " + horaFin + " | " + costoEstimado;
    }

    public static Actividad fromStorageLine(String line) {
        if (line == null) return null;
        String[] p = line.split("\\|");
        if (p.length < 5) return null;
        String n = p[0].trim();
        String t = p[1].trim();
        String hi = p[2].trim();
        String hf = p[3].trim();
        double c;
        try {
            c = Double.parseDouble(p[4].trim());
        } catch (NumberFormatException e) {
            c = 0.0;
        }
        return new Actividad(n, t, hi, hf, c);
    }

    // Para CSV: escapa comillas dobles
    public String toCsvRow() {
        String safeNombre = "\"" + nombre.replace("\"","\"\"") + "\"";
        String safeTipo = "\"" + tipo.replace("\"","\"\"") + "\"";
        return safeNombre + "," + safeTipo + "," + horaInicio + "," + horaFin + "," + costoEstimado;
    }

    // Comprueba si dos actividades se solapan (intervalos semi-abiertos [inicio,fin))
    public boolean chocaCon(Actividad otra) {
        if (otra == null) return false;
        int a1 = parseHoraMin(this.horaInicio);
        int a2 = parseHoraMin(this.horaFin);
        int b1 = parseHoraMin(otra.horaInicio);
        int b2 = parseHoraMin(otra.horaFin);
        return a1 < b2 && b1 < a2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actividad)) return false;
        Actividad a = (Actividad) o;
        return Double.compare(a.costoEstimado, costoEstimado) == 0 &&
               Objects.equals(nombre, a.nombre) &&
               Objects.equals(tipo, a.tipo) &&
               Objects.equals(horaInicio, a.horaInicio) &&
               Objects.equals(horaFin, a.horaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo, horaInicio, horaFin, costoEstimado);
    }
}