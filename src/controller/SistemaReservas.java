package controller;

import model.Evento;
import model.Reserva;
import model.Salon;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    
    //Gettersss
    public Salon[] getSalones() { return salones; }
    public Evento[] getEventos() { return eventos; }
    public Reserva[] getReservas() { return reservas; }
    public Evento[] getListaEspera() { return listaEspera; }
    public int getContadorSalones() { return contadorSalones; }
    public int getContadorReservas() { return contadorReservas; }
    public int getContadorListaEspera() { return contadorListaEspera; }

    public void inicializarSalonesPredeterminados() {
        agregarSalon(new Salon(101, 20, 50.0, "Pequeño", true));
        agregarSalon(new Salon(102, 50, 100.0, "Mediano", true));
        agregarSalon(new Salon(103, 100, 200.0, "Grande", true));
        agregarSalon(new Salon(104, 30, 75.0, "Pequeño", true));
    }

    public boolean agregarSalon(Salon salon) {
        if (contadorSalones < salones.length) {
            salones[contadorSalones] = salon;
            contadorSalones++;
            return true;
        }
        return false;
    }

    public boolean recibirSolicitudEvento(Evento evento) {
        if (contadorEventos < eventos.length) {
            eventos[contadorEventos] = evento;
            contadorEventos++;
            return true;
        }
        return false;
    }

    public Salon buscarSalonDisponible(Evento evento) {
        for (int i = 0; i < contadorSalones; i++) {
            Salon salon = salones[i];
            if (salon.esDisponible() && salon.puedeAlbergarEvento(evento.getNumeroAsistentes()) && salon.esApropiadoParaTipoEvento(evento.getTipoEvento())) {
                return salon;
            }
        }

        return null;
    }

    public String verificarReglasNegocio(Evento evento, Salon salon) {
        //Primera regla, del depósito pagado
        if (!evento.tienePagoCompleto()) {
            return "Tiene que pagar el depósito, es decir, 30% del costo total, para confirmar la reserva";
        }

        //Segunda regla, capacidad del salon
        if (!salon.puedeAlbergarEvento(evento.getNumeroAsistentes())) {
            return "El salon no cuenta con la capacidad suficieten para este número de asistentes";

        }

        //Tercera regla,alon apropiado
        if (!salon.esApropiadoParaTipoEvento(evento.getTipoEvento())) {
            return "Los salones grandes solo pueden ser reservados para los eventos VIP";
        }

        //Cuarta regla, Disponibilidad
        if (!salon.esDisponible()) {
            return "El salón no está disponible para esta fecha";
        }

        return "OK";
    }

    public String asignarSalon(Evento evento){
        Salon salonDisponible = buscarSalonDisponible(evento);
        if(salonDisponible == null){
            agregarAListaEspera(evento);
            return "La reserva no ha sido aprobada: " + " Este evento ha sido agregado a lista de espera";
        }

        String validacion = verificarReglasNegocio(evento, salonDisponible);
        if(!validacion.equals("OK")){
            agregarAListaEspera(evento);
            return "Esta reserva no ha sido aprobada" + validacion + ". Evento agregado a lista de espera";

        }

        Reserva nuevaReserva = new Reserva(evento, salonDisponible, new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        nuevaReserva.confirmarReserva();

        if(contadorReservas < reservas.length){
            reservas[contadorReservas] = nuevaReserva;
            contadorReservas++;
            return "Reserva realizada exitosamente en el salón : " + salonDisponible.getNumeroSalon();

        }

        return "Error: El sistema esta lleno de reservas";
    }

    public boolean agregarAListaEspera(Evento evento) {
        if (contadorListaEspera < listaEspera.length) {
            listaEspera[contadorListaEspera] = evento;
            contadorListaEspera++;
            return true;
        }
        return false;
    }

    public double calcularIngresosTotales() {
        double total = 0;
        for (int i = 0; i < contadorReservas; i++) {
            if (reservas[i].isConfirmada()) {
                total += reservas[i].getCostoTotal();
            }
        }
        return total;
    }

}


