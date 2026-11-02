package hu.mik.pte.prog5.library.service;

import hu.mik.pte.prog5.library.entity.Book;
import hu.mik.pte.prog5.library.entity.BookCategory;
import hu.mik.pte.prog5.library.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

   public BookServiceImpl(@Qualifier("mock")BookRepository bookRepository) {
       this.bookRepository = bookRepository;

   }

    @PreAuthorize("isAuthenticated()")
    @PostFilter("@securityService.getRolesAsString().contains(filterObject.category.name())")
    @Override
    public List<Book> listAll() {
        return this.bookRepository.findAll();
    }

    // JAVÍTVA (Codex): a service-szintű lekérés is bejelentkezést igényel, a nézettől függetlenül.
    @PreAuthorize("isAuthenticated()")
    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @PreAuthorize("hasRole('MANAGE')")
    @Override
    public Book save(Book book) {
        return this.bookRepository.save(book);
    }

    @PreAuthorize("hasRole('DELETE')")
    @Override
    public void deleteById(Long id) {
       this.bookRepository.deleteById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public List<BookCategory> listCategories() {
        return Arrays.asList(BookCategory.values());
    }
}
