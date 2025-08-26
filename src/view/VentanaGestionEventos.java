package view;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel; 
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.ControladorReservas;
import controller.SistemaReservas;
import model.Evento;
import model.Reserva;

class VentanaGestionEventos extends JFrame {
    private JTextField txtEncargado, txtNombreEvento, txtFecha, txtHorario, txtAsistentes, txtDeposito;
    private JComboBox<String> cmbTipoEvento;
    private JCheckBox chkDepositoPagado;
    private JButton btnCrearSolicitud, btnAsignarSalon;
    private JTable tablaReservas, tablaEspera;
    private DefaultTableModel modeloReservas, modeloEspera;
    private ControladorReservas controlador;

    public VentanaGestionEventos(ControladorReservas controlador) {
        this.controlador = controlador;
        inicializarComponentes();
        setTitle("Gestión de Eventos y Reservas");
        setSize(900, 700);
        setLocationRelativeTo(null);
        actualizarTablas();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel panelNuevaSolicitud = crearPanelNuevaSolicitud();
        tabbedPane.addTab("Nueva Solicitud", panelNuevaSolicitud);
        
        JPanel panelReservas = crearPanelReservas();
        tabbedPane.addTab("Reservas Confirmadas", panelReservas);
        
        JPanel panelEspera = crearPanelEspera();
        tabbedPane.addTab("Lista de Espera", panelEspera);
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelNuevaSolicitud() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(8, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Evento"));
        
        panelForm.add(new JLabel("Encargado:"));
        txtEncargado = new JTextField();
        panelForm.add(txtEncargado);
        
        panelForm.add(new JLabel("Nombre del Evento:"));
        txtNombreEvento = new JTextField();
        panelForm.add(txtNombreEvento);
        
        panelForm.add(new JLabel("Tipo de Evento:"));
        cmbTipoEvento = new JComboBox<>(new String[]{"Normal", "VIP"});
        panelForm.add(cmbTipoEvento);
        
        panelForm.add(new JLabel("Fecha (DD/MM/YYYY):"));
        txtFecha = new JTextField();
        panelForm.add(txtFecha);
        
        panelForm.add(new JLabel("Horario:"));
        txtHorario = new JTextField();
        panelForm.add(txtHorario);
        
        panelForm.add(new JLabel("Número de Asistentes:"));
        txtAsistentes = new JTextField();
        panelForm.add(txtAsistentes);
        
        panelForm.add(new JLabel("Depósito:"));
        txtDeposito = new JTextField();
        panelForm.add(txtDeposito);
        
        panelForm.add(new JLabel("Depósito Pagado:"));
        chkDepositoPagado = new JCheckBox();
        panelForm.add(chkDepositoPagado);
        
        panel.add(panelForm, BorderLayout.NORTH);
        
        // Botones
        JPanel panelBotones = new JPanel();
        btnCrearSolicitud = new JButton("Crear Solicitud");
        btnCrearSolicitud.addActionListener(e -> crearSolicitudEvento());
        btnAsignarSalon = new JButton("Asignar Salón");
        btnAsignarSalon.addActionListener(e -> asignarSalonAUltimaSolicitud());
        
        panelBotones.add(btnCrearSolicitud);
        panelBotones.add(btnAsignarSalon);
        
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel crearPanelReservas() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"Evento", "Encargado", "Salón", "Fecha", "Costo"};
        modeloReservas = new DefaultTableModel(columnas, 0);
        tablaReservas = new JTable(modeloReservas);
        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarTablas());
        panel.add(btnActualizar, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel crearPanelEspera() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"Evento", "Encargado", "Tipo", "Fecha", "Asistentes"};
        modeloEspera = new DefaultTableModel(columnas, 0);
        tablaEspera = new JTable(modeloEspera);
        JScrollPane scrollPane2 = new JScrollPane(tablaEspera);
        
        panel.add(scrollPane2, BorderLayout.CENTER);
        
        JButton btnActualizarEspera = new JButton("Actualizar Lista de Espera");
        btnActualizarEspera.addActionListener(e -> actualizarTablas());
        panel.add(btnActualizarEspera, BorderLayout.SOUTH);
        
