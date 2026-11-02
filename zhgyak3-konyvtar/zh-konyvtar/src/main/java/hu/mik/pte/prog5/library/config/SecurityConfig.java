package hu.mik.pte.prog5.library.config;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import hu.mik.pte.prog5.library.service.SecurityService;
import hu.mik.pte.prog5.library.ui.view.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.with(VaadinSecurityConfigurer.vaadin(), l-> l.loginView(LoginView.class));
        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        var admin = User.withUsername("admin").password("{noop}password")
                .roles("ADMIN", "MANAGE", "DELETE", "FICTION", "SCIENCE", "HISTORY").build();
        var librarian = User.withUsername("librarian").password("{noop}password")
                .roles("MANAGE", "FICTION", "SCIENCE", "HISTORY").build();
        var reader = User.withUsername("reader").password("{noop}password")
                .roles("FICTION").build();
        var researcher = User.withUsername("researcher").password("{noop}password")
                .roles("SCIENCE", "HISTORY").build();
        return new InMemoryUserDetailsManager(admin, librarian, reader, researcher);
    }

    @Bean
    public SecurityService securityService() {
        return new SecurityService() {};
    }
}
