package pl.dawidstepien.vbook.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pl.dawidstepien.vbook.model.BookEntity;
import pl.dawidstepien.vbook.model.book.BookStatus;

@Stateful
public class CheckoutBean {

  @Inject
  private EntityManager entityManager;

  List<BookEntity> pendingList = new ArrayList<>();

  public void addBookToPendingList(BookEntity book) {
    pendingList.add(book);
  }

  public List<BookEntity> getPendingList() {
    return pendingList;
  }

  @Remove
  public void checkOut() {
    for(BookEntity book : pendingList) {
      book.setStatus(BookStatus.BORROWED);
    }
    pendingList.clear();
  }
}
