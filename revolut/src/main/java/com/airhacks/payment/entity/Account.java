package com.airhacks.payment.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
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
public class Account implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  private String accountNumber;
  private String accountName;
  private BigDecimal balance;

  public Account(UUID accountNumber, String accountName, BigDecimal balance) {
    Objects.requireNonNull(accountNumber);
    Objects.requireNonNull(accountName);
    Objects.requireNonNull(balance);
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
  
  @Override
  public int hashCode() {
    int hash = accountNumber != null? accountNumber.hashCode() : 0;
    hash = hash * 31 + accountName.hashCode();
    hash = hash * 31 + balance.hashCode();
    return hash;
  }
  
  @Override
  public boolean equals(Object other) {
    if(this == other) return true;
    if(!(other instanceof Account)) return false;
    
    Account account = (Account) other;
    if(!Objects.equals(accountNumber, account.accountNumber)) return false;
    if(!Objects.equals(accountName, account.accountName)) return false;
    
    return (Objects.equals(balance, account.balance));
  }
  
  @Override
  public String toString() {
    return "Account{" +
        "AcccountNumber=" + accountNumber +
        ", AccontName='" + accountName + '\'' +
        ", balance=" + balance +
        '}';
  }
}
