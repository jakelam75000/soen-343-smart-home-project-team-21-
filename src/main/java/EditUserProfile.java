import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Frame that allows the user to change type and apsswords of users
 */
public class EditUserProfile extends JFrame {
    private JLabel username;
    private JLabel oldPasswordLabel;
    private JLabel newPasswordLabel;
    private JLabel currentUsername;
    private JRadioButton parentRadio;
    private JRadioButton childRadio;
    private JRadioButton guestRadio;
    private JRadioButton strangerRadio;
    private JPanel mainPanel;
    private JButton confirmChangesButton;
    private JButton deleteUserButton;
    private JPasswordField oldPassword;
    private JPasswordField newPassword;
    private String callingUser;
    private EditUserProfile self;
    private SmartHomeDashboard caller;
    private static EditUserProfile instance = new EditUserProfile("Edit User Profile");

    /**
     * Constructor
     * @param title String Name of the frame
     */
    private EditUserProfile(String title) {
        super(title);
        this.setContentPane(mainPanel);
        this.pack();
        this.setResizable(false);
        addActionListeners();
        self = this;
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /**
     * Getter for the single instance of EditUserProfile class.
     * @return EditUserProfile instance.
     */
    public static EditUserProfile getInstance(){
        return instance;
    }

    /**
     * Setter for the current type of the user.
     * @param currentType String current type of the user
     */
    public void setCurrentType(String currentType){
        if (currentType.equals("CHILD")){
            parentRadio.setEnabled(false);
            guestRadio.setEnabled(false);
            strangerRadio.setEnabled(false);
            childRadio.setSelected(true);
        }else if (currentType.equals("GUEST")){
            parentRadio.setEnabled(false);
            childRadio.setEnabled(false);
            strangerRadio.setEnabled(false);
            guestRadio.setSelected(true);
        } else if(currentType.equals("PARENT")){
            parentRadio.setSelected(true);
            childRadio.setEnabled(true);
            strangerRadio.setEnabled(true);
            guestRadio.setEnabled(true);
            parentRadio.setEnabled(true);
        }
    }

    /**
     * Setter for the username of the user to be edited.
     * @param username String username of the user to be edited.
     */
    public void setUsername(String username){
        currentUsername.setText(username);
    }

    /**
     * Setter for he calling user.
     * @param callingUser String the calling user.
     */
    public void setCallingUser(String callingUser){
        this.callingUser = callingUser;
    }

    /**
     * Setter for the caller attribute.
     * @param caller SmartHomeDashboard object.
     */
    public void setCaller(SmartHomeDashboard caller){
        this.caller = caller;
    }

    /**
     * Adds all the actions listeners of the class.
     */
    public void addActionListeners(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                oldPassword.setText("");
                newPassword.setText("");
            }
        });

        confirmChangesButton.addActionListener(new ActionListener() {
            /**
             * Confirms the changes to be made
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = currentUsername.getText();
                String oldPasswordFormatted = new String(oldPassword.getPassword());
                String newPasswordFormatted = new String(newPassword.getPassword());

                UserTypes type = null;
                if (parentRadio.isSelected())type = UserTypes.PARENT;
                else if (childRadio.isSelected())type = UserTypes.CHILD;
                else if (guestRadio.isSelected())type = UserTypes.GUEST;
                else if (strangerRadio.isSelected())type = UserTypes.STRANGER;
                if (type == null) return;

                //Check if we successfully edited the user
                if (UserManager.editUser(username, oldPasswordFormatted, newPasswordFormatted, type)) {
                    caller.printToConsole(username + "'s password and/or type has been updated.");
                    oldPassword.setText("");
                    newPassword.setText("");
                } else {
                    caller.printToConsole(username + "'s password and/or type has failed to be updated please try again.");
                }
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            /**
             * confirms that the selected user is the one to be deleted (needs to have the correct password in oldpassword)
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = currentUsername.getText();
                String password = new String(oldPassword.getPassword());
                if (UserManager.removeUser(username, password)) {
                    if (username.equals(callingUser)) {
                        Login loginFrame = new Login("Login");
                        loginFrame.setVisible(true);
                        self.setVisible(false);
                        caller.setVisible(false);
                    }

                    caller.updateUsers();
                    caller.printToConsole(username + "'s account has been deleted.");
                    oldPassword.setText("");
                    newPassword.setText("");

                    self.setVisible(false);
                } else {
                    caller.printToConsole(username + "'s account has not been deleted please try again.");
                }
            }
        });
    }
}