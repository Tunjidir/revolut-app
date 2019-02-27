package com.airhacks.payment.boundary;

import com.airhacks.payment.entity.Account;
import java.util.Optional;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author user
 */
@Path("/account")
@Produces({APPLICATION_JSON, APPLICATION_XML})
@Consumes({APPLICATION_JSON, APPLICATION_XML})
public class AccountResource {
  
  @Inject
  BankService service;
  
  @POST
  public void registerAccount(Account request, @Context UriInfo info, @Suspended AsyncResponse response) {
        supplyAsync(() -> this.service.registerAccount(request)).thenApply(account -> account.getAccountNumber()).
                whenComplete((result, error) -> Optional.ofNullable(error).ifPresent(response::resume)).
                thenApply(id -> info.getAbsolutePathBuilder().path("/" + id).build()).
                thenApply(uri -> Response.created(uri).build()).
                thenAccept(response::resume);
  }
}
