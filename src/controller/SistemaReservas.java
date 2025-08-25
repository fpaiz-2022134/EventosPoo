package controller;

import model.Evento;
import model.Reserva;
import model.Salon;

public class SistemaReservas {

    private Salon[] salones;
    private Evento[] eventos;
    private Reserva[] reservas;
    private Evento[] listaEspera;

    private int contadorSalones;
    private int contadorEventos;
    private int contadorReservas;
    private int contadorListaEspera;

    public SistemaReservas(int capacidadSalones, int capacidadEventos, int capacidadReservas, int capacidadListaEspera) {
        this.salones = new Salon[capacidadSalones];
        this.eventos = new Evento[capacidadEventos];
        this.reservas = new Reserva[capacidadReservas];
        this.listaEspera = new Evento[capacidadListaEspera];

        this.contadorSalones = 0;
        this.contadorEventos = 0;
        this.contadorReservas = 0;
        this.contadorListaEspera = 0;

        inicializarSalonesPredeterminados();


    }

    public void inicializarSalonesPredeterminados(){
        agregarSalon(new Salon(101, 20, 50.0, "Pequeño", true));
        agregarSalon(new Salon(102, 50, 100.0, "Mediano", true));
        agregarSalon(new Salon(103, 100, 200.0, "Grande", true));
        agregarSalon(new Salon(104, 30, 75.0, "Pequeño", true));
    }

    public boolean agregarSalon(Salon salon){
        if(contadorSalones < salones.length){
            salones[contadorSalones] = salon;
            contadorSalones++;
            return true;
        }
        return false;
    }

    public boolean recibirSolicitudEvento(Evento evento){
        if(contadorEventos< eventos.length){
            eventos[contadorEventos] = evento;
            contadorEventos++;
            return true;
        }
        return false;
    }

}
