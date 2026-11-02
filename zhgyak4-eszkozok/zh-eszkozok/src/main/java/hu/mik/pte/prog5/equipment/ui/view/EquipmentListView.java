package hu.mik.pte.prog5.equipment.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import hu.mik.pte.prog5.equipment.entity.Equipment;
import hu.mik.pte.prog5.equipment.service.EquipmentService;
import hu.mik.pte.prog5.equipment.service.SecurityService;
import hu.mik.pte.prog5.equipment.ui.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@Route(value="equipment", layout= MainLayout.class)
@RouteAlias(value="", layout= MainLayout.class)
@PermitAll
public class EquipmentListView extends VerticalLayout implements HasDynamicTitle {

    private final EquipmentService equipmentService;
    private final Grid<Equipment> equipmentGrid = new Grid<>(Equipment.class, false);
    private final SecurityService securityService;


    public EquipmentListView(EquipmentService equipmentService, SecurityService securityService) {
        // TODO 5, 7, 8, 9: lista, muveletek, jogosultsagok, forditott feliratok.
        setSizeFull();
        this.equipmentService = equipmentService;
        this.securityService = securityService;
        if(securityService.getRolesAsString().contains("ADMIN")){
            this.equipmentGrid.addColumn(Equipment::getId).setHeader(this.getTranslation("equipment.id"));
        }

        this.equipmentGrid.addColumn(Equipment::getName).setHeader(this.getTranslation("equipment.name"));
        this.equipmentGrid.addColumn(Equipment::getRoom).setHeader(this.getTranslation("equipment.room"));
        this.equipmentGrid.addColumn(Equipment::getPurchaseYear).setHeader(this.getTranslation("equipment.year"));

        this.equipmentGrid.addColumn(equipment -> equipment.getType() != null
                ? getTranslation("equipment.type." + equipment.getType().name())
                : " ")
        .setHeader(this.getTranslation("equipment.type"));

        this.equipmentGrid.addColumn(equipment -> equipment.isAvailable() 
                ? getTranslation("value.yes") 
                : getTranslation("value.no")
        ).setHeader(this.getTranslation("equipment.available"));
        

        boolean canEdit = securityService.getRolesAsString().contains("MANAGE");
        boolean canDelete = securityService.getRolesAsString().contains("DELETE");

        if(canEdit || canDelete){
            this.equipmentGrid.addComponentColumn(i -> createActions(i)).setHeader(this.getTranslation("equipment.actions"));
        }

        this.equipmentGrid.setEmptyStateText(this.getTranslation("list.empty"));
        this.add(this.equipmentGrid);
        this.equipmentGrid.setSizeFull();
        this.equipmentGrid.setItems(equipmentService.listAll());
        refresh();
    }

    private Component createActions(Equipment eq) {
        HorizontalLayout hl = new HorizontalLayout();
        List<String> roles = this.securityService.getRolesAsString();

        if (roles.contains("MANAGE")) {
            Button editBtn = new Button(this.getTranslation("action.edit"),
                    e -> UI.getCurrent().navigate("equipment/" + eq.getId() + "/edit"));
            hl.add(editBtn);
        }

        if (roles.contains("DELETE")) {
            Button deleteBtn = new Button(this.getTranslation("action.delete"), e -> {
                ConfirmDialog dialog = new ConfirmDialog();
                dialog.setHeader(this.getTranslation("delete.title"));
                dialog.setText(this.getTranslation("delete.message", eq.getName()));

                dialog.setCancelable(true);
                dialog.setCancelText(this.getTranslation("action.cancel"));
                dialog.setConfirmText(this.getTranslation("action.delete"));
                dialog.addConfirmListener(ev -> {
                    this.equipmentService.deleteById(eq.getId());
                    this.refresh();
                });

                dialog.open();
            });
            hl.add(deleteBtn);
        }

        return hl;


    }

    private void refresh() {
        this.equipmentGrid.setItems(this.equipmentService.listAll());
    }

    @Override
    public String getPageTitle() {
        return this.getTranslation("title.list");
    }
}
