package org.jivesoftware.spark.sso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.jivesoftware.Spark;

public class SSOProperties {
	
    private Properties props;
    private File configFile;

    private static final String REMOTEURL = "remoteUrl";
    private static final String REMOTENAME = "remoteName";
    private static final String REMOTEUSER = "username";
    private static final String REMOTEPASSWORD = "password";
    private static final String ENABLE = "enable";
    
    private static final Object LOCK = new Object();  
    
    private static SSOProperties ins = null;
    
    public static SSOProperties getInstance() {
    	synchronized (LOCK) {
    	    if (ins == null) {
    		ins = new SSOProperties();
    	    }
    	    return ins;
    	}
    }
    
    private SSOProperties() {
    	this.props = new Properties();

    	try {
    	    props.load(new FileInputStream(getConfigFile()));
    	} catch (IOException e) {
    	    // Can't load ConfigFile
    	}

    }
    
    private File getConfigFile() {
    	if (configFile == null)
    	    configFile = new File(Spark.getSparkUserHome(), "sso.properties");
    	return configFile;
    }
    
    public void save() {
    	try {
    	    props.store(new FileOutputStream(getConfigFile()),
    		    "Storing sso properties");
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    }
    
    public String getRemoteUrl()
    {
    	return props.getProperty(REMOTEURL,"");
    }
    
    public void setRemoteUrl(String url)
    {
    	props.setProperty(REMOTEURL, url);
    }
    
    public String getRemoteName()
    {
    	return props.getProperty(REMOTENAME, "");
    }
    
    public void setRemoteName(String name)
    {
    	props.setProperty(REMOTENAME, name);
    }
    
    public String getCodedPwd()
    {
    	return props.getProperty(REMOTEPASSWORD, "");
    }
    
    public void setCodedPwd(String plainPwd)
    {
    	props.setProperty(REMOTEPASSWORD, PasswordMD5.md5(plainPwd));
    }
    
    public String getUserName()
    {
    	return props.getProperty(REMOTEUSER,"");
    }
    
    public void setUserName(String username)
    {
    	props.setProperty(REMOTEUSER,username);
    }
    
    public void setEnable(boolean enable)
    {
    	if(enable){
    	props.setProperty(ENABLE,"true");
    	}else{
    		props.setProperty(ENABLE, "false");
    	}
    }
    
    public boolean getEnable()
    {
    	String enable= props.getProperty(ENABLE,"false");
    	if("true".equals(enable))
    	{
    		return true;
    	}else{
    		return false;
    	}
    }
}
