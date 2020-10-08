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

    public AddUser(String title) {
        super(title);


        this.setContentPane(mainPanel);
        this.pack();

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(addParent.isSelected()) {
                    UserManager.addUser(userText.getText(), new String(passwordText.getPassword()), "parent");
                } else if(addChild.isSelected()) {
                    UserManager.addUser(userText.getText(), new String(passwordText.getPassword()), "child");
                } else {
                    UserManager.addUser(userText.getText(), new String(passwordText.getPassword()), "guest");
                }

            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new AddUser("Add User");
        frame.setVisible(true);

    }
}
