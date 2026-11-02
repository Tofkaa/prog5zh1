package hu.mik.pte.prog5.equipment.repository;

import hu.mik.pte.prog5.equipment.entity.Equipment;
import java.util.List;
import java.util.Optional;

public interface EquipmentRepository {
    List<Equipment> findAll();
    Optional<Equipment> findById(Long id);
    Equipment save(Equipment equipment);
    void deleteById(Long id);
}
