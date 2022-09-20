package fish.payara.tests;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet({"/login"})
public class LoginServlet extends HttpServlet
{
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
    ServletException, IOException
  {
    response.sendRedirect("login.html");
  }
}
