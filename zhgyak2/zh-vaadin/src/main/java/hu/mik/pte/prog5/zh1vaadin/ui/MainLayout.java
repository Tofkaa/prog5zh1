package hu.mik.pte.prog5.zh1vaadin.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;
import hu.mik.pte.prog5.zh1vaadin.service.SecurityService;
import hu.mik.pte.prog5.zh1vaadin.ui.view.ListView;
import hu.mik.pte.prog5.zh1vaadin.ui.view.RegisterView;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class MainLayout extends AppLayout {
    public MainLayout(AuthenticationContext authenticationContext, SecurityService securityService) {
        Span username = new Span(getTranslation("header.username") + " "
                + authenticationContext.getPrincipalName().orElse(""));
        RouterLink list = new RouterLink(getTranslation("header.menu.pet.list"), ListView.class);
        HorizontalLayout header = new HorizontalLayout(username, list);
        header.setWidthFull();
        header.setPadding(true);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        // A menü elrejtése mellett a route és a service is külön védett.
        if (securityService.getRolesAsString().contains("REGISTER")) {
            header.add(new RouterLink(getTranslation("header.menu.pet.register"), RegisterView.class));
        }
        header.add(new Button(getTranslation("header.menu.logout"), event -> authenticationContext.logout()));
        addToNavbar(header);
    }
}
