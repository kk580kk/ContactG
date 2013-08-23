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
import java.util.ArrayList;
import java.util.Collection;
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

        ContactGGroup contactGGroup1 = new ContactGGroup("unfiledGroup");
        this.addContactGroup(contactGGroup1);


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