        return panel;
    }

    private void crearSolicitudEvento() {
        if (!validarFormulario()) {
            return;
        }
        
        try {
            String encargado = txtEncargado.getText().trim();
            String nombreEvento = txtNombreEvento.getText().trim();
            String tipoEvento = (String) cmbTipoEvento.getSelectedItem();
            String fecha = txtFecha.getText().trim();
            String horario = txtHorario.getText().trim();
            int asistentes = Integer.parseInt(txtAsistentes.getText());
            double deposito = Double.parseDouble(txtDeposito.getText());
            boolean depositoPagado = chkDepositoPagado.isSelected();
            
            Evento nuevoEvento = new Evento(encargado, nombreEvento, tipoEvento, 
                                          fecha, horario, asistentes, deposito, depositoPagado);
            
            if (controlador.procesarNuevoEvento(nuevoEvento)) {
                JOptionPane.showMessageDialog(this, "Solicitud de evento creada exitosamente");
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se pudo crear la solicitud");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Verifique que los números sean válidos");
        }
    }

    private void asignarSalonAUltimaSolicitud() {
        SistemaReservas modelo = controlador.getModelo();
        Evento[] eventos = modelo.getEventos();
        int contadorEventos = modelo.getContadorSalones(); // Usar el contador correcto
        
        if (contadorEventos == 0) {
            JOptionPane.showMessageDialog(this, "No hay solicitudes de eventos para procesar");
            return;
        }
        
        // Tomar el último evento ingresado
        Evento ultimoEvento = null;
        for (int i = 0; i < eventos.length; i++) {
            if (eventos[i] != null) {
                ultimoEvento = eventos[i];
            } else {
                break;
            }
        }
        
        if (ultimoEvento != null) {
            String resultado = modelo.asignarSalon(ultimoEvento);
            mostrarMensajeResultado(resultado);
            actualizarTablas();
        }
    }

    private boolean validarFormulario() {
        if (txtEncargado.getText().trim().isEmpty() || 
            txtNombreEvento.getText().trim().isEmpty() ||
            txtFecha.getText().trim().isEmpty() ||
            txtHorario.getText().trim().isEmpty() ||
            txtAsistentes.getText().trim().isEmpty() ||
            txtDeposito.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos");
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        txtEncargado.setText("");
        txtNombreEvento.setText("");
        txtFecha.setText("");
        txtHorario.setText("");
        txtAsistentes.setText("");
        txtDeposito.setText("");
        cmbTipoEvento.setSelectedIndex(0);
        chkDepositoPagado.setSelected(false);
    }

    private void mostrarMensajeResultado(String mensaje) {
        if (mensaje.contains("exitosamente")) {
            JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarTablas() {
        actualizarTablaReservas();
        actualizarTablaEspera();
    }

    private void actualizarTablaReservas() {
        modeloReservas.setRowCount(0);
        Reserva[] reservas = controlador.getModelo().getReservas();
        int contador = controlador.getModelo().getContadorReservas();
        
        for (int i = 0; i < contador; i++) {
            Reserva r = reservas[i];
            Object[] fila = {
                r.getEvento().getNombreEvento(),
                r.getEvento().getEncargado(),
                "Salón " + r.getSalon().getNumeroSalon(),
                r.getEvento().getFecha(),
                "$" + r.getCostoTotal()
            };
            modeloReservas.addRow(fila);
        }
    }

    private void actualizarTablaEspera() {
        modeloEspera.setRowCount(0);
        Evento[] listaEspera = controlador.getModelo().getListaEspera();
        int contador = controlador.getModelo().getContadorListaEspera();
        
        for (int i = 0; i < contador; i++) {
            Evento e = listaEspera[i];
            Object[] fila = {
                e.getNombreEvento(),
                e.getEncargado(),
                e.getTipoEvento(),
                e.getFecha(),
                e.getNumeroAsistentes()
            };
            modeloEspera.addRow(fila);
        }
    }
}