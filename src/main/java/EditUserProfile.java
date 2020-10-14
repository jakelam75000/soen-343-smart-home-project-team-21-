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

    public EditUserProfile(String title) {
        super(title);


        this.setContentPane(mainPanel);
        this.pack();
    }

    public static void main(String[] args) {

        JFrame frame = new EditUserProfile("Edit User Profile");
        frame.setVisible(true);
    }


}
