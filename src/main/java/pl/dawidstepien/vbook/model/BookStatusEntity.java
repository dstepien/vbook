package pl.dawidstepien.vbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import pl.dawidstepien.vbook.model.book.BookStatus;

@Entity
@Table(name = "books_statuses")
public class BookStatusEntity {

  @Id @GeneratedValue
  private int id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private BookStatus status;

  public BookStatusEntity() {}

  public BookStatusEntity(BookStatus bookStatus) {
    this.status = bookStatus;
  }

  public int getId() {
    return id;
  }

  public BookStatus getStatus() {
    return status;
  }

  public void setStatus(BookStatus status) {
    this.status = status;
  }
}
