package br.com.murilo.locadora.controller;

import br.com.murilo.locadora.entity.CarroEntity;
import br.com.murilo.locadora.model.exception.EntityNotFoundException;
import br.com.murilo.locadora.service.CarroService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarroController.class) // testes na api
class CarroControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    CarroService carroService;

    @Test
    void deveSalvarUmCarro() throws Exception {
        //cenário
        CarroEntity carro = new CarroEntity(1L, "Honda Civic", 150, 2027);

        when(carroService.salvar(Mockito.any())).thenReturn(carro);

        String json = """
                {
                    "modelo": "Honda Civic",
                    "valorDiaria": 150,
                    "ano": 2027
                }
                """;

        //execução
        //executa a operação na api
        ResultActions result = mvc.perform(
                post("/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        //verificação
        result
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelo").value("Honda Civic"));
    }

    @Test
    void deveObterDetalhesCarro() throws Exception {
        when(carroService.buscarPorId(Mockito.any())).thenReturn(new CarroEntity(
                1L, "Civic", 250, 2028
        ));

        mvc.perform(
                        MockMvcRequestBuilders.get("/carros/1")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelo").value("Civic"))
                .andExpect(jsonPath("$.valorDiaria").value(250))
                .andExpect(jsonPath("$.ano").value(2028));
    }

    @Test
    void deveRetornarNotFoundAoTentarObterDetalhesCarroInexistente() throws Exception {
        when(carroService.buscarPorId(Mockito.any())).thenThrow(EntityNotFoundException.class);

        mvc.perform(
                MockMvcRequestBuilders.get("/carros/1")
        ).andExpect(status().isNotFound());
    }

    @Test
    void deveListarCarros() throws Exception {
        var listagem = List.of(
                new CarroEntity(1L, "Argo", 150, 2025),
                new CarroEntity(2L, "Celta", 80, 2015)
        );

        when(carroService.listarTodos()).thenReturn(listagem);

        mvc.perform(
                        MockMvcRequestBuilders.get("/carros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].modelo").value("Argo"))
                .andExpect(jsonPath("$[1].modelo").value("Celta"));
    }

    @Test
    void deveAtualizarUmCarro() throws Exception {
        when(carroService.atualizar(Mockito.any(), Mockito.any()))
                .thenReturn(new CarroEntity(1L, "Celta", 100, 2025));

        String json = """
                {
                    "modelo": "Celta",
                    "valorDiaria": 100,
                    "ano": 2025
                }
                """;

        mvc.perform(MockMvcRequestBuilders.put("/carros/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoTentarAtualizarCarroInexistente() throws Exception {
        when(carroService.atualizar(Mockito.any(), Mockito.any()))
                .thenThrow(EntityNotFoundException.class);

        String json = """
                {
                    "modelo": "Celta",
                    "valorDiaria": 100,
                    "ano": 2025
                }
                """;

        mvc.perform(
                MockMvcRequestBuilders.put("/carros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarUmCarro() throws Exception {
        doNothing().when(carroService).deletar(any());

        mvc.perform(MockMvcRequestBuilders.delete("/carros/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoDeletarUmCarroInexistente() throws Exception {
        doThrow(EntityNotFoundException.class).when(carroService).deletar(any());

        mvc.perform(MockMvcRequestBuilders.delete("/carros/1"))
                .andExpect(status().isNotFound());
    }


}