import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmartHomeDashboard extends JFrame{
    private JPanel mainPanel;
    private JButton onOff;
    private JPanel Simulation;
    private JButton editUserLocation;
    private JTabbedPane tabbedPane1;
    private JPanel MIDPanel;
    private JPanel HouseLayout;
    private JPanel Console;
    private JTextArea consoleDvSvSdvTextArea;
    private JLabel Image;
    private JLabel Type;
    private JLabel Username;
    private JComboBox comboDate;
    private JComboBox comboDay;
    private JComboBox comboMonth;
    private JComboBox comboYear;
    private JLabel dayOfWeek;
    private JLabel Month;
    private JLabel Year;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JSpinner spinner3;
    private JLabel hourLabel;
    private JLabel minuteLabel;
    private JLabel secondsLabel;
    private JLabel setTimeLabel;
    private JLabel setDateLabel;
    private JLabel editUserLabel;
    private JLabel selectUserLabel;
    private JComboBox comboBox1;
    private JButton addUserButton;
    private JLabel userAccessLabel;
    private JComboBox comboBox2;
    private JButton deleteUsrButton;
    private JButton addAccessButton;
    private JButton removeAccessButton;
    private JComboBox comboBox3;


    public SmartHomeDashboard(String title, String type, String username) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        Type.setText(type);
        Username.setText(username);

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUser("Add User").setVisible(true);

            }
        });
        editUserLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Edit("Edit").setVisible(true);
            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new SmartHomeDashboard("Smart Home Simulator", "", "");
        frame.setVisible(true);



    }
}
