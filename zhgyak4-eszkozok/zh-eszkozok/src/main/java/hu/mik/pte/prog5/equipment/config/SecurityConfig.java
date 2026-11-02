package hu.mik.pte.prog5.equipment.config;

import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import hu.mik.pte.prog5.equipment.service.SecurityService;
import hu.mik.pte.prog5.equipment.ui.view.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    // TODO 2: regisztralja beankent; keszitse el a Vaadin security filter chain-t!
    // TODO 8: engedelyezze a method security-t!

    @Bean
    public SecurityService securityService() {
        return new SecurityService() {
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.with(VaadinSecurityConfigurer.vaadin(), l -> l.loginView(LoginView.class));
        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        var admin = User.withUsername("admin").password("{noop}password")
                .roles("ADMIN", "MANAGE", "DELETE", "LAPTOP", "PROJECTOR", "TABLET").build();
        var technician = User.withUsername("technician").password("{noop}password")
                .roles("MANAGE", "LAPTOP", "PROJECTOR", "TABLET").build();
        var student = User.withUsername("student").password("{noop}password")
                .roles("LAPTOP", "TABLET").build();
        var lecturer = User.withUsername("lecturer").password("{noop}password")
                .roles("PROJECTOR").build();
        return new InMemoryUserDetailsManager(admin, technician, student, lecturer);
    }
}
