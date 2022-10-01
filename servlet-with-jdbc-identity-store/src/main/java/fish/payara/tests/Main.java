package fish.payara.tests;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.security.spec.*;

public class Main
{
  public static void main (String args[]) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException
  {
    String password = "xrS7AJk+V6L8J?B%";
    SecureRandom rnd = new SecureRandom();
    int saltLength = 16;
    int keyLength = 128;
    int iterationCount = 10000;
    byte[] salt = new byte[saltLength];
    rnd.nextBytes(salt);
    SecretKeyFactory factorySun = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512", "SunJCE");
    KeySpec keyspecSun = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);
    SecretKey keySun = factorySun.generateSecret(keyspecSun);
    System.out.println(keySun.getClass().getName());
    System.out.println(keySun.getEncoded());
  }
}
