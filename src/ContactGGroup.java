import org.jivesoftware.resource.Res;
import org.jivesoftware.spark.component.VerticalFlowLayout;
import org.jivesoftware.spark.component.panes.CollapsiblePane;
import org.jivesoftware.spark.component.renderer.JPanelRenderer;
import org.jivesoftware.spark.ui.ContactGroup;
import org.jivesoftware.spark.util.GraphicUtils;
import org.jivesoftware.spark.util.log.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SaintKnight
 * Date: 13-8-20
 * Time: 下午4:02
 * Creator:Huang Jie
 * To change this template use File | Settings | File Templates.
 */
public class ContactGGroup extends CollapsiblePane implements MouseListener {
    private static final long serialVersionUID = 1L;

    private List<ContactGItem> contactGItems = new ArrayList<ContactGItem>();
    private List<ContactGGroup> contactGGroups = new ArrayList<ContactGGroup>();
    private List<ContactGGroupListener> listeners = new ArrayList<ContactGGroupListener>();
    private List<ContactGItem> offlineGContacts = new ArrayList<ContactGItem>();

    // Used to display no contacts in list.

    private String groupName;
    private DefaultListModel model;
    private JPanel listPanel;

    public ContactGGroup(String groupName) {
//        super(groupName);
        // Initialize Model and UI
        model = new DefaultListModel();
        contactGItemList = new JList(model);

        setTitle(getGroupTitle(groupName));

        // Use JPanel Renderer
        contactGItemList.setCellRenderer(new JPanelRenderer());

        this.groupName = groupName;

        listPanel = new JPanel(new VerticalFlowLayout(VerticalFlowLayout.TOP, 0, 0, true, false));


        listPanel.add(contactGItemList, listPanel);

        //设置分组是不可以拖拽的
        contactGItemList.setDragEnabled(false);
//        contactGItemList.setTransferHandler(new ContactGroupTransferHandler());


        this.setContentPane(listPanel);

    }

    /**
     * Returns the "pretty" title of the ContactGGroup.
     *
     * @param title the title.
     * @return the new title.
     */
    public String getGroupTitle(String title) {
        int lastIndex = title.lastIndexOf("::");
        if (lastIndex != -1) {
            title = title.substring(lastIndex + 2);
        }

        return title;
    }

    /**
     * Returns the name of the ContactGGroup.
     *
     * @return the name of the ContactGGroup.
     */
    public String getGroupName() {
        return groupName;
    }

    public Collection<ContactGGroup> getContactGGroups() {
        return contactGGroups;
    }


