package hu.mik.pte.prog5.library.ui.view;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import hu.mik.pte.prog5.library.entity.Book;
import hu.mik.pte.prog5.library.service.BookService;
import hu.mik.pte.prog5.library.service.SecurityService;
import hu.mik.pte.prog5.library.ui.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@Route(value = "books", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class BookListView extends VerticalLayout implements HasDynamicTitle {

    private final BookService bookService;
    private final Grid<Book> grid = new Grid<>(Book.class, false);
    private final SecurityService securityService;

    public BookListView(BookService bookService, SecurityService securityService) {
        this.bookService = bookService;
        this.securityService = securityService;
        setSizeFull();


        if(securityService.getRolesAsString().contains("ADMIN")){
            this.grid.addColumn(Book::getId).setHeader(getTranslation("book.id"));
        }

        this.grid.addColumn(Book::getTitle).setHeader(getTranslation("book.title"));
        this.grid.addColumn(Book::getAuthor).setHeader(getTranslation("book.author"));
        this.grid.addColumn(Book::getPublicationYear).setHeader(getTranslation("book.year"));

        this.grid.addColumn(book -> book.getCategory() != null
                ? getTranslation("book.category." + book.getCategory().name())
                : ""
        ).setHeader(getTranslation("book.category"));

        this.grid.addColumn(book -> book.isLoanable()
                ? getTranslation("value.yes")
                : getTranslation("value.no")
        ).setHeader(getTranslation("book.loanable"));


        boolean canManage = securityService.getRolesAsString().contains("MANAGE");
        boolean canDelete = securityService.getRolesAsString().contains("DELETE");

        if(canManage ||  canDelete) {
            this.grid.addComponentColumn(i->this.createActions(i)).setHeader(getTranslation("book.manage"));
        }


        this.grid.setEmptyStateText(this.getTranslation("list.empty"));

        this.add(grid);
        this.grid.setItems(this.bookService.listAll());
        refresh();
    }

    private Component createActions(Book book) {
        HorizontalLayout actions = new HorizontalLayout();
        List<String> roles = this.securityService.getRolesAsString();

        // Szerkesztés gomb: csak MANAGE jogosultsággal
        if (roles.contains("MANAGE")) {
            Button editBtn = new Button(this.getTranslation("action.edit"),
                    e -> UI.getCurrent().navigate("books/" + book.getId() + "/edit"));
            actions.add(editBtn);
        }

        // Törlés gomb: csak DELETE jogosultsággal
        if (roles.contains("DELETE")) {
            Button deleteBtn = new Button(this.getTranslation("action.delete"), e -> {
                ConfirmDialog dialog = new ConfirmDialog();
                dialog.setHeader(this.getTranslation("delete.title"));
                // A fordított kérdés paraméterként megkapja a könyv címét
                dialog.setText(this.getTranslation("delete.message", book.getTitle()));

                dialog.setCancelable(true);
                dialog.setCancelText(this.getTranslation("action.cancel"));
                dialog.setConfirmText(this.getTranslation("action.delete"));
                dialog.addConfirmListener(ev -> {
                    this.bookService.deleteById(book.getId());
                    this.refresh();
                });

                dialog.open();
            });
            actions.add(deleteBtn);
        }

        return actions;
    }

    private void refresh() {
        this.grid.setItems(this.bookService.listAll());
    }

    @Override
    public String getPageTitle() {
        return this.getTranslation("title.list");
    }
}
