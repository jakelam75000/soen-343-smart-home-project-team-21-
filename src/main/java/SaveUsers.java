import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class SaveUsers extends JFrame{
    private JButton dismissButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private SmartHomeDashboard caller;
    private SaveUsers self;

    //Bounds variables
    private static final int x = 300;
    private static final int y = 200;
    private static final int width = 560;
    private static final int height = 300;

    public SaveUsers(String title, SmartHomeDashboard caller){
        super(title);
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(x, y,width, height);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.caller = caller;
        this.self = this;

        addActionListeners();
    }

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
