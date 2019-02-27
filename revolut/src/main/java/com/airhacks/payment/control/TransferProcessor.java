package com.airhacks.payment.control;

import com.airhacks.payment.entity.Account;
import com.airhacks.payment.entity.TransferDetails;
import com.airhacks.payment.entity.TransferStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author olatunji oniyide
 */
@ApplicationScoped
public class TransferProcessor {
  
  @PersistenceContext
  EntityManager manager;
  
  public TransferDetails process(TransferDetails details) throws BankException {
    Account sender  = manager.find(Account.class, details.getSender().getAccountNumber());
    Account receiver = manager.find(Account.class, details.getReceiver().getAccountNumber());
    
    if(sender == receiver && !isTimeStampValid(details.getTimeStamp()) || (sender == null && receiver == null)) {
      throw new BankException("invalid details");
    }
    
    long balance = sender.getAccountBalance().longValue();
    long amountToSend = details.getAmount().longValue();
    
    if(amountToSend > balance) {
      throw new BankException("low account balance");
    }
    
    long deducted = balance - amountToSend;
    sender.setAccountBalance(new BigDecimal(deducted));
    manager.merge(sender);
    
    long credited = (receiver.getAccountBalance().longValue()) + amountToSend;
    receiver.setAccountBalance(new BigDecimal(credited));
    manager.merge(receiver);
    
    return this.manager.merge(details);
  }
  
  public boolean isTimeStampValid(Date timestamp) {
    final LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
    final LocalDateTime transactionTime = LocalDateTime.from(timestamp.toInstant().atZone(ZoneId.of("UTC")));
    return !transactionTime.isAfter(now);
  }
  
  public TransferDetails cancelTransaction(Long transferId) {
    TransferDetails details = this.manager.find(TransferDetails.class, transferId);
    Account sender = details.getSender();
    Account receiver = details.getReceiver();
    
    BigDecimal senderBalance = sender.getAccountBalance().add(details.getAmount());
    BigDecimal receiverBalance = receiver.getAccountBalance().subtract(details.getAmount());
    
    sender.setAccountBalance(senderBalance);
    receiver.setAccountBalance(receiverBalance);
    details.setStatus(TransferStatus.CANCELLED);
    
    this.manager.merge(sender);
    this.manager.merge(receiver);
    return this.manager.merge(details);
  }
}
