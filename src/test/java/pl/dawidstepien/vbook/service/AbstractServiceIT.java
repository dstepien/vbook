package pl.dawidstepien.vbook.service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public abstract class AbstractServiceIT {

  @Deployment
  public static JavaArchive createTestArchive() {
    return ShrinkWrap.create(JavaArchive.class)
            .addPackages(true, "pl.dawidstepien.vbook")
            .addAsResource("persistence.xml", "META-INF/persistence.xml")
            .addAsResource("books.yml", "books.yml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }
}
