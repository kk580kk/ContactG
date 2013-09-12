package org.jivesoftware.spark.sso;

import org.jivesoftware.spark.SparkManager;
import org.jivesoftware.spark.plugin.Plugin;


/**
 * provider a method to simply achieve SSO
 * @author luye66
 *
 */
public class SSOPlugin implements Plugin{

	SSOPreference pref = null;
	@Override
	public void initialize() {
		//draw panel on login advanced panel
		pref = new SSOPreference();
		SSOMessageListener ssoListener = new SSOMessageListener();
		SparkManager.getPreferenceManager().addPreference(pref);
		SparkManager.getChatManager().addChatRoomListener(ssoListener);
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canShutDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void uninstall() {
		if(pref != null){
			SparkManager.getPreferenceManager().removePreference(pref);
		}
	}

	
}
