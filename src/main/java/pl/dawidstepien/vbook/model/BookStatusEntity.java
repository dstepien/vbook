package pl.dawidstepien.vbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pl.dawidstepien.vbook.model.book.BookStatus;

@Entity
@Table(name = "book_statuses")
@NamedQuery(name = BookStatusEntity.GET_STATUS, query = "SELECT bookStatus FROM BookStatusEntity bookStatus WHERE status = :status")
public class BookStatusEntity {

  public static final String GET_STATUS = "BookStatusEntity.getStatus";

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
