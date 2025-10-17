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
}
