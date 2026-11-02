package hu.mik.pte.prog5.equipment.entity;

public class Equipment {
    private Long id;
    private String name;
    private String room;
    private Integer purchaseYear;
    private EquipmentType type;
    private boolean available;

    public Equipment() {}

    public Equipment(Long id, String name, String room, Integer purchaseYear,
                     EquipmentType type, boolean available) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.purchaseYear = purchaseYear;
        this.type = type;
        this.available = available;
    }

    public Equipment(Equipment other) {
        this(other.id, other.name, other.room, other.purchaseYear, other.type, other.available);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    public Integer getPurchaseYear() { return purchaseYear; }
    public void setPurchaseYear(Integer purchaseYear) { this.purchaseYear = purchaseYear; }
    public EquipmentType getType() { return type; }
    public void setType(EquipmentType type) { this.type = type; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
