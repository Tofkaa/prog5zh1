package hu.mik.pte.prog5.library.ui.view;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value="login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver , HasDynamicTitle {
    private final LoginForm loginForm = new LoginForm();

    public LoginView() {
        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(false);

        setAlignItems(Alignment.CENTER);
        this.add(loginForm);
    }

    @Override
    public String getPageTitle() {
        return getTranslation("title.login");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            this.loginForm.setError(true);
        }
    }
}
