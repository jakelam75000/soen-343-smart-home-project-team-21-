import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public EditUserProfile(String title, String currentType,String username, String calluser, SmartHomeDashboard caller) {
        super(title);
        currentUsername.setText(username);
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
        }
        callingUser = calluser;
        this.setContentPane(mainPanel);
        this.pack();
        this.setResizable(false);
        addActionListeners();
        self = this;
        this.caller = caller;

    }

//    public static void main(String[] args){
//
//        JFrame frame = new EditUserProfile("Edit User Profile","PARENT","mt", "none");
//        frame.setVisible(true);
//    }

    public void addActionListeners(){
        confirmChangesButton.addActionListener(new ActionListener() {
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
                } else {
                    caller.printToConsole(username + "'s password and/or type has failed to be updated please try again.");
                }
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
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

                    self.setVisible(false);
                } else {
                    caller.printToConsole(username + "'s account has not been deleted please try again.");
                }
            }
        });
    }
}
