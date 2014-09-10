package pl.dawidstepien.vbook.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import pl.dawidstepien.vbook.model.AuthorEntity;
import pl.dawidstepien.vbook.model.BookEntity;

@Stateless
public class CatalogService {

  @Inject
  private EntityManager entityManager;

  public List<BookEntity> findAllBooks() {
    return entityManager.createNamedQuery(BookEntity.FIND_ALL_BOOKS, BookEntity.class).getResultList();
  }

  public BookEntity createBook(@NotNull BookEntity book) {
    createAuthorsIfNotExists(book.getAuthors());
    entityManager.persist(book);
    return book;
  }

  private void createAuthorsIfNotExists(@NotNull List<AuthorEntity> authors) {
    for(AuthorEntity author : authors) {
      if(author.getId() == 0) {
        entityManager.persist(author);
      }
    }
  }

  public BookEntity findBook(@NotNull long id) {
    return entityManager.find(BookEntity.class, id);
  }

  public void updateBook(@NotNull BookEntity book) {
    entityManager.merge(book);
  }

  public void removeBook(@NotNull BookEntity book) {
    entityManager.remove(entityManager.merge(book));
  }

  public List<BookEntity> findBooksByAuthor(long authorId) {
    TypedQuery<BookEntity> query = entityManager.createNamedQuery(BookEntity.FIND_ALL_BOOKS_BY_AUTHOR, BookEntity.class);
    query.setParameter("id", authorId);
    return query.getResultList();
  }

  public List<AuthorEntity> findAllAuthors() {
    return entityManager.createNamedQuery(AuthorEntity.FIND_ALL_AUTHORS, AuthorEntity.class).getResultList();
  }

  public void createAuthor(AuthorEntity author) {
    entityManager.persist(author);
  }

  public void removeAuthor(AuthorEntity author) {
    entityManager.remove(author);
  }

  public void updateAuthor(AuthorEntity author) {
    entityManager.merge(author);
  }
}
