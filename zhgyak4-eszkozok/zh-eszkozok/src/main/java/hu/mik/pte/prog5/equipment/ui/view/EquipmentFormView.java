package hu.mik.pte.prog5.equipment.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import hu.mik.pte.prog5.equipment.entity.Equipment;
import hu.mik.pte.prog5.equipment.entity.EquipmentType;
import hu.mik.pte.prog5.equipment.service.EquipmentService;
import hu.mik.pte.prog5.equipment.ui.MainLayout;
import jakarta.annotation.security.RolesAllowed;

@Route(value="equipment/new", layout = MainLayout.class)
@RouteAlias(value = "equipment/:id/edit", layout = MainLayout.class)
@RolesAllowed("MANAGE")
public class EquipmentFormView extends VerticalLayout implements HasDynamicTitle, BeforeEnterObserver {
    private final EquipmentService equipmentService;

    private final Binder<Equipment> binder = new Binder<>(Equipment.class);
    private Equipment equipment;

    private TextField name = new TextField();
    private TextField room  = new TextField();
    private IntegerField year = new IntegerField();
    ComboBox<EquipmentType> equipmentType = new ComboBox<>();
    private Checkbox available = new Checkbox();

    public EquipmentFormView(EquipmentService equipmentService) {
        // TODO 6, 8, 9: kozos uj/szerkeszto url, Binder, validacio es mentes.
        this.equipmentService = equipmentService;

        name.setLabel(this.getTranslation("equipment.name"));
        room.setLabel(this.getTranslation("equipment.room"));
        year.setLabel(this.getTranslation("equipment.year"));
        available.setLabel(this.getTranslation("equipment.available"));

        this.equipmentType.setLabel(this.getTranslation("equipment.type"));
        this.equipmentType.setItems(equipmentService.listTypes());
        this.equipmentType.setItemLabelGenerator(i ->getTranslation("equipment.type." + i.name()));

        binder.forField(name)
                .asRequired(getTranslation("validation.name"))
                .withValidator(i -> !i.isBlank(), getTranslation("validation.name"))
                .bind(Equipment::getName, Equipment::setName);

        binder.forField(room)
                .asRequired(getTranslation("validation.room"))
                .withValidator(i -> !i.isBlank(), getTranslation("validation.room"))
                .bind(Equipment::getRoom, Equipment::setRoom);

        binder.forField(year)
                .asRequired(getTranslation("validation.year.required"))
                .withValidator(i -> i!= null && i>= 2000 && i <= 2026, getTranslation("validation.year.range"))
                .bind(Equipment::getPurchaseYear, Equipment::setPurchaseYear);

        binder.forField(equipmentType)
                .asRequired(getTranslation("validation.type"))
                .bind(Equipment::getType, Equipment::setType);

        binder.forField(available)
                .bind(Equipment::isAvailable, Equipment::setAvailable);

        FormLayout formLayout = new FormLayout(name, room, year, equipmentType, available);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formLayout.setWidth("100%");
        formLayout.setMaxWidth("350px");
        add(formLayout, new Button(getTranslation("action.save"), e -> save()),
                new Button(getTranslation("action.cancel"), e-> UI.getCurrent().navigate(EquipmentListView.class)));
    }

    private void save() {
        if (this.binder.writeBeanIfValid(this.equipment)) {
            this.equipmentService.save(this.equipment);
            UI.getCurrent().navigate(EquipmentListView.class);
        }
    }

    @Override
    public String getPageTitle() {
        return this.getTranslation(this.equipment == null || this.equipment.getId() == null ? "title.new" : "title.edit");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        var idParam = event.getRouteParameters().get("id");

        if (idParam.isPresent()) {
            Long id;
            try {
                id = Long.valueOf(idParam.get());
            } catch (NumberFormatException exception) {
                Notification.show(getTranslation("equipment.notfound"));
                event.forwardTo(EquipmentListView.class);
                return;
            }

            var eqOpt = this.equipmentService.findById(id);
            if (eqOpt.isEmpty()) {
                Notification.show(getTranslation("equipment.notfound"));
                event.forwardTo(EquipmentListView.class);
                return;
            }

            this.equipment = eqOpt.get();
        } else {

            this.equipment = new Equipment();
        }


        this.binder.readBean(this.equipment);
    }
}
