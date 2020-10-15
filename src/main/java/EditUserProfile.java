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
                System.out.println("working up to here");
                if (!UserManager.isUserValid(currentUsername.getText(), new String(oldPassword.getPassword())))return;
                String type = "error";
                if (parentRadio.isSelected())type = UserTypes.PARENT.toString();
                else if (childRadio.isSelected())type = UserTypes.CHILD.toString();
                else if (guestRadio.isSelected())type = UserTypes.GUEST.toString();
                else if (strangerRadio.isSelected())type = UserTypes.STRANGER.toString();
                if (type.equals("error")) return;
                System.out.println("the type is " + type );
                UserManager.removeUser(currentUsername.getText());
                UserManager.addUser(currentUsername.getText(), new String(newPassword.getPassword()), type);
                caller.printToConsole(currentUsername.getText() + "'s password has been updated.");
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!UserManager.isUserValid(currentUsername.getText(), new String(oldPassword.getPassword())) && !UserManager.isUserValid(currentUsername.getText(),"null")){
                    return;
                }
                UserManager.removeUser(currentUsername.getText());
                if (currentUsername.getText().equals(callingUser)) {
                    Login loginFrame = new Login("Login");
                    loginFrame.setVisible(true);
                    self.setVisible(false);
                    caller.setVisible(false);
                }

                caller.updateUsers();
                caller.printToConsole(currentUsername.getText() + "'s account has been deleted.");
                self.setVisible(false);
            }
        });
    }
}
