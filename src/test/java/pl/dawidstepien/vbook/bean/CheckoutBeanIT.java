package pl.dawidstepien.vbook.bean;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.dawidstepien.vbook.model.BookEntity;
import pl.dawidstepien.vbook.model.book.BookStatus;

@RunWith(Arquillian.class)
public class CheckoutBeanIT extends AbstractBeanIT {

  @Inject
  private CheckoutBean checkoutBean;

  @Test
  @UsingDataSet("books.yml")
  public void shouldAddBooksToPendingList() {
    // given
    BookEntity firstBook = entityManager.find(BookEntity.class, 1L);
    BookEntity secondBook = entityManager.find(BookEntity.class, 2L);

    // when
    checkoutBean.addBookToPendingList(firstBook);
    checkoutBean.addBookToPendingList(secondBook);

    // then
    assertEquals(2, checkoutBean.getPendingList().size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldSetStatusBorrowedForAllPendingBooks() {
    // given
    BookEntity book = entityManager.find(BookEntity.class, 1L);
    checkoutBean.addBookToPendingList(book);

    // when
    checkoutBean.checkOut();
    book = entityManager.find(BookEntity.class, 1L);

    // then
    assertEquals(BookStatus.BORROWED, book.getStatus());
  }
}
