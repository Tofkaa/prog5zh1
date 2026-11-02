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
import jakarta.annotation.security.PermitAll;

@PermitAll
public class MainLayout extends AppLayout  {

    private final AuthenticationContext authenticationContext;

   public MainLayout(AuthenticationContext authenticationContext, SecurityService securityService) {

       HorizontalLayout header = new HorizontalLayout();
       header.setWidth("100%");
       header.setAlignItems(FlexComponent.Alignment.CENTER);
       header.getStyle().set("padding", "10px");


       this.authenticationContext = authenticationContext;
       Span username = new Span(getTranslation("header.username")+ " " + authenticationContext.getPrincipalName().orElse(""));
        header.add(username);

       Button listBtn = new Button(getTranslation("header.menu.pet.list"),
               e -> UI.getCurrent().navigate(ListView.class));
        header.add(listBtn);

        if(securityService.getRolesAsString().contains("REGISTER")) {
            Button registerBtn = new Button(getTranslation("header.menu.pet.register"),
                    e -> UI.getCurrent().navigate(RegisterView.class));
            header.add(registerBtn);
        }

       Button logoutBtn = new Button(getTranslation("header.menu.logout"),
              e-> this.authenticationContext.logout());
        header.add(logoutBtn);

        addToNavbar(header);
   }

}
