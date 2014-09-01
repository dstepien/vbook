package pl.dawidstepien.vbook;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.dawidstepien.vbook.model.AuthorEntity;
import pl.dawidstepien.vbook.model.BookEntity;
import pl.dawidstepien.vbook.model.BookStatusEntity;
import pl.dawidstepien.vbook.model.book.BookStatus;
import pl.dawidstepien.vbook.service.BookService;

@RunWith(Arquillian.class)
public class BookServiceIT {

  @PersistenceContext
  EntityManager entityManager;

  @Inject
  UserTransaction userTransaction;

  @Inject
  private BookService bookService;

  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class, "test.jar")
            .addPackage(BookEntity.class.getPackage())
            .addClass(BookService.class)
            .addClass(BookStatus.class)
            .addAsResource("persistence.xml", "META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @Before
  public void preparePersistenceTest() throws Exception {
    clearData();
    startTransaction();
  }

  private void clearData() throws Exception {
    userTransaction.begin();
    entityManager.joinTransaction();

    System.out.println("Dumping old records...");

    entityManager.createQuery("DELETE FROM BookEntity").executeUpdate();
    userTransaction.commit();
  }

  private void startTransaction() throws Exception {
    userTransaction.begin();
    entityManager.joinTransaction();
  }

  @After
  public void commitTransaction() throws Exception {
    userTransaction.commit();
  }

  @Test
  public void shouldCreateBook() {
    // given
    BookEntity book = getBookEntity();

    // when
    bookService.createBook(book);

    // then
    assertNotNull(book.getId());
  }

  private BookEntity getBookEntity() {
    BookEntity book = new BookEntity();

    book.setAuthors(getAuthors());
    book.setCover("cover");
    book.setDescription("Lorem ipsum");
    book.setIsbn("123-123-123");
    book.setNumberOfPages(100);
    book.setStatus(getStatus());
    book.setTitle("Short fairy tale");

    return book;
  }

  private List<AuthorEntity> getAuthors() {
    AuthorEntity author = new AuthorEntity("John Doe");
    List<AuthorEntity> authors = new ArrayList<>();
    authors.add(author);
    entityManager.persist(author);
    return authors;
  }

  private BookStatusEntity getStatus() {
    BookStatusEntity bookStatus = new BookStatusEntity(BookStatus.AVAILABLE);
    entityManager.persist(bookStatus);
    return bookStatus;
  }
}
