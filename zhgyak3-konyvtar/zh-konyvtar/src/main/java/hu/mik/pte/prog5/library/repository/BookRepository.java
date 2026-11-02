package hu.mik.pte.prog5.library.repository;
import hu.mik.pte.prog5.library.entity.Book;
import java.util.List;
import java.util.Optional;
public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book save(Book book);
    void deleteById(Long id);
}
