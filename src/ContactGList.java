import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.spark.SparkManager;
import org.jivesoftware.spark.Workspace;
import org.jivesoftware.spark.component.VerticalFlowLayout;
import org.jivesoftware.spark.plugin.Plugin;
import org.jivesoftware.spark.ui.ContactGroup;
import org.jivesoftware.spark.ui.ContactGroupListener;
import org.jivesoftware.spark.ui.ContactItem;
import org.jivesoftware.spark.util.log.Log;
import org.jivesoftware.sparkimpl.settings.local.LocalPreferences;
import org.jivesoftware.sparkimpl.settings.local.SettingsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
        ContactGroupListener, Plugin, RosterListener, ConnectionListener {

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
        mainPanel.setBackground((Color)UIManager.get("ContactItem.background"));

        contactListScrollPane = new JScrollPane(mainPanel);
        contactListScrollPane.setAutoscrolls(true);

        contactListScrollPane.setBorder(BorderFactory.createEmptyBorder());
        contactListScrollPane.getVerticalScrollBar().setBlockIncrement(200);
        contactListScrollPane.getVerticalScrollBar().setUnitIncrement(20);

        add(contactListScrollPane, BorderLayout.CENTER);

        this.addContactGroup(unfiledGroup);
        System.out.println("unfiledGroup Added");


    }

    /**
     * Adds a new ContactGroup to the ContactList.
     *
     * @param group the group to add.
     */
    private void addContactGroup(ContactGGroup group) {
        groupList.add(group);

//        Collections.sort(groupList, GROUP_COMPARATOR);
        System.out.println("addContactGroup");
        try {
            mainPanel.add(group, groupList.indexOf(group));
            System.out.println("addContactGroup Done");
        } catch (Exception e) {
            Log.error(e);
            System.out.println("addContactGroup Error");
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
                cGroup = getSubContactGroup(contactGGroup, groupName);
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
    private ContactGGroup getSubContactGroup(ContactGGroup group, String groupName) {
        final Iterator<ContactGGroup> contactGGroups = group.getContactGGroups().iterator();
        ContactGGroup grp = null;

        while (contactGGroups.hasNext()) {
            ContactGGroup contactGroup = contactGGroups.next();
            if (contactGroup.getGroupName().equals(groupName)) {
                grp = contactGroup;
                break;
            }
            else if (contactGroup.getContactGGroups().size() > 0) {
                grp = getSubContactGroup(contactGroup, groupName);
                if (grp != null) {
                    break;
                }
            }

        }
        return grp;
    }

    /**
     * Sorts ContactGroups
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

    public void contactItemAdded(ContactItem contactItem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void contactItemRemoved(ContactItem contactItem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void contactItemDoubleClicked(ContactItem contactItem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void contactItemClicked(ContactItem contactItem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void showPopup(MouseEvent mouseEvent, ContactItem contactItem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void showPopup(MouseEvent mouseEvent, Collection collection) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void contactGroupPopup(MouseEvent mouseEvent, ContactGroup contactGroup) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void addContactListToWorkspace() {
        Workspace workspace = SparkManager.getWorkspace();
//        workspace.getWorkspacePane().addTab("My Tab",null,new JButton("Hello"));
        workspace.getWorkspacePane().addTab("ContactList", null, this);
    }

    public void initialize() {

        System.out.println();
        System.out.println("Welcome To Spark");
        // Add Contact List
        addContactListToWorkspace();

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

        this.addContactGroup(contactGGroup1);
        this.addContactGroup(contactGGroup2);
        this.addContactGroup(contactGGroup3);
        this.addContactGroup(contactGGroup4);
        contactGGroup4.addContactGroup(contactGGroup41);
        contactGGroup4.addContactGroup(contactGGroup42);
        contactGGroup4.addContactGroup(contactGGroup43);
        contactGGroup43.addContactGroup(contactGGroup431);
        contactGGroup43.addContactGroup(contactGGroup432);
        contactGGroup432.addContactGroup(contactGGroup4321);
        contactGGroup432.addContactGroup(contactGGroup4322);

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
}
