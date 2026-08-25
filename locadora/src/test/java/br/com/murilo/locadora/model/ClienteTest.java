package br.com.murilo.locadora.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ClienteTest {

    @Test
    void deveCriarClienteComNome(){
        // 1 - Cenário
        var cliente = new Cliente("Maria");

        // 2 - Execução
        String nome = cliente.getNome();

        // 3 - Verificação
        assertNotNull(nome);

        assertThat(nome).isEqualTo("Maria"); //assertJ
        assertThat(nome).isLessThan("Maria5");

        assertTrue(nome.startsWith("M"));
        assertFalse(nome.length() == 100);

        assertThat(nome.length()).isLessThan(100);
        assertThat(nome).contains("Ma");
    }

    @Test
    void devecriarClienteSemNome(){
        var cliente = new Cliente(null);

        var nome = cliente.getNome();

        assertNull(nome);
    }

}
