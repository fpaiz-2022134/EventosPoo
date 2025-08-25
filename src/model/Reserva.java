package model;

public class Reserva {
    private Evento evento;
    private Salon salon;
    private String fechaReserva;
    private boolean confirmada;
    private double costoTotal;

    public Reserva(Evento evento, Salon salon, String fechaReserva) {
        this.evento = evento;
        this.salon = salon;
        this.fechaReserva = fechaReserva;
        this.confirmada = false;
        this.costoTotal = 0.0;
    }

    // Getters y Setters
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }

    public Salon getSalon() { return salon; }
    public void setSalon(Salon salon) { this.salon = salon; }

    public String getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(String fechaReserva) { this.fechaReserva = fechaReserva; }

    public boolean isConfirmada() { return confirmada; }
    public void setConfirmada(boolean confirmada) { this.confirmada = confirmada; }

    public double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }

    // Métodos de negocio
    public void calcularCostoTotal() {
        // Asumimos 4 horas por defecto para el evento
        costoTotal = salon.getCostoPorHora() * 4;
    }

    public void confirmarReserva() {
        confirmada = true;
        salon.setDisponible(false);
        calcularCostoTotal();
    }

    public void cancelarReserva() {
        confirmada = false;
        salon.setDisponible(true);
    }

    @Override
    public String toString() {
        return "Reserva: " + evento.getNombreEvento() + " en Salón " +
                salon.getNumeroSalon() + " - $" + costoTotal;
    }
}
