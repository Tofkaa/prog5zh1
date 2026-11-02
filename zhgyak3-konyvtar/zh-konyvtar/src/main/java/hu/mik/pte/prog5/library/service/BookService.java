package hu.mik.pte.prog5.library.service;
import hu.mik.pte.prog5.library.entity.*;
import java.util.List;
import java.util.Optional;
public interface BookService {
    List<Book> listAll();
    Optional<Book> findById(Long id);
    Book save(Book book);
    void deleteById(Long id);
    List<BookCategory> listCategories();
}
