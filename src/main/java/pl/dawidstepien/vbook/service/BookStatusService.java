package pl.dawidstepien.vbook.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pl.dawidstepien.vbook.model.BookStatusEntity;
import pl.dawidstepien.vbook.model.book.BookStatus;

@Stateless
public class BookStatusService {

  @Inject
  private EntityManager entityManager;

  public BookStatusEntity getBookStatusEntity(BookStatus status) {
    return entityManager.createNamedQuery(BookStatusEntity.GET_STATUS, BookStatusEntity.class)
            .setParameter("status", status).getSingleResult();
  }
}
