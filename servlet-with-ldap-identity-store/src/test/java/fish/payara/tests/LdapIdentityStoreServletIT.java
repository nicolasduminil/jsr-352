package fish.payara.tests;

import io.restassured.http.*;
import jakarta.ws.rs.core.*;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.*;
import org.testcontainers.containers.wait.strategy.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.*;
import org.testcontainers.utility.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Testcontainers
public class LdapIdentityStoreServletIT
{
  private static URI uri;

  @Container
  private static GenericContainer payara =
    new GenericContainer("payara/micro:latest")
      .withExposedPorts(8080)
      .withCopyFileToContainer(MountableFile.forHostPath(
        Paths.get("target/servlet-with-ldap-identity-store.war")
          .toAbsolutePath(), 0777), "/opt/payara/deployments/test.war")
      .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
      .withCommand(
        "--noCluster --deploy /opt/payara/deployments/test.war --contextRoot /test");

  @BeforeAll
  public static void beforeAll() throws MalformedURLException
  {
    uri = UriBuilder.fromUri("http://" + payara.getHost())
      .port(payara.getMappedPort(8080))
      .path("test").path("secured")
      .build();
  }

  @AfterAll
  public static void after()
  {
    uri = null;
  }

  @Test
  public void testGetSecuredPageShouldSucceed() throws IOException
  {
    given()
      .contentType(ContentType.TEXT)
      .auth().basic("admin", "passadmin")
      .when()
      .get(uri)
      .then()
      .assertThat().statusCode(200)
      .and()
      .body(containsString("admin-role"))
      .and()
      .body(containsString("admin-role"));
  }

  @Test
  public void testGetSecuredPageShouldFail403()
  {
    given()
      .contentType(ContentType.TEXT)
      .auth().basic("user", "passuser")
      .when()
      .get(uri)
      .then()
      .assertThat().statusCode(403);
  }

  @Test
  public void testGetSecuredPageShouldFail401()
  {
    given()
      .contentType(ContentType.TEXT)
      .auth().basic("admin", "none")
      .when()
      .get(uri)
      .then()
      .assertThat().statusCode(401);
  }
}
