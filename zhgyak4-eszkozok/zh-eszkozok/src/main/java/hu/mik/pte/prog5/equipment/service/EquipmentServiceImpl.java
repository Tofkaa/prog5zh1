package hu.mik.pte.prog5.equipment.service;

import hu.mik.pte.prog5.equipment.entity.Equipment;
import hu.mik.pte.prog5.equipment.entity.EquipmentType;
import hu.mik.pte.prog5.equipment.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private EquipmentRepository equipmentRepository;
    public EquipmentServiceImpl(@Qualifier("mock")EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @PostFilter("@securityService.getRolesAsString().contains(filterObject.type.name())")
    @Override
    public List<Equipment> listAll() {
        return this.equipmentRepository.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public Optional<Equipment> findById(Long id) {
        return this.equipmentRepository.findById(id);
    }

    @PreAuthorize("hasRole('MANAGE')")
    @Override
    public Equipment save(Equipment equipment) {
        return this.equipmentRepository.save(equipment);
    }

    @PreAuthorize("hasRole('DELETE')")
    @Override
    public void deleteById(Long id) {
        this.equipmentRepository.deleteById(id);
    }


    @PreAuthorize("isAuthenticated()")
    @Override
    public List<EquipmentType> listTypes() {
        return Arrays.asList(EquipmentType.values());
    }
}
