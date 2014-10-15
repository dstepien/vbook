package pl.dawidstepien.vbook.bean;

import static pl.dawidstepien.vbook.model.BookEntity.FIND_ALL_BOOKS;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import pl.dawidstepien.vbook.model.BookEntity;

@Stateless
public class BookService {

//  @Inject
  private EntityManager entityManager;

  public List<BookEntity> findBooks() {
    return entityManager.createNamedQuery(FIND_ALL_BOOKS, BookEntity.class).getResultList();
  }

  public BookEntity createBook(BookEntity book) {
    entityManager.persist(book);
    return book;
  }
}
