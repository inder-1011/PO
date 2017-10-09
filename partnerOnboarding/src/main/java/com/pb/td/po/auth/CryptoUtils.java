package com.pb.td.po.auth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.apache.cxf.common.i18n.Exception;

public class CryptoUtils {

 public static void main(String arg[]) throws IOException, NoSuchAlgorithmException {
    try {
    	  Scanner scanIn = new Scanner(System.in);
      //
      System.out.print("Input your secret password : ");
      
      String hash = byteArrayToHexString(CryptoUtils.computeHash(scanIn.next()));
      System.out.println("the computed hash (hex string) : " + hash);
      boolean ok = true;
      String inputHash = "";
      while (ok) {
          System.out.print("Now try to enter a password : " );
          String ha = scanIn.next();
          inputHash = byteArrayToHexString(CryptoUtils.computeHash(ha));
          if (hash.equals(inputHash)){
             System.out.println("You got it!");
             ok = false;
             }
          else
             System.out.println("Wrong, try again...!");
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

  public static byte[] computeHash(String x)
  throws Exception, NoSuchAlgorithmException
  {
     java.security.MessageDigest d =null;
     d = java.security.MessageDigest.getInstance("SHA-1");
     d.reset();
     d.update(x.getBytes());
     return  d.digest();
  }

  public static String byteArrayToHexString(byte[] b){
     StringBuffer sb = new StringBuffer(b.length * 2);
     for (int i = 0; i < b.length; i++){
       int v = b[i] & 0xff;
       if (v < 16) {
         sb.append('0');
       }
       sb.append(Integer.toHexString(v));
     }
     return sb.toString().toUpperCase();
  }
}