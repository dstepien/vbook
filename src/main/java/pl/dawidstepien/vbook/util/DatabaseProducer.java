package pl.dawidstepien.vbook.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DatabaseProducer {

  @Produces
  @PersistenceContext(unitName = "vbookPersistenceUnit")
  private EntityManager entityManager;
}
