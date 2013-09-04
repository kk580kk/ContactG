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
package com.baosight.spark.plugin;


import org.jivesoftware.resource.Res;
import org.jivesoftware.resource.SparkRes;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.LastActivityManager;
import org.jivesoftware.smackx.packet.LastActivity;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.spark.SparkManager;
import org.jivesoftware.spark.util.GraphicUtils;
import org.jivesoftware.spark.util.ModelUtil;
import org.jivesoftware.spark.util.log.Log;
import org.jivesoftware.sparkimpl.plugin.gateways.transports.Transport;
import org.jivesoftware.sparkimpl.plugin.gateways.transports.TransportUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the UI for the "ToolTip" functionallity in the contactGList.
 *
 * @author Derek DeMoro
 */
public class ContactGInfoWindow extends JPanel {
    //    private final long serialVersionUID = 8409694344721276453L;
    private final JLabel nicknameLabel = new JLabel();
    private final JTextArea statusLabel = new JTextArea();
    private final JLabel fullJIDLabel = new JLabel();
    private final JLabel avatarLabel = new JLabel();
    private final JLabel iconLabel = new JLabel();
    private final JLabel titleLabel = new JLabel();

    private ContactGItem contactGItem;

    private JWindow window = new JWindow();

    private final Object LOCK = new Object();

    ContactGList contactGList;


    public ContactGInfoWindow(ContactGList contactGList) {
        this(true, contactGList);
    }

    public ContactGInfoWindow(boolean populate, ContactGList contactGList) {

        setLayout(new GridBagLayout());

        setBackground(Color.white);
        if (populate) {
            add(avatarLabel, new GridBagConstraints(0, 1, 1, 3, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
            add(iconLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 0, 2), 0, 0));
            add(nicknameLabel, new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 0, 2), 0, 0));
            add(statusLabel, new GridBagConstraints(2, 2, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 2), 0, 0));
            add(titleLabel, new GridBagConstraints(2, 3, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 2, 2), 0, 0));
            add(fullJIDLabel, new GridBagConstraints(0, 5, 4, 1, 1.0, 1.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 2, 2, 2), 0, 0));


            nicknameLabel.setFont(new Font("Dialog", Font.BOLD, 12));
            statusLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
            statusLabel.setForeground(Color.gray);
            statusLabel.setLineWrap(true);
            statusLabel.setWrapStyleWord(true);
            statusLabel.setEditable(false);
            statusLabel.setBorder(null);
            fullJIDLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
            fullJIDLabel.setForeground(Color.gray);
            titleLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
            titleLabel.setForeground(Color.gray);


            fullJIDLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));
        }

        setBorder(BorderFactory.createLineBorder(Color.gray, 1));

        window.getContentPane().add(this);
        //todo: 这里没有设置好，待改正
        this.contactGList = contactGList;
