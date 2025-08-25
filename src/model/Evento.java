package model;

public class Evento {
    private String encargado;
    private String nombreEvento;
    private String tipoEvento;
    private String fecha;
    private String horario;
    private int numeroAsistentes;
    private double deposito;
    private boolean depositoPagado;

    public Evento(String encargado, String nombreEvento, String tipoEvento,
                  String fecha, String horario, int numeroAsistentes,
                  double deposito, boolean depositoPagado) {
        this.encargado = encargado;
        this.nombreEvento = nombreEvento;
        this.tipoEvento = tipoEvento;
        this.fecha = fecha;
        this.horario = horario;
        this.numeroAsistentes = numeroAsistentes;
        this.deposito = deposito;
        this.depositoPagado = depositoPagado;
    }

    // Getters y Setters
    public String getEncargado() { return encargado; }
    public void setEncargado(String encargado) { this.encargado = encargado; }

    public String getNombreEvento() { return nombreEvento; }
    public void setNombreEvento(String nombreEvento) { this.nombreEvento = nombreEvento; }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public int getNumeroAsistentes() { return numeroAsistentes; }
    public void setNumeroAsistentes(int numeroAsistentes) { this.numeroAsistentes = numeroAsistentes; }

    public double getDeposito() { return deposito; }
    public void setDeposito(double deposito) { this.deposito = deposito; }

    public boolean isDepositoPagado() { return depositoPagado; }
    public void setDepositoPagado(boolean depositoPagado) { this.depositoPagado = depositoPagado; }

    // Métodos de negocio
    public boolean cumpleRequisitosBasicos() {
        return encargado != null && !encargado.trim().isEmpty() &&
                nombreEvento != null && !nombreEvento.trim().isEmpty() &&
                numeroAsistentes > 0;
    }

    public boolean tienePagoCompleto() {
        return depositoPagado;
    }

    @Override
    public String toString() {
        return nombreEvento + " - " + encargado + " (" + tipoEvento + ") - " +
                fecha + " " + horario + " - " + numeroAsistentes + " asistentes";
    }
}
