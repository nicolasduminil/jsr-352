package fish.payara.tests;

import jakarta.enterprise.context.*;
import jakarta.security.enterprise.authentication.mechanism.http.*;
import jakarta.security.enterprise.identitystore.*;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName="admin-realm")
@LdapIdentityStoreDefinition(
  url = "ldap://localhost:33389",
  callerBaseDn = "ou=caller,dc=payara,dc=fish",
  groupSearchBase = "ou=group,dc=payara,dc=fish")
public class LdapIdentityStoreConfig
{
}
