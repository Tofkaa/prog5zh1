package hu.mik.pte.prog5.library.repository;
import hu.mik.pte.prog5.library.entity.Book;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository("db")
public class DbBookRepository implements BookRepository {
    @Override public List<Book> findAll() { throw new UnsupportedOperationException(); }
    @Override public Optional<Book> findById(Long id) { throw new UnsupportedOperationException(); }
    @Override public Book save(Book book) { throw new UnsupportedOperationException(); }
    @Override public void deleteById(Long id) { throw new UnsupportedOperationException(); }
}
