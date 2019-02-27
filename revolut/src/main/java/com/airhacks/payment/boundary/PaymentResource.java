package com.airhacks.payment.boundary;

import com.airhacks.payment.entity.TransferDetails;
import java.util.Optional;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
 * @author olatunji oniyide
 */
@Path("transfer")
@Produces({APPLICATION_JSON, APPLICATION_XML})
@Consumes({APPLICATION_JSON, APPLICATION_XML})
public class PaymentResource {
  
  @Inject
  BankService service;
  
  @Resource
  ManagedExecutorService mes;
  
  @POST
  public void transfer(TransferDetails request, @Context UriInfo info, @Suspended AsyncResponse response) {
    supplyAsync(() -> this.service.processPayment(request)).thenApply(account -> account.getId()).
            whenComplete((result, error) -> Optional.ofNullable(error).ifPresent(response::resume)).
            thenApply(id -> info.getAbsolutePathBuilder().path("/" + id).build()).
            thenApply(uri -> Response.created(uri).entity(request).build()).
            thenAccept(response::resume);
  }
  
  //for record purposes, cancelled transactions are not deleted.
  @GET
  @Path("{id}")
  public void cancel(@PathParam("id") Long id, @Suspended AsyncResponse response) {
    response.setTimeout(1, TimeUnit.SECONDS);
    supplyAsync(() -> this.service.cancelTransaction(id), mes).
            whenComplete((result, error) -> Optional.ofNullable(error).ifPresent(response::resume)).
            thenAccept(response::resume);
  }
}
