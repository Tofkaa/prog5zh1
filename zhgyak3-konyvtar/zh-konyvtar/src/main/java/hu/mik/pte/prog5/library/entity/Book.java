package hu.mik.pte.prog5.library.entity;
public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer publicationYear;
    private BookCategory category;
    private boolean loanable;

    public Book() {}
    public Book(Long id, String title, String author, Integer publicationYear, BookCategory category, boolean loanable) {
        this.id = id; this.title = title; this.author = author;
        this.publicationYear = publicationYear; this.category = category; this.loanable = loanable;
    }
    public Book(Book other) {
        this(other.id, other.title, other.author, other.publicationYear, other.category, other.loanable);
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    public BookCategory getCategory() { return category; }
    public void setCategory(BookCategory category) { this.category = category; }
    public boolean isLoanable() { return loanable; }
    public void setLoanable(boolean loanable) { this.loanable = loanable; }
}
