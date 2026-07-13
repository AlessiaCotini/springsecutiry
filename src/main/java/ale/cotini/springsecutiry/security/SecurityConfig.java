package ale.cotini.springsecutiry.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){

        // DISABILITO IL FORM DI DEFAULT

        httpSecurity.formLogin(formLogin -> formLogin.disable() );

        //DISABILITO LE SESSIONI

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //DISABILITIAMO ANCHE DA ATTACCHI CSRF

        httpSecurity.csrf(csrf -> csrf.disable());

        //ELIMINO I CONTROLLI AUTOMATICI DI SPRING SECURITY - quindi poi non interverrà sulle richieste

        httpSecurity.authorizeHttpRequests(req -> req.requestMatchers("/**").permitAll());

        //IMPLEMENTO I MIEI CONTROLLI BASATI SUI TOKEN

        return httpSecurity.build();

    }
}
