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
    private JTextArea consoleText;
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
    private JButton button1;
    private JPanel SHS;
    private JPanel SHC;
    private JPanel SHP;
    private JPanel SHH;
    private JList listItems;
    private JList listOpenClose;
    private JSpinner outSideTemp;
    private JLabel imageLayout;


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
        onOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(onOff.isSelected()) {
                    onOff.setSelected(false);
                    tabbedPane1.setEnabledAt(0, true);
                    tabbedPane1.setEnabledAt(3, true);
                    tabbedPane1.setEnabledAt(1, false);
                    tabbedPane1.setSelectedIndex(0);
                }
                else {
                    tabbedPane1.setEnabledAt(1, true);
                    tabbedPane1.setEnabledAt(0, false);
                    tabbedPane1.setEnabledAt(3, false);
                    tabbedPane1.setSelectedIndex(1);
                    onOff.setSelected(true);
                }
            }
        });
    }



    public static void main(String[] args) {

        JFrame frame = new SmartHomeDashboard("Smart Home Simulator", "", "");
        frame.setVisible(true);



    }
}
