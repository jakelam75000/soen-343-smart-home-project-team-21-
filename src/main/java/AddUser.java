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
    private AddUser self;
    private SmartHomeDashboard caller;

    public AddUser(String title, SmartHomeDashboard caller) {
        super(title);

        this.setContentPane(mainPanel);
        this.pack();

        self = this;
        this.caller = caller;

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
                } else if (addGuest.isSelected()){
                    UserManager.addUser(userText.getText(), new String(passwordText.getPassword()), UserTypes.GUEST.toString());
                }
                else if(strangerRadioButton.isSelected()){
                    UserManager.addUser(userText.getText(), null,UserTypes.STRANGER.toString());
                }

                caller.printToConsole(userText.getText() +" has been added.");

                userText.setText("");
                passwordText.setText("");

                caller.updateUsers();
            }
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.setVisible(false);
            }
        });
    }
//    public static void main(String[] args) {
//
//        JFrame frame = new AddUser("Add User");
//        frame.setSize(300,400);
//        frame.setVisible(true);
//
//    }
}
