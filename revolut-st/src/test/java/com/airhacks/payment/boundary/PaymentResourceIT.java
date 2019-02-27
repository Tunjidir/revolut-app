package com.airhacks.payment.boundary;

import com.airhacks.payment.entity.Account;
import com.airhacks.payment.entity.TransferDetails;
import com.airhacks.payment.entity.TransferStatus;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author olatunji oniyide
 */
public class PaymentResourceIT {
  
  private static final String RUT_URI = "http://localhost:8080/revolut/resources/transfer";
  
  private WebTarget rut;
  private Client client;
  
  @Before
  public void setup() {
    client = ClientBuilder.newBuilder().build();
    rut = client.target(RUT_URI);
  }
  
  @Test
  public void transfer() {
    final String account_uri = "http://localhost:8080/revolut/resources/account";
    this.rut = client.target(account_uri);
    
    Account sender = new Account(UUID.randomUUID(), "Olatunji oniyide", BigDecimal.TEN);
    Response senderResponse = this.rut.request(APPLICATION_JSON)
            .header("Content-Type", APPLICATION_XML)
            .post(Entity.entity(sender, APPLICATION_XML));
    
    URI senderAccountNo = senderResponse.getLocation();
    assertNotNull(senderAccountNo);
    assertThat(senderResponse.getStatus(), is(201));
    
    Account receiver = new Account(UUID.randomUUID(), "Andrew Mbata", BigDecimal.ONE);
    Response receiverResponse = this.rut.request(APPLICATION_JSON)
            .header("Content-Type", APPLICATION_XML)
            .post(Entity.entity(receiver, APPLICATION_XML));
    
    URI receiverAccountNo = receiverResponse.getLocation();
    assertNotNull(receiverAccountNo);
    assertThat(senderResponse.getStatus(), is(201));
    
    LocalDateTime now = LocalDateTime.now();
    Date date = Date.from(now.toInstant(ZoneOffset.UTC));
    TransferDetails details = new TransferDetails(sender, receiver, BigDecimal.ONE, date);
    
    this.rut = client.target(RUT_URI);
    Response transferResponse = this.rut.request(APPLICATION_JSON)
            .header("Content-Type", APPLICATION_XML)
            .post(Entity.entity(details,APPLICATION_XML));
    
    URI transferId = transferResponse.getLocation();
    assertNotNull(transferId);
    assertThat(transferResponse.getStatus(), is(201));
  }
  
  @Test
  public void cancelTransaction() {
    final String account_uri = "http://localhost:8080/revolut/resources/account";
    this.rut = client.target(account_uri);
    
    Account sender = new Account(UUID.randomUUID(), "Olatunji oniyide", BigDecimal.TEN);
    Response senderResponse = this.rut.request(APPLICATION_JSON)
            .header("Content-Type", APPLICATION_XML)
            .post(Entity.entity(sender, APPLICATION_XML));
    
    Account receiver = new Account(UUID.randomUUID(), "Andrew Mbata", BigDecimal.ONE);
    Response receiverResponse = this.rut.request(MediaType.APPLICATION_JSON)
            .header("Content-Type", APPLICATION_XML)
            .post(Entity.entity(receiver, APPLICATION_XML));
    
    LocalDateTime now = LocalDateTime.now();
    Date date = Date.from(now.toInstant(ZoneOffset.UTC));
    TransferDetails details = new TransferDetails(sender, receiver, BigDecimal.ONE, date);
    
    this.rut = client.target(RUT_URI);
    Response transferResponse = this.rut.request(APPLICATION_JSON)
            .header("Content-Type", APPLICATION_XML)
            .post(Entity.entity(details, APPLICATION_XML));
    
    TransferDetails cancelDetails = this.rut.path("/{id}")
            .resolveTemplate("id", 1L)
            .request()
            .accept(APPLICATION_XML)
            .get(TransferDetails.class);
    
   assertNotNull(cancelDetails);
   assertThat(cancelDetails.getStatus(), is(TransferStatus.CANCELLED));
  }
}
