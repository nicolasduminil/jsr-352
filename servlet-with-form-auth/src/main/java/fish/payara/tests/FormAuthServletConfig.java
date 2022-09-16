package fish.payara.tests;

import jakarta.enterprise.context.*;
import jakarta.security.enterprise.authentication.mechanism.http.*;

@ApplicationScoped
@FormAuthenticationMechanismDefinition(
  loginToContinue = @LoginToContinue(
    loginPage = "/login",
    errorPage = "/login-error"
  )
)
public class FormAuthServletConfig
{
}
