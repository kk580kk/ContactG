package org.jivesoftware.spark.sso;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class PasswordMD5 {
	public  String passwordMD5;
	public  String adminName;
	
	private static Map<String, MessageDigest> digests =
        new ConcurrentHashMap<String, MessageDigest>();
	
	private static PasswordMD5 instance;
	private PasswordMD5(){}	
	
	public static PasswordMD5 getInstance()
	{
		if (instance == null)
		{
			instance = new PasswordMD5();
		}
		return instance;
	}
	
	public void setAdminName(String name)
	{
		adminName = name ;
	}
	public void setPasswordMD5(String password)
	{
		passwordMD5 = password ;
	}
	
	public String getAdminName()
	{
		return adminName;
	}
	
	public String getPasswordMD5()
	{
		return passwordMD5;
	}
	
	public static String md5(String str){
		String algorithm = "md5";
		try {
			byte[] bytes = str.getBytes("UTF-8");
			
			synchronized (algorithm.intern()) {
	            MessageDigest digest = digests.get(algorithm);
	            if (digest == null) {
	                try {
	                    digest = MessageDigest.getInstance(algorithm);
	                    digests.put(algorithm, digest);
	                }
	                catch (NoSuchAlgorithmException nsae) {
	                    //Log.error("Failed to load the " + algorithm + " MessageDigest. " +
	                            //"Jive will be unable to function normally.", nsae);
	                    return null;
	                }
	            }
	            // Now, compute hash.
	            digest.update(bytes);
	            //digest.toString();
	            bytes = digest.digest();
	            StringBuilder buf = new StringBuilder(bytes.length * 2);
	            int i;

	            for (i = 0; i < bytes.length; i++) {
	                if (((int)bytes[i] & 0xff) < 0x10) {
	                    buf.append("0");
	                }
	                buf.append(Long.toString((int)bytes[i] & 0xff, 16));
	            }
	            return buf.toString();
	        }
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	


}

