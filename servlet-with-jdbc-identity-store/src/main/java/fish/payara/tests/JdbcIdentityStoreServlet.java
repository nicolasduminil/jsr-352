package fish.payara.tests;

import jakarta.annotation.security.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.security.*;

@WebServlet("/secured")
@DeclareRoles({ "admin-role", "user-role" })
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin-role"))
public class JdbcIdentityStoreServlet extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    response.getWriter().write("This is a secured servlet \n");
    Principal principal = request.getUserPrincipal();
    String user = principal == null ? null : principal.getName();
    response.getWriter().write("User name: " + user + "\n");
    response.getWriter().write("\thas role \"admin-role\": " + request.isUserInRole("admin-role") + "\n");
    response.getWriter().write("\thas role \"user-role\": " + request.isUserInRole("user-role") + "\n");
  }
}
