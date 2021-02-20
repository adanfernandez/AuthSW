package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {	
	
	/**
	 *  
	 * @param wordToEncrypt
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encrypt(String wordToEncrypt) throws NoSuchAlgorithmException {
		// Creation of encryption instance
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		// Getting password bytes
		md.update(wordToEncrypt.getBytes());
		
		// Get the hashs bytes
		byte[] bytes = md.digest();
		
        //Convert the array of bytes from decimal to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++) sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        
        return sb.toString();
	}
}