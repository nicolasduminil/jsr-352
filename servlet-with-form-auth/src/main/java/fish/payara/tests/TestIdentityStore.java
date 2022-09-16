package fish.payara.tests;

import jakarta.enterprise.context.*;
import jakarta.security.enterprise.credential.*;
import jakarta.security.enterprise.identitystore.*;

import java.util.*;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.*;
import static java.util.Arrays.*;

@ApplicationScoped
public class TestIdentityStore implements IdentityStore
{
  public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential)
  {
    if (usernamePasswordCredential.compareTo("admin", "admin"))
    {
      return new CredentialValidationResult("admin",
        new HashSet<>(asList("admin")));
    }
    return INVALID_RESULT;
  }
}
