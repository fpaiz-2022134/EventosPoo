package view;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.ControladorReservas;
import controller.SistemaReservas;

public class VentanaPrincipal extends JFrame {
    private JMenuBar menuBar;
    private JPanel panelPrincipal;
    private ControladorReservas controlador;
    private JLabel lblEstadisticas;

    public VentanaPrincipal(ControladorReservas controlador) {
        this.controlador = controlador;
        inicializarComponentes();
        configurarMenu();
        setTitle("Sistema de Gestión de Reservas de Salones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout());
        
        JPanel panelBienvenida = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBienvenida.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel lblTitulo = new JLabel("Sistema de Gestión de Reservas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel lblSubtitulo = new JLabel("Centro de Eventos", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 16));
        
        lblEstadisticas = new JLabel("", SwingConstants.CENTER);
        lblEstadisticas.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel lblInstrucciones = new JLabel("Use el menú superior para gestionar salones y eventos", 
                                           SwingConstants.CENTER);
        
        panelBienvenida.add(lblTitulo);
        panelBienvenida.add(lblSubtitulo);
        panelBienvenida.add(lblEstadisticas);
        panelBienvenida.add(lblInstrucciones);
        
        panelPrincipal.add(panelBienvenida, BorderLayout.CENTER);
        add(panelPrincipal);
        
        actualizarEstadisticas();
    }

    private void configurarMenu() {
        menuBar = new JMenuBar();
        
        JMenu menuSalones = new JMenu("Salones");
        JMenuItem itemGestionSalones = new JMenuItem("Gestionar Salones");
        itemGestionSalones.addActionListener(e -> mostrarVentanaGestionSalones());
        menuSalones.add(itemGestionSalones);
        
        JMenu menuEventos = new JMenu("Eventos");
        JMenuItem itemGestionEventos = new JMenuItem("Gestionar Eventos");
        itemGestionEventos.addActionListener(e -> mostrarVentanaGestionEventos());
        menuEventos.add(itemGestionEventos);
        
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemEstadisticas = new JMenuItem("Actualizar Estadísticas");
        itemEstadisticas.addActionListener(e -> actualizarEstadisticas());
        menuReportes.add(itemEstadisticas);
        
        menuBar.add(menuSalones);
        menuBar.add(menuEventos);
        menuBar.add(menuReportes);
        
        setJMenuBar(menuBar);
    }

    private void mostrarVentanaGestionSalones() {
        new VentanaGestionSalones(controlador).setVisible(true);
    }

    private void mostrarVentanaGestionEventos() {
        new VentanaGestionEventos(controlador).setVisible(true);
    }

    public void actualizarEstadisticas() {
        SistemaReservas modelo = controlador.getModelo();
        String estadisticas = String.format(
            "Estadísticas: %d salones, %d reservas confirmadas, %d eventos en espera - Ingresos: $%.2f",
            modelo.getContadorSalones(),
            modelo.getContadorReservas(),
            modelo.getContadorListaEspera(),
            modelo.calcularIngresosTotales()
        );
        lblEstadisticas.setText(estadisticas);
    }
}