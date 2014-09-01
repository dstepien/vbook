package pl.dawidstepien.vbook.service;

import static pl.dawidstepien.vbook.model.BookEntity.FIND_ALL;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.dawidstepien.vbook.model.BookEntity;

@Stateless
public class BookService {

  @PersistenceContext(unitName = "vbookPersistenceUnit")
  private EntityManager entityManager;

  public List<BookEntity> findBooks() {
    return entityManager.createNamedQuery(FIND_ALL, BookEntity.class).getResultList();
  }

  public BookEntity createBook(BookEntity book) {
    entityManager.persist(book);
    return book;
  }
}
