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
        strangerRadioButton.setSelected(true);
        self = this;
        this.caller = caller;

        addActionListeners();
    }

    public void addActionListeners() {
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(addParent.isSelected() && passwordText.getPassword().length != 0 && !userText.getText().equals("") ) {
                    System.out.println("the password is : "+ passwordText.getPassword());
                    UserManager.addUser(userText.getText(), new String(passwordText.getPassword()), UserTypes.PARENT.toString());
                    caller.printToConsole(userText.getText() +" has been added.");
                } else if(addChild.isSelected()&& passwordText.getPassword().length != 0 && !userText.getText().equals("")) {
                    UserManager.addUser(userText.getText(), new String(passwordText.getPassword()), UserTypes.CHILD.toString());
                    caller.printToConsole(userText.getText() +" has been added.");
                } else if (addGuest.isSelected()&& passwordText.getPassword().length != 0 && !userText.getText().equals("")){
                    UserManager.addUser(userText.getText(), new String(passwordText.getPassword()), UserTypes.GUEST.toString());
                    caller.printToConsole(userText.getText() +" has been added.");
                }
                else if(strangerRadioButton.isSelected() && !userText.getText().equals("")){
                    UserManager.addUser(userText.getText(), null,UserTypes.STRANGER.toString());
                    caller.printToConsole(userText.getText() +" has been added.");
                }
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
