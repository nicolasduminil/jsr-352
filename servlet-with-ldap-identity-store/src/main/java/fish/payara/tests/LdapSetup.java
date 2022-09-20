package fish.payara.tests;

import com.unboundid.ldap.listener.*;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldif.*;
import jakarta.annotation.*;
import jakarta.ejb.*;

@Startup
@Singleton
public class LdapSetup
{
  private InMemoryDirectoryServer directoryServer;

  @PostConstruct
  public void init()
  {
    try
    {
      System.out.println ("### LdapSetup.init(): start");
      InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=fish");
      config.setListenerConfigs(
        new InMemoryListenerConfig("myListener", null, 33389, null, null, null));

      directoryServer = new InMemoryDirectoryServer(config);

      directoryServer.importFromLDIF(true,
        new LDIFReader(this.getClass().getResourceAsStream("/users.ldif")));

      directoryServer.startListening();
      System.out.println ("### LdapSetup.init(): exit");
    } catch (LDAPException e)
    {
      throw new IllegalStateException(e);
    }
  }

  @PreDestroy
  public void destroy()
  {
    System.out.println ("### LdapSetup.destroy(): start");
    directoryServer.shutDown(true);
    System.out.println ("### LdapSetup.destroy(): exit");
  }
}
