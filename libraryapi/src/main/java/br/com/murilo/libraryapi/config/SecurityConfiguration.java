package br.com.murilo.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // CSRF:
    // Proteção contra ataques CSRF.
    // Normalmente utilizada em aplicações Web com sessão e formulários.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // deixando como padrao por enquanto
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                // Habilita autenticação Basic Auth
                .httpBasic(Customizer.withDefaults())

                // Configuração de formulário de login
                .formLogin(configurer -> configurer.loginPage("/login"))


                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers("/autores/**").hasRole("ADMIN"); // somente o admin tem acesso
                    authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN"); // os dois podem ter acesso ao endpoint de livros

                    authorize.anyRequest().authenticated();// Todas as requisições precisam de autenticação
                    // tem que ser o último, o que vier depois será ignorado

//                    tem como definir para cada método http
//                    authorize.requestMatchers(HttpMethod.POST,"/autores/**").hasAuthority("CADASTRAR_AUTOR"); // authority - permissão de executar uma tarefa, ação
//                    authorize.requestMatchers(HttpMethod.POST,"/autores/**").hasRole("ADMIN"); // role - é um grupo de usuário
//                    authorize.requestMatchers(HttpMethod.DELETE,"/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT,"/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET,"/autores/**").hasAnyRole("USER", "ADMIN");
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails user1 = User.builder()
                .username("usuario")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password(encoder.encode("321"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }


}
