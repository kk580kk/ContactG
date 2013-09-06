package com.baosight.spark.plugin;

import org.jivesoftware.Spark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ContactGProperties {

    private Properties props;
    private File configFile;

    public static final String REMOTE_CONTACTS = "remoteContacts";
    public static final String REMOTE_GROUP = "remoteGroup";

    private static final Object LOCK = new Object();

    private static ContactGProperties ins = null;

    public static ContactGProperties getInstance() {
        synchronized (LOCK) {
            if (ins == null) {
                ins = new ContactGProperties();
            }
            return ins;
        }
    }

    private ContactGProperties() {
        this.props = new Properties();

        try {
            props.load(new FileInputStream(getConfigFile()));
        } catch (IOException e) {
            // Can't load ConfigFile
        }

    }

    private File getConfigFile() {
        if (configFile == null)
            configFile = new File(Spark.getSparkUserHome(), "orgcontacts.properties");
        return configFile;
    }

    public void save() {
        try {
            props.store(new FileOutputStream(getConfigFile()),
                    "Storing orgcontacts properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRemoteContacts() {
        return props.getProperty(REMOTE_CONTACTS, "");
    }

    public void setRemoteContacts(String url) {
        props.setProperty(REMOTE_CONTACTS, url);
    }

    public String getRemoteGroup() {
        return props.getProperty(REMOTE_GROUP, "");
    }

    public void setRemoteGroup(String name) {
        props.setProperty(REMOTE_GROUP, name);
    }

}
