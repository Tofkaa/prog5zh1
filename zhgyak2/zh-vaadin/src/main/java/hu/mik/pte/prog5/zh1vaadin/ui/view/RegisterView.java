package hu.mik.pte.prog5.zh1vaadin.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import hu.mik.pte.prog5.zh1vaadin.entity.Pet;
import hu.mik.pte.prog5.zh1vaadin.entity.PetSpecies;
import hu.mik.pte.prog5.zh1vaadin.service.PetService;
import hu.mik.pte.prog5.zh1vaadin.ui.MainLayout;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "register", layout = MainLayout.class)
@RolesAllowed("REGISTER")
public class RegisterView extends VerticalLayout implements HasDynamicTitle {
    private final PetService petService;
    private final Binder<Pet> binder = new Binder<>(Pet.class);
    private final Pet pet = new Pet();

    public RegisterView(PetService petService) {
        this.petService = petService;
        TextField name = new TextField(getTranslation("pet.name"));
        ComboBox<PetSpecies> species = new ComboBox<>(getTranslation("pet.species"));
        species.setItems(petService.listSpecies());
        species.setItemLabelGenerator(value -> getTranslation("pet.species." + value.name()));

        binder.forField(name)
                .asRequired(getTranslation("validation.name.required"))
                .withValidator(value -> !value.isBlank(), getTranslation("validation.name.required"))
                .bind(Pet::getName, Pet::setName);
        binder.forField(species)
                .asRequired(getTranslation("validation.species.required"))
                .bind(Pet::getSpecies, Pet::setSpecies);
        // readBean nem módosítja a modellt gépelés közben; csak az érvényes mentés írja vissza.
        binder.readBean(pet);

        FormLayout form = new FormLayout(name, species);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        form.setWidthFull();
        form.setMaxWidth("300px");
        add(form, new Button(getTranslation("register.submit"), event -> save()));
    }

    private void save() {
        if (binder.writeBeanIfValid(pet)) {
            petService.save(pet);
            UI.getCurrent().navigate(ListView.class);
        }
    }

    @Override
    public String getPageTitle() {
        return getTranslation("title.pet.register");
    }
}
