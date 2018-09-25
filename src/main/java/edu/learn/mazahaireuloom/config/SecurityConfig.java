package edu.learn.mazahaireuloom.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebFluxSecurity
public class SecurityConfig {
/*    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        System.out.println(encoder.encode("user"));
        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$10$gwM7Pe6Kcr745iAjNjo/se3f/GLABecAMTNKw53dN4EwsvVoQ/rIG")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    *//**
     * Following example is taken from @see WebFluxSecurityConfiguration in EnableWebFluxSecurity
     *//*
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange().pathMatchers("/login","/signup","/webjars/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic().and()
                .formLogin().and().csrf().disable();
        //.loginPage("/login");
        return http.build();
    }*/
}