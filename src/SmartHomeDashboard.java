import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
    private JButton editUsrButton;
    private JButton addAccessButton;
    private JButton removeAccessButton;
    private JComboBox<String> comboLocation;
    private JLabel dateLabel;
    private JLabel currentLocLabel;
    private JLabel timeLabel;
    private JButton LogOutButton;
    private JPanel SHS;
    private JPanel SHC;
    private JPanel SHP;
    private JPanel SHH;
    private JList<SmartObjectType> listItems;
    private JList<String> listOpenClose;
    private JSpinner outSideTemp;
    private JLabel imageLayout;
    private JLabel Outsidetemplabel;
    private JLabel outsidetempvalue;
    private JLabel itemsLabel;
    Timer timer;
    private House currentHouse;

    //Frames
    private static JFrame loginFrame = new Login("Login");
    private static JFrame dashboard;

    //Bounds variables
    private static int xPos = 300;
    private static int yPos = 200;
    private static int xPosD = 100;
    private static int yPosD = 100;
    private static int frameWidth = 400;
    private static int frameHeight = 300;
    private static int DashWidth = 1100;
    private static int DashHeight = 600;


    public SmartHomeDashboard(String title, String type, String username) {
        // Set up dashboard with correct parameters
        super(title);
        Type.setText(type);
        Username.setText(username);
        currentHouse = HouseReader.loadhouse("Houselayout");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        setUpDashboardOptions();
        addActionListeners();

    }

    /**
     * Method that sets up all the comboboxes and spinner in the SHC and SHH tabs when dashboard is created.
     */
    public void setUpDashboardOptions() {
        //Setting up items in "Date" section comboboxes
        String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String[] months = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};


        comboDay.addItem("Something"); //Without this the first the for loop does not add any item to comboDay.
        comboDay.removeAllItems();

        for(String x : weekDays) comboDay.addItem(x);
        for(String x : months) comboMonth.addItem(x);
        for(int i=1; i<32; i++) comboDate.addItem(""+i);
        for(int i=2020; i>1999; i--) comboYear.addItem(""+i);

        //Setting the "Location" combobox
        String[] locations = currentHouse.getRoomNames();
        for(String x : locations) comboLocation.addItem(x);

        //Setting "Time" spinners
        hourSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 23.0, 1));
        minuteSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 59, 1));
        secondSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 59, 1));
        outSideTemp.setModel(new SpinnerNumberModel(0,-90,57,1 ));
    }

    public void addActionListeners() {
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
                    timer.stop();
                    String curtime = timeLabel.getText();
                    int[] times =timetest.Breakdowntime(curtime);
                    hourSpinner.setValue((double)times[2]);
                    minuteSpinner.setValue((double)times[1]);
                    secondSpinner.setValue((double)times[0]);
                }
                else {
                    tabbedPane1.setEnabledAt(1, true);
                    tabbedPane1.setEnabledAt(0, false);
                    tabbedPane1.setEnabledAt(3, false);
                    tabbedPane1.setSelectedIndex(1);
                    onOff.setSelected(true);
                    setUpSimulation();
                    timer.start();
                    int temp = (int)outSideTemp.getValue();
                    outsidetempvalue.setText(temp +"°C");
                }
            }
        });
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s =timeLabel.getText();
                s = timetest.updatetime(s);
                timeLabel.setText(s);
            }
        });
        editUsrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditUserProfile("Edit User Profile").setVisible(true);
            }
        });
        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboard.setVisible(false);
                loginFrame = new Login("Login");
                setUpLoginFrame();
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

        setUpSHCItems();
    }

    /**
     * Updates the SHC with the items in the room the user is currently located.
     */
    public void setUpSHCItems(){

        //Checking if user is child, guest, or parent to see what items should be displayed in the SHC.
        if(Type.getText().equalsIgnoreCase("Child") || Type.getText().equalsIgnoreCase("Guest")){

            String currentLocation = currentLocLabel.getText();
            String[] roomNames = currentHouse.getRoomNames();
            Room currentRoom = null;
            List<SmartObjectType> roomItems;
            List<JCheckBox> openChecks = new ArrayList<JCheckBox>();

            //Figuring out which room object we are currently in.
            for (int i = 0; i < roomNames.length; i++) {
                if (roomNames[i].equalsIgnoreCase(currentLocation)) {
                    currentRoom = currentHouse.rooms[i];
                    break;
                }
            }
            if (currentRoom != null) {

                //Getting all the different item categories in the room and displaying them.
                roomItems = currentRoom.getItemMapKeys();
                SmartObjectType[] roomItemsArr = new SmartObjectType[roomItems.size()];

                for (int i = 0; i < roomItems.size(); i++) {
                    roomItemsArr[i] = roomItems.get(i);
                }

                listItems.setListData(roomItemsArr);

                if (listItems.getFirstVisibleIndex() != -1) {
                    listItems.setSelectedIndex(0);
                }

            }

            //Changing the items label to reflect current location.
            itemsLabel.setText("Items in " + currentLocation);
        }
        else{
            //Getting all the different item categories in the room and displaying them.
            List<SmartObjectType> houseItems = currentHouse.getHouseItemsKeys();
            SmartObjectType[] houseItemsArr = new SmartObjectType[houseItems.size()];

            for (int i = 0; i < houseItems.size(); i++) {
                houseItemsArr[i] = houseItems.get(i);
            }

            listItems.setListData(houseItemsArr);

            if (listItems.getFirstVisibleIndex() != -1) {
                listItems.setSelectedIndex(0);
            }

            //Changing the items back to items in case it was changed for child user.
            itemsLabel.setText("Items");

        }


        setSHCOpenClose();
    }

    /**
     * Method that sets all the items in open/close that are related to the selected category of items in SHC.
     */
    public void setSHCOpenClose(){

        //Checking if user is child, guest, or parent to see what items should be displayed in the SHC.
        if(Type.getText().equalsIgnoreCase("Child") || Type.getText().equalsIgnoreCase("Guest")){
            String currentLocation = currentLocLabel.getText();
            String[] roomNames = currentHouse.getRoomNames();
            Room currentRoom = null;

            for (int i = 0; i < roomNames.length; i++) {
                if (roomNames[i].equalsIgnoreCase(currentLocation)) {
                    currentRoom = currentHouse.rooms[i];
                    break;
                }
            }

            if (currentRoom != null) {
                SmartObjectType selectedItem = listItems.getSelectedValue();
                List<String> items = currentRoom.getItemMapValue(selectedItem);

                String[] itemsArr = new String[items.size()];
                for (int i = 0; i < items.size(); i++) {
                    itemsArr[i] = items.get(i);
                }

                listOpenClose.setListData(itemsArr);

            }
        }
        else{
            SmartObjectType selectedItem = listItems.getSelectedValue();
            List<String> items = currentHouse.getHouseItemValue(selectedItem);

            String[] itemsArr = new String[items.size()];
            for (int i = 0; i < items.size(); i++) {
                itemsArr[i] = items.get(i);
            }

            listOpenClose.setListData(itemsArr);
        }
    }
    public static void loginClicked(String username, String password){
        // User Authentication
        User user = UserManager.findUser(username, password);
        if(user != null) {
            loginFrame.setVisible(false);

            // User type
            if(user instanceof Child) {
                System.out.println("It is a child");
                // Show house simulator for child
                dashboard = new SmartHomeDashboard("Smart Home Simulator", UserTypes.CHILD.toString(), username);
            } else if (user instanceof Parent) {
                System.out.println("It is a parent");
                // Show house simulator for parent
                dashboard = new SmartHomeDashboard("Smart Home Simulator", UserTypes.PARENT.toString(), username);
            } else if (user instanceof Guest) {
                System.out.println("It is a guest");
                // Show house simulator for guest
                dashboard = new SmartHomeDashboard("Smart Home Simulator", UserTypes.GUEST.toString(), username);
            }
            if(dashboard != null) {
                dashboard.setBounds(xPosD, yPosD, DashWidth, DashHeight);
                dashboard.setVisible(true);
                dashboard.setResizable(false);
            }
        } else {
            System.out.println("Login failed");
            //display failed login message
        }
    }

    public static void setUpLoginFrame() {
        //Show login frame
        loginFrame.setBounds(xPos, yPos, frameWidth, frameHeight);
        loginFrame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new SmartHomeDashboard("Smart Home Simulator", "", "");
        frame.setVisible(true);
    }
}
