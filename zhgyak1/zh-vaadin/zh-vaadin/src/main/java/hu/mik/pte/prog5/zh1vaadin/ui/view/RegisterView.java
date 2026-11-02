package hu.mik.pte.prog5.zh1vaadin.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import hu.mik.pte.prog5.zh1vaadin.entity.Pet;
import hu.mik.pte.prog5.zh1vaadin.entity.PetSpecies;
import hu.mik.pte.prog5.zh1vaadin.service.PetService;
import hu.mik.pte.prog5.zh1vaadin.ui.MainLayout;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.prepost.PreAuthorize;

@Route(value = "register", layout = MainLayout.class)
@RolesAllowed("REGISTER")
public class RegisterView extends VerticalLayout implements HasDynamicTitle {

    private final PetService petService;
    private final Binder<Pet> binder = new Binder<>(Pet.class);
    private final Pet pet = new Pet();
    private final TextField name = new TextField();
     ComboBox<PetSpecies> species = new ComboBox<>();

    public RegisterView(PetService petService) {
    // this.species.setItems
    // this.species.setItemLabelGenerator
        this.petService = petService;
        name.setLabel(getTranslation("pet.name"));
        this.species.setLabel(getTranslation("pet.species"));

        this.species.setItems(this.petService.listSpecies());
        this.species.setItemLabelGenerator(s->getTranslation("pet.species." + s.name()));

        binder.forField(name).asRequired("A név megadása kötelező!").bind(Pet::getName, Pet::setName);
        binder.forField(species).asRequired("A faj megadása kötelező!").bind(Pet::getSpecies, Pet::setSpecies);

        binder.readBean(pet);

        FormLayout formLayout = new FormLayout(name, this.species);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formLayout.setMaxWidth("300px");
        Button submitBtn = new Button(getTranslation("register.submit"), e-> save());

        add(formLayout, submitBtn);
    }

    private void save() {
        if(binder.writeBeanIfValid(pet)) {
            petService.save(pet);
            UI.getCurrent().navigate(ListView.class);
        }
    }

    @Override
    public String getPageTitle() {
        return this.getTranslation("title.pet.register");
    }
}
