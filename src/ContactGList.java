import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.spark.ChatManager;
import org.jivesoftware.spark.SparkManager;
import org.jivesoftware.spark.Workspace;
import org.jivesoftware.spark.component.VerticalFlowLayout;
import org.jivesoftware.spark.plugin.Plugin;
import org.jivesoftware.spark.ui.*;
import org.jivesoftware.spark.util.log.Log;
import org.jivesoftware.sparkimpl.settings.local.LocalPreferences;
import org.jivesoftware.sparkimpl.settings.local.SettingsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SaintKnight
 * Date: 8/15/13
 * Time: 2:43 PM
 * Creator:Huang Jie
 * To change this template use File | Settings | File Templates.
 */
public class ContactGList extends JPanel implements ActionListener,
        ContactGGroupListener, Plugin, RosterListener, ConnectionListener {

    private static final long serialVersionUID = 1L;
    private JPanel mainPanel = new JPanel();
    private JScrollPane contactListScrollPane;

    private final List<ContactGGroup> groupList = new ArrayList<ContactGGroup>();


    private ContactGGroup unfiledGroup;


    private LocalPreferences localPreferences;


    /**
     * Creates a new instance of ContactGList.
     */
    public ContactGList() {
        // Load Local Preferences
        localPreferences = SettingsManager.getLocalPreferences();

        unfiledGroup = new ContactGGroup("unfiledGroup");
        System.out.println("unfiledGroup Created");

        setLayout(new BorderLayout());

        mainPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 0, 0, true, false));
        mainPanel.setBackground((Color)UIManager.get("ContactGItem.background"));

        contactListScrollPane = new JScrollPane(mainPanel);
        contactListScrollPane.setAutoscrolls(true);

        contactListScrollPane.setBorder(BorderFactory.createEmptyBorder());
        contactListScrollPane.getVerticalScrollBar().setBlockIncrement(200);
        contactListScrollPane.getVerticalScrollBar().setUnitIncrement(20);

        add(contactListScrollPane, BorderLayout.CENTER);

        this.addContactGGroup(unfiledGroup);
        System.out.println("unfiledGroup Added");


    }

    /**
     * Adds a new ContactGGroup to the ContactGList.
     *
     * @param group the group to add.
     */
    private void addContactGGroup(ContactGGroup group) {
        groupList.add(group);

//        Collections.sort(groupList, GROUP_COMPARATOR);
        System.out.println("addContactGGroup");
        try {
            mainPanel.add(group, groupList.indexOf(group));
            System.out.println("addContactGGroup Done");
        } catch (Exception e) {
            Log.error(e);
            System.out.println("addContactGGroup Error");
        }


    }

    /**
     * Returns a ContactGGroup based on its name.
     *
     * @param groupName the name of the ContactGGroup.
     * @return the ContactGGroup. If no ContactGGroup is found, null is returned.
     */
    public ContactGGroup getContactGGroup(String groupName) {
        ContactGGroup cGroup = null;

        for (ContactGGroup contactGGroup : groupList) {
            if (contactGGroup.getGroupName().equals(groupName)) {
                cGroup = contactGGroup;
                break;
            }
            else {
                cGroup = getSubContactGGroup(contactGGroup, groupName);
                if (cGroup != null) {
                    break;
                }
            }
        }

        return cGroup;
    }

    /**
     * Returns the nested ContactGGroup of a given ContactGGroup with associated name.
     *
     * @param group     the parent ContactGGroup.
     * @param groupName the name of the nested group.
     * @return the nested ContactGGroup. If not found, null will be returned.
     */
    private ContactGGroup getSubContactGGroup(ContactGGroup group, String groupName) {
        final Iterator<ContactGGroup> contactGGroups = group.getContactGGroups().iterator();
        ContactGGroup grp = null;

        while (contactGGroups.hasNext()) {
            ContactGGroup contactGGroup = contactGGroups.next();
            if (contactGGroup.getGroupName().equals(groupName)) {
                grp = contactGGroup;
                break;
            }
            else if (contactGGroup.getContactGGroups().size() > 0) {
                grp = getSubContactGGroup(contactGGroup, groupName);
                if (grp != null) {
                    break;
                }
            }

        }
        return grp;
    }

    /**
     * Sorts ContactGGroups
     */
    public static final Comparator<ContactGGroup> GROUP_COMPARATOR = new Comparator<ContactGGroup>() {
        public int compare(ContactGGroup group1, ContactGGroup group2) {


            return group1.getGroupName().trim().toLowerCase().compareTo(group2.getGroupName().trim().toLowerCase());
        }
    };

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void actionPerformed(ActionEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void connectionClosed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void connectionClosedOnError(Exception e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void reconnectingIn(int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void reconnectionSuccessful() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void reconnectionFailed(Exception e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }



    public void showPopup(MouseEvent mouseEvent, ContactGItem contactGItem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void showPopup(MouseEvent mouseEvent, Collection collection) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void contactGGroupPopup(MouseEvent mouseEvent, ContactGGroup contactGGroup) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void addContactGListToWorkspace() {
        Workspace workspace = SparkManager.getWorkspace();
//        workspace.getWorkspacePane().addTab("My Tab",null,new JButton("Hello"));
        workspace.getWorkspacePane().addTab("ContactGList", null, this);
    }

    public void initialize() {

        System.out.println();
        System.out.println("Welcome To Spark");
        // Add Contact List
        addContactGListToWorkspace();

        ContactGGroup contactGGroup1 = new ContactGGroup("Group 1");
        ContactGGroup contactGGroup2 = new ContactGGroup("Group 2");
        ContactGGroup contactGGroup3 = new ContactGGroup("Group 3");
        ContactGGroup contactGGroup4 = new ContactGGroup("Group 4");
        ContactGGroup contactGGroup41 = new ContactGGroup("Group 41");
        ContactGGroup contactGGroup42 = new ContactGGroup("Group 42");
        ContactGGroup contactGGroup43 = new ContactGGroup("Group 43");
        ContactGGroup contactGGroup431 = new ContactGGroup("Group 431");
        ContactGGroup contactGGroup432 = new ContactGGroup("Group 432");
        ContactGGroup contactGGroup4321 = new ContactGGroup("Group 4321");
        ContactGGroup contactGGroup4322 = new ContactGGroup("Group 4322");

        this.addContactGGroup(contactGGroup1);
        this.addContactGGroup(contactGGroup2);
        this.addContactGGroup(contactGGroup3);
        this.addContactGGroup(contactGGroup4);
        contactGGroup4.addContactGGroup(contactGGroup41);
        contactGGroup4.addContactGGroup(contactGGroup42);
        contactGGroup4.addContactGGroup(contactGGroup43);
        contactGGroup43.addContactGGroup(contactGGroup431);
        contactGGroup43.addContactGGroup(contactGGroup432);
        contactGGroup432.addContactGGroup(contactGGroup4321);
        contactGGroup432.addContactGGroup(contactGGroup4322);

        ContactGItem contactGItem = new ContactGItem("a","a","a");
        ContactGItem contactGItem1 = new ContactGItem("b","b","b");
        ContactGItem contactGItem2 = new ContactGItem("c","c","c");
        ContactGItem contactGItem3 = new ContactGItem("d","d","d");
        ContactGItem contactGItem4 = new ContactGItem("e","e","e");
        ContactGItem contactGItem5 = new ContactGItem("f","f","f");
        ContactGItem contactGItem6 = new ContactGItem("g","g","g");
        ContactGItem contactGItem7 = new ContactGItem("h","h","h");

        contactGGroup1.addContactGItem(contactGItem);
        contactGGroup2.addContactGItem(contactGItem1);
        contactGGroup3.addContactGItem(contactGItem2);
        contactGGroup4.addContactGItem(contactGItem3);
        contactGGroup41.addContactGItem(contactGItem4);
        contactGGroup42.addContactGItem(contactGItem5);
        contactGGroup43.addContactGItem(contactGItem6);
        contactGGroup431.addContactGItem(contactGItem7);




        this.setVisible(true);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            }
        });

    }

    public void shutdown() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean canShutDown() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void uninstall() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void entriesAdded(Collection collection) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void entriesUpdated(Collection collection) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void entriesDeleted(Collection collection) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void presenceChanged(Presence presence) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private ContactGItem activeItem;


    public void contactGItemDoubleClicked(ContactGItem item) {
        activeItem = item;

        System.out.println("Start contactGItemDoubleClicked");

        ChatManager chatManager = SparkManager.getChatManager();
        //chatManager 里面没有好用的方法，尚未找到解决办法，2013年8月27日10:52:21
//        boolean handled = chatManager.fireContactItemDoubleClicked(item);

//        if (!handled) {
            chatManager.activateChat(item.getJID(), item.getDisplayName());
//        }

        clearSelectionList(item);

        fireContactGItemDoubleClicked(item);
    }

    public void contactGItemClicked(ContactGItem item) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void clearSelectionList(ContactGItem selectedItem) {
        // Check for null. In certain cases the event triggering the model might
        // not find the selected object.
        if (selectedItem == null) {
            return;
        }

        final ContactGGroup owner = getContactGGroup(selectedItem.getGroupName());
        for (ContactGGroup contactGGroup : new ArrayList<ContactGGroup>(groupList)) {
            if (owner != contactGGroup) {
                contactGGroup.clearSelection();
            }
        }
    }


    public List<ContactGGroup> getContactGGroups() {
        final List<ContactGGroup> gList = new ArrayList<ContactGGroup>(groupList);
        Collections.sort(gList, GROUP_COMPARATOR);
        return gList;
    }
    private final List<FileDropListener> dndListeners = new ArrayList<FileDropListener>();
    public void addFileDropListener(FileDropListener listener) {
        dndListeners.add(listener);
    }

    public void removeFileDropListener(FileDropListener listener) {
        dndListeners.remove(listener);
    }

    public void fireFilesDropped(Collection<File> files, ContactGItem item) {
        for (FileDropListener fileDropListener : new ArrayList<FileDropListener>(dndListeners)) {
            fileDropListener.filesDropped(files, item);
        }
    }

    public void contactGItemAdded(ContactGItem item) {
        fireContactGItemAdded(item);
    }

    public void contactGItemRemoved(ContactGItem item) {
        fireContactGItemRemoved(item);
    }

    /*
        Adding ContactGListListener support.
    */

    private final List<ContactGListListener> contactListListeners = new ArrayList<ContactGListListener>();
    public void addContactGListListener(ContactGListListener listener) {
        contactListListeners.add(listener);
    }

    public void removeContactGListListener(ContactGListListener listener) {
        contactListListeners.remove(listener);
    }

    public void fireContactGItemAdded(ContactGItem item) {
        for (ContactGListListener contactListListener : new ArrayList<ContactGListListener>(contactListListeners)) {
            contactListListener.contactGItemAdded(item);
        }
    }

    public void fireContactGItemRemoved(ContactGItem item) {
        for (ContactGListListener contactListListener : new ArrayList<ContactGListListener>(contactListListeners)) {
            contactListListener.contactGItemRemoved(item);
        }
    }

    public void fireContactGGroupAdded(ContactGGroup group) {
        for (ContactGListListener contactListListener : new ArrayList<ContactGListListener>(contactListListeners)) {
            contactListListener.contactGGroupAdded(group);
        }
    }

    public void fireContactGGroupRemoved(ContactGGroup group) {
        for (ContactGListListener contactListListener : new ArrayList<ContactGListListener>(contactListListeners)) {
            contactListListener.contactGGroupRemoved(group);
        }
    }

    public void fireContactGItemClicked(ContactGItem contactGItem) {
        for (ContactGListListener contactListListener : new ArrayList<ContactGListListener>(contactListListeners)) {
            contactListListener.contactGItemClicked(contactGItem);
        }
    }

    public void fireContactGItemDoubleClicked(ContactGItem contactGItem) {
        for (ContactGListListener contactListListener : new ArrayList<ContactGListListener>(contactListListeners)) {
            contactListListener.contactGItemDoubleClicked(contactGItem);
        }
    }
}
