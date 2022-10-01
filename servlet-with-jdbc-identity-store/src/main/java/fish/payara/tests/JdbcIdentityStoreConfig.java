package fish.payara.tests;

import jakarta.enterprise.context.*;
import jakarta.inject.*;
import jakarta.security.enterprise.authentication.mechanism.http.*;
import jakarta.security.enterprise.identitystore.*;

@ApplicationScoped
@Named
@BasicAuthenticationMechanismDefinition(realmName="admin-realm")
@DatabaseIdentityStoreDefinition(
  dataSourceLookup = "${'java:global/H2'}",
  callerQuery = "#{'select password from caller where name = ?'}",
  groupsQuery = "select group_name from caller_groups where caller_name = ?",
  hashAlgorithm = Pbkdf2PasswordHash.class,
  priorityExpression = "#{100}",
  hashAlgorithmParameters = {
    "Pbkdf2PasswordHash.Iterations=3072",
    "${jdbcIdentityStoreConfig.algo}"
  }
)
public class JdbcIdentityStoreConfig
{
  public String[] getAlgo()
  {
    return new String[]{"Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512", "Pbkdf2PasswordHash.SaltSizeBytes=64"};
  }
}
