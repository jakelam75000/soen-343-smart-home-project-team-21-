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

    /**
     * Parameterised constructor.
     *
     * @param title String title of the frame
     * @param caller SmartHomeDashboard pointer referring to the caller
     */
    public AddUser(String title, SmartHomeDashboard caller) {
        super(title);

        this.setContentPane(mainPanel);
        this.pack();
        this.addGuest.setSelected(true);
        self = this;
        this.caller = caller;
        this.setResizable(false);
        addActionListeners();
    }

    /**
     * Adds all the action listeners for the class.
     */
    public void addActionListeners() {
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                UserTypes type = null;
                // Only can add user there is a password, username and type OR if user is of type stranger it does not need a password
                if ((strangerRadioButton.isSelected() && !userText.getText().equals("")) ||
                        (passwordText.getPassword().length != 0 && !userText.getText().equals(""))) {
                    if(addParent.isSelected()) {
                        type = UserTypes.PARENT;
                    } else if(addChild.isSelected()) {
                        type = UserTypes.CHILD;
                    } else if (addGuest.isSelected()) {
                        type = UserTypes.GUEST;
                    } else if (strangerRadioButton.isSelected()) {
                        type = UserTypes.STRANGER;
                    }
                    if(UserManager.addUser(username, password,type)) {
                        caller.printToConsole(userText.getText() +" has been added.");
                    } else {
                        caller.printToConsole("This username already exists.");
                    }
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
}
