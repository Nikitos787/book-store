package project.bookstore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import project.bookstore.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService {
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Cannot find user with username: %s",
                        username)));
    }

}
