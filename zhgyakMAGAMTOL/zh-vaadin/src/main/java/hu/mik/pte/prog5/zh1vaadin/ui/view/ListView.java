package hu.mik.pte.prog5.zh1vaadin.ui.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import hu.mik.pte.prog5.zh1vaadin.entity.Pet;
import hu.mik.pte.prog5.zh1vaadin.service.PetService;
import hu.mik.pte.prog5.zh1vaadin.service.SecurityService;
import hu.mik.pte.prog5.zh1vaadin.ui.MainLayout;
import jakarta.annotation.security.PermitAll;

@Route(value = "list", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class ListView extends VerticalLayout implements HasDynamicTitle {

    private final  Grid<Pet> grid = new Grid<>(Pet.class, false);
    private final PetService petService;

    public ListView(PetService petService, SecurityService securityService) {
        this.petService = petService;
        setSizeFull();

        if (securityService.getRolesAsString().contains("ADMIN")) {
            grid.addColumn(Pet::getUid).setHeader(getTranslation("pet.id"));
        }

        grid.addColumn(Pet::getName).setHeader(getTranslation("pet.name"));

        grid.addColumn(pet -> getTranslation("pet.species." + pet.getSpecies().name()))
                .setHeader(getTranslation("pet.species"));

        grid.setSizeFull();
        grid.setItems(petService.listAll());

        add(grid);
        refresh();
    }

    @Override
    public String getPageTitle() {
        return getTranslation("title.pet.list");
    }

    private void refresh() {
        this.grid.setItems(petService.listAll());
    }
}
