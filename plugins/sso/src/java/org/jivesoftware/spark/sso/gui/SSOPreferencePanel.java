package org.jivesoftware.spark.sso.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jivesoftware.spark.component.VerticalFlowLayout;
import org.jivesoftware.spark.sso.SSOResources;


public class SSOPreferencePanel extends JPanel {

	private static final long serialVersionUID = -4217756610718587907L;
	private JTextField _url;
	private JTextField _name;
	private JTextField _username;
	private JPasswordField _password;
	private JCheckBox _enable;
	
	public SSOPreferencePanel() {
		JPanel contents = new JPanel();
		contents.setLayout(new GridBagLayout());
		contents.setBackground(new Color(0,0,0,0));
		this.setLayout(new VerticalFlowLayout());
		contents.setBorder(BorderFactory.createTitledBorder(SSOResources.getString("sso.settings")));
		
		add(contents);
		
		_url = new JTextField();
		_name = new JTextField();
		_username = new JTextField();
		_password = new JPasswordField();
		_enable = new JCheckBox(SSOResources.getString("sso.enable"));
		
		Insets in = new Insets(5,5,5,5);
		
		contents.add(new JLabel(SSOResources.getString("sso.url")), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, in, 0, 0));
		contents.add(_url, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, in, 0, 0));

		contents.add(new JLabel(SSOResources.getString("sso.name")), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, in, 0, 0));
		contents.add(_name, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, in, 0, 0));
		
		contents.add(new JLabel(SSOResources.getString("sso.username")),new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, in, 0, 0));
		contents.add(_username, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, in, 0, 0));
        
		contents.add(new JLabel(SSOResources.getString("sso.password")),new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, in, 0, 0));
		contents.add(_password, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, in, 0, 0));
		
		contents.add(_enable,new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, in, 0, 0));
	}
	
	public String getRemoteUrl()
	{
		return _url.getText();
	}
	
	public void setRemoteUrl(String url)
	{
		_url.setText(""+url);
	}
	
	public String getRemoteName()
	{
		return _name.getText();
	}
	
	public void setRemoteName(String name)
	{
		_name.setText(name);
	}
	
	public String getUsername()
	{
		return _username.getText();
	}
	
	public void setUsername(String username)
	{
		_username.setText(username);
	}
	
	public String getPassword()
	{
		return new String(_password.getPassword());
	}
	
	public void setPassword(String password)
	{
		_password.setText(password);
	}
	public boolean getSSOEnable()
	{
		return _enable.isSelected();
	}
	
	public void setSSOEnable(boolean enable)
	{
		_enable.setSelected(enable);
	}
}
