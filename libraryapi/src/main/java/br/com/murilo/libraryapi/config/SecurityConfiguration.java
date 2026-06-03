package br.com.murilo.libraryapi.config;

import br.com.murilo.libraryapi.security.CustomUserDetailsService;
import br.com.murilo.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) // para mudar os authorize.requestMatchers (regras de acesso) para dentro do controller
public class SecurityConfiguration {

    // CSRF:
    // Proteção contra ataques CSRF.
    // Normalmente utilizada em aplicações Web com sessão e formulários.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                // Habilita autenticação Basic Auth
                .httpBasic(Customizer.withDefaults())

                // Configuração de formulário de login
//                .formLogin(configurer -> configurer.loginPage("/login"))
                .formLogin(Customizer.withDefaults())

                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();

                    authorize.anyRequest().authenticated();// Todas as requisições precisam de autenticação

                    // colocando isso dentro do controller
//                    authorize.requestMatchers("/autores/**").hasRole("ADMIN"); // somente o admin tem acesso
//                    authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN"); // os dois podem ter acesso ao endpoint de livros

                    // tem que ser o último, o que vier depois será ignorado

//                    tem como definir para cada método http
//                    authorize.requestMatchers(HttpMethod.POST,"/autores/**").hasAuthority("CADASTRAR_AUTOR"); // authority - permissão de executar uma tarefa, ação
//                    authorize.requestMatchers(HttpMethod.POST,"/autores/**").hasRole("ADMIN"); // role - é um grupo de usuário
//                    authorize.requestMatchers(HttpMethod.DELETE,"/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT,"/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET,"/autores/**").hasAnyRole("USER", "ADMIN");
                })
                .oauth2Login(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

//    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {

//        UserDetails user1 = User.builder()
//                .username("usuario")
//                .password(encoder.encode("123"))
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("admin")
//                .password(encoder.encode("321"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);

        return new CustomUserDetailsService(usuarioService);
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // ignora o prefixo ROLE
    }


}
