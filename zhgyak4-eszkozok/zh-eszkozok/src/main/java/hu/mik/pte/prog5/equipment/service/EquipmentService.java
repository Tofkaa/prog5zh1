package hu.mik.pte.prog5.equipment.service;

import hu.mik.pte.prog5.equipment.entity.Equipment;
import hu.mik.pte.prog5.equipment.entity.EquipmentType;
import java.util.List;
import java.util.Optional;

public interface EquipmentService {
    List<Equipment> listAll();
    Optional<Equipment> findById(Long id);
    Equipment save(Equipment equipment);
    void deleteById(Long id);
    List<EquipmentType> listTypes();
}
