package edu.learn.mazahaireuloom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

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
    /*@Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //Add the "/**" following for dev to disable security
        http.authorizeExchange().pathMatchers("/login", "/signup", "/api/users/login", "/ui/assets/**", "/webjars/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic().disable()
                .formLogin().loginPage("/login").and()
                .csrf().disable()
                .logout().disable();
        return http.build();
    }*/

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        //corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfig.setAllowedHeaders(List.of("Accept", "Origin", "X-Requested-With", "Content-Type", "Accept-Language", "X-Auth-Token", "Authorization", "X-Forwarded-For"));
        corsConfig.setAllowedMethods(List.of("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE"));
        corsConfig.setExposedHeaders(List.of("Access-Control-Expose-Headers", "Set-Cookie"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", corsConfig);
        return source;
    }

    /*@Bean
    public CorsWebFilter corsWebFilter() {
        return new CorsWebFilter(corsConfiguration());
    }*/

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain1(ServerHttpSecurity http) {
        http
        //.cors(ServerHttpSecurity.CorsSpec::disable)
        .cors(corsConfig -> {
            corsConfig.configurationSource(corsConfiguration());
        }).
        authorizeExchange((exchanges) ->
                exchanges
                        // any URL that starts with /admin/ requires the role "ROLE_ADMIN"
                        .pathMatchers("/login", "/signup", "/api/users/login", "/ui/assets/**", "/webjars/**", "/**").permitAll()
                        // a request to /users/{username} requires the current authentication's username
                        // to be equal to the {username}
                        /*.pathMatchers("/users/{username}").access((authentication, context) ->
                                authentication
                                        .map(Authentication::getName)
                                        .map((username) -> username.equals(context.getVariables().get("username")))
                                        .map(AuthorizationDecision::new)
                        )*/
                        // any other request requires the user to be authenticated
                        .anyExchange().authenticated()
        ).httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .formLogin(formLoginConfig -> {
            formLoginConfig.loginPage("/login");
        })
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .logout(ServerHttpSecurity.LogoutSpec::disable);
        return http.build();
    }
}
