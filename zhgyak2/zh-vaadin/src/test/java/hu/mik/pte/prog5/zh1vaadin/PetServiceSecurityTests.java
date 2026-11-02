package hu.mik.pte.prog5.zh1vaadin;

import hu.mik.pte.prog5.zh1vaadin.entity.Pet;
import hu.mik.pte.prog5.zh1vaadin.entity.PetSpecies;
import hu.mik.pte.prog5.zh1vaadin.service.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetServiceSecurityTests {
    @Autowired
    private PetService petService;

    @Test
    @WithMockUser(roles = "DOG")
    void dogRoleOnlyReceivesDogs() {
        assertFalse(petService.listAll().isEmpty());
        assertTrue(petService.listAll().stream().allMatch(pet -> pet.getSpecies() == PetSpecies.DOG));
    }

    @Test
    @WithMockUser(roles = {"TURTLE", "PARROT", "FERRET"})
    void multipleRolesReceiveTheirSpecies() {
        Set<PetSpecies> species = petService.listAll().stream()
                .map(Pet::getSpecies).collect(Collectors.toSet());
        assertEquals(Set.of(PetSpecies.TURTLE, PetSpecies.PARROT, PetSpecies.FERRET), species);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminWithoutSpeciesRolesDoesNotBypassFiltering() {
        assertTrue(petService.listAll().isEmpty());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "REGISTER", "CAT", "DOG", "PARROT", "TURTLE", "FERRET"})
    void suppliedAdminRolesReceiveEverySpecies() {
        assertEquals(Set.of(PetSpecies.values()), petService.listAll().stream()
                .map(Pet::getSpecies).collect(Collectors.toSet()));
    }

    @Test
    @WithMockUser(roles = "DOG")
    void savingAndRegistrationOptionsRequireRegisterRole() {
        assertThrows(AccessDeniedException.class,
                () -> petService.save(new Pet(null, "Tiltott", PetSpecies.DOG)));
        assertThrows(AccessDeniedException.class, () -> petService.listSpecies());
    }

    @Test
    @WithMockUser(roles = {"REGISTER", "DOG"})
    void registerRoleCanLoadOptionsAndSave() {
        assertEquals(Set.of(PetSpecies.values()), Set.copyOf(petService.listSpecies()));
        Pet saved = petService.save(new Pet(null, "Tesztkutya", PetSpecies.DOG));
        assertNotNull(saved.getUid());
        assertTrue(petService.listAll().stream().anyMatch(pet -> pet.getUid().equals(saved.getUid())));
    }
}
