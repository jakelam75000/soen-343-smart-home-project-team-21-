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
    private Timer timetillcops;
    Boolean timeleft = true;

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
        int[] formattime = SmartHomeDashboard.Breakdowntime(caller.getTimer());
        int temphr = formattime[0];
        int tempsec = formattime[2];
        int tempmin = formattime[1];
        String hour,minute,second;
        if (temphr <10) hour = "0" + temphr;
        else hour = String.valueOf(temphr);
        if (tempmin <10) minute = "0" + tempmin;
        else minute = String.valueOf(tempmin);
        if (tempsec <10) second = "0" + tempsec;
        else second = String.valueOf(tempsec);
        decreasingTimer.setText(hour+":"+minute+":"+second);
        self = this;
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(x, y, width, height);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addActionListeners();
        timetillcops.setDelay(1000/multiplier);
        timetillcops.start();
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
                timetillcops.stop();
            }
        });
        timetillcops = new Timer(1000/multiplier, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decreasingTimer.setText(UpdateCountdowntimer(decreasingTimer.getText()));
                if (!timeleft){
                    caller.confirmAlert();
                    timetillcops.stop();
                    self.setVisible(false);

                }
            }
        });
    }

    /**
     * Similar to the updatetime method in SMARTHOMEDASHBOARD but this one does not concern itself with date and coutns down instead of up
     * @param inputime the current time to be updated
     * @return the new time in a standard time formated string (e.g. "01:24:06")
     */
    public String UpdateCountdowntimer(String inputime){
        String outputtime;
        int temphr;
        int tempmin;
        int tempsec;
        String second;
        String hour;
        String minute;
        int[] temptime = SmartHomeDashboard.Breakdowntime(inputime);
        tempmin = temptime[1];
        temphr = temptime[0];
        tempsec = temptime[2];
        if (timeleft)tempsec--;
        else tempsec++;
        if (tempsec<0&& timeleft){
            tempsec=59;
            tempmin--;
        }else if (tempsec == 59 && !timeleft){
            tempmin++;
            tempsec=0;
        }
        if (tempmin<0 && timeleft){
            temphr--;
            tempmin=59;
        }else if (tempmin ==60 && !timeleft){
            tempmin = 0;
            temphr++;
        }
        if(temphr <0 && timeleft){
            temphr = 0;
            timeleft =false;
        }
        if (temphr <10) hour = "0" + temphr;
        else hour = String.valueOf(temphr);
        if (tempmin <10) minute = "0" + tempmin;
        else minute = String.valueOf(tempmin);
        if (tempsec <10) second = "0" + tempsec;
        else second = String.valueOf(tempsec);
        if (timeleft)outputtime = hour+":"+minute+":"+second;
        else outputtime = "-" +hour+":"+minute+":"+second;
        return outputtime;
    }
}
