import javax.swing.*;

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

    public EditUserProfile(String title, String curtype,String Username) {
        super(title);
        currentUsername.setText(Username);
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
        this.setContentPane(mainPanel);
        this.pack();
    }

    public static void main(String[] args){

        JFrame frame = new EditUserProfile("Edit User Profile","PARENT","mt");
        frame.setVisible(true);
    }


}
