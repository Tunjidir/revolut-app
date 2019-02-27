package com.airhacks.payment.boundary;

import com.airhacks.payment.entity.Account;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author olatunji oniyide
 */
public class BankServiceTest {
  
  BankService cut;
  
  @Before
  public void setup() {
    this.cut = new BankService();
    this.cut.em = mock(EntityManager.class);
  }
  
  @Test
  public void registerAccount() {
    Account account = new Account(UUID.randomUUID(), "Olatunji Oniyide", new BigDecimal(10000.00));
    merge(account);
    this.cut.registerAccount(account);
    verify(this.cut.em).merge(account);
  }
  
  void merge(Account account) {
    when(this.cut.em.merge(account)).thenReturn(account);
  }
}
