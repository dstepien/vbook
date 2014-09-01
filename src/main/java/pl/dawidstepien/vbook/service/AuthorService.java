package pl.dawidstepien.vbook.service;

import static pl.dawidstepien.vbook.model.AuthorEntity.FIND_ALL;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.dawidstepien.vbook.model.AuthorEntity;

@Stateless
public class AuthorService {

  @PersistenceContext(unitName = "vbookPersistenceUnit")
  private EntityManager entityManager;

  public List<AuthorEntity> findAuthors() {
    return entityManager.createNamedQuery(FIND_ALL, AuthorEntity.class).getResultList();
  }

  public AuthorEntity createBook(AuthorEntity author) {
    entityManager.persist(author);
    return author;
  }
}