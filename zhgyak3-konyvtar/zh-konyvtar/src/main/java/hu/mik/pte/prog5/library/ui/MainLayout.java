package hu.mik.pte.prog5.library.ui;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.security.AuthenticationContext;
import hu.mik.pte.prog5.library.service.SecurityService;
import hu.mik.pte.prog5.library.ui.view.BookListView;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class MainLayout extends AppLayout {
    private final AuthenticationContext authenticationContext;

    public MainLayout(AuthenticationContext authenticationContext, SecurityService securityService) {
        this.authenticationContext = authenticationContext;

        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getStyle().set("padding", "10px");

        // JAVÍTVA (Codex): az Optional helyett a benne lévő felhasználónevet jelenítjük meg.
        Span username = new Span(getTranslation("header.username") + " " + authenticationContext.getPrincipalName().orElse(""));
        header.add(username);

        Button listBtn = new Button(getTranslation("menu.list") ,e->UI.getCurrent().navigate(BookListView.class));
        header.add(listBtn);

        if(securityService.getRolesAsString().contains("MANAGE")){
            Button mngBtn = new Button(getTranslation("menu.new"), e-> UI.getCurrent().navigate("books/new"));
            header.add(mngBtn);
        }

        Button logoutBtn = new Button(getTranslation("menu.logout"), e-> authenticationContext.logout());
        header.add(logoutBtn);

        addToNavbar(header);
    }
}