    /**
     * Adds a sub group to this Contact group.
     *
     * @param contactGGroup that should be the new subgroup
     */
    public void addContactGGroup(ContactGGroup contactGGroup) {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.add(contactGGroup, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 15, 0, 0), 0, 0));
        panel.setBackground(Color.white);
        contactGGroup.setSubPane(true);

        // contactGroup.setStyle(CollapsiblePane.TREE_STYLE);
        contactGGroups.add(contactGGroup);
        Collections.sort(contactGGroups, ContactGList.GROUP_COMPARATOR);
        listPanel.add(panel, contactGGroups.indexOf(contactGGroup));
    }

    /**
     * Removes a child ContactGGroup.
     *
     * @param contactGGroup the contact group to remove.
     */
    public void removeContactGGroup(ContactGGroup contactGGroup) {
        Component[] comps = listPanel.getComponents();
        for (Component comp : comps) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                ContactGGroup group = (ContactGGroup) panel.getComponent(0);
                if (group == contactGGroup) {
                    listPanel.remove(panel);
                    break;
                }
            }
        }
        contactGGroups.remove(contactGGroup);
    }

    public void setPanelBackground(Color color) {
        Component[] comps = listPanel.getComponents();
        for (Component comp : comps) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                panel.setBackground(color);
            }
        }

    }

    /**
     * Returns a ContactGroup based on it's name.
     *
     * @param groupName the name of the group.
     * @return the ContactGGroup.
     */
    public ContactGGroup getContactGGroup(String groupName) {
        for (ContactGGroup group : new ArrayList<ContactGGroup>(contactGGroups)) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }

        return null;
    }

    /**
     * Adds a <code>ContactGItem</code> to the ContactGroup.
     *
     * @param item the ContactGItem.
     */
    public void addContactGItem(ContactGItem item) {


        item.setGroupName(getGroupName());
        contactGItems.add(item);

        List<ContactGItem> tempItems = getContactGItems();


        Collections.sort(tempItems, itemComparator);


        int index = tempItems.indexOf(item);


        Object[] objs = contactGItemList.getSelectedValues();

        model.insertElementAt(item, index);

        int[] intList = new int[objs.length];
        for (int i = 0; i < objs.length; i++) {
            ContactGItem contact = (ContactGItem) objs[i];
            intList[i] = model.indexOf(contact);
        }

        if (intList.length > 0) {
            contactGItemList.setSelectedIndices(intList);
        }

        fireContactGItemAdded(item);
    }

    /**
     * Removes a <code>ContactGItem</code>.
     *
     * @param item the ContactGItem to remove.
     */
    public void removeContactGItem(ContactGItem item) {
        contactGItems.remove(item);
        if (contactGItems.isEmpty()) {
            removeContactGGroup(this);
        }

        model.removeElement(item);
        updateTitle();

        fireContactGItemRemoved(item);
    }

    private void updateTitle() {
        int count = 0;
        List<ContactGItem> list = new ArrayList<ContactGItem>(getContactGItems());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ContactGItem it = list.get(i);
            if (it.isAvailable()) {
                count++;
            }
        }
        setTitle(getGroupTitle(groupName) + " (" + count + " " + Res.getString("online") + ")");
    }

    /**
     * Returns all <code>ContactGItem</cod>s in the ContactGroup.
     *
     * @return all ContactGItems.
     */
    public List<ContactGItem> getContactGItems() {
        final List<ContactGItem> list = new ArrayList<ContactGItem>(contactGItems);
        Collections.sort(list, itemComparator);
        return list;
    }

    /**
     * Sorts ContactGItems.
     */
    final protected Comparator<ContactGItem> itemComparator = new Comparator<ContactGItem>() {
        public int compare(ContactGItem item1, ContactGItem item2) {
            return item1.getDisplayName().toLowerCase().compareTo(item2.getDisplayName().toLowerCase());
        }
    };



    /**
     * Returns a <code>ContactGItem</code> by the users bare bareJID.
     *
     * @param bareJID the bareJID of the user.
     * @return the ContactGItem.
     */
    public ContactGItem getContactGItemByJID(String bareJID) {
        for (ContactGItem item : new ArrayList<ContactGItem>(contactGItems)) {
            if (item != null && item.getJID().equals(bareJID)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Returns a <code>ContactGItem</code> by the displayed name the user has been assigned.
     *
     * @param displayName the displayed name of the user.
     * @return the ContactGItem.
     */
    public ContactGItem getContactGItemByDisplayName(String displayName) {
        for (ContactGItem item : new ArrayList<ContactGItem>(contactGItems)) {
            if (item.getDisplayName().equals(displayName)) {
                return item;
            }
        }
        return null;
    }

    public void mouseClicked(MouseEvent e) {

        Object o = contactGItemList.getSelectedValue();
        if (!(o instanceof ContactGItem)) {
            return;
        }

        // Iterator through rest
        ContactGItem item = (ContactGItem) o;

        if (e.getClickCount() == 2) {
            //双击事件，测试版本
            fireContactGItemDoubleClicked(item);
        } else if (e.getClickCount() == 1) {

            //单击事件，测试版本
            fireContactGItemClicked(item);
        }
    }

    public void mousePressed(MouseEvent e) {
        checkPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        checkPopup(e);
    }

    public void mouseEntered(MouseEvent e) {
        int loc = contactGItemList.locationToIndex(e.getPoint());

        Object o = model.getElementAt(loc);
        if (!(o instanceof ContactGItem)) {
            return;
        }

        contactGItemList.setCursor(GraphicUtils.HAND_CURSOR);
    }

    public void mouseExited(MouseEvent e) {
        Object o;
        try {
            int loc = contactGItemList.locationToIndex(e.getPoint());
            if (loc == -1) {
                return;
            }

            o = model.getElementAt(loc);
            if (!(o instanceof ContactGItem)) {
//                UIComponentRegistry.getContactInfoWindow().dispose();
                return;
            }
        } catch (Exception e1) {
            Log.error(e1);
            return;
        }

        contactGItemList.setCursor(GraphicUtils.DEFAULT_CURSOR);
    }

    private void checkPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            // Otherwise, handle single selection
            int index = contactGItemList.locationToIndex(e.getPoint());
            if (index != -1) {
                int[] indexes = contactGItemList.getSelectedIndices();
                boolean selected = false;
                for (int o : indexes) {
                    if (index == o) {
                        selected = true;
                    }
                }

                if (!selected) {
                    contactGItemList.setSelectedIndex(index);
//                    fireContactGItemClicked((ContactGItem)contactGItemList.getSelectedValue());
                }
            }


//            final Collection<ContactGItem> selectedItems = SparkManager.getChatManager().getSelectedContactGItems();
//            if (selectedItems.size() > 1) {
//                firePopupEvent(e, selectedItems);
//            }
//            else if (selectedItems.size() == 1) {
//                final ContactGItem contactGItem = selectedItems.iterator().next();
//                firePopupEvent(e, contactGItem);
//            }
        }
    }

    private void fireContactGItemClicked(ContactGItem item) {
        for (ContactGGroupListener contactGroupListener : new ArrayList<ContactGGroupListener>(listeners)) {
            contactGroupListener.contactGItemClicked(item);
        }
    }

    private void fireContactGItemDoubleClicked(ContactGItem item) {
        for (ContactGGroupListener contactGroupListener : new ArrayList<ContactGGroupListener>(listeners)) {
            contactGroupListener.contactGItemDoubleClicked(item);
        }
    }


    private void firePopupEvent(MouseEvent e, ContactGItem item) {
        for (ContactGGroupListener contactGroupListener : new ArrayList<ContactGGroupListener>(listeners)) {
            contactGroupListener.showPopup(e, item);
        }
    }

    private void firePopupEvent(MouseEvent e, Collection<ContactGItem> items) {
        for (ContactGGroupListener contactGroupListener : new ArrayList<ContactGGroupListener>(listeners)) {
            contactGroupListener.showPopup(e, items);
        }
    }

    private void fireContactGroupPopupEvent(MouseEvent e) {
        for (ContactGGroupListener contactGroupListener : new ArrayList<ContactGGroupListener>(listeners)) {
            contactGroupListener.contactGGroupPopup(e, this);
        }
    }

    private void fireContactGItemAdded(ContactGItem item) {
        for (ContactGGroupListener contactGroupListener : new ArrayList<ContactGGroupListener>(listeners)) {
            contactGroupListener.contactGItemAdded(item);
        }
    }

    private void fireContactGItemRemoved(ContactGItem item) {
        for (ContactGGroupListener contactGroupListener : new ArrayList<ContactGGroupListener>(listeners)) {
            contactGroupListener.contactGItemRemoved(item);
        }
    }
    private JList contactGItemList;
    public void clearSelection() {
        contactGItemList.clearSelection();
    }
}
