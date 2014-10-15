package pl.dawidstepien.vbook.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.dawidstepien.vbook.model.AuthorEntity;
import pl.dawidstepien.vbook.model.BookEntity;
import pl.dawidstepien.vbook.model.book.BookStatus;

@RunWith(Arquillian.class)
public class CatalogBeanIT extends AbstractBeanIT {

  @Inject
  private CatalogBean catalogBean;

  @Test
  @UsingDataSet("books.yml")
  public void shouldFindAllBooks() {
    // when
    List<BookEntity> books = catalogBean.findAllBooks();

    // then
    assertEquals(3, books.size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldFindBookById() {
    // when
    BookEntity book = catalogBean.findBook(1L);

    // then
    assertNotNull(book);
    assertEquals("Short fairy tales", book.getTitle());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldFindAllBooksByAuthorId() {
    // when
    List<BookEntity> books = catalogBean.findBooksByAuthor(1L);

    // then
    assertNotNull(books);
    assertEquals(1, books.size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldCreateNewBook() {
    // given
    int currentBookNumber = catalogBean.findAllBooks().size();
    BookEntity book = createNewBookEntity();

    // when
    catalogBean.createBook(book);

    // then
    assertEquals(currentBookNumber + 1, catalogBean.findAllBooks().size());
  }

  private BookEntity createNewBookEntity() {
    BookEntity book = new BookEntity();

    book.setAuthor(entityManager.find(AuthorEntity.class, 1L));
    book.setCover("cover");
    book.setDescription("Lorem ipsum");
    book.setIsbn("123-123-123");
    book.setNumberOfPages(120);
    book.setStatus(BookStatus.AVAILABLE);
    book.setTitle("My new book");

    return book;
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldRemoveBook() {
    // given
    int currentBookNumber = catalogBean.findAllBooks().size();
    BookEntity book = catalogBean.findBook(1L);

    // when
    catalogBean.removeBook(book);

    // then
    assertEquals(currentBookNumber - 1, catalogBean.findAllBooks().size());
    assertEquals(3, catalogBean.findAllAuthors().size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldFindAllAuthors() {
    // when
    List<AuthorEntity> authors = catalogBean.findAllAuthors();

    // then
    assertEquals(3, authors.size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldCreateNewAuthor() {
    // given
    int currentAuthorNumber = catalogBean.findAllAuthors().size();
    AuthorEntity author = new AuthorEntity("Jenifer Cole");

    // when
    catalogBean.createAuthor(author);

    // then
    assertEquals(currentAuthorNumber + 1, catalogBean.findAllAuthors().size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldNotCreateAuthorIfExists() {
    // given
    int currentAuthorNumber = catalogBean.findAllAuthors().size();
    AuthorEntity author = new AuthorEntity("John Doe");

    // when
    catalogBean.createAuthor(author);

    // then
    assertEquals(currentAuthorNumber, catalogBean.findAllAuthors().size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldRemoveAuthorWithBooks() {
    // given
    int currentAuthorNumber = catalogBean.findAllAuthors().size();
    AuthorEntity author = entityManager.find(AuthorEntity.class, 1L);

    // when
    catalogBean.removeAuthor(author);

    // then
    assertEquals(currentAuthorNumber - 1, catalogBean.findAllAuthors().size());
    assertEquals(2, catalogBean.findAllBooks().size());
  }

  @Test
  @UsingDataSet("books.yml")
  public void shouldUpdateBookDetails() {
    // given
    BookEntity book = catalogBean.findBook(1L);
    String newBookTitle = "Dark Night";

    // when
    book.setTitle(newBookTitle);
    catalogBean.updateBook(book);
    book = catalogBean.findBook(1L);

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
    catalogBean.updateAuthor(author);
    author = entityManager.find(AuthorEntity.class, 1L);

    // then
    assertEquals(newAuthorName, author.getName());
  }
}
