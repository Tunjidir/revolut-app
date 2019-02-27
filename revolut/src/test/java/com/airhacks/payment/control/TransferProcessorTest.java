package com.airhacks.payment.control;

import com.airhacks.payment.entity.Account;
import com.airhacks.payment.entity.TransferDetails;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;
import javax.persistence.EntityManager;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author olatunji oniyide
 */
public class TransferProcessorTest {
  
  TransferProcessor cut;
  
  @Before
  public void setup() {
    this.cut = new TransferProcessor();
    this.cut.manager = mock(EntityManager.class);
  }
  
  @Test
  public void processPayment() {
    LocalDateTime now = LocalDateTime.now();
    Date date = Date.from(now.toInstant(ZoneOffset.UTC));
    
    Account sender = new Account(UUID.randomUUID(), "Olatunji Oniyide", new BigDecimal(10000.00));
    Account receiver = new Account(UUID.randomUUID(), "Andrew Mbata", new BigDecimal(10000.00));
    TransferDetails details = new TransferDetails(sender, receiver, new BigDecimal(10000.00), date);
    
    when(this.cut.manager.find(eq(Account.class), anyString())).thenReturn(sender, receiver);
    this.cut.process(details);
    merge(sender);
    merge(receiver);
    merge(details);
    
    verify(this.cut.manager, times(2)).merge(any(Account.class));
    verify(this.cut.manager, times(1)).merge(details);
    org.junit.Assert.assertEquals(BigDecimal.ZERO, sender.getAccountBalance());
  }
  
  @Test(expected = BankException.class)
  public void processPayment_AmountExceeded() {
    LocalDateTime now = LocalDateTime.now();
    Date date = Date.from(now.toInstant(ZoneOffset.UTC));
    
    Account sender = new Account(UUID.randomUUID(), "Olatunji Oniyide", new BigDecimal(10000.00));
    Account receiver = new Account(UUID.randomUUID(), "Andrew Mbata", new BigDecimal(10000.00));
    TransferDetails details = new TransferDetails(sender, receiver, new BigDecimal(11000.00), date);
    
    when(this.cut.manager.find(eq(Account.class), anyString())).thenReturn(sender, receiver);
    this.cut.process(details);
  }
  
  @Test(expected = BankException.class)
  public void processPayment_invalidDate() {
   LocalDateTime timeAhead = LocalDateTime.now(ZoneId.of("UTC")).plusDays(1L);
   Date date = Date.from(timeAhead.toInstant(ZoneOffset.UTC));
   
    Account sender = new Account(UUID.randomUUID(), "Olatunji Oniyide", new BigDecimal(10000.00));
    Account receiver = new Account(UUID.randomUUID(), "Andrew Mbata", new BigDecimal(10000.00));
    TransferDetails details = new TransferDetails(sender, receiver, new BigDecimal(11000.00), date);
    
    when(this.cut.manager.find(eq(Account.class), anyString())).thenReturn(sender, receiver);
    this.cut.process(details);
  }
  
  @Test
  public void cancelPayment() {
   LocalDateTime timeAhead = LocalDateTime.now(ZoneId.of("UTC")).plusDays(1L);
   Date date = Date.from(timeAhead.toInstant(ZoneOffset.UTC));
   
    Account sender = new Account(UUID.randomUUID(), "Olatunji Oniyide", new BigDecimal(10000.00));
    Account receiver = new Account(UUID.randomUUID(), "Andrew Mbata", new BigDecimal(9000.00));
    TransferDetails details = new TransferDetails(sender, receiver, new BigDecimal(2000.00), date);
    
    find(details);
    merge(sender);
    merge(receiver);
    this.cut.cancelTransaction(1L);
    
    verify(this.cut.manager, times(2)).merge(any(Account.class));
    assertThat(sender.getAccountBalance(), is(new BigDecimal(12000.00)));
  }
  
  void merge(Account account) {
    when(this.cut.manager.merge(account)).thenReturn(account);
  }
  
  void merge(TransferDetails details) {
    when(this.cut.manager.merge(details)).thenReturn(details);
  }
  
  void find(TransferDetails details) {
    when(this.cut.manager.find(TransferDetails.class, 1L)).thenReturn(details);
  }
}
