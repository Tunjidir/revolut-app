package com.airhacks.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
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
public class PaymentDetailsIT {
  
  EntityManager em;
  EntityTransaction tx;
  
  @Before
  public void initEM() {
    this.em = Persistence.createEntityManagerFactory("integration-test").createEntityManager();
    this.tx = this.em.getTransaction();
  }
  
  @Test
  public void validateORM() {
    LocalDateTime now = LocalDateTime.now();
    Date date = Date.from(now.toInstant(ZoneOffset.UTC));
    
    tx.begin();
    
    Account sender = new Account(UUID.randomUUID(), "Olatunji Oniyide", new BigDecimal(10000.00));
    Account receiver = new Account(UUID.randomUUID(), "Andrew Mbata", new BigDecimal(10000.00));
    TransferDetails details = new TransferDetails(sender, receiver, new BigDecimal(10000.00), date);
    
    em.merge(sender);
    em.merge(receiver);
    em.merge(details);
    
    tx.commit();
  }
  
}
