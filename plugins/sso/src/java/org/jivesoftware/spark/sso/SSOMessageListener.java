/**
 * $RCSfile: ,v $
 * $Revision: $
 * $Date: $
 * 
 * Copyright (C) 2004-2011 Jive Software. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jivesoftware.spark.sso;

import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.jivesoftware.spark.SparkManager;
import org.jivesoftware.spark.ui.ChatRoom;
import org.jivesoftware.spark.ui.ChatRoomListener;
import org.jivesoftware.spark.ui.LinkInterceptor;

/**
 * Message Listener<br>
 * 
 * @author wolf.posdorfer
 * 
 */
public class SSOMessageListener implements ChatRoomListener {


    private HashMap<String, Long> _rooms = new HashMap<String, Long>();
    private String patterUrl = null;
    private String caller = null;
    private String serverUrl = null;
    private String self = null;
	private LinkInterceptor interceptor = null;
	private String name = null;
	private String cre = null;
    public SSOMessageListener() {

    	
    }
	@Override
	public void chatRoomOpened(ChatRoom room) {
		boolean enable = SSOProperties.getInstance().getEnable();
		if(enable){
		patterUrl = SSOProperties.getInstance().getRemoteUrl();
//		String enable = SSOProperties.getInstance()
//    	caller = SparkManager.getConnection().getConnectionID();
//    	serverUrl = SparkManager.getConnection().getHost();
//    	self = SparkManager.getConnection().getUser();
		 SimpleDateFormat CREDENTIAL_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
		 String authenType = "CodedPwd";
		 Calendar now = Calendar.getInstance();
	     String minuteStr = CREDENTIAL_FORMAT.format(now.getTime());
         name = SSOProperties.getInstance().getUserName();
         String password = SSOProperties.getInstance().getCodedPwd();
         cre = PasswordMD5.md5((minuteStr + password));
    	 interceptor  = new LinkInterceptor(){
			@Override
			public boolean handleLink(MouseEvent mouseEvent, String link) {
				 String targetUrl = link;
	             String fullUrl = null;
	             if(targetUrl.startsWith(patterUrl)){
	            	 //using properties
	                 //fullUrl = "http://"+serverUrl+":9090/plugins/logincenter?";
	                 try {
	                	 
//	                	String paramStr = 
//	                		      "type=redirect"
//	                		      +"&target="+URLEncoder.encode(targetUrl,"UTF-8")
//	            		          +"&caller="+caller
//	            		          +"&user="+URLEncoder.encode(self,"UTF-8");
	                	String paramStr = "&p_username=" + name + "&p_password=" + cre + "&p_authen=CodedPwd";
	                	URI uri = URI.create(link+paramStr);
	                    Desktop.getDesktop().browse(uri);  
	                } catch (Exception e1) {  
	                    e1.printStackTrace();
	                    return false;
	                } 
	                return true;
			    }else{
			    	return false;
			    }
			}
    	};
			room.getTranscriptWindow().addLinkInterceptor(interceptor);
		}
	}
	@Override
	public void chatRoomLeft(ChatRoom room) {
		
	}
	@Override
	public void chatRoomClosed(ChatRoom room) {
		// TODO Auto-generated method stub
		try{
			if(interceptor != null)
			  room.getTranscriptWindow().removeLinkInterceptor(interceptor);
		}finally{
			
		}
	}
	@Override
	public void chatRoomActivated(ChatRoom room) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void userHasJoined(ChatRoom room, String userid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void userHasLeft(ChatRoom room, String userid) {
		// TODO Auto-generated method stub
		
	}



}
