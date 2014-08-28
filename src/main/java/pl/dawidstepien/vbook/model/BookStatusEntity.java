package pl.dawidstepien.vbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import pl.dawidstepien.vbook.model.book.BookStatus;

@Entity
public class BookStatusEntity {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BookStatus status;

  public BookStatus getStatus() {
    return status;
  }

  public void setStatus(BookStatus status) {
    this.status = status;
  }
}
