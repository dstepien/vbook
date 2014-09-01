package pl.dawidstepien.vbook;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
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
import pl.dawidstepien.vbook.model.book.BookStatus;
import pl.dawidstepien.vbook.service.AuthorService;
import pl.dawidstepien.vbook.util.LoggerProducer;

@RunWith(Arquillian.class)
public class AuthorServiceIT {

  @PersistenceContext
  EntityManager entityManager;

  @Inject
  UserTransaction userTransaction;

  @Inject
  private AuthorService authorService;

  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class, "test.jar")
            .addPackage(BookEntity.class.getPackage())
            .addPackage(LoggerProducer.class.getPackage())
            .addClass(AuthorService.class)
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

    entityManager.createQuery("DELETE FROM AuthorEntity").executeUpdate();
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
  public void shouldCreateAuthor() {
    // given
    AuthorEntity author = new AuthorEntity("John Doe");

    // when
    authorService.createBook(author);

    // then
    assertAuthorCreated(author);
  }

  private void assertAuthorCreated(AuthorEntity author) {
    CriteriaQuery<AuthorEntity> query = entityManager.getCriteriaBuilder().createQuery(AuthorEntity.class);
    query.select(query.from(AuthorEntity.class));
    assertEquals(author.getName(), entityManager.createQuery(query).getSingleResult().getName());
  }

  @Test
  public void shouldFindAllAuthors() {
    // given
    List<AuthorEntity> newAuthors = getAuthors();
    persistAuthors(newAuthors);

    // when
    List<AuthorEntity> authors = authorService.findAuthors();

    // then
    assertEquals(newAuthors.size(), authors.size());
  }

  public List<AuthorEntity> getAuthors() {
    List<AuthorEntity> authors = new ArrayList<>();

    authors.add(new AuthorEntity("John Doe"));
    authors.add(new AuthorEntity("Michael Brown"));
    authors.add(new AuthorEntity("William Taylor"));

    return authors;
  }

  private void persistAuthors(List<AuthorEntity> authors) {
    for(AuthorEntity author : authors) {
      entityManager.persist(author);
    }
  }
}
