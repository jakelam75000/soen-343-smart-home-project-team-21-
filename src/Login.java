import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

    public Login(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SmartHomeDashboard.loginClicked(userText.getText(), new String(passwordText.getPassword()));
            }
        });

        passwordText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    SmartHomeDashboard.loginClicked(userText.getText(), new String(passwordText.getPassword()));
            }
        });
        userText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    SmartHomeDashboard.loginClicked(userText.getText(), new String(passwordText.getPassword()));
            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new Login("My Smart Home");
        frame.setVisible(true);

        frame.setSize(400,500);

    }
}
