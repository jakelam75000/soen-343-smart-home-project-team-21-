import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * frame that holds the adduser functionality
 */
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
    private static AddUser instance = new AddUser("Add User");

    /**
     * Parameterised constructor.
     *
     * @param title String title of the frame
     */
    private AddUser(String title) {
        super(title);

        this.setContentPane(mainPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.addGuest.setSelected(true);
        self = this;
        this.setResizable(false);
        addActionListeners();
    }

    /**
     * Returns the single instance of the AddUser class.
     * @return AddUser object.
     */
    public static AddUser getInstance(){
        return instance;
    }

    /**
     * Setter for the caller attribute.
     * @param caller SmartHomeDashboard
     */
    public void setCaller(SmartHomeDashboard caller){
        this.caller = caller;
    }

    /**
     * Adds all the action listeners for the class.
     */
    public void addActionListeners() {

        createUserButton.addActionListener(new ActionListener() {
            /**
             * Creates the user if it is within acceptable parameters and stores it in usermanager (only holds for the session)
             * @param e ActionEvent
             *
             */
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
                        caller.printToConsole("The user was unable to be added.");
                    }
                }
                userText.setText("");
                passwordText.setText("");

                caller.updateUsers();
            }
        });

        goBackButton.addActionListener(new ActionListener() {
            /**
             * REturns to the previouse window
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                self.setVisible(false);
            }
        });
    }
}