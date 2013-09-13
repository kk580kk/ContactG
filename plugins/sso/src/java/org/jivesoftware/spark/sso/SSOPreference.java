package org.jivesoftware.spark.sso;

import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.jivesoftware.spark.preference.Preference;
import org.jivesoftware.spark.sso.gui.SSOPreferencePanel;
import org.jivesoftware.spark.util.log.Log;


public class SSOPreference implements Preference{

	private SSOProperties _props;
	private SSOPreferencePanel _prefPanel;
	private final static String ssoName = "业务系统设置";
	private final static String ssoNS = "bussiness";
	
	
	public SSOPreference()
	{
		_props = SSOProperties.getInstance();
		try {
		    if (EventQueue.isDispatchThread()) {
			_prefPanel = new SSOPreferencePanel();
		    } else {
			EventQueue.invokeAndWait(new Runnable() {
			    @Override
			    public void run() {
				_prefPanel = new SSOPreferencePanel();
			    }
			});
		    }
		} catch (Exception e) {
		    Log.error(e);
		}
	}
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return ssoName;
	}

	@Override
	public Icon getIcon() {
		ClassLoader cl = getClass().getClassLoader();
		return new ImageIcon(cl.getResource("sso-logo.png"));
	}

	@Override
	public String getTooltip() {
		// TODO Auto-generated method stub
		return ssoName;
	}

	@Override
	public String getListName() {
		// TODO Auto-generated method stub
		return ssoName;
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return ssoNS;
	}

	@Override
	public JComponent getGUI() {
		// TODO Auto-generated method stub
		return _prefPanel;
	}

	@Override
	public void load() {
		//get value via the _prefPanel
		_prefPanel.setRemoteUrl(_props.getRemoteUrl());
		_prefPanel.setRemoteName(_props.getRemoteName());
		_prefPanel.setUsername(_props.getUserName());
		_prefPanel.setPassword(_props.getCodedPwd());
		_prefPanel.setSSOEnable(_props.getEnable());
	}

	@Override
	public void commit() {
		//save value in the _prefPanel
		_props.setRemoteUrl(_prefPanel.getRemoteUrl());
		_props.setRemoteName(_prefPanel.getRemoteName());
		_props.setUserName(_prefPanel.getUsername());
		_props.setCodedPwd(_prefPanel.getPassword());
		_props.setEnable(_prefPanel.getSSOEnable());
		_props.save();
	}

	@Override
	public boolean isDataValid() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return "FAAAAAAAAAAAQ";
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
	   return _props;
	}

	@Override
	public void shutdown() {
		//do Nothing
	}

}
