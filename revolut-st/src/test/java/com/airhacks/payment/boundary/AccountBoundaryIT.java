package com.airhacks.payment.boundary;

import com.airhacks.payment.entity.Account;
import java.math.BigDecimal;
import java.net.URI;
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
public class AccountBoundaryIT {
  
  private static final String RUT_URI = "http://localhost:8080/revolut/resources/account";
  
  private WebTarget rut;
  private Client client;
  
  @Before
  public void setup() {
    this.client = ClientBuilder.newBuilder().build();
    this.rut = client.target(RUT_URI);
  }
  
  @Test
  public void createAccount() {
    Account request = new Account(UUID.randomUUID(), "Olatunji oniyide", BigDecimal.TEN);
    Response response = this.rut.request(APPLICATION_JSON)
            .header("Content-Type", APPLICATION_XML)
            .post(Entity.entity(request, APPLICATION_XML));
    
    URI accountNo = response.getLocation();
    assertNotNull(accountNo);
    assertThat(response.getStatus(), is(201));
  }  
}
