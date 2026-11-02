package hu.mik.pte.prog5.library.ui.view;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.router.*;
import hu.mik.pte.prog5.library.entity.Book;
import hu.mik.pte.prog5.library.entity.BookCategory;
import hu.mik.pte.prog5.library.service.BookService;
import hu.mik.pte.prog5.library.ui.MainLayout;
import jakarta.annotation.security.RolesAllowed;


@Route(value="books/new", layout = MainLayout.class)
@RouteAlias(value="books/:id/edit", layout = MainLayout.class)
@RolesAllowed("MANAGE")
public class BookFormView extends VerticalLayout implements BeforeEnterObserver, HasDynamicTitle{
    private final BookService bookService;

    private final Binder<Book> binder = new Binder<>(Book.class);

    private Book book;

    private  TextField title = new TextField();
    private  TextField author = new TextField();
    private  IntegerField year = new IntegerField();
    ComboBox<BookCategory> category = new ComboBox<>();

    private final Checkbox loanable = new Checkbox();


    public BookFormView(BookService bookService) {
        this.bookService = bookService;

        title.setLabel(getTranslation("book.title"));
        author.setLabel(getTranslation("book.author"));
        year.setLabel(getTranslation("book.year"));
        loanable.setLabel(getTranslation("book.loanable"));
        this.category.setLabel(getTranslation("book.category"));
        this.category.setItems(bookService.listCategories());
        this.category.setItemLabelGenerator(b->getTranslation("book.category." + b.name()));

        binder.forField(title)
                .asRequired(getTranslation("validation.title"))
                .withValidator(value -> !value.isBlank(),getTranslation("validation.title"))
                .bind(Book::getTitle, Book::setTitle);

        binder.forField(author)
                .asRequired(getTranslation("validation.author"))
                .withValidator(value -> !value.isBlank(),getTranslation("validation.author"))
                .bind(Book::getAuthor, Book::setAuthor);

        binder.forField(year)
                .asRequired(getTranslation("validation.year.required"))
                .withValidator(
                        value -> value != null && value >= 1450 && value <= 2026,
                        getTranslation("validation.year.range")
                )
                .bind(Book::getPublicationYear, Book::setPublicationYear);

        binder.forField(category)
                .asRequired(getTranslation("validation.category"))
                .bind(Book::getCategory, Book::setCategory);

        binder.forField(loanable)
                .bind(Book::isLoanable, Book::setLoanable);


        FormLayout formLayout = new FormLayout(title, author, year, category, loanable);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0",2));
        formLayout.setWidth("100%");
        formLayout.setMaxWidth("300px");
        add(formLayout, new Button(getTranslation("action.save") , e -> save()),
                new Button(getTranslation("action.cancel"), e-> UI.getCurrent().navigate(BookListView.class)));


    }

    private void save() {
        if (this.binder.writeBeanIfValid(this.book)) {
            this.bookService.save(this.book);
            UI.getCurrent().navigate(BookListView.class);
        }
    }


    @Override
    public String getPageTitle() {
        return this.getTranslation(this.book == null || this.book.getId() == null ? "title.new" : "title.edit");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        var idParam = event.getRouteParameters().get("id");

        if (idParam.isPresent()) {
            // JAVÍTVA (Codex): hibás számformátumnál kivétel keletkezik, nem üres Optional.
            // A túl nagy, Long típusban nem ábrázolható számot is ez az ág kezeli.
            Long id;
            try {
                id = Long.valueOf(idParam.get());
            } catch (NumberFormatException exception) {
                Notification.show(getTranslation("book.notfound"));
                event.forwardTo(BookListView.class);
                return;
            }

            // 2. Számszerű, de nem létező azonosító kivédése
            var bookOpt = this.bookService.findById(id);
            if (bookOpt.isEmpty()) {
                Notification.show(getTranslation("book.notfound"));
                event.forwardTo(BookListView.class);
                return;
            }

            this.book = bookOpt.get();
        } else {
            // Új könyv ág
            this.book = new Book();
        }

        // Itt kell felolvasni az adatokat a mezőkbe
        this.binder.readBean(this.book);
    }
}
