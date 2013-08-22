import org.jivesoftware.spark.component.VerticalFlowLayout;
import org.jivesoftware.spark.component.panes.CollapsiblePane;
import org.jivesoftware.spark.component.renderer.JPanelRenderer;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: SaintKnight
 * Date: 13-8-20
 * Time: 下午4:02
 * Creator:Huang Jie
 * To change this template use File | Settings | File Templates.
 */
public class ContactGGroup extends CollapsiblePane implements MouseListener{
    private static final long serialVersionUID = 1L;

    // Used to display no contacts in list.

    private String groupName;
    private DefaultListModel model;
    private JList contactItemList;
    private JPanel listPanel;

    public ContactGGroup(String groupName) {
        // Initialize Model and UI
        model = new DefaultListModel();
        contactItemList = new JList(model);

        setTitle(getGroupTitle(groupName));

        contactItemList.setCellRenderer(new JPanelRenderer());

        this.groupName = groupName;

        listPanel = new JPanel(new VerticalFlowLayout(VerticalFlowLayout.TOP, 0, 0, true, false));
        listPanel.add(contactItemList, listPanel);
        this.setContentPane(listPanel);

    }
    /**
     * Returns the "pretty" title of the ContactGroup.
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



    public void mouseClicked(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
