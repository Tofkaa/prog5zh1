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

@Route(value="list", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class ListView extends VerticalLayout implements SecurityService, HasDynamicTitle {

    private final PetService petService;
    private final Grid<Pet> grid = new Grid<>(Pet.class, false);

    public ListView(PetService petService) {
        this.petService = petService;

        setSizeFull();

        boolean isAdmin = getRolesAsString().contains("ADMIN");
        if(isAdmin) {
            grid.addColumn(Pet::getUid).setHeader(getTranslation("pet.id"));
        }

        grid.addColumn(Pet::getName).setHeader(getTranslation("pet.name"));

        grid.addColumn(pet-> getTranslation("pet.species." + pet.getSpecies())).setHeader(getTranslation("pet.species"));

        add(grid);
        grid.setItems(this.petService.listAll());
    }


    @Override
    public String getPageTitle() {
        return this.getTranslation("title.pet.list");
    }
}
