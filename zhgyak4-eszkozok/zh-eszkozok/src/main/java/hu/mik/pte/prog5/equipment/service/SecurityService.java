package hu.mik.pte.prog5.equipment.service;

import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

// A segedmetodus kesz, az injektalhato Spring bean letrehozasa feladat.
public interface SecurityService {
    default List<String> getRolesAsString() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return List.of();
        return authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role)
                .toList();
    }
}
