package hu.mik.pte.prog5.equipment.ui.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

// A lista root aliasanak felvetele elott tavolitsa el ezt a Route annotaciot!
//@Route("")
@AnonymousAllowed
public class StarterView extends VerticalLayout {
    public StarterView() {
        add(new H2("Prog5 - Eszkoznyilvantartas: kiindulo projekt"),
            new Paragraph("A feladatleiras a projekt melletti FELADATLEIRAS.md fajlban van."),
            new Paragraph("A CRUD, a bejelentkezes es a HU/EN nyelvvalaszto megvalositasa a feladat."));
    }
}
