import javax.swing.SwingUtilities;

import controller.ControladorReservas;

/**
  Developer: Franco Paiz
  Project on GitHub:
  
  https://github.com/fpaiz-2022134/EventosPoo.git
 **/

public class Main {
    /**
     * Método principal que inicia la app
     * @param args 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ControladorReservas controlador = new ControladorReservas();
                controlador.iniciarAplicacion();
                
                System.out.println("=== Sistema de Gestión de Reservas de Salones ===");
                System.out.println("Aplicación iniciada exitosamente");
                System.out.println("Developer: Franco Paiz 25780");
                System.out.println("Curso: CC2008 - Introducción a la Programación Orientada a Objetos");
                
            } catch (Exception e) {
                System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
