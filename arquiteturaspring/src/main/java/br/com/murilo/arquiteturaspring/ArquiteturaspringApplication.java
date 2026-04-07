package br.com.murilo.arquiteturaspring;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@EnableConfigurationProperties
public class ArquiteturaspringApplication {

    public static void main(String[] args) {
        // SpringApplication.run(ArquiteturaspringApplication.class, args);

        // geralmente não se mexe nessa classe, só se for realmente necessário fazer alguma parametrização.

        SpringApplicationBuilder builder =
                new SpringApplicationBuilder(ArquiteturaspringApplication.class);
        builder.bannerMode(Banner.Mode.OFF); // tira o banner do spring no console
        builder.profiles("producao"); // tem como subir com mais de um perfil ("producao", "homologação");
        // builder.lazyInitialization(true); se não definir aqui vai ser false

        builder.run(args);

        //   contexto da aplicação já iniciada
        ConfigurableApplicationContext applicationContext = builder.context();
        // var produtoRepository = applicationContext.getBean("produtoRepository");

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String applicationName = environment.getProperty("spring.application.name");
        System.out.println("Nome da aplicação: " + applicationName); // dentro do environment consigo ler qualquer propriedade do properties

        ExemploValue value = applicationContext.getBean(ExemploValue.class);
        value.imprimirVariavel();

        AppProperties properties = applicationContext.getBean(AppProperties.class);
        System.out.println(properties.getValor1());

    }


}
