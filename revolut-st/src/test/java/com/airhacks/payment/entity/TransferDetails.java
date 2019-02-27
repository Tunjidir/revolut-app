package com.airhacks.payment.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * copied on purpose from revolut for true decoupling
 *
 * @author olatunji oniyide
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TransferDetails {
  
  private Long id;
  private Account sender;
  private Account receiver;
  
  private TransferStatus status = TransferStatus.PROCESSING;
  
  private BigDecimal amount;
  private Date timestamp;

  public TransferDetails(Account sender, Account receiver, BigDecimal amount, Date timestamp) {
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
    this.timestamp = timestamp;
  }
  
  public TransferDetails() {
    
  }
  
  public Long getId() {
    return id;
  }
  
  public Account getSender() {
    return sender;
  }
  
  public void setSender(Account sender) {
    this.sender = sender;
  }
  
  public Account getReceiver() {
    return receiver;
  }
  
  public void setReceiver(Account receiver) {
    this.receiver = receiver;
  }
  
  public BigDecimal getAmount() {
    return amount;
  }
  
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
  
  public Date getTimeStamp() {
    return timestamp;
  }
  
  public void setTimeStamp(Date timeStamp) {
    this.timestamp = timeStamp;
  }
  
  public TransferStatus getStatus() {
    return status;
  }
  
  public void setStatus(TransferStatus status) {
    this.status = status;
  }
  
  @Override
  public String toString() {
    return "";
  }
}
