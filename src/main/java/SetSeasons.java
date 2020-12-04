import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetSeasons extends JFrame{
    private JButton ConfimSeasonsButton;
    private JButton CancelSeasonButton;
    private JSpinner startWinterMonthSpinner;
    private JSpinner EndWinterMonthSpinner;
    private JSpinner startWinterDaySpinner;
    private JSpinner EndWinterDaySpinner;
    private JPanel mainPanel;
    private SmartHomeDashboard caller;
    private static SetSeasons instance = new SetSeasons("SetSeasons");
    SetSeasons self = this;

    //Bounds variables
    private static final int x = 300;
    private static final int y = 200;
    private static final int width = 560;
    private static final int height = 300;

    /**
     * Parameterised contructor
     *
     * @param title String title of the frame
     */
    private SetSeasons(String title) {
        super(title);
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(x, y,width, height);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        startWinterMonthSpinner.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        startWinterDaySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        EndWinterDaySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        EndWinterMonthSpinner.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        addActionListeners();
    }

    public void addActionListeners() {
        ConfimSeasonsButton.addActionListener(new ActionListener() {
            @Override
            /**
             * sets the season interval (its stored as a 2 int array)
             */
            public void actionPerformed(ActionEvent e) {
                int fmonth = (Integer)startWinterMonthSpinner.getValue();
                int fday =  (Integer)startWinterDaySpinner.getValue();
                int lmonth = (Integer)EndWinterMonthSpinner.getValue();
                int lday =  (Integer)EndWinterDaySpinner.getValue();
                caller.setfromseaons(fmonth,fday);
                caller.settoseaons(lmonth,lday);
                self.setVisible(false);
            }
        });
        CancelSeasonButton.addActionListener(new ActionListener() {
            @Override
            /**
             * meant to close the window
             */
            public void actionPerformed(ActionEvent e) {
                self.setVisible(false);
            }
        });
    }
    /**
     * Returns the single instance of Edit class.
     * @return
     */
    public static SetSeasons getInstance(){
        return instance;
    }
    public void setCaller(SmartHomeDashboard caller){
        this.caller = caller;
    }

}
