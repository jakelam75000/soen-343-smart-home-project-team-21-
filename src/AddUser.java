import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUser extends JFrame{
    private JPanel mainPanel;
    private JLabel usernameAdd;
    private JLabel passwordAdd;
    private JPasswordField passwordText;
    private JTextField userText;
    private JRadioButton addParent;
    private JRadioButton addChild;
    private JRadioButton addGuest;
    private JLabel addTypeLabel;
    private JButton createUserButton;
    private JButton goBackButton;
    private JRadioButton strangerRadioButton;

    public AddUser(String title) {
        super(title);


        this.setContentPane(mainPanel);
        this.pack();

        addActionListeners();
    }

    public void addActionListeners() {
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(addParent.isSelected()) {
                    UserManager.addUser(userText.getText(), new String(passwordText.getPassword()), UserTypes.PARENT.toString());
                } else if(addChild.isSelected()) {
                    UserManager.addUser(userText.getText(), new String(passwordText.getPassword()), UserTypes.CHILD.toString());
                } else {
                    UserManager.addUser(userText.getText(), new String(passwordText.getPassword()), UserTypes.GUEST.toString());
                }
            }
        });
    }
    public static void main(String[] args) {

        JFrame frame = new AddUser("Add User");
        frame.setVisible(true);

    }
}
