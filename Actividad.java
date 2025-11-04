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

    public String toString() {
        return nombre + " | " + tipo + " | " + horaInicio + "-" + horaFin + " | Q " + costoEstimado;
    }
    public static int parseHoraMin(String hhmm) {
    if (hhmm == null || !hhmm.contains(":")) return 0;
    String[] p = hhmm.split(":");
    int h = Integer.parseInt(p[0].trim());
    int m = Integer.parseInt(p[1].trim());
    return h * 60 + m;
}

public String toStorageLine() {
    // Formato estable: nombre | tipo | horaInicio | horaFin | costo
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
    double c = Double.parseDouble(p[4].trim());
    return new Actividad(n, t, hi, hf, c);
}

public String toCsvRow() {
    String safeNombre = "\"" + nombre.replace("\"","\"\"") + "\"";
    String safeTipo = "\"" + tipo.replace("\"","\"\"") + "\"";
    return safeNombre + "," + safeTipo + "," + horaInicio + "," + horaFin + "," + costoEstimado;
}

public boolean chocaCon(Actividad otra) {
    if (otra == null) return false;
    int a1 = parseHoraMin(this.horaInicio);
    int a2 = parseHoraMin(this.horaFin);
    int b1 = parseHoraMin(otra.horaInicio);
    int b2 = parseHoraMin(otra.horaFin);
    return a1 < b2 && b1 < a2; // intersección de intervalos
}
    

}
