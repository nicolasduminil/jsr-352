package fish.payara.tests;

import jakarta.annotation.*;
import jakarta.annotation.sql.*;
import jakarta.ejb.Singleton;
import jakarta.ejb.*;
import jakarta.inject.*;
import jakarta.security.enterprise.identitystore.*;

import javax.sql.*;
import java.sql.*;

@DataSourceDefinition(
  name = "java:global/H2",
  className = "org.h2.jdbcx.JdbcDataSource",
  url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
)

@Singleton
@Startup
public class JdbcSetup
{
  @Resource(lookup = "java:global/H2")
  private DataSource dataSource;
  @Inject
  private Pbkdf2PasswordHash passwordHash;

  @PostConstruct
  public void init()
  {
    try
    {
      executeUpdate(dataSource, "DROP TABLE IF EXISTS caller");
      executeUpdate(dataSource, "DROP TABLE IF EXISTS caller_groups");
      executeUpdate(dataSource, "CREATE TABLE IF NOT EXISTS caller(name VARCHAR(64) PRIMARY KEY, password VARCHAR(255))");
      executeUpdate(dataSource, "CREATE TABLE IF NOT EXISTS caller_groups(caller_name VARCHAR(64), group_name VARCHAR(64))");
      executeUpdate(dataSource, "INSERT INTO caller VALUES('admin', '" + passwordHash.generate("passadmin".toCharArray()) + "')");
      executeUpdate(dataSource, "INSERT INTO caller VALUES('user', '" + passwordHash.generate("passuser".toCharArray()) + "')");
      executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('admin', 'admin-role')");
      executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('admin', 'user-role')");
      executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('user', 'user-role')");
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }

  @PreDestroy
  public void destroy()
  {
    try
    {
      executeUpdate(dataSource, "DROP TABLE IF EXISTS caller");
      executeUpdate(dataSource, "DROP TABLE IF EXISTS caller_groups");
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }

  private void executeUpdate(DataSource dataSource, String query) throws SQLException
  {
    try (Connection connection = dataSource.getConnection())
    {
      try (PreparedStatement statement = connection.prepareStatement(query))
      {
        statement.executeUpdate();
      }
    }
  }
}
