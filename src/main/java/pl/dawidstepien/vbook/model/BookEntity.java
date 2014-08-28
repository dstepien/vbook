package pl.dawidstepien.vbook.model;

import static pl.dawidstepien.vbook.model.BookEntity.FIND_ALL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import pl.dawidstepien.vbook.model.book.BookStatus;

@Entity
@NamedQuery(name = FIND_ALL, query = "SELECT book FROM BookEntity book")
public class BookEntity {

  public static final String FIND_ALL = "BookEntity.findAllBooks";

  @Id @GeneratedValue
  private long id;

  @NotNull
  @Column(nullable = false)
  private String title;

  @NotNull
  @Column(nullable = false)
  @ManyToMany
  private AuthorEntity author;

  @NotNull
  @Column(nullable = false)
  private String isbn;

  @NotNull
  @Column(nullable = false)
  private int numberOfPages;

  @NotNull
  @Column(nullable = false)
  private String description;

  @NotNull
  @Column(nullable = false)
  private String cover;

  @NotNull
  @Column(nullable = false)
  private BookStatus status;

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public AuthorEntity getAuthor() {
    return author;
  }

  public void setAuthor(AuthorEntity author) {
    this.author = author;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public int getNumberOfPages() {
    return numberOfPages;
  }

  public void setNumberOfPages(int numberOfPages) {
    this.numberOfPages = numberOfPages;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public BookStatus getStatus() {
    return status;
  }

  public void setStatus(BookStatus status) {
    this.status = status;
  }
}
