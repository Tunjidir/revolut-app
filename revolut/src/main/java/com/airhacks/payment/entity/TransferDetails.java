package com.airhacks.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author olatunji oniyide
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TransferDetails implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = IDENTITY)
  Long id;
  
  @OneToOne
  @JoinColumn(name = "sender_account_number")
  private Account sender;
  
  @OneToOne
  @JoinColumn(name = "receiver_account_number")
  private Account receiver;
  
  @Enumerated(EnumType.STRING)
  private TransferStatus status = TransferStatus.PROCESSING;
  
  private BigDecimal amount;
  
  @Temporal(TemporalType.DATE)
  private Date timestamp;
  
  @PostPersist
  public void updateStatus() {
    status = TransferStatus.SUCCESSFUL;
  }

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
    return "TransferDetails{" +
        "id=" + id +
        ", sender='" + sender + '\'' +
        ", receiver='" + receiver + '\'' +
        ", amount='" + amount + '\'' +
        ", status=" + status +
        ", timestamp=" + timestamp +
        '}';
  }
}
