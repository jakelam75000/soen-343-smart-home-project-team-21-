import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlertConfirmationWindow extends JFrame{
    private JLabel decreasingTimer;
    private JButton cancelAlertButton;
    private JButton confirmAlertButton;
    private JLabel topLabel;
    private JLabel bottomLabel;
    private JPanel mainPanel;

    private int multiplier;
    private SHPObserver caller;
    private AlertConfirmationWindow self;

    //Bounds variables
    private static final int x = 300;
    private static final int y = 200;
    private static final int width = 500;
    private static final int height = 300;


    /**
     * Parameterised constructor.
     *
     * @param caller reference to the calling <code>SHPObserver</code> object.
     */
    public AlertConfirmationWindow(SHPObserver caller){
        super("Intruder Detected!");
        this.caller = caller;
        multiplier = caller.getMultiplier();
        decreasingTimer.setText(caller.getTimer());
        self = this;

        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(x, y, width, height);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        addActionListeners();
    }

    /**
     * Adds all the action listeners.
     */
    private void addActionListeners() {

        cancelAlertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.setVisible(false);
                caller.cancelAlert();
            }
        });

        confirmAlertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topLabel.setText("THE AUTHORITIES HAVE BEEN CALLED!");
                bottomLabel.setText("COPS ARE ON THEIR WAY TO YOUR HOUSE!");
                self.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                caller.confirmAlert();
            }
        });
    }


}
