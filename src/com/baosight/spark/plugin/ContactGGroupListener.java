package com.baosight.spark.plugin; /**
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

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.spark.plugin.ContextMenuListener;

import java.awt.event.MouseEvent;
import java.util.Collection;

/**
 * The com.baosight.spark.plugin.ContactGGroupListener interface is one of the interfaces extension writers use to add functionality to Spark.
 * <p/>
 * In general, you implement this interface in order to listen for mouse and key events on <code>com.baosight.spark.plugin.ContactGGroup</code>s within
 * the Spark client <code>ContactList</code>.
 */
public interface ContactGGroupListener {

    /**
     * Notifies a user that a new <code>com.baosight.spark.plugin.ContactGItem</code> has been added to the com.baosight.spark.plugin.ContactGGroup.
     *
     * @param item the com.baosight.spark.plugin.ContactGItem.
     */
    public void contactGItemAdded(ContactGItem item);

    /**
     * Notifies the user that a <code>com.baosight.spark.plugin.ContactGItem</code> has been removed from a com.baosight.spark.plugin.ContactGGroup.
     *
     * @param item the com.baosight.spark.plugin.ContactGItem removed.
     */
    public void contactGItemRemoved(ContactGItem item);

    /**
     * Notifies the user that a com.baosight.spark.plugin.ContactGItem within the com.baosight.spark.plugin.ContactGGroup has been double-clicked.
     *
     * @param item the com.baosight.spark.plugin.ContactGItem double clicked.
     */
    public void contactGItemDoubleClicked(ContactGItem item);

    /**
     * Notifies the user that a com.baosight.spark.plugin.ContactGItem within the com.baosight.spark.plugin.ContactGGroup has been clicked.
     *
     * @param item the com.baosight.spark.plugin.ContactGItem clicked.
     */
    public void contactGItemClicked(ContactGItem item);

    /**
     * Notifies the user that a popup call has occurred on the com.baosight.spark.plugin.ContactGGroup.
     *
     * @param e    the MouseEvent that triggered the event.
     * @param item the com.baosight.spark.plugin.ContactGItem clicked within the com.baosight.spark.plugin.ContactGGroup.
     * @deprecated see {@link ContextMenuListener}
     */
    public void showPopup(MouseEvent e, ContactGItem item);

    /**
     * Notifies the user that a popup call has occurred on the com.baosight.spark.plugin.ContactGGroup.
     *
     * @param e     the MouseEvent that triggered the event.
     * @param items the ContactGItems within the com.baosight.spark.plugin.ContactGGroup.
     * @deprecated see <code>ContextMenuListener</code>
     */
    public void showPopup(MouseEvent e, Collection<ContactGItem> items);

    /**
     * Notifies the user that a Popup event has occurred on the com.baosight.spark.plugin.ContactGGroup title
     * bar.
     *
     * @param e     the MouseEvent that triggered the event.
     * @param group the com.baosight.spark.plugin.ContactGGroup.
     */
    public void contactGGroupPopup(MouseEvent e, ContactGGroup group);

    void presenceChanged(Presence presence);
}
