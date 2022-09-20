package fish.payara.tests;

import jakarta.annotation.security.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.security.*;

@WebServlet("/secured")
@DeclareRoles({"admin"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"admin"}))
public class FormAuthServlet extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    PrintWriter printWriter = response.getWriter();
    printWriter.append("This is a secured servlet\n");
    Principal principal = request.getUserPrincipal();
    response.getWriter().append(principal == null ?
      "User " + principal.getName() + " in role admin :" +
        request.isUserInRole("admin") + "\n" : "User principal is null");
  }
}
