package br.com.murilo.arquiteturaspring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExemploValue {

    @Value("${app.config.variavel}")
    private String variavel;
    // injetando o valor lá do yml aqui nessa variavel


    public void imprimirVariavel(){
        System.out.println(variavel);
    }

}
