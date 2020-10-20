import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * frame that holds the login information
 */
public class Login extends JFrame {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private JPasswordField passwordText;
    private JTextField userText;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton UploadFile;
    private JLabel housefieldlabel;
    private String filepath;
    private static String lasthousefilepath = null;

    //Bounds variables
    private static final int x = 300;
    private static final int y = 200;
    private static final int width = 400;
    private static final int height = 300;

    /**
     * Parameterised constructor.
     *
     * @param title String title of the frame.
     */
    public Login(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        this.setBounds(x, y, width, height);
        this.setResizable(false);
        addActionListeners();

        if (lasthousefilepath!=null){
            UploadFile.setVisible(false);
            housefieldlabel.setVisible(false);
        }
    }

    /**
     * Adding all the action listeners.
     */
    public void addActionListeners() {
        loginButton.addActionListener(new ActionListener() {
            /**
             * calles the login clicked method to verify information adn then allow to proceed to the next frame
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                loginClicked(userText.getText(), new String(passwordText.getPassword()), filepath);
            }
        });

        passwordText.addKeyListener(new KeyAdapter() {
            /**
             * allows for the user to login by pressing enter
             * @param e ActionEvenet
             */
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    loginClicked(userText.getText(), new String(passwordText.getPassword()), filepath);
            }
        });

        userText.addKeyListener(new KeyAdapter() {
            /**
             * allows for the user to login by pressing enter
             * @param e ActionEvenet
             */
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    loginClicked(userText.getText(), new String(passwordText.getPassword()), filepath);
            }
        });

        UploadFile.addActionListener(new ActionListener() {
            /**
             * allows for the user to choose the file via file explorer
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.showOpenDialog(UploadFile);
                if (fc.getSelectedFile()!= null) {
                    filepath = fc.getSelectedFile().getAbsoluteFile().toString();
                }
                else filepath=null;
                System.out.println(filepath);
            }
        });
    }

    /**
     * Authenticates the username/password entered and sets up the dashboard.
     *
     * @param username String username entered
     * @param password String password entered
     */
    public boolean loginClicked(String username, String password, String houseFilePath){
        // User Authentication
        User user = UserManager.findUser(username, password);
        House temp;
        File f = null;
        if (houseFilePath != null) {
            f = new File(houseFilePath);
            lasthousefilepath = houseFilePath;
        }

        if (lasthousefilepath != null) f = new File(lasthousefilepath);

        temp = HouseReader.readAndLoadHouse(f.getPath());

        if(user != null && f!=null && f.exists() && f.isFile() && temp!=null) {
            this.setVisible(false);

            // User type
            if(user instanceof Child) {
                System.out.println("It is a child");
                // Show house simulator for child
                new SmartHomeDashboard("Smart Home Simulator", UserTypes.CHILD.toString(), username, f.getPath()).setVisible(true);
            } else if (user instanceof Parent) {
                System.out.println("It is a parent");
                // Show house simulator for parent
                new SmartHomeDashboard("Smart Home Simulator", UserTypes.PARENT.toString(), username, f.getPath()).setVisible(true);
            } else if (user instanceof Guest) {
                System.out.println("It is a guest");
                // Show house simulator for guest
                new SmartHomeDashboard("Smart Home Simulator", UserTypes.GUEST.toString(), username, f.getPath()).setVisible(true);
            }
            else if (user instanceof Stranger) System.out.println("Login failed, trying to login as stranger");
            return true;
        } else {
            System.out.println("Login failed");
            return false;
        }

    }

}
