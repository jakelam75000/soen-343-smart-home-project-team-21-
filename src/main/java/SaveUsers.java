import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

/**
 * Window that prompts the user to select if they want to save the user changes or not.
 */
public class SaveUsers extends JFrame{
    private JButton dismissButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private SmartHomeDashboard caller;
    private SaveUsers self;
    private static SaveUsers instance = new SaveUsers("Save Users");

    //Bounds variables
    private static final int x = 300;
    private static final int y = 200;
    private static final int width = 375;
    private static final int height = 225;

    /**
     * Parameterised constructor.
     *
     * @param title String title of the frame.
     */
    private SaveUsers(String title){
        super(title);
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(x, y,width, height);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.self = this;

        addActionListeners();
    }

    /**
     * Getter for the single instance of SaveUsers.
     * @return SaveUsers instance.
     */
    public static SaveUsers getInstance(){
        return instance;
    }

    /**
     * Setter for the caller attribute.
     * @param caller SmartHomeDashboard object.
     */
    public void setCaller(SmartHomeDashboard caller){
        this.caller = caller;
    }

    /**
     * Adds all the action listeners for the class.
     */
    public void addActionListeners(){

        dismissButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
                caller.dispose();
                System.exit(0);
            }
        });

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDatabaseManager.updateUserFile();
                caller.dispose();
                self.dispose();
                System.exit(0);
            }
        });
    }

}
