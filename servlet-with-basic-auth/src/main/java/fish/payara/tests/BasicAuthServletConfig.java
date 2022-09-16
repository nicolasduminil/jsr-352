package fish.payara.tests;

import jakarta.enterprise.context.*;
import jakarta.security.enterprise.authentication.mechanism.http.*;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "admin")
public class BasicAuthServletConfig
{
}
