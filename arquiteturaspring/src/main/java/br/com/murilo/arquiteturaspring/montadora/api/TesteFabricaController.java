package br.com.murilo.arquiteturaspring.montadora.api;

import br.com.murilo.arquiteturaspring.montadora.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carros") // url de acesso
public class TesteFabricaController {

    // fiz assim e funcionou tbm, só mudar a variavel no metodo ligarCarro - nao é uma boa pratica
//    @Autowired
//    private Motor motorAspirado;
//
//    @Autowired
//    private Motor motorEletrico;
//
//    @Autowired
//    private Motor motorTurbo;

    // autowired injeta o objeto aqui - injeção de dependência
    @Autowired
    // @Qualifier("motorEletrico") // passa o nome do bean, para saber qual bean será injetado
    @Aspirado // criando nossa própria annotation
    private Motor motor;

    @PostMapping
    public CarroStatus ligarCarro(@RequestBody Chave chave){
        var carro = new HondaHRV(motor);
        return carro.darIgnicao(chave);
    }



}
