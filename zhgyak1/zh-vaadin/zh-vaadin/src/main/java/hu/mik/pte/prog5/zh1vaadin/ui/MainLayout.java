package hu.mik.pte.prog5.zh1vaadin.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.security.AuthenticationContext;
import hu.mik.pte.prog5.zh1vaadin.service.SecurityService;
import hu.mik.pte.prog5.zh1vaadin.ui.view.ListView;
import hu.mik.pte.prog5.zh1vaadin.ui.view.RegisterView;


public class MainLayout extends AppLayout implements SecurityService {

    public MainLayout(AuthenticationContext authenticationContext) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontalLayout.getStyle().set("padding", "10px");

        String username = authenticationContext.getPrincipalName().orElse("guest");
        Span usernameLabel = new Span(getTranslation("header.username") + " " + username);
        horizontalLayout.add(usernameLabel);

        Button listBtn = new Button(getTranslation("header.menu.pet.list"),
                e -> UI.getCurrent().navigate(ListView.class));
        horizontalLayout.add(listBtn);

        if (getRolesAsString().contains("REGISTER")) {
            Button registerBtn = new Button(getTranslation("header.menu.pet.register"),
                    e -> UI.getCurrent().navigate(RegisterView.class));
            horizontalLayout.add(registerBtn);
        }

        Button logoutBtn = new Button(getTranslation("header.menu.logout"),
                e-> authenticationContext.logout());
        horizontalLayout.add(logoutBtn);

        addToNavbar(horizontalLayout);
    }
}
