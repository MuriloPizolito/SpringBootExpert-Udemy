package br.com.murilo.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    // só com os campos do yaml já funciona a conexão com o banco, isso aqui é um passo a mais, config datasource pool de conexões ...

    @Value("${spring.datasource.url}") // ler do arquivo .yaml
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

//    @Bean
    public DataSource dataSource(){
        // Não recomendado para produção: DriverManagerDataSource abre uma nova conexão a cada uso, o que impacta performance. O Hikari faz o pool de conexões.
        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);

        return ds;
    }

    // por padrão o spring já usa o Hikari, nao precisaria dessas configuracoes
//    @Bean
    public DataSource hikariDataSource(){

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); // tamanho maximo do pool de conexoes liberadas
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); // 600 mil ms - 10 minutos
        config.setConnectionTimeout(100000); // tempo para obter uma conexao, timeout para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); // query simples de teste

        return new HikariDataSource(config);
    }


}
