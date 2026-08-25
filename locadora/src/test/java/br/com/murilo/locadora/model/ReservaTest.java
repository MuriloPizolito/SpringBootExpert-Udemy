package br.com.murilo.locadora.model;

import br.com.murilo.locadora.model.exception.ReservaInvalidaException;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservaTest {
    //deixando as variaveis globais, para nao repetir em cada teste
    Cliente cliente;
    Carro carro;

    @BeforeEach
        // vai inicializar as variáveis antes de cada execução
    void setUp() {
        cliente = new Cliente("José");
        carro = new Carro("hatch", 50.0);
    }

    @Test
    void deveCriarUmaReserva() {
        // cenario
        var dias = 5;

        // execucao
        var reserva = new Reserva(cliente, carro, dias);

        // verificacao
        Assertions.assertThat(reserva).isNotNull();
    }


    @Test
    void deveDarErroAoCriarUmaReservaComDiasNegativos() {
        // JUnit
        assertThrows(ReservaInvalidaException.class, () -> new Reserva(cliente, carro, 0));
        assertDoesNotThrow(() -> new Reserva(cliente, carro, 1));

        //AssertJ
        var erro = Assertions.catchThrowable(() -> new Reserva(cliente, carro, 0));
        Assertions.assertThat(erro)
                .isInstanceOf(ReservaInvalidaException.class)
                .hasMessage("A reserva não pode ter uma quantidade de dias menor que 1.");
    }


    @Test
    void deveCalcularOTotalDoAluguel() {
        var reserva = new Reserva(cliente, carro, 3);

        var total = reserva.calcularTotalReserva();

        Assertions.assertThat(total).isEqualTo(150.0);

    }


}
