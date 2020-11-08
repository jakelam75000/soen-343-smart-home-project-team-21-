import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Frame that holds the object to be manipulated and the users and their locations to be set
 */
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
    private User currentUser;
    private SmartHomeDashboard caller;
    private static Edit instance = new Edit("Edit");

    //Bounds variables
    private static final int x = 300;
    private static final int y = 200;
    private static final int width = 560;
    private static final int height = 300;

    /**
     * Parameterised contructor
     *
     * @param title String title of the frame
     */
    private Edit(String title) {
        super(title);
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(x, y,width, height);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addActionListeners();
    }

    /**
     * Returns the single instance of Edit class.
     * @return
     */
    public static Edit getInstance(){
        return instance;
    }

    /**
     * Sets the caller attribute.
     * @param caller SmartHomeDashboard object.
     */
    public void setCaller(SmartHomeDashboard caller){
        this.caller = caller;
    }

    /**
     * Adding all the action listeners.
     */
    public void addActionListeners(){
        confirmObjButton.addActionListener(new ActionListener() {
            /**
             * Blocks or unblocks the window depending on if its checked or not
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                caller.blockWindow(comboWindows.getItemAt(comboWindows.getSelectedIndex()), blockedCheckBox.isSelected());
                caller.updateHouseLayout();
            }
        });

        comboWindows.addActionListener(new ActionListener() {
            /**
             * sets the default value of the check box to if the window is blocked or not
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                blockedCheckBox.setSelected(caller.isWindowBlocked(comboWindows.getItemAt(comboWindows.getSelectedIndex())));
            }
        });

        comboUsers.addActionListener(new ActionListener() {
            /**
             * sets the default location of the user to their current location
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                comboLocations.setSelectedItem(UserManager.getUserLocation(comboUsers.getItemAt(comboUsers.getSelectedIndex())));
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            /**
             * Sets the user's location to the new location
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = comboUsers.getItemAt(comboUsers.getSelectedIndex());
                String userLocation = comboLocations.getItemAt(comboLocations.getSelectedIndex());
                String oldLocation = UserManager.getUserLocation(username);

                UserManager.changeUserLocation(username, userLocation);

                //calls autoLights function in smart home dashboard
                caller.autoLights(oldLocation, userLocation);

                caller.printToConsole(username + " has moved from "+oldLocation+" to "+ userLocation+".");
                caller.updateHouseLayout();
                caller.notifyObservers(caller);
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
            comboLocations.addItem(LocationType.OUTSIDE.toString());

            for(String window : windows){
                comboWindows.addItem(window);
            }

            comboLocations.setSelectedItem(UserManager.getUserLocation(comboUsers.getItemAt(comboUsers.getSelectedIndex())));
    }
}
