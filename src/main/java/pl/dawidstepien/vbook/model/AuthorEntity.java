package pl.dawidstepien.vbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class AuthorEntity {

  @Id @GeneratedValue
  private long id;

  @NotNull
  @Column(nullable = false)
  private String name;

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
