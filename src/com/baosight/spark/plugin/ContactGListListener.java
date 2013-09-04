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

/**
 * The <code>ContactListListener</code> interface is used to listen for model changes within the Contact List.
 * <p/>
 * In general, you implement this interface in order to listen
 * for adding and removal of both ContactGItems and ContactGGroups.
 */
public interface ContactGListListener {

    /**
     * Notified when a <code>com.baosight.spark.plugin.ContactGItem</code> has been added to the ContactList.
     *
     * @param item the com.baosight.spark.plugin.ContactGItem added.
     */
    void contactGItemAdded(ContactGItem item);

    /**
     * Notified when a <code>com.baosight.spark.plugin.ContactGItem</code> has been removed from the ContactList.
     *
     * @param item the com.baosight.spark.plugin.ContactGItem removed.
     */
    void contactGItemRemoved(ContactGItem item);

    /**
     * Called when a <code>com.baosight.spark.plugin.ContactGGroup</code> has been added to the ContactList.
     *
     * @param group the com.baosight.spark.plugin.ContactGGroup.
     */
    void contactGGroupAdded(ContactGGroup group);

    /**
     * Called when a <code>com.baosight.spark.plugin.ContactGGroup</code> has been removed from the ContactList.
     *
     * @param group the com.baosight.spark.plugin.ContactGGroup.
     */
    void contactGGroupRemoved(ContactGGroup group);

    /**
     * Called when a <code>com.baosight.spark.plugin.ContactGItem</code> has been clicked in the Contact List.
     *
     * @param item the <code>com.baosight.spark.plugin.ContactGItem</code> double clicked.
     */
    void contactGItemClicked(ContactGItem item);

    /**
     * Called when a <code>com.baosight.spark.plugin.ContactGItem</code> has been double clicked in the Contact List.
     *
     * @param item the <code>com.baosight.spark.plugin.ContactGItem</code> double clicked.
     */
    void contactGItemDoubleClicked(ContactGItem item);
}
