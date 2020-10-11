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
    private JComboBox<String> comboDate;
    private JComboBox<String> comboDay;
    private JComboBox<String> comboMonth;
    private JComboBox<String> comboYear;
    private JLabel dayOfWeek;
    private JLabel Month;
    private JLabel Year;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JSpinner secondSpinner;
    private JLabel hourLabel;
    private JLabel minuteLabel;
    private JLabel secondsLabel;
    private JLabel setTimeLabel;
    private JLabel setDateLabel;
    private JLabel editUserLabel;
    private JLabel selectUserLabel;
    private JComboBox<String> comboBox1;
    private JButton addUserButton;
    private JLabel userAccessLabel;
    private JComboBox<String> comboBox2;
    private JButton deleteUsrButton;
    private JButton addAccessButton;
    private JButton removeAccessButton;
    private JComboBox<String> comboLocation;
    private JLabel dateLabel;
    private JLabel currentLocLabel;
    private JLabel timeLabel;
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


        //Setting up items in "Date" section comboboxes
        String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String[] months = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};


        comboDay.addItem("Something"); //Without this the first for loop does not add any item to comboDay.
        comboDay.removeAllItems();

        for(String x : weekDays) comboDay.addItem(x);
        for(String x : months) comboMonth.addItem(x);
        for(int i=1; i<32; i++) comboDate.addItem(""+i);
        for(int i=2020; i>1999; i--) comboYear.addItem(""+i);

        //Setting the "Location" combobox (Temporary, might need to make it depend on house reader)
        String[] locations = {"Master Bedroom", "Kid's Bedroom", "Kitchen", "Living Room", "Outside House"};
        for(String x : locations) comboLocation.addItem(x);

        //Setting "Time" spinners
        hourSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 23.0, 1));
        minuteSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 59, 1));
        secondSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 59, 1));


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
                    setUpSimulation();
                }
            }
        });
    }

    /**
     * Method that sets all the necessary parameters when simulation starts.
     */
    public void setUpSimulation(){
        String date, day, month, year, hour, minute, second;
        int hourInt, minuteInt, secondInt;

        //Setting up date label
        date = comboDate.getItemAt(comboDate.getSelectedIndex());
        day = comboDay.getItemAt(comboDay.getSelectedIndex());
        month = comboMonth.getItemAt(comboMonth.getSelectedIndex());
        year = comboYear.getItemAt(comboYear.getSelectedIndex());

        dateLabel.setText(day.substring(0, 3) + " " + month.substring(0, 3) + " " + date + " " + year);

        //Setting current location
        currentLocLabel.setText(comboLocation.getItemAt(comboLocation.getSelectedIndex()));

        //Setting time
        hourInt = (int)Math.round((double)hourSpinner.getValue());
        minuteInt = (int)Math.round((double)minuteSpinner.getValue());
        secondInt = (int)Math.round((double)secondSpinner.getValue());

        hour = ((hourInt < 10) ? "0"+hourInt : ""+hourInt);
        minute = ((minuteInt < 10) ? "0"+minuteInt : ""+minuteInt);
        second = ((secondInt < 10) ? "0"+secondInt : ""+secondInt);

        timeLabel.setText(hour + ":" + minute + ":" + second);
    }


    public static void main(String[] args) {

        JFrame frame = new SmartHomeDashboard("Smart Home Simulator", "", "");
        frame.setVisible(true);



    }
}
