package view;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.ControladorReservas;
import model.Salon;

class VentanaGestionSalones extends JFrame {
    private JTextField txtNumero, txtCapacidad, txtCosto;
    private JComboBox<String> cmbTipo;
    private JButton btnAgregar, btnConsultar;
    private JTable tablaSalones;
    private DefaultTableModel modeloTabla;
    private ControladorReservas controlador;

    public VentanaGestionSalones(ControladorReservas controlador) {
        this.controlador = controlador;
        inicializarComponentes();
        setTitle("Gestión de Salones");
        setSize(700, 500);
        setLocationRelativeTo(null);
        actualizarTabla();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Agregar Nuevo Salón"));
        
        panelForm.add(new JLabel("Número de Salón:"));
        txtNumero = new JTextField();
        panelForm.add(txtNumero);
        
        panelForm.add(new JLabel("Capacidad Máxima:"));
        txtCapacidad = new JTextField();
        panelForm.add(txtCapacidad);
        
        panelForm.add(new JLabel("Costo por Hora:"));
        txtCosto = new JTextField();
        panelForm.add(txtCosto);
        
        panelForm.add(new JLabel("Tipo de Salón:"));
        cmbTipo = new JComboBox<>(new String[]{"Pequeño", "Mediano", "Grande"});
        panelForm.add(cmbTipo);
        
        btnAgregar = new JButton("Agregar Salón");
        btnAgregar.addActionListener(e -> agregarSalon());
        btnConsultar = new JButton("Actualizar Lista");
        btnConsultar.addActionListener(e -> actualizarTabla());
        
        panelForm.add(btnAgregar);
        panelForm.add(btnConsultar);
        
        add(panelForm, BorderLayout.NORTH);
        
        // Tabla de salones
        String[] columnas = {"Número", "Tipo", "Capacidad", "Costo/Hora", "Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaSalones = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaSalones);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Salones Registrados"));
        
        add(scrollPane, BorderLayout.CENTER);
    }

    private void agregarSalon() {
        if (!validarDatos()) {
            return;
        }
        
        try {
            int numero = Integer.parseInt(txtNumero.getText());
            int capacidad = Integer.parseInt(txtCapacidad.getText());
            double costo = Double.parseDouble(txtCosto.getText());
            String tipo = (String) cmbTipo.getSelectedItem();
            
            Salon nuevoSalon = new Salon(numero, capacidad, costo, tipo, true);
            
            if (controlador.procesarNuevoSalon(nuevoSalon)) {
                JOptionPane.showMessageDialog(this, "Salón agregado exitosamente");
                limpiarCampos();
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se pudo agregar el salón");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Verifique que los números sean válidos");
        }
    }

    private boolean validarDatos() {
        if (txtNumero.getText().trim().isEmpty() || 
            txtCapacidad.getText().trim().isEmpty() || 
            txtCosto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtNumero.setText("");
        txtCapacidad.setText("");
        txtCosto.setText("");
        cmbTipo.setSelectedIndex(0);
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        Salon[] salones = controlador.getModelo().getSalones();
        int contador = controlador.getModelo().getContadorSalones();
        
        for (int i = 0; i < contador; i++) {
            Salon s = salones[i];
            Object[] fila = {
                s.getNumeroSalon(),
                s.getTipoSalon(),
                s.getCapacidadMaxima(),
                "$" + s.getCostoPorHora(),
                s.isDisponible() ? "Sí" : "No"
            };
            modeloTabla.addRow(fila);
        }
    }
}