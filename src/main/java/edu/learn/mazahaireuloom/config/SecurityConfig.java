package edu.learn.mazahaireuloom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    //Following code will be commented when ServiceReactiveUserDetailsService is in use
    /*@Bean
    public MapReactiveUserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        System.out.println(encoder.encode("user"));
        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$10$gwM7Pe6Kcr745iAjNjo/se3f/GLABecAMTNKw53dN4EwsvVoQ/rIG")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }*/

    /*
     * Following example is taken from @see WebFluxSecurityConfiguration in EnableWebFluxSecurity
     */
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange().pathMatchers("/login","/signup","/api/users/login","/ui/assets/**","/webjars/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic().disable()
                .formLogin().loginPage("/login").and()
                .csrf().disable()
                .logout().disable();
        return http.build();
    }
}