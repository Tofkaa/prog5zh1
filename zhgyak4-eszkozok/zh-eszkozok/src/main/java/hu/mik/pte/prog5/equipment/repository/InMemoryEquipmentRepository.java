package hu.mik.pte.prog5.equipment.repository;

import hu.mik.pte.prog5.equipment.entity.Equipment;
import hu.mik.pte.prog5.equipment.entity.EquipmentType;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("mock")
public class InMemoryEquipmentRepository implements EquipmentRepository {
    private final Map<Long, Equipment> items = new LinkedHashMap<>();
    private long sequence;

    public InMemoryEquipmentRepository() {
        save(new Equipment(null, "Dell Latitude 5440", "A101", 2023, EquipmentType.LAPTOP, true));
        save(new Equipment(null, "Epson EB-X49", "B202", 2021, EquipmentType.PROJECTOR, false));
        save(new Equipment(null, "Samsung Galaxy Tab S9", "A103", 2024, EquipmentType.TABLET, true));
        save(new Equipment(null, "Lenovo ThinkPad T14", "A101", 2022, EquipmentType.LAPTOP, false));
        save(new Equipment(null, "BenQ MW560", "C301", 2025, EquipmentType.PROJECTOR, true));
    }

    // A tarolt adatok csak save hivasra modosulnak, az olvasas masolatot ad.
    @Override public synchronized List<Equipment> findAll() {
        List<Equipment> result = new ArrayList<>();
        items.values().forEach(item -> result.add(new Equipment(item)));
        return result;
    }
    @Override public synchronized Optional<Equipment> findById(Long id) {
        return Optional.ofNullable(items.get(id)).map(Equipment::new);
    }
    @Override public synchronized Equipment save(Equipment equipment) {
        Equipment copy = new Equipment(equipment);
        if (copy.getId() == null) {
            copy.setId(++sequence);
        } else if (!items.containsKey(copy.getId())) {
            throw new IllegalArgumentException("Unknown equipment id: " + copy.getId());
        }
        items.put(copy.getId(), copy);
        return new Equipment(copy);
    }
    @Override public synchronized void deleteById(Long id) { items.remove(id); }
}
