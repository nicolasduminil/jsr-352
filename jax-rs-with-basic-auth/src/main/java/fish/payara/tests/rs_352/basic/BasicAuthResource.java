package fish.payara.tests.rs_352.basic;

import jakarta.annotation.security.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.bean.*;
import jakarta.security.enterprise.authentication.mechanism.http.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@RequestScoped
@Path("/api")
@Produces("text/plain")
@BasicAuthenticationMechanismDefinition(realmName = "admin")
@DeclareRoles({"admin"})
public class BasicAuthResource
{
  private String fmt = "This is a secured endpoint.\nUser %s in role admin %b";
  @GET
  @Path("unsecure")
  public Response unsecured()
  {
    return Response.ok().entity("This is an unsecured endpoint").build();
  }

  @GET
  @Path("secure")
  @RolesAllowed("admin")
  public Response secured(@Context SecurityContext context)
  {
    String msg = String.format(fmt, context.getUserPrincipal().getName(), context.isUserInRole("admin"));
    return Response.ok().entity(msg).build();
  }
}
