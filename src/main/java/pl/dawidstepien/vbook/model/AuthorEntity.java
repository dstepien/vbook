package pl.dawidstepien.vbook.model;

import static pl.dawidstepien.vbook.model.AuthorEntity.FIND_ALL_AUTHORS;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authors")
@NamedQuery(name = FIND_ALL_AUTHORS, query = "SELECT author FROM AuthorEntity author")
public class AuthorEntity implements Serializable {

  public static final String FIND_ALL_AUTHORS = "AuthorEntity.findAllAuthors";

  @Id @GeneratedValue
  private long id;

  @NotNull
  @Column(nullable = false)
  private String name;

  @ManyToMany(mappedBy = "authors", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
  private List<BookEntity> books;

  public AuthorEntity() {}

  public AuthorEntity(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<BookEntity> getBooks() {
    return books;
  }

  public void setBooks(List<BookEntity> books) {
    this.books = books;
  }
}
