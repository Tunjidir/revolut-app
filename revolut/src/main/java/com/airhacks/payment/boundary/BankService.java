package com.airhacks.payment.boundary;

import com.airhacks.payment.control.TransferProcessor;
import com.airhacks.payment.entity.Account;
import com.airhacks.payment.entity.TransferDetails;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author olatunji oniyide
 */
@Stateless
public class BankService {
  
  @Inject
  TransferProcessor tp;
  
  @PersistenceContext
  EntityManager em;
  
  public TransferDetails processPayment(TransferDetails details) {
    return this.tp.process(details);
  }
  
  public Account registerAccount(Account account) {
    return this.em.merge(account);
  }
  
  public TransferDetails cancelTransaction(Long transferId) {
    return this.tp.cancelTransaction(transferId);
  }
}
