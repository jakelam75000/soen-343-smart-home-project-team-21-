import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class Edit extends JFrame {
    private JPanel mainPanel;
    private JComboBox<String> comboUsers;
    private JLabel userLabel;
    private JLabel locationLabel;
    private JComboBox<String> comboLocations;
    private JButton confirmButton;
    private JComboBox<String> comboWindows;
    private JCheckBox blockedCheckBox;
    private JButton confirmObjButton;


        public Edit(String title) {
            super(title);
            this.setContentPane(mainPanel);
            this.pack();

            addActionListeners();
        }

        public void addActionListeners(){
            confirmObjButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.blockWindow(comboWindows.getItemAt(comboWindows.getSelectedIndex()), blockedCheckBox.isSelected());
                }
            });

            comboWindows.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    blockedCheckBox.setSelected(Main.isWindowBlocked(comboWindows.getItemAt(comboWindows.getSelectedIndex())));
                }
            });
        }

    /**
     * Method that sets up the edit window everytime it is open.
     *
     * @param locations String[] array containing all locations in the house.
     * @param windows a List of type String containing all the windows in the house.
     */
    public void setUpEditOptions(String[] locations, List<String> windows){

            //Clearing all previous options so that the can be updated in case user made changes
            comboUsers.removeAllItems();
            comboLocations.removeAllItems();
            comboWindows.removeAllItems();

            //Setting up all the comboboxes with their options
            HashMap<String, Parent> parents = UserManager.getUserParent();
            HashMap<String, Child> children = UserManager.getUserChild();
            HashMap<String, Guest> guests = UserManager.getUserGuest();

            parents.forEach((k, v) -> comboUsers.addItem(k));
            children.forEach((k, v) -> comboUsers.addItem(k));
            guests.forEach((k, v) -> comboUsers.addItem(k));

            for(String location : locations){
                comboLocations.addItem(location);
            }

            for(String window : windows){
                comboWindows.addItem(window);
            }



        }

        public static void main(String[] args) {

            JFrame frame = new Edit("Edit");
            frame.setVisible(true);

        }
}
