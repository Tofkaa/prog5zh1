package hu.mik.pte.prog5.library.service;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
public interface SecurityService {
    default List<String> getRolesAsString() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role).toList();
    }
}
