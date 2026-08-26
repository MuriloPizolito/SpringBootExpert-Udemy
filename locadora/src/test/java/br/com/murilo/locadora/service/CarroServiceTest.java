package br.com.murilo.locadora.service;

import br.com.murilo.locadora.entity.CarroEntity;
import br.com.murilo.locadora.repository.CarroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @InjectMocks
    CarroService service;

    @Mock // instância mockada do repository
    CarroRepository repository; //se usar o repository diretamente, importando com autowired, aí seria teste integrado

    //simular o comportamento do repository
    @Test
    void deveSalvarUmCarro() {
        CarroEntity carroParaSalvar = new CarroEntity("Sedan", 10.0, 2027);

        CarroEntity carroParaRetornar = new CarroEntity("Sedan", 10.0, 2027);
        carroParaRetornar.setId(1L);

        Mockito.when(repository.save(Mockito.any())).thenReturn(carroParaRetornar);

        var carroSalvo = service.salvar(carroParaSalvar);

        assertNotNull(carroSalvo);
        assertEquals("Sedan", carroSalvo.getModelo());

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void deveDarErroAoTentarSalvarCarroComDiariaNegativa() {
        CarroEntity carro = new CarroEntity("Sedan", 0, 2027);

        var erro = catchThrowable(() -> service.salvar(carro));
        assertThat(erro).isInstanceOf(IllegalArgumentException.class);

        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveAtualizarUmCarro() {
        CarroEntity carro = new CarroEntity("Sedan", 0, 2027);


    }


}