package hu.mik.pte.prog5.equipment.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.security.AuthenticationContext;
import hu.mik.pte.prog5.equipment.service.SecurityService;
import hu.mik.pte.prog5.equipment.ui.view.EquipmentFormView;
import hu.mik.pte.prog5.equipment.ui.view.EquipmentListView;
import jakarta.annotation.security.PermitAll;

import java.util.Locale;

@PermitAll
public class MainLayout extends AppLayout {

   private final AuthenticationContext authenticationContext;

    public MainLayout(AuthenticationContext authenticationContext, SecurityService securityService) {

        // TODO 4, 9: kozos fejlec, navigacio, felhasznalonev, kilepes, nyelvvalaszto.

        this.authenticationContext = authenticationContext;

        // JAVÍTVA: en_US -> en, hu_HU -> hu; más nyelvnél magyar az alap.
        // Még a fordított feliratok létrehozása előtt az UI és a munkamenet is ezt kapja.
        UI ui = UI.getCurrent();
        VaadinSession session = VaadinSession.getCurrent();
        Locale currentLocale = ui.getLocale();
        Locale languageLocale = Locale.of("en".equals(currentLocale.getLanguage()) ? "en" : "hu");
        session.setLocale(languageLocale);
        ui.setLocale(languageLocale);

        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getStyle().set("padding", "10px");
        if(this.authenticationContext.isAuthenticated()) {
            Span username = new Span(getTranslation("header.username") + " " + authenticationContext.getPrincipalName().orElse(""));
            header.add(username);
        }


        Button listBton = new Button(getTranslation("menu.list"), e -> UI.getCurrent().navigate(EquipmentListView.class));
        header.add(listBton);

        if(securityService.getRolesAsString().contains("MANAGE")){
            Button mngBton = new Button(getTranslation("menu.new"), e-> UI.getCurrent().navigate(EquipmentFormView.class));
            header.add(mngBton);
        }

        // JAVÍTVA: csak a két támogatott nyelvet kínáljuk fel.
        ComboBox<Locale> language = new ComboBox<>(getTranslation("language.label"));
        language.setItems(Locale.of("hu"), Locale.of("en"));
        language.setItemLabelGenerator(l -> l.getDisplayLanguage(this.getLocale()));
        language.setValue(languageLocale);

        header.add(language);
        language.addValueChangeListener(e -> {
            // JAVÍTVA: csak felhasználói váltás tölthet újra;
            // üres választásnál visszaállítjuk az előző értéket, null nem kerül a sessionbe.
            if (!e.isFromClient()) {
                return;
            }
            if (e.getValue() == null) {
                language.setValue(e.getOldValue());
                return;
            }
            session.setLocale(e.getValue());
            ui.getPage().reload();
        });

        Button logoutBton = new Button(getTranslation("menu.logout"), e-> authenticationContext.logout());
        header.add(logoutBton);
        addToNavbar(header);
    }
}
