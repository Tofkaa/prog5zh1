package hu.mik.pte.prog5.library.repository;
import hu.mik.pte.prog5.library.entity.Book;
import hu.mik.pte.prog5.library.entity.BookCategory;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository("mock")
public class InMemoryBookRepository implements BookRepository {
    private final Map<Long, Book> books = new LinkedHashMap<>();
    private final AtomicLong sequence = new AtomicLong();
    public InMemoryBookRepository() {
        save(new Book(null, "A Pál utcai fiúk", "Molnár Ferenc", 1907, BookCategory.FICTION, true));
        save(new Book(null, "Az idő rövid története", "Stephen Hawking", 1988, BookCategory.SCIENCE, true));
        save(new Book(null, "Egri csillagok", "Gárdonyi Géza", 1901, BookCategory.FICTION, false));
        save(new Book(null, "Történelmi atlasz", "Szerkesztőbizottság", 2020, BookCategory.HISTORY, false));
        save(new Book(null, "Csillagászati kézikönyv", "Kiss Anna", 2024, BookCategory.SCIENCE, true));
    }
    // Másolatok: a megnyitott űrlap csak save híváskor változtatja meg a tárolt rekordot.
    @Override public List<Book> findAll() {
        List<Book> result = new ArrayList<>();
        books.values().forEach(book -> result.add(new Book(book)));
        return result;
    }
    @Override public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id)).map(Book::new);
    }
    @Override public Book save(Book book) {
        Book copy = new Book(book);
        if (copy.getId() == null) copy.setId(sequence.incrementAndGet());
        else if (!books.containsKey(copy.getId())) throw new IllegalArgumentException("Unknown book id: " + copy.getId());
        books.put(copy.getId(), copy);
        return new Book(copy);
    }
    @Override public void deleteById(Long id) { books.remove(id); }
}
