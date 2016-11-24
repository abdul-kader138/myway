package org.hurryapp.quickstart.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.*;

import sun.misc.*;

public class ThreeDESCode {
	 
     private static String charSet = "utf-8";
     private static String key = "izmFNROXQ98C3w3T8tTiDD/ril0TlAzJGuEY+WiagsN19YPz3ewZJPIsLH4JBp2mUDwr5mfv1y7mPNLAnnndSQbklhcpK/aZXJfO7xxuLt2Z9/xRX7J6DcxlHa9LTOfhloXHrlFeOVGbX1O8Et4t6DvFdZOh2SIendLNsF";
    
     public static String encryptThreeDESECB(String src) throws Exception {
    		 
         BASE64Encoder encoder = new BASE64Encoder();
         DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(charSet));
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
         SecretKey securekey = keyFactory.generateSecret(dks);

         Cipher cipher = Cipher.getInstance("DESede");
         cipher.init(Cipher.ENCRYPT_MODE, securekey);
         byte[] b = cipher.doFinal(src.getBytes(charSet));
         return encoder.encode(b).replaceAll("\n", "");

     }

     public static String decryptThreeDESECB(String src) throws Exception {
    	 
         BASE64Decoder decoder = new BASE64Decoder();
         byte[] bytesrc = decoder.decodeBuffer(src);
         DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(charSet));
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
         SecretKey securekey = keyFactory.generateSecret(dks);
         Cipher cipher = Cipher.getInstance("DESede");
         cipher.init(Cipher.DECRYPT_MODE, securekey);
         byte[] retByte = cipher.doFinal(bytesrc);
         return new String(retByte, charSet);
     }
}


	