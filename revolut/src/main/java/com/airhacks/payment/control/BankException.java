package com.airhacks.payment.control;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author olatunji oniyide
 */
@ApplicationException(rollback = true)
public class BankException extends WebApplicationException {

  public BankException(String message) {
    super(Response.status(Status.BAD_REQUEST)
            .header("x-cause", message)
            .build());
  }
}
