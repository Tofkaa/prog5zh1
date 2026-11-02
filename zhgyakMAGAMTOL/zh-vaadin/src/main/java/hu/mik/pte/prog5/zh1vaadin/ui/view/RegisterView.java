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
    private Pet pet = new Pet();
    private TextField name = new TextField();

    ComboBox<PetSpecies> species = new ComboBox<>();

    public RegisterView(PetService petService) {
        this.petService = petService;
        name.setLabel(getTranslation("pet.name"));
        this.species.setLabel(getTranslation("pet.species"));

        this.species.setItems(petService.listSpecies());
        this.species.setItemLabelGenerator(v -> getTranslation("pet.species." + v.name()));

        binder.forField(name)
                .asRequired()
                .bind(Pet::getName, Pet::setName);

        binder.forField(species)
                .asRequired()
                .bind(Pet::getSpecies, Pet::setSpecies);

        binder.readBean(pet);

        FormLayout formLayout = new FormLayout(name, species);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formLayout.setWidth("100%");
        formLayout.setMaxWidth("300px");
        add(formLayout, new Button(getTranslation("register.submit") , e -> save()));
    }

    private void save(){
        if(binder.writeBeanIfValid(pet)){
            petService.save(pet);
            UI.getCurrent().navigate(ListView.class);
        }
    }

    @Override
    public String getPageTitle() {
        return this.getTranslation("title.pet.register");
    }
}