//        contactGList contactGList = new contactGList();

        this.contactGList.addContactGListListener(new ContactGListListener() {
            public void contactGItemAdded(ContactGItem item) {
            }

            public void contactGItemRemoved(ContactGItem item) {
            }

            public void contactGGroupAdded(ContactGGroup group) {
            }

            public void contactGGroupRemoved(ContactGGroup group) {
            }

            public void contactGItemClicked(ContactGItem item) {
                if (window != null) {
                    window.dispose();
                }
            }

            public void contactGItemDoubleClicked(ContactGItem item) {
                if (window != null) {
                    window.dispose();
                }
            }
        });
    }

    public void display(ContactGGroup group, MouseEvent e) {
        int loc = group.getList().locationToIndex(e.getPoint());


        ContactGItem item = group.getList().getModel().getElementAt(loc);

//        //增加一个功能，刷新Presence,效果不是特别好，需要切换tab才会刷新。需要增加个事件刷新tab表单。
//        if(null!=item) {
//            item.setPresence(PresenceManager.getPresence(item.getJID()));
//        }


        if (item == null || item.getJID() == null) {
            return;
        }

        if (getcontactGItem() != null && getcontactGItem() == item) {
            return;
        }

        iconLabel.setIcon(item.getIcon());

        Point point = group.getList().indexToLocation(loc);

        window.setFocusableWindowState(false);
        setcontactGItem(item);
        window.pack();


        Point mainWindowLocation = SparkManager.getMainWindow().getLocationOnScreen();
        Point listLocation = group.getList().getLocationOnScreen();

        int x = (int) mainWindowLocation.getX() + SparkManager.getMainWindow().getWidth();
        int y = (int) listLocation.getY() + (int) point.getY();
        setWindowLocation(x, y);
        if (!window.isVisible()) {
            window.setVisible(true);
        }
    }

    public void setWindowLocation(int x, int y) {
        Point mainWindowLocation = SparkManager.getMainWindow().getLocationOnScreen();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int actualX = x;
        int actualY = y - 5;
        if ((int) screenSize.getWidth() - getPreferredSize().getWidth() < x) {
            actualX = (int) mainWindowLocation.getX() - (int) getPreferredSize().getWidth();
        }

        // keep this away from bottom edge
        if (actualY + getHeight() > screenSize.height - 64) {
            actualY -= actualY + getHeight() - screenSize.height + 64;
        }
        window.setLocation(actualX, actualY);
    }

    public void customizeUI(ContactGItem contactGItem) {
        if (contactGItem == null) {
            return;
        }

        nicknameLabel.setText(contactGItem.getDisplayName());

        String status = contactGItem.getStatus();
        if (!ModelUtil.hasLength(status)) {
            if (contactGItem.getPresence() == null || contactGItem.getPresence().getType() == Presence.Type.unavailable) {
                status = Res.getString("offline");
            } else {
                status = Res.getString("online");
            }
        }
        if (status.equals(Res.getString("offline")) || contactGItem.getPresence().isAway()) {
            //If user is offline or away, try to see last activity
            try {
                String client = "";
                if (!status.equals(Res.getString("offline"))) {
                    //If user is away (not offline), last activity request is sent to client
                    client = contactGItem.getPresence().getFrom();
                    if ((client != null) && (client.lastIndexOf("/") != -1)) {
                        client = client.substring(client.lastIndexOf("/"));
                    } else client = "/";
                }

                LastActivity activity = LastActivityManager.getLastActivity(SparkManager.getConnection(), contactGItem.getJID() + client);

                long idleTime = (activity.getIdleTime() * 1000);

                if (idleTime > 0) {
                    if (status.equals(Res.getString("offline"))) {
                        SimpleDateFormat format = new SimpleDateFormat("M/d/yy");
                        Date l = new Date();
                        String curDay = format.format(l);
                        l.setTime(l.getTime() - idleTime);
                        //If idleTime is within today show the time, otherwise, show the day, date, and time
                        if (curDay.equals(format.format(l))) {
                            format = new SimpleDateFormat("h:mm a");
                        } else {
                            format = new SimpleDateFormat("EEE M/d/yy h:mm a");
                        }
                        status += (" since " + format.format(l));
                    } else if (contactGItem.getPresence().isAway()) {
                        status += "\n";
                        String time = ModelUtil.getTimeFromLong(idleTime);
                        status += Res.getString("message.idle.for", time);
                    }
                }
            } catch (Exception e1) {
            }
        }
        statusLabel.setText(status);

        Transport transport = TransportUtils.getTransport(StringUtils.parseServer(contactGItem.getJID()));
        if (transport != null) {
            fullJIDLabel.setIcon(transport.getIcon());
            String name = StringUtils.parseName(contactGItem.getJID());
            name = StringUtils.unescapeNode(name);
            fullJIDLabel.setText(transport.getName() + " - " + name);
        } else {
            String name = StringUtils.unescapeNode(contactGItem.getJID());
            fullJIDLabel.setText(name);
            fullJIDLabel.setIcon(null);
        }

        avatarLabel.setBorder(null);

        try {
            URL avatarURL = contactGItem.getAvatarURL();
            ImageIcon icon = null;
            if (avatarURL != null) {
                icon = new ImageIcon(avatarURL);
            }

            if (icon != null && icon.getIconHeight() > 1) {
                icon = GraphicUtils.scaleImageIcon(icon, 96, 96);
                avatarLabel.setIcon(icon);
            } else {
                icon = SparkRes.getImageIcon(SparkRes.DEFAULT_AVATAR_64x64_IMAGE);
                avatarLabel.setIcon(icon);
            }
            avatarLabel.setBorder(BorderFactory.createBevelBorder(0, Color.white, Color.lightGray));
        } catch (MalformedURLException e) {
            Log.error(e);
        }

        // Get VCard from memory (if available)
        String title = "";
        VCard vcard = SparkManager.getVCardManager().getVCardFromMemory(StringUtils.parseBareAddress(contactGItem.getJID()));
        if (vcard != null) {
            title = vcard.getField("TITLE");
            if (!ModelUtil.hasLength(title)) {
                title = "";
            }
        }

        titleLabel.setText(title);
    }

    public void setcontactGItem(ContactGItem contactGItem) {
        this.contactGItem = contactGItem;
        customizeUI(this.contactGItem);
    }

    public ContactGItem getcontactGItem() {
        return contactGItem;
    }

    public void dispose() {
        window.setVisible(false);
        contactGItem = null;
        window.dispose();
    }


    public Dimension getPreferredSize() {
        final Dimension size = super.getPreferredSize();
        size.width = 300;
        size.height = 125;
        return size;
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        Point point = e.getPoint();

        Dimension dim = window.getSize();

        int x = (int) point.getX();
        int y = (int) point.getY();

        boolean close = false;

        if (x < 0 || x >= dim.getWidth()) {
            close = true;
        }

        if (y < 0 || y >= dim.getHeight()) {
            close = true;
        }

        if (close) {
            window.setVisible(false);
            contactGItem = null;
            hideWindow();
        }
    }

    private void hideWindow() {
        window.setVisible(false);
        contactGItem = null;
    }

    private JWindow getWindow() {
        return window;
    }

    private Object getLock() {
        return LOCK;
    }


}
