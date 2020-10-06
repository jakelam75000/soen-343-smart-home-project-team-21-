import javax.swing.*;

public class AddUser extends JFrame{
    private JPanel mainPanel;
    private JLabel usernameAdd;
    private JLabel passwordAdd;
    private JTextField textField1;
    private JTextField textField2;
    private JRadioButton addParent;
    private JRadioButton addChild;
    private JRadioButton addGuest;
    private JLabel addTypeLabel;
    private JButton createUserButton;
    private JButton goBackButton;

    public AddUser(String title) {
        super(title);


        this.setContentPane(mainPanel);
        this.pack();
    }

    public static void main(String[] args) {

        JFrame frame = new AddUser("Add User");
        frame.setVisible(true);

    }
}
