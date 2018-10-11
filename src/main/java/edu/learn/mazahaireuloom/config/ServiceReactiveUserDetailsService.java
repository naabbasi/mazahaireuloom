package edu.learn.mazahaireuloom.config;

import edu.learn.mazahaireuloom.repos.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ServiceReactiveUserDetailsService implements ReactiveUserDetailsService {
    private UserRepo userRepo;

    public ServiceReactiveUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return this.userRepo.findByUsername(username).map(CustomUser::new);
    }
}