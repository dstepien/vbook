package pl.dawidstepien.vbook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "books")
@NamedQueries({
  @NamedQuery(name = BookEntity.FIND_ALL_BOOKS, query = "SELECT book FROM BookEntity book"),
  @NamedQuery(name = BookEntity.FIND_ALL_BOOKS_BY_AUTHOR, query = "SELECT book FROM BookEntity book JOIN book.authors authors WHERE authors.id = :id")
})
public class BookEntity implements Serializable {

  public static final String FIND_ALL_BOOKS = "BookEntity.findAllBooks";
  public static final String FIND_ALL_BOOKS_BY_AUTHOR = "BookEntity.findBookByAuthor";

  @Id @GeneratedValue
  private long id;

  @NotNull
  @Column(nullable = false)
  private String title;

  @NotNull
  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "author_id"))
  @OrderBy("name ASC")
  private List<AuthorEntity> authors;

  @NotNull
  @Column(nullable = false)
  private String isbn;

  @NotNull
  @Column(nullable = false, name = "number_of_pages")
  private int numberOfPages;

  @NotNull
  @Column(nullable = false)
  private String description;

  @NotNull
  @Column(nullable = false)
  private String cover;

  @NotNull
  @OneToOne
  @JoinColumn(nullable = false)
  private BookStatusEntity status;

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<AuthorEntity> getAuthors() {
    return authors;
  }

  public void setAuthor(AuthorEntity author) {
    setAuthors(new ArrayList<>(Arrays.asList(author)));
  }

  public void setAuthors(List<AuthorEntity> authors) {
    this.authors = authors;
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

  public BookStatusEntity getStatus() {
    return status;
  }

  public void setStatus(BookStatusEntity status) {
    this.status = status;
  }
}
