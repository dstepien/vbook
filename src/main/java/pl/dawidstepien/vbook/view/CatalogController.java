package pl.dawidstepien.vbook.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.dawidstepien.vbook.bean.CatalogBean;
import pl.dawidstepien.vbook.model.AuthorEntity;
import pl.dawidstepien.vbook.model.BookEntity;
import pl.dawidstepien.vbook.model.book.BookStatus;

@Named
@RequestScoped
public class CatalogController {

  @Inject
  private CatalogBean catalog;

  @Inject
  private BookEntity book;

  @Inject
  private AuthorEntity author;

  public String doCreateBook() {
    book.setAuthor(author);
    book.setStatus(BookStatus.AVAILABLE);
    book.setCover("cover.jpg");
    catalog.createBook(book);
    return "newBook.xhtml";
  }

  public void doFindBookById() {
    book = catalog.findBook(book.getId());
  }

  public BookEntity getBook() {
    return book;
  }

  public void setBook(BookEntity book) {
    this.book = book;
  }

  public AuthorEntity getAuthor() {
    return author;
  }

  public void setAuthor(AuthorEntity author) {
    this.author = author;
  }
}
