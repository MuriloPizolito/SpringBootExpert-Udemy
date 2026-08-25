package br.com.murilo.locadora.model;

import br.com.murilo.locadora.model.exception.ReservaInvalidaException;

public class Reserva {

    private Cliente cliente;
    private Carro carro;
    private int dias;

    public Reserva(Cliente cliente, Carro carro, int dias) {
        if (dias < 1) {
            throw new ReservaInvalidaException("A reserva não pode ter uma quantidade de dias menor que 1.");
        }
        this.cliente = cliente;
        this.carro = carro;
        this.dias = dias;
    }

    public double calcularTotalReserva() {
        return this.carro.calcularValorAluguel(this.dias);
    }

}
