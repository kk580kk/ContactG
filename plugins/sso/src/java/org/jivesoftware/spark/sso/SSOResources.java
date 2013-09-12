package org.jivesoftware.spark.sso;


import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.jivesoftware.spark.util.log.Log;

public class SSOResources {
	 private static PropertyResourceBundle prb;
	 
	 static ClassLoader cl = SSOResources.class.getClassLoader();
    
	 static {
		 prb = (PropertyResourceBundle) ResourceBundle.getBundle("i18n/sso_i18n",Locale.getDefault());
     }
	 
	 public static final String getString(String propertyName) {
    	try {
    	    return prb.getString(propertyName);
    	} catch (Exception e) {
    	    Log.error(e);
    	    return propertyName;
    	}
	 }
}
