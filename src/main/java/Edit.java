import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private User currentuser;
    private SmartHomeDashboard caller;

    //Bounds variables
    private static final int x = 300;
    private static final int y = 200;
    private static final int width = 400;
    private static final int height = 300;

    /**
     * Parameterised contructor
     *
     * @param title String title of the frame
     * @param caller SmartHomeDashboard pointer of the calling object.
     */
    public Edit(String title, SmartHomeDashboard caller) {
        super(title);
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(x, y,width, height);
        this.caller = caller;

        addActionListeners();
    }

    /**
     * Adding all the action listeners.
     */
    public void addActionListeners(){
        confirmObjButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caller.blockWindow(comboWindows.getItemAt(comboWindows.getSelectedIndex()), blockedCheckBox.isSelected());
            }
        });

        comboWindows.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockedCheckBox.setSelected(caller.isWindowBlocked(comboWindows.getItemAt(comboWindows.getSelectedIndex())));
            }
        });

        comboUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboLocations.setSelectedItem(UserManager.getUserLocation(comboUsers.getItemAt(comboUsers.getSelectedIndex())));
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = comboUsers.getItemAt(comboUsers.getSelectedIndex());
                String userLocation = comboLocations.getItemAt(comboLocations.getSelectedIndex());
                String oldLocation = UserManager.getUserLocation(username);

                UserManager.changeUserLocation(username, userLocation);

                caller.printToConsole(username + " has moved from "+oldLocation+" to "+ userLocation+".");
            }
        });
    }

    /**
     * Method that sets up the edit window everytime it is open.
     *
     * @param locations String[] array containing all locations in the house.
     * @param windows a List of type String containing all the windows in the house.
     */
    public void setUpEditOptions(String[] locations, List<String> windows, String[] users, String currentUser){

            //Clearing all previous options so that the can be updated in case user made changes
            comboUsers.removeAllItems();
            comboLocations.removeAllItems();
            comboWindows.removeAllItems();

            //Setting up all the comboboxes with their options
            for(String user : users){
                if (!user.equals(currentUser))comboUsers.addItem(user);
            }

            for(String location : locations){
                comboLocations.addItem(location);
            }
            comboLocations.addItem("Outside");

            for(String window : windows){
                comboWindows.addItem(window);
            }

             comboLocations.setSelectedItem(UserManager.getUserLocation(comboUsers.getItemAt(comboUsers.getSelectedIndex())));
        }
}
