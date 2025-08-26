package controller;
import javax.swing.UIManager;

import model.Evento;
import model.Salon;
import view.VentanaPrincipal;

public class ControladorReservas {
    private SistemaReservas modelo;
    private VentanaPrincipal ventanaPrincipal;

    public ControladorReservas() {
        this.modelo = new SistemaReservas(20, 50, 50, 30);
    }

    public void iniciarAplicacion() {
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            
        }
        
        ventanaPrincipal = new VentanaPrincipal(this);
        ventanaPrincipal.setVisible(true);
    }

    public boolean procesarNuevoSalon(Salon salon) {
        return modelo.agregarSalon(salon);
    }

    public boolean procesarNuevoEvento(Evento evento) {
        return modelo.recibirSolicitudEvento(evento);
    }

    public SistemaReservas getModelo() {
        return modelo;
    }

    public VentanaPrincipal getVentanaPrincipal() {
        return ventanaPrincipal;
    }

    public void actualizarEstadisticasVentanaPrincipal() {
        if (ventanaPrincipal != null) {
            ventanaPrincipal.actualizarEstadisticas();
        }
    }
}