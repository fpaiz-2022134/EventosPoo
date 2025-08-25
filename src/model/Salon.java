package model;

public class Salon {
    private int numeroSalon;
    private int capacidadMaxima;
    private double costoPorHora;
    private String tipoSalon;
    private boolean disponible;

    public Salon(int numeroSalon, int capacidadMaxima, double costoPorHora, String tipoSalon, boolean disponible) {
        this.numeroSalon = numeroSalon;
        this.capacidadMaxima = capacidadMaxima;
        this.costoPorHora = costoPorHora;
        this.tipoSalon = tipoSalon;
        this.disponible = disponible;
    }

    // Getters y Setters
    public int getNumeroSalon() { return numeroSalon; }
    public void setNumeroSalon(int numeroSalon) { this.numeroSalon = numeroSalon; }

    public int getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }

    public double getCostoPorHora() { return costoPorHora; }
    public void setCostoPorHora(double costoPorHora) { this.costoPorHora = costoPorHora; }

    public String getTipoSalon() { return tipoSalon; }
    public void setTipoSalon(String tipoSalon) { this.tipoSalon = tipoSalon; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    // Métodos de negocio
    public boolean esDisponible() {
        return disponible;
    }

    public boolean puedeAlbergarEvento(int numeroAsistentes) {
        return numeroAsistentes <= capacidadMaxima;
    }

    public boolean esApropiadoParaTipoEvento(String tipoEvento) {
        if (tipoSalon.equals("Grande")) {
            return tipoEvento.equals("VIP");
        }
        return true; // Pequeños y medianos aceptan cualquier tipo
    }

    @Override
    public String toString() {
        return "Salón " + numeroSalon + " (" + tipoSalon + ") - Capacidad: " +
                capacidadMaxima + " - Costo: $" + costoPorHora + "/hora";
    }
}
