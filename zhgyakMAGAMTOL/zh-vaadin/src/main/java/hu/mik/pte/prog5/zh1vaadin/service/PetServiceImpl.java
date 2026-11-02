package hu.mik.pte.prog5.zh1vaadin.service;

import hu.mik.pte.prog5.zh1vaadin.entity.Pet;
import hu.mik.pte.prog5.zh1vaadin.entity.PetSpecies;
import hu.mik.pte.prog5.zh1vaadin.repository.PetRepository;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(@Qualifier("mock") PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    @PostFilter("@securityService.getRolesAsString().contains(filterObject.species.name())")
    public List<Pet> listAll() {
        return this.petRepository.findAll();
    }

    @PreAuthorize("hasRole('REGISTER')")
    @Override
    public Pet save(Pet pet) {
        return this.petRepository.save(pet);
    }

    @Override
    public List<PetSpecies> listSpecies() {
        return Arrays.asList(PetSpecies.values());
    }
}
