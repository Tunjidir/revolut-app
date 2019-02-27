package com.airhacks.payment.entity;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author olatunji oniyide
 */
public class AccountIT {
  
  EntityManager em;
  EntityTransaction tx;
  
  @Before
  public void initEM() {
    this.em = Persistence.createEntityManagerFactory("integration-test").createEntityManager();
    this.tx = this.em.getTransaction();
  }
  
  @Test
  public void validateORMapping() {
    this.tx.begin();
    this.em.merge(new Account(UUID.randomUUID(), "Olatunji oniyide", new BigDecimal(10000.00)));
    this.tx.commit();
  }
}
