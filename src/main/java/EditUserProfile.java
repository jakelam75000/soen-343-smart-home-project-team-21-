import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUserProfile extends JFrame {
    private JLabel username;
    private JLabel oldPassword;
    private JLabel newPassword;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel currentUsername;
    private JRadioButton parentRadio;
    private JRadioButton childRadio;
    private JRadioButton guestRadio;
    private JRadioButton strangerRadio;
    private JPanel mainPanel;
    private JButton confirmChangesButton;
    private JButton deleteUserButton;
    private String callinguser;
    private EditUserProfile p;

    public EditUserProfile(String title, String curtype,String userna, String calluser) {
        super(title);
        currentUsername.setText(userna);
        if (curtype.equals("CHILD")){
            parentRadio.setEnabled(false);
            guestRadio.setEnabled(false);
            strangerRadio.setEnabled(false);
            childRadio.setSelected(true);
        }else if (curtype.equals("STRANGER")){
            parentRadio.setEnabled(false);
            guestRadio.setEnabled(false);
            childRadio.setEnabled(false);
            strangerRadio.setSelected(true);
        }else if (curtype.equals("GUEST")){
            parentRadio.setEnabled(false);
            childRadio.setEnabled(false);
            strangerRadio.setEnabled(false);
            guestRadio.setSelected(true);
        }
        callinguser = calluser;
        this.setContentPane(mainPanel);
        this.pack();
        addActionListeners();
        p = this;
    }

//    public static void main(String[] args){
//
//        JFrame frame = new EditUserProfile("Edit User Profile","PARENT","mt", "noone");
//        frame.setVisible(true);
//    }

    public void addActionListeners(){
        confirmChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("working up to here");
                if (!UserManager.isUserValid(currentUsername.getText(), textField1.getText()))return;
                String type = "error";
                if (parentRadio.isSelected())type = UserTypes.PARENT.toString();
                else if (childRadio.isSelected())type = UserTypes.CHILD.toString();
                else if (guestRadio.isSelected())type = UserTypes.GUEST.toString();
                else if (strangerRadio.isSelected())type = UserTypes.STRANGER.toString();
                if (type.equals("error")) return;
                System.out.println("the type is " + type );
                UserManager.removeUser(currentUsername.getText());
                UserManager.addUser(currentUsername.getText(), textField2.getText(), type);
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( !UserManager.isUserValid(currentUsername.getText(), textField1.getText()) && !UserManager.isUserValid(currentUsername.getText(),"null"))return;
                UserManager.removeUser(currentUsername.getText());
                if (currentUsername.getText().equals(callinguser)) {
                    Main.logoutClicked();
                    p.setVisible(false);
                }
            }
        });
    }
}
