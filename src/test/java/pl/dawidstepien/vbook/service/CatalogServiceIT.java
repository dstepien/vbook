package pl.dawidstepien.vbook.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.dawidstepien.vbook.model.AuthorEntity;
import pl.dawidstepien.vbook.model.BookEntity;
import pl.dawidstepien.vbook.model.BookStatusEntity;

@RunWith(Arquillian.class)
public class CatalogServiceIT extends AbstractServiceIT {

  @Inject
  private CatalogService catalogService;

  @Inject
  private BookStatusService bookStatusService;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  @UsingDataSet("books.yml")
  public void shouldFindAllBooks() {
    // when
    List<BookEntity> books = catalogService.findAllBooks();

    // then
    assertEquals(3, books.size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldFindBookById() {
    // when
    BookEntity book = catalogService.findBook(1L);

    // then
    assertNotNull(book);
    assertEquals("Short fairy tales", book.getTitle());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldFindAllBooksByAuthorId() {
    // when
    List<BookEntity> books = catalogService.findBooksByAuthor(1L);

    // then
    assertNotNull(books);
    assertEquals(1, books.size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldCreateNewBook() {
    // given
    int currentBookNumber = catalogService.findAllBooks().size();
    BookEntity book = createNewBookEntity();

    // when
    catalogService.createBook(book);

    // then
    assertEquals(currentBookNumber + 1, catalogService.findAllBooks().size());
  }

  private BookEntity createNewBookEntity() {
    BookEntity book = new BookEntity();

    book.setAuthors(new ArrayList<>(Arrays.asList(entityManager.find(AuthorEntity.class, 1L))));
    book.setCover("cover");
    book.setDescription("Lorem ipsum");
    book.setIsbn("123-123-123");
    book.setNumberOfPages(120);
    book.setStatus(entityManager.find(BookStatusEntity.class, 1));
    book.setTitle("My new book");

    return book;
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldRemoveBook() {
    // given
    int currentBookNumber = catalogService.findAllBooks().size();
    BookEntity book = catalogService.findBook(1L);

    // when
    catalogService.removeBook(book);

    // then
    assertEquals(currentBookNumber - 1, catalogService.findAllBooks().size());
    assertEquals(3, catalogService.findAllAuthors().size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldFindAllAuthors() {
    // when
    List<AuthorEntity> authors = catalogService.findAllAuthors();

    // then
    assertEquals(3, authors.size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldCreateNewAuthor() {
    // given
    int currentAuthorNumber = catalogService.findAllAuthors().size();
    AuthorEntity author = new AuthorEntity("Jenifer Cole");

    // when
    catalogService.createAuthor(author);

    // then
    assertEquals(currentAuthorNumber + 1, catalogService.findAllAuthors().size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldRemoveAuthorWithBooks() {
    // given
    int currentAuthorNumber = catalogService.findAllAuthors().size();
    AuthorEntity author = entityManager.find(AuthorEntity.class, 1L);

    // when
    catalogService.removeAuthor(author);

    // then
    assertEquals(currentAuthorNumber - 1, catalogService.findAllAuthors().size());
    assertEquals(2, catalogService.findAllBooks().size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldUpdateBookDetails() {
    // given
    BookEntity book = catalogService.findBook(1L);
    String newBookTitle = "Dark Night";

    // when
    book.setTitle(newBookTitle);
    catalogService.updateBook(book);
    book = catalogService.findBook(1L);

    // then
    assertEquals(newBookTitle, book.getTitle());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldUpdateAuthorDetails() {
    // given
    AuthorEntity author = entityManager.find(AuthorEntity.class, 1L);
    String newAuthorName = "Mark Thompson";

    // when
    author.setName(newAuthorName);
    catalogService.updateAuthor(author);
    author = entityManager.find(AuthorEntity.class, 1L);

    // then
    assertEquals(newAuthorName, author.getName());
  }
}
