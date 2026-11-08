package br.edu.sgpd.security;

import br.edu.sgpd.model.AppUser;
import br.edu.sgpd.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BootstrapUser {
    @Bean
    CommandLineRunner createInitialUser(AppUserRepository users, PasswordEncoder encoder,
            @Value("${SGPD_BOOTSTRAP_NAME:}") String name,
            @Value("${SGPD_BOOTSTRAP_EMAIL:}") String email,
            @Value("${SGPD_BOOTSTRAP_PASSWORD:}") String password) {
        return args -> {
            if (email.isBlank() || password.isBlank()) return;
            if (!users.existsByEmailIgnoreCase(email)) {
                users.save(new AppUser(name.isBlank() ? "RH" : name, email, encoder.encode(password)));
            }
        };
    }
}
