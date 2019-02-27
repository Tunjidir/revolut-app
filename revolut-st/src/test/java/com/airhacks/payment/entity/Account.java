package com.airhacks.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *copied on purpose from revolut for true decoupling
 * 
 * @author olatunji oniyide
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Account implements Serializable{
  
  private String accountNumber;
  private String accountName;
  private BigDecimal balance;

  public Account(UUID accountNumber, String accountName, BigDecimal balance) {
    this.accountNumber = accountNumber.toString();
    this.accountName = accountName;
    this.balance = balance;
  }
  
  public Account() {
  }
  
  public String getAccountNumber() {
    return accountNumber;
  }
  
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }
  
  public String getAccountName() {
    return accountName;
  }
  
  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }
  
  public BigDecimal getAccountBalance() {
    return balance;
  }
  
  public void setAccountBalance(BigDecimal balance) {
    this.balance = balance;
  }

}
