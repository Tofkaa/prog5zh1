package hu.mik.pte.prog5.equipment.repository;

import hu.mik.pte.prog5.equipment.entity.Equipment;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// Szandekos elterelo implementacio: NEM feladat adatbazist kesziteni.
@Repository("db")
public class DbEquipmentRepository implements EquipmentRepository {
    @Override public List<Equipment> findAll() { throw new UnsupportedOperationException("Use mock"); }
    @Override public Optional<Equipment> findById(Long id) { throw new UnsupportedOperationException("Use mock"); }
    @Override public Equipment save(Equipment equipment) { throw new UnsupportedOperationException("Use mock"); }
    @Override public void deleteById(Long id) { throw new UnsupportedOperationException("Use mock"); }
}
