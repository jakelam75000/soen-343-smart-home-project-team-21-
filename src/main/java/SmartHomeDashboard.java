import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

/**
 * main frame that holds the dashboard and acts as a central hub
 */
public class SmartHomeDashboard extends JFrame implements Observable{
    private JPanel mainPanel;
    private JButton onOff;
    private JButton editSimulation;
    private JTabbedPane tabbedPane1;
    private JPanel MIDPanel;
    private JPanel HouseLayout;
    private JPanel Console;
    private JTextArea consoleText;
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
    private JComboBox<String> comboUsers;
    private JButton addUserButton;
    private JLabel userAccessLabel;
    private JComboBox<String> comboEnabledAccessibility;
    private JButton editUsrButton;
    private JButton addAccessButton;
    private JButton removeAccessButton;
    private JComboBox<String> comboLocation;
    private JLabel dateLabel;
    private JLabel currentLocLabel;
    private JLabel timeLabel;
    private JButton LogOutButton;
    private JPanel SHSPanel;
    private JPanel SHCPanel;
    private JPanel SHPPanel;
    private JPanel SHHPanel;
    private JList<SmartObjectType> listItems;
    private JList<String> listOpenClose;
    private JSpinner outSideTemp;
    private JLabel outsidetempvalue;
    private JLabel itemsLabel;
    private JPanel openClosePanel;
    private JPanel Simulation;
    private JLabel Image;
    private JLabel Outsidetemplabel;
    private JScrollPane openCloseScroll;
    private JSpinner speedSpinner;
    private JLabel SpeederLabel;
    private JLabel automatedLabel;
    private JScrollPane autoModeScroll;
    private JPanel autoPanel;
    private JLabel AwayModeLabel;
    private JLabel NoteForAwayModeLabel;
    private JCheckBox awayModeCheckbox;
    private JPanel AwayModeCheckBoxPanel;
    private JLabel SetTimerLabel;
    private JPanel TimerPanel;
    private JLabel TimerHoursLabel;
    private JLabel TimerMinutesLabel;
    private JLabel TimerSecondsLabel;
    private JSpinner timerHoursSpinner;
    private JSpinner timerMinutesSpinner;
    private JSpinner timerSecondsSpinner;
    private JLabel LightsRemainOnLabel;
    private JScrollPane LightsScrollPane;
    private JPanel lightsListPanel;
    private JLabel DurationLightsLabel;
    private JLabel TimerFromLabel;
    private JLabel SchedualLightHoursLabel;
    private JLabel SchedualLightMinutesLabel;
    private JLabel SchedualLightSecondsLabel;
    private JSpinner fromSchedualHoursSpinner;
    private JSpinner fromSchedualMinutesSpinner;
    private JSpinner fromSchedualSecondsSpinner;
    private JLabel TimerToLabel;
    private JLabel ToSchedualHoursLabel;
    private JLabel ToSchedualMinutesLabel;
    private JLabel ToSchedualSecondsLabel;
    private JSpinner toSchedualHoursSpinner;
    private JSpinner toSchedualMinutesSpinner;
    private JSpinner toSchedualSecondsSpinner;
    private JCheckBox setToAutoModeCheckBox;
    private JComboBox comboDisabledAccessibility;
    private JComboBox comboLocationAccessiblity;
    private JLabel houseLayoutText2;
    private JLabel awayModeEnableLabel;
    private JButton ButtonSeasons;
    private JComboBox<String> listOfRooms;
    private JButton addRoom;
    private JComboBox<String> addedRoomsList;
    private JButton removeRoomFromZone;
    private JButton createZone;
    private JComboBox<String> zonesCombo;
    private JComboBox<String> periodCombo;
    private JSpinner tempZoneSpinner;
    private JButton setZoneTempButton;
    private JLabel createZoneLabel;
    private JLabel setDesTempPerZoneLabel;
    private JComboBox<String> roomTempCombo;
    private JSpinner tempRoomSpinner;
    private JButton setRoomTempButton;
    private JSpinner winterTempSpinner;
    private JSpinner summerTempSpinner;
    private JButton setDefaultTempForSeasonsButton;
    private JComboBox<String> zoneNameCombo;
    private JButton saveZoneButton;
    private JLabel roomTempLabel;
    private JLabel selectLocationLabel;
    private Timer timer;
    private House house;
    private boolean welcomeMessageDisplayed = false;
    private SmartHomeDashboard self;
    private DynamicLayout dynamicLayout;
    private String[] automatedLights;
    private List<Observer> observers = new ArrayList<Observer>();
    private int[] fromseasonsmonth = {1,1};
    private int[] toseasonsmonth = {1,1};
    private int wintertemp;
    private int summertemp;
    JCheckBox[] itemsArr;
    private int dayint;
    private boolean firstSetUpOfSimulation = true;
    private boolean firstTimeRun = true;


    //Singleton instances
    private SHPObserver shp = SHPObserver.getInstance();
    private Edit edit = Edit.getInstance();
    private SaveUsers saveUsers = SaveUsers.getInstance();
    private AddUser addUser = AddUser.getInstance();
    private EditUserProfile editUserProfile = EditUserProfile.getInstance();
    private SHH shh = SHH.getInstance();
    private CreateZone createZoneForm = CreateZone.getInstance();
    private SetSeasons seasonsFrame = SetSeasons.getInstance();


    //Bounds variables
    private static final int x = 200;
    private static final int y = 100;
    private static final int width = 1300;
    private static final int height = 670;

    /**
     * Parameterised constructor.
     *
     * @param title String title of the dashboard frame
     * @param type String type of user that logged in.
     * @param username String username of the user that logged in.
     */
    public SmartHomeDashboard(String title, String type, String username, String housefilepath) {
        // Set up dashboard with correct parameters
        super(title);
        Type.setText(type);
        Username.setText(username);

        try {
            house = HouseReader.readAndLoadHouse(housefilepath);
        }
        catch(WrongExtensionException e){
            System.out.println(e.getMessage());
        }

        ZoneManager.setHouse(house);
        self = this;

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        this.setBounds(x, y, width, height);
        this.setResizable(false);

        if (isItWinter())dynamicLayout = new DynamicLayout(house.getRoomsList(),isItWinter(),isAwayModeOn(),getWintertemp());
        else dynamicLayout = new DynamicLayout(house.getRoomsList(),isItWinter(),isAwayModeOn(),getSummertemp());
        HouseLayout.add(dynamicLayout, BorderLayout.CENTER);

        edit.setCaller(this);
        addUser.setCaller(this);
        createZoneForm.setCaller(this);
        shh.setCaller(this);
        editUserProfile.setCurrentType(type);
        editUserProfile.setCallingUser(username);
        editUserProfile.setCaller(this);
        attachObserver(shh);
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
        for(int i=2000; i<=2025; i++) comboYear.addItem(""+i);
        comboYear.setSelectedIndex(20);

        updateUsers();

        //Setting the "Location" and "Select location" comboBox
        Location.setLocationDropdowns(house.getRoomNames(), comboLocation, comboLocationAccessiblity);
        Location.setUserLocation(Username.getText(), comboLocation, currentLocLabel);

        //Setting the "Accessibility
        Accessibility.setAccessibilitiesDropdown(comboEnabledAccessibility,comboDisabledAccessibility, comboLocationAccessiblity,comboUsers);
        //Setting zones
        updateZoneRooms();

        //Setting all the spinners
        hourSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 23.0, 1));
        minuteSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 59, 1));
        secondSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 59, 1));
        outSideTemp.setModel(new SpinnerNumberModel(22,-90,57,1 ));
        speedSpinner.setModel(new SpinnerNumberModel(1,1,100,1));
        fromSchedualHoursSpinner.setModel(new SpinnerNumberModel(0,0,23,1));
        fromSchedualMinutesSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        fromSchedualSecondsSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        toSchedualHoursSpinner.setModel(new SpinnerNumberModel(0,0,23,1));
        toSchedualMinutesSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        toSchedualSecondsSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        timerHoursSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        timerMinutesSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        timerSecondsSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        winterTempSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        summerTempSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));

        setupSHPlights();
    }

    public void updateZoneRooms() {
        if(ZoneManager.getZoneSize() > 0) {
            ZoneManager.setZoneDropdown(zoneNameCombo);
            ZoneManager.setRoomDropdown(zoneNameCombo.getSelectedItem().toString(), listOfRooms, addedRoomsList, house.getRoomNames());
        }
    }

    /**
     * Updates the user list in the SHS tab on the dashboard.
     */
    public void updateUsers(){
        comboUsers.removeAllItems();
        if(Type.getText().equalsIgnoreCase("parent")){
            String[] users = UserManager.getUsernames();
            for (String user : users) {
                comboUsers.addItem(user);
            }
        }
        else{
            comboUsers.addItem(Username.getText());
            addUserButton.setEnabled(false);
            addAccessButton.setEnabled(false);
            removeAccessButton.setEnabled(false);
        }
        updateHouseLayout();
    }

    /**
     * Adds all action listeners to attributes of the class.
     */
    public void addActionListeners() {
        addRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ZoneManager.removeOrAddRoom(listOfRooms.getSelectedItem(), listOfRooms, addedRoomsList)) {
                    printToConsole("Save changes for action to be performed.");
                } else {
                    printToConsole("Cannot perform this action.");
                }
            }
        });
        removeRoomFromZone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ZoneManager.removeOrAddRoom(addedRoomsList.getSelectedItem(), addedRoomsList,listOfRooms)) {
                    printToConsole("Save changes for action to be performed.");
                } else {
                    printToConsole("Cannot perform this action.");
                }
            }
        });
        saveZoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZoneManager.modifyZone(house.getRoomsList(), zoneNameCombo.getSelectedItem().toString(), listOfRooms,addedRoomsList);
                printToConsole("Zone has succesfully been added.");
                updateHouseLayout();
            }
        });
        zoneNameCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ZoneManager.getZoneSize() > 0 && zoneNameCombo.getSelectedItem() != null) {
                    ZoneManager.setRoomDropdown(zoneNameCombo.getSelectedItem().toString(), listOfRooms, addedRoomsList, house.getRoomNames());
                }
            }
        });
        createZone.addActionListener(new ActionListener() {
            /**
             * opens the createZone frame
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                createZoneForm.setRooms(house.getRoomsList());
                createZoneForm.setRoomNames(house.getRoomNames());
                createZoneForm.setVisible(true);
                createZone.setEnabled(false);
                updateHouseLayout();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveUsers.setCaller(self);
                saveUsers.setVisible(true);
            }
        });

        ButtonSeasons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seasonsFrame.setCaller(self);
                seasonsFrame.setVisible(true);
            }
        });

        setDefaultTempForSeasonsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wintertemp = (Integer)winterTempSpinner.getValue();
                summertemp = (Integer)summerTempSpinner.getValue();
                printToConsole("seasons temperature set!");
            }
        });

        addAccessButton.addActionListener(new ActionListener() {
            /**
             * Updates the accessibility dropdown when new user is selected
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboDisabledAccessibility.getItemCount() > 0) {
                    String username = comboUsers.getSelectedItem().toString();
                    if(UserManager.getUserType(username) == UserTypes.STRANGER || (!username.equals(Username.getText()) && UserManager.getUserType(username) == UserTypes.PARENT)) {
                        printToConsole("User cannot change accessibilities this type of user.");
                    } else {
                        printToConsole(comboDisabledAccessibility.getSelectedItem().toString() + " was added for " +
                                username + " in " + comboLocationAccessiblity.getSelectedItem().toString());
                        Accessibility.addAccessibility(comboEnabledAccessibility,comboDisabledAccessibility, comboLocationAccessiblity,comboUsers);
                    }
                } else {
                    printToConsole("User has all accessibilities for this location. Additional accessibility cannot be added.");
                }
            }
        });

        removeAccessButton.addActionListener(new ActionListener() {
            /**
             * Updates the accessibility dropdown when new user is selected
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboEnabledAccessibility.getItemCount() > 0 && Type.getText().equalsIgnoreCase("Parent")) {
                    String username = comboUsers.getSelectedItem().toString();
                    if(UserManager.getUserType(username) == UserTypes.STRANGER || (!username.equals(Username.getText()) && UserManager.getUserType(username) == UserTypes.PARENT)) {
                        printToConsole("User cannot change accessibilities for this type of user.");
                    } else {
                        printToConsole(comboEnabledAccessibility.getSelectedItem().toString() + " was removed for " +
                            comboUsers.getSelectedItem().toString() + " in " + comboLocationAccessiblity.getSelectedItem().toString());
                        Accessibility.removeAccessibility(comboEnabledAccessibility,comboDisabledAccessibility, comboLocationAccessiblity,comboUsers); }
                } else {
                    printToConsole("User has no accessibilities for this location. Additional accessibility cannot be deleted.");
                }
            }
        });

        comboUsers.addActionListener(new ActionListener() {
            /**
             * Updates the accessibility dropdown when new user is selected
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Accessibility.setAccessibilitiesDropdown(comboEnabledAccessibility,comboDisabledAccessibility, comboLocationAccessiblity,comboUsers);
            }
        });

        comboLocationAccessiblity.addActionListener(new ActionListener() {
            /**
             * Updates the accessibility dropdown when new location is selected
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Accessibility.setAccessibilitiesDropdown(comboEnabledAccessibility,comboDisabledAccessibility, comboLocationAccessiblity,comboUsers);
            }
        });

        awayModeCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (awayModeCheckbox.isSelected()) {
                    if (isSomeoneHome() == null){
                        if(Accessibility.allowAwayMode(Username.getText()) || UserManager.getUserType(Username.getText())==UserTypes.PARENT) {
                            if (house.closeAllWindows()) {
                                String alertTimer = timerHoursSpinner.getValue() + ":" + timerMinutesSpinner.getValue() + ":" + timerSecondsSpinner.getValue();
                                shp.setTimer(alertTimer);
                                shp.setMultiplier((int) (speedSpinner.getValue()));
                                attachObserver(shp);
                                house.lockAllDoors(true);
                                awayModeEnableLabel.setVisible(true);
                                printToConsole("Garage, kitchen and entry way doors locked");
                                updateHouseLayout();
                                printToConsole("Away mode enabled.");
                                self.setUpSHCOpenClose();
                            } else {
                                printToConsole("Away mode not set! a Window is blocked");
                                awayModeCheckbox.setSelected(false);
                            }
                        } else {
                            awayModeCheckbox.setSelected(false);
                            printToConsole("Away mode cannot be enable. User does not have access to perform this action.");
                        }
                    } else{
                        awayModeCheckbox.setSelected(false);
                        printToConsole("Away mode cannot be enable. There are people left in the house.");
                    }
                }
                else {
                    house.lockAllDoors(false);
                    awayModeEnableLabel.setVisible(false);
                    printToConsole("Garage, kitchen and entry way doors unlocked");
                    detachObserver(shp);
                    printToConsole("Away mode disabled.");
                    self.setUpSHCOpenClose();
                }
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            /**
             * opens the adduser frame
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser.setVisible(true);
            }
        });

        editSimulation.addActionListener(new ActionListener() {
            /**
             * opens the edit frame
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                edit.setUpEditOptions(house.getRoomNames(), house.getHouseItemValue(SmartObjectType.WINDOW), UserManager.getUsernames(),Username.getText());
                edit.setVisible(true);
            }
        });

        onOff.addActionListener(new ActionListener() {
            /**
             * turns the simulation on and off
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(onOff.isSelected()) {
                    onOff.setSelected(false);
                    configureTabbedPanel(false);
                    timer.stop();

                    String currentTime = timeLabel.getText();
                    int[] times = breakDownTime(currentTime);
                    hourSpinner.setValue((double)times[0]);
                    minuteSpinner.setValue((double)times[1]);
                    secondSpinner.setValue((double)times[2]);
                }
                else {
                    String oldLocation = currentLocLabel.getText();
                    configureTabbedPanel(true);
                    if(Type.getText().equals(UserTypes.PARENT.toString()) || Type.getText().equals(UserTypes.GUEST.toString())) {
                        if(Type.getText().equals(UserTypes.GUEST.toString())) {
                            addRoom.setEnabled(false);
                            removeRoomFromZone.setEnabled(false);
                            createZone.setEnabled(false);
                            saveZoneButton.setEnabled(false);
                            setZoneTempButton.setEnabled(false);
                            setDefaultTempForSeasonsButton.setEnabled(false);
                        }
                    } else {
                        tabbedPane1.setEnabledAt(3, false);
                    }

                    onOff.setSelected(true);
                    setUpSimulation();
                    timer.setDelay((int)(1000 / (int)speedSpinner.getValue()));
                    timer.start();
                    int temp = (int)outSideTemp.getValue();
                    outsidetempvalue.setText(temp +"°C");
                    if (firstTimeRun){
                        Room[] rooms = house.getRoomsList();
                        for (int i =0 ; i < rooms.length; i++){
                            rooms[i].setDesiredTemp(temp);
                            rooms[i].setTemperature(temp);
                        }
                        firstTimeRun = false;
                    }
                    autoLights(oldLocation, comboLocation.getItemAt(comboLocation.getSelectedIndex()));
                }
                updateHouseLayout();
            }
        });

        setToAutoModeCheckBox.addActionListener(e -> {
           if (setToAutoModeCheckBox.isSelected()) {
               printToConsole("Lights auto mode is activated.");
               self.autoModeClicked();
           }
           else {
               printToConsole("Lights auto mode was deactivated.");
           }
        });

        timer = new Timer(1000, new ActionListener() {
            /**
             * timer that updates the date and time. This also notifies all observers of the update.
             * @param e ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = timeLabel.getText();
                s = updatetime(s);
                timeLabel.setText(s);
                notifyObservers(self);
            }
        });

        editUsrButton.addActionListener(new ActionListener() {
            /**
             * opens the edit user profiel frame
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                editUserProfile.setUsername(comboUsers.getSelectedItem().toString());
                editUserProfile.setVisible(true);
            }
        });

        LogOutButton.addActionListener(new ActionListener() {
            /**
             * allows the user to logout and return to login
             * @param e ActionEvenet
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Login loginFrame = new Login("Login");
                loginFrame.setBounds(300, 200, 400, 320);
                loginFrame.setVisible(true);
                self.setVisible(false);
            }
        });

        listItems.addListSelectionListener(e -> setUpSHCOpenClose());

        zonesCombo.addActionListener(e -> shh.updateZoneTempSpinner());

        roomTempCombo.addActionListener(e -> shh.updateRoomTempSpinner());

        setZoneTempButton.addActionListener(e -> {
            shh.setZoneTemperature();
            shh.updateRoomTempSpinner();
            updateHouseLayout();
        });

        setRoomTempButton.addActionListener(e -> {

            if(Type.getText().equals(UserTypes.GUEST.toString()) && !roomTempCombo.getSelectedItem().toString().equals(currentLocLabel.getText())) {
                printToConsole("Guests can only change temperature of room they are currently in.");
            } else {
                shh.setRoomTemperature();
                updateHouseLayout();
                printToConsole("Temperature has successfully been updated.");
            }

        });

        periodCombo.addActionListener(e -> shh.updateZoneTempSpinner());
    }

    public void configureTabbedPanel(boolean simulationOn){
        tabbedPane1.setEnabledAt(0, !simulationOn);
        tabbedPane1.setEnabledAt(3, simulationOn);
        tabbedPane1.setEnabledAt(2, simulationOn);
        tabbedPane1.setEnabledAt(1, simulationOn);
        tabbedPane1.setSelectedIndex(simulationOn ? 1 : 0);
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
        Location.setUserLocation(Username.getText(),comboLocation, currentLocLabel);


        //Setting time
        hourInt = (int)Math.round((double)hourSpinner.getValue());
        minuteInt = (int)Math.round((double)minuteSpinner.getValue());
        secondInt = (int)Math.round((double)secondSpinner.getValue());

        hour = ((hourInt < 10) ? "0"+hourInt : ""+hourInt);
        minute = ((minuteInt < 10) ? "0"+minuteInt : ""+minuteInt);
        second = ((secondInt < 10) ? "0"+secondInt : ""+secondInt);

        timeLabel.setText(hour + ":" + minute + ":" + second);
        setUpSHCItems();

        if(firstSetUpOfSimulation){
            shh.setUpRoomTempBlock();
            shh.setUpZoneTempBlock();
            firstSetUpOfSimulation = false;
        }

        if (!welcomeMessageDisplayed) {
            printToConsole("Welcome to your new Smart home " + Username.getText() + "!");
            welcomeMessageDisplayed = true;
        }
    }

    /**
     * Updates the SHC with the items in the room the user is currently located.
     */
    public void setUpSHCItems(){
        //Getting all the different item categories in the room and displaying them.
        List<SmartObjectType> houseItems = house.getHouseItemsKeys();
        SmartObjectType[] houseItemsArr = new SmartObjectType[houseItems.size()];

        for (int i = 0; i < houseItems.size(); i++) {
            houseItemsArr[i] = houseItems.get(i);
        }
        Arrays.stream(houseItemsArr).sorted();
        listItems.setListData(houseItemsArr);

        if (listItems.getFirstVisibleIndex() != -1) {
            listItems.setSelectedIndex(0);
        }

        //Changing the items back to items in case it was changed for child user.
        itemsLabel.setText("Items");
        setUpSHCOpenClose();
        consoleText.setRows(consoleText.getRows()+1);

    }

    /**
     * Method that sets all the items in open/close that are related to the selected category of items in SHC.
     */
    public void setUpSHCOpenClose(){
        // Get user to check accessibilities
        User user = UserManager.findExistingUser(Username.getText());
        SmartObjectType selectedItem = listItems.getSelectedValue();

        if (selectedItem==null)selectedItem = SmartObjectType.WINDOW;

        if (selectedItem.equals(SmartObjectType.LIGHT)) {setToAutoModeCheckBox.setVisible(true);}
        else setToAutoModeCheckBox.setVisible(false);

        //These are the strings that will be in the checkboxes
        Set<String> itemsForCheckbox = new HashSet<>();
        Room[] rooms = house.getRoomsList();
        //This is to get the corresponding accessibility to the smart object selected
        AccessibilityType accessibilityType = Accessibility.getSmartObjToAccessibility().get(selectedItem);
        //This is to store the specified room LocationType
        LocationType locationType;
        //This is to store all accessibility user has for the specified room
        ArrayList<AccessibilityType> accessibilities;
        //This is to get all smart objects for specific room and smart object type
        List<String> smartObjInRoom;

        //User's current location
        String currentLocation = user.getLocation();
        if(currentLocation.contains("STOOP"))currentLocation = "STOOP";

        //Iterating through each room to get populate itemsForCheckbox arraylist
        for(Room room : rooms) {
            //This is to get all smart objects for specific room and smart object type
            smartObjInRoom = room.getItemMap().get(selectedItem);
            //Make sure that smart object selected is present
            if(smartObjInRoom != null) {
                if (!room.getName().contains("STOOP"))locationType = LocationType.valueOf(room.getName());
                else locationType = LocationType.valueOf(room.getName().replace(" STOOP",""));
                // If location is outside then only check OUTSIDE
                if(  LocationType.valueOf(currentLocation) == LocationType.OUTSIDE ||  LocationType.valueOf(currentLocation) == LocationType.STOOP) {
                    accessibilities = user.getAccessibilities().get(LocationType.OUTSIDE);
                } else {
                    accessibilities = user.getAccessibilities().get(locationType);
                }
                //check if that accessibility is present for that room
                for(AccessibilityType accessibility : accessibilities) {
                    if(accessibility == accessibilityType) {
                        for(String obj : smartObjInRoom)
                        itemsForCheckbox.add(obj);
                    }
                }
                //This is to add accessibilities that user has when he is in that room CURRENT, this does not apply to OUTSIDE
                if(LocationType.valueOf(currentLocation) != LocationType.OUTSIDE && room.getName().equals(currentLocation)) {
                    accessibilities = user.getAccessibilities().get(LocationType.CURRENT);
                    for(AccessibilityType accessibility : accessibilities) {
                        if(accessibility == accessibilityType) {
                            for(String obj : smartObjInRoom) {
                                itemsForCheckbox.add(obj);
                            }
                        }
                    }
                }
            }
        }
        // itemsForCheckbox to easily get value with index
        List<String> itemsList = new ArrayList<>(itemsForCheckbox);
        Collections.sort(itemsList);
        openClosePanel.removeAll();
        openClosePanel.setLayout(new GridLayout(itemsList.size(), 1));
        itemsArr = new JCheckBox[itemsList.size()];
        for(int i=0; i<itemsForCheckbox.size(); i++){
            itemsArr[i] = new JCheckBox(itemsList.get(i));
            itemsArr[i].setSelected(isObjectOpen(itemsList.get(i)));
            int index = i;
            itemsArr[i].addActionListener(new ActionListener() {
                @Override
                 public void actionPerformed(ActionEvent e) {
                    if(!house.openCloseObject(itemsArr[index].getText(), itemsArr[index].isSelected())){
                       itemsArr[index].setSelected(!itemsArr[index].isSelected());
                        if (itemsArr[index].getText().toString().contains("window"))printToConsole(itemsArr[index].getText() + " is blocked and cannot be opened/closed.");
                        else if (itemsArr[index].getText().toString().contains("door")) printToConsole(itemsArr[index].getText() + " is locked and cannot be opened.");
                    }
                    else{
                        if(itemsArr[index].isSelected()&& (itemsArr[index].getText().contains("window") ||itemsArr[index].getText().contains("door"))) printToConsole(itemsArr[index].getText() + " was opened.");
                        else if (itemsArr[index].getText().contains("window")||itemsArr[index].getText().contains("door")) printToConsole(itemsArr[index].getText() + " was closed.");
                        else if (itemsArr[index].isSelected()&& itemsArr[index].getText().contains("light")) printToConsole(itemsArr[index].getText() + " was turned on.");
                        else if (itemsArr[index].getText().contains("light")) printToConsole(itemsArr[index].getText() + " was turned off.");
                    }
                    updateHouseLayout();
                }
            });
            openClosePanel.add(itemsArr[i]);
        }
    }

    /**
     * Method that checks how many rooms are occupied when auto mode for lights is engaged and changes the states
     * of the lights to correspond (as well as house layout)
     */
    public void autoModeClicked() {
        Set<String> pplInRooms = new HashSet<String>();
        for (String user : UserManager.getUsernames()) {
            pplInRooms.add(UserManager.getUserLocation(user));
        }

        for (String room : house.getRoomNames()) {
            if (pplInRooms.contains(room)) {
                house.openCloseObject(room+" light", true);
            }
            else
                house.openCloseObject(room+" light", false);
        }

        setUpSHCOpenClose();
        updateHouseLayout();
    }

    /**
     * Method that takes a person's old and new locations for light auto mode in order to determine if lights
     * are to be closed or turned on
     * @param oldLoc
     * @param newLoc
     */
    public void autoLights(String oldLoc, String newLoc) {
        boolean someoneInOld = false;
        if(!isAutoMode()) {
            return;
        }

        for (String user : UserManager.getUsernames()) {
            if (UserManager.getUserLocation(user).equalsIgnoreCase(oldLoc)){
                someoneInOld = true;
                break;
            }
        }

        if (!someoneInOld){
            house.openCloseObject(oldLoc +" light", false);
            printToConsole(oldLoc +" light was turned off.");
        }

        if (!house.getObjectState(newLoc+" light") && !newLoc.equalsIgnoreCase("outside")){
            printToConsole(newLoc +" light was turned on.");
            house.openCloseObject(newLoc+" light", true);
        }
        setUpSHCOpenClose();
        updateHouseLayout();
    }

    /**
     * Sets up the SHP lights list, allowing the user to select which lights are automated
     */
    public void setupSHPlights() {
        SmartObjectType selectedItem = SmartObjectType.LIGHT;
        List<String> items = house.getHouseItemValue(selectedItem);
        automatedLights = new String[items.size()];
        lightsListPanel.removeAll();
        lightsListPanel.setLayout(new GridLayout(items.size(), 1));
        JCheckBox[] itemsArr = new JCheckBox[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemsArr[i] = new JCheckBox(items.get(i));
            itemsArr[i].setSelected(isObjectOpen(items.get(i)));
            int index = i;
            itemsArr[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (itemsArr[index].isSelected() ) {
                        printToConsole(itemsArr[index].getText() + " was added to the automated list");
                        int lightindex = 0;
                        while (automatedLights[lightindex] != null){
                            lightindex++;
                            if (lightindex == automatedLights.length)System.err.println("out of bounds index in setuplights");
                        }
                        automatedLights[lightindex] = itemsArr[index].getText();
                    }
                    if (!itemsArr[index].isSelected() ) {
                        int lightindex = 0;
                        while (true) {
                            if (automatedLights[lightindex] != null) {
                                if (automatedLights[lightindex].equalsIgnoreCase(itemsArr[index].getText()))break;
                            }
                            lightindex++;
                            if (index == automatedLights.length)System.err.println("out of bounds index in setuplights");
                        }
                        String[] temp = new String[automatedLights.length];
                        for (int j = 0; j< automatedLights.length-1; j++){
                            if (j < lightindex)temp[j] = automatedLights[j];
                            else temp[j] = automatedLights[j+1];
                        }
                        automatedLights = temp;
                        printToConsole(itemsArr[index].getText() + " was removed from the automated list");
                    }
                }
            });
            lightsListPanel.add(itemsArr[i]);
        }
    }

    /**
     * Method that blocks/unblocks a given window
     *
     * @param name String that is the name of the window
     * @param blocked boolean that is the desired state of the window
     */
    public void blockWindow(String name, boolean blocked){
        house.blockWindow(name, blocked);
        if(blocked) printToConsole(name + " status was changed to \"blocked\"");
        else printToConsole(name + " status was changed to \"unblocked\"");

    }

    /**
     * Checks if a window or a door is blocked by an object.
     *
     * @param name a String containing the name of the window being checked.
     * @return boolean true if window is blocked false otherwise.
     */
    public boolean isWindowBlocked(String name){
        for(Room room : house.getRoomsList()){
            for(Smartobj obj : room.getSmartObjects()){
                if(obj.getName().equalsIgnoreCase(name) && obj.getType() == SmartObjectType.WINDOW){
                    Window window = (Window)obj;
                    return window.isBlocked();
                }
                else if (obj.getName().equalsIgnoreCase(name) && obj.getType() == SmartObjectType.DOOR){
                    Door door = (Door)obj;
                    return door.islocked();
                }
            }
        }
        return false;
    }

    /**
     * Checks if object is open or closed
     *
     * @param name String name of the object
     * @return boolean true if item is open false otherwise
     */
    public boolean isObjectOpen(String name){
        for(Room room : house.getRoomsList()){
            for(Smartobj obj : room.getSmartObjects()){
                if(obj.getName().equalsIgnoreCase(name)){
                    switch(obj.getType()){
                        case WINDOW:
                            Window object = (Window)obj;
                            return object.isOpen();
                        case DOOR:
                            Door objectdoor = (Door)obj;
                            return objectdoor.isOpen();
                        case LIGHT:
                            Light objectlight = (Light)obj;
                            return objectlight.isON();
                    }
                }
            }
        }
        return false;
    }

    /**
     * Prints updates to the console inside the Dashboard.
     *
     * @param text String that is the text to be printed on the console.
     */
    public void printToConsole(String text){
        String[] currentTime = timeLabel.getText().split(":");
        String current_Time_Formatted = "";

        if(onOff.isSelected()){
            current_Time_Formatted = "["+currentTime[0] + ":" + currentTime[1]+"]:";
        }

        consoleText.setRows(consoleText.getRows()+ 1);
        consoleText.append("\n" + current_Time_Formatted + text);
        try{
            PrintWriter out = new PrintWriter("OutputLog.txt");
            out.println(consoleText.getText());
            out.close();
        }catch (FileNotFoundException e){
            System.err.println("Erorr with the file printer to log");
        }

    }

    /**
     *  updates the date and time
     *  also allows for away mode to check fi it is between the time it needs to change lights
     * @param inputime string representing time in hr:min:sec format
     * @return time to be updated to the label (date is done internally as this mehtod was taken from a different java class)
     */
    public String updatetime(String inputime) {
        String outputtime;
        int temphr;
        int tempmin;
        int tempsec;
        String second;
        String hour;
        String minute;
        String date = comboDate.getItemAt(comboDate.getSelectedIndex());
        String day = comboDay.getItemAt(comboDay.getSelectedIndex());
        String month = comboMonth.getItemAt(comboMonth.getSelectedIndex());
        String year = comboYear.getItemAt(comboYear.getSelectedIndex());

        //format hr.min
        int[] temptime = breakDownTime(inputime);
        tempmin = temptime[1];
        temphr = temptime[0];
        tempsec = temptime[2];
        if (tempsec > 58) {
            tempsec = 0;
            tempmin++;
        } else tempsec++;
        if (tempmin > 59){
            tempmin =0;
            temphr++;
        }
        if (temphr >= 24){
            temphr=0;
            int tempd =Integer.parseInt(date);
            tempd++;

            if (!day.contains("Sun")) {
                day = comboDay.getItemAt(comboDay.getSelectedIndex() + 1);
                comboDay.setSelectedItem(comboDay.getItemAt(comboDay.getSelectedIndex() + 1));
            }
            else {
                day = comboDay.getItemAt( 0);
                comboDay.setSelectedItem(comboDay.getItemAt(0));
            }
            if (tempd >31 && !month.contains("Dec")){
                month = comboMonth.getItemAt(comboMonth.getSelectedIndex() +1);
                comboMonth.setSelectedItem(comboMonth.getItemAt(comboMonth.getSelectedIndex() + 1));
            }
            else if (tempd >31 && month.contains("Dec")){
                month = comboMonth.getItemAt(0);
                comboMonth.setSelectedItem(comboMonth.getItemAt(0));
                year = comboYear.getItemAt(comboYear.getSelectedIndex() +1);
                comboYear.setSelectedItem(comboYear.getItemAt(comboYear.getSelectedIndex() + 1));
                printToConsole("Happy new Years!");
            }
            if (tempd < 31)date = String.valueOf(tempd);
            else date = "1";

            dateLabel.setText(day.substring(0, 3) + " " + month.substring(0, 3) + " " + date + " " + year);
        }
        if (temphr <10) hour = "0" + temphr;
        else hour = String.valueOf(temphr);
        if (tempmin <10) minute = "0" + tempmin;
        else minute = String.valueOf(tempmin);
        if (tempsec <10) second = "0" + tempsec;
        else second = String.valueOf(tempsec);
        outputtime = hour+":"+minute+":"+second;
        return outputtime;
    }

    /**
     * Breaksdown a time input hr:min:sec to an int array[] e.g. int[0] = hr, int [1] = min int[2] = sec.
     *
     * @param inputime String of above mentioned format to represent time to be decomposed
     * @return an int array representing a frame of time broken into different int elements
     */
    static int[] breakDownTime(String inputime){
        int[] a = new int[3];

        String[] strings = inputime.split(":");
        for(int i=0; i<strings.length;i++){
            a[i] = Integer.parseInt(strings[i].trim());
        }
        return a;
    }

    /**
     * Updates the house layout with the new information.
     */
    public void updateHouseLayout(){
        HouseLayout.remove(dynamicLayout);
        if (isItWinter())dynamicLayout = new DynamicLayout(house.getRoomsList(),isItWinter(),isAwayModeOn(),getWintertemp());
        else dynamicLayout = new DynamicLayout(house.getRoomsList(),isItWinter(),isAwayModeOn(),getSummertemp());
        HouseLayout.add(dynamicLayout);
    }

    /**
     * Checks if automode is selected.
     *
     * @return true if the auto mode is On; False otherwise.
     */
    public boolean isAutoMode (){
        return setToAutoModeCheckBox.isSelected();
    }

    /**
     * Disables the away mode and displays message in the console.
     */
    public void disableAwayMode(String reason){
        awayModeCheckbox.setSelected(false);
        detachObserver(shp);
        awayModeEnableLabel.setVisible(false);
        printToConsole("Away mode was disabled. " + reason);
    }

    /**
     * For testing
     * @return
     */
    public JComboBox<String> getComboDate() {
        return comboDate;
    }

    /**
     * For testing
     * @return
     */
    public JComboBox<String> getComboDay() {
        return comboDay;
    }

    /**
     * For testing
     * @return
     */
    public JComboBox<String> getComboMonth() {
        return comboMonth;
    }

    /**
     * For testing
     * @return
     */
    public JComboBox<String> getComboYear() {
        return comboYear;
    }

    /**
     * For testing
     * @return
     */
    public JSpinner getHourSpinner() {
        return hourSpinner;
    }

    /**
     * For testing
     * @return
     */
    public JSpinner getMinuteSpinner() {
        return minuteSpinner;
    }

    /**
     * For testing
     * @return
     */
    public JSpinner getSecondSpinner() {
        return secondSpinner;
    }

    /**
     * For testing
     * @return
     */
    public JSpinner getOutSideTemp() {
        return outSideTemp;
    }

    /**
     * For testing
     * @return
     */
    public JComboBox<String> getComboLocation() {
        return comboLocation;
    }

    /**
     * For testing
     * @return
     */
    public JButton getOnOff() {
        return onOff;
    }

    /**
     * For Testing
     * @return
     */
    public JCheckBox getAwayModeCheckbox(){ return awayModeCheckbox;}

    /**
     * For Testing
     * @return
     */
    public void setCurrentTime(String time){
        timeLabel.setText(time);
    }

    /**
     * For Testing
     * @return
     */
    public String getConsoleText(){
        return consoleText.getText();
    }

    /**
     * For Testing
     * @return
     */
    public JCheckBox getAutoModeCheckBox(){
        return setToAutoModeCheckBox;
    }

    /**
     * Checks if someone is home and returns the type of one of the people at home.
     *
     * @return the type of the person at home; Null if nobody is in the house.
     */
    public String isSomeoneHome(){
        for(String username : UserManager.getUsernames()){
            if(!UserManager.getUserLocation(username).equalsIgnoreCase("outside") && !UserManager.getUserLocation(username).contains("STOOP")) return username;
        }
        return null;
    }

    /**
     * Getter for current time.
     *
     * @return the current time.
     */
    public String getCurrentTime(){
        return timeLabel.getText();
    }

    /**
     * Getter for the scheduled time.
     *
     * @return the scheduled time.
     */
    public String getScheduleTime(){
        String s = fromSchedualHoursSpinner.getValue() + ":" +fromSchedualMinutesSpinner.getValue() + ":" + fromSchedualSecondsSpinner.getValue();
        s += ":";
        s += toSchedualHoursSpinner.getValue() + ":" + toSchedualMinutesSpinner.getValue() + ":" + toSchedualSecondsSpinner.getValue();
        return s;
    }

    /**
     * Getter for the Alert timer.
     *
     * @return the alert timer time.
     */
    public String getAlertTimer(){
        return timerHoursSpinner.getValue() + ":" + timerMinutesSpinner.getValue() + ":" + timerSecondsSpinner.getValue();
    }

    /**
     * return the time speed multiplier value.
     *
     * @return value of the time speed multiplier.
     */
    public int getMultiplier(){
        return (int)speedSpinner.getValue();
    }

    /**
     * Sets all automated lights to the state provided.
     * @param state The state that the automated must be set to. (true = on, false = off)
     */
    public void setAutomatedLights(boolean state){
        for(String light : automatedLights){
            house.openCloseObject(light, state);
        }
        setUpSHCOpenClose();
        updateHouseLayout();
    }

    /**
     * Attaches an observer to this object.
     *
     * @param o The observer to be attached.
     */
    @Override
    public void attachObserver(Observer o) {
        this.observers.add(o);
    }

    /**
     * Detaches an observer from this object.
     *
     * @param o The observer to be detached.
     */
    @Override
    public void detachObserver(Observer o) {
        observers.removeIf(observer -> observer.getClass().toString().equals(o.getClass().toString()));
    }

    /**
     * Calls the <code>update()</code> method of all the observers attached to this object.
     *
     * @param observable a reference to the observable object to be updated. Usually it is a reference to the calling object.
     */
    @Override
    public void notifyObservers(Observable observable) {
        for(int i=0; i<observers.size(); i++){
            observers.get(i).update(observable);
        }
    }

    /**
     * returns the speed spinner for testing
     * @return jspinner speed multiplier spinner
     */
    public JSpinner getSpeedspinner(){
        return speedSpinner;
    }

    /**
     * run method for testing
     */
    public void run(boolean b){
        onOff.setSelected(b);
    }

    /**
     * getter method, returns the current delay of timer
     * @return timer's delay
     */
    public int getTimerDelay(){
        return timer.getDelay();
    }

    /**
     * getter method for dynamic house layout
     * @return DynamicLayout gets returned
     */
    public DynamicLayout getDynamicLayout(){return dynamicLayout;}

    /**
     * Getter method for house for testing
     * @return The house object
     */
    public House getHouse(){return house;}

    /**
     * getter method for testing
     * @return the jpanle of open close
     */
    public JCheckBox[] getOpenClosePanel(){return itemsArr;}

    /**
     * Getter method for testing
     * @return the jlist of items
     */
    public JList<SmartObjectType> getListItems() { return listItems; }

    /**
     * Adds the passed string to the zones combo box.
     *
     * @param item Item to be added to the zones combo box.
     */
    public void addZonesComboItem(String item){
        zonesCombo.addItem(item);
    }

    /**
     * Adds the passed string to the period combo box.
     *
     * @param item Item to be added to the period combo box.
     */
    public void addPeriodComboItem(String item){ periodCombo.addItem(item); }

    /**
     * @return The selected period in the period combo box.
     */
    public String getSelectedPeriod(){
        return (String) periodCombo.getSelectedItem();
    }

    /**
     * @return The selected zone in the zones combo box.
     */
    public String getSelectedZone(){
        return (String) zonesCombo.getSelectedItem();
    }

    /**
     * Sets the model of the zone temperature spinner.
     * @param snm The model to be set to.
     */
    public void setTempZoneSpinnerModel(SpinnerNumberModel snm){
        tempZoneSpinner.setModel(snm);
    }

    /**
     * Sets the value of the zone temperature spinner.
     * @param value the new value of the zone spinner
     */
    public void setTempZoneSpinnerValue(double value){
        tempZoneSpinner.setValue(value);
    }

    /**
     * @return the value of the zone temperature spinner
     */
    public double getTempZoneSpinnerValue(){
        return Double.parseDouble(tempZoneSpinner.getValue().toString());
    }

    /**
     * Adds the passed string to the room temperature combo box.
     *
     * @param item Item to be added to the room temperature combo box.
     */
    public void addRoomTempComboItem(String item){ roomTempCombo.addItem(item); }

    /**
     * @return The selected room temperature combo item.
     */
    public String getSelectedRoom(){ return (String)roomTempCombo.getSelectedItem(); }

    /**
     * Sets the model of the room temp spinner.
     * @param snm The model to be set to.
     */
    public void setTempRoomSpinnerModel(SpinnerNumberModel snm){
        tempRoomSpinner.setModel(snm);
    }

    /**
     * Sets the value to the room temperature spinner.
     * @param value The new value of the room tempeature spinner.
     */
    public void setTempRoomSpinnerValue(double value){
        tempRoomSpinner.setValue(value);
    }

    /**
     * @return The room temperature spinner value.
     */
    public double getTempRoomSpinnerValue(){
        if (tempRoomSpinner.getValue() instanceof  Integer){
            int i = ((Integer) tempRoomSpinner.getValue()).intValue();
            return (double) i;
        }
        else return (double)tempRoomSpinner.getValue();
    }

    /**
     * Clears the zone temperature combo.
     */
    public void clearZoneTemp(){
        zonesCombo.removeAllItems();
    }

    /**
     * Clears the room temperature combo.
     */
    public void clearRoomTemp(){
        roomTempCombo.removeAllItems();
    }

    /**
     * Updates the zone temperature block.
     */
    public void updateZoneTempBlock(){
        shh.updateZoneBlock();
        updateHouseLayout();
    }

    /**
     * Updates the room temperature block.
     */
    public void updateRoomTempBlock(){
        shh.updateRoomTempSpinner();
        updateHouseLayout();
    }

    /**
     * getter
     * @return int[] the to seasons array ([0] = month, [1] = day)
     */
    public int[] getfromseasons(){ return fromseasonsmonth;}

    /**
     * getter
     * @return int[] the to seasons array ([0] = month, [1] = day)
     */
    public int[] gettoseason(){return toseasonsmonth;}

    /**
     * setter for fromseasons
     * @param ar array containing the month and day
     */
    public void setfromseaons(int[] ar){
        fromseasonsmonth[0] = ar[0];
        fromseasonsmonth[1] = ar[1];
    }

    /**
     * setter for toseasons
     * @param ar array containing the month and day
     */
    public void settoseaons(int[] ar){
        toseasonsmonth[0] = ar[0];
        toseasonsmonth[1] = ar[1];
    }

    /**
     * getter
     * @return int winter temp
     */
    public int getWintertemp(){ return wintertemp; }

    /**
     * getter
     * @return int summer temp
     */
    public int getSummertemp(){return summertemp;}

    /**
     * getter
     * @return boolean if the system is in away mode
     */
    public boolean isAwayModeOn(){return awayModeCheckbox.isSelected();}

    /**
     * getter
     * @return returns all rooms from house
     */
    public Room[] getallrooms(){return house.getRoomsList();}

    /**
     * checks if the current day is within the winter interval
     * @return boolean if it is or isnt winter
     */
    public boolean isItWinter(){
        if (comboMonth.getSelectedItem() == null || dateLabel.getText().split(" ").length < 2)return false;

        String tempMonth = ((String)comboMonth.getSelectedItem()).toUpperCase();
        int month = Months.valueOf(tempMonth).ordinal() + 1;
        int day = comboDay.getSelectedIndex();

        int currentDayCount = getDayCount(month, day);
        int fromDayCount = getDayCount(fromseasonsmonth[0], fromseasonsmonth[1]);
        int toDayCount =  getDayCount(toseasonsmonth[0], toseasonsmonth[1]);

       if(fromDayCount <= toDayCount) {
           if (currentDayCount >= fromDayCount && currentDayCount < toDayCount) return true;
           else return false;
       }
       else{
           if(currentDayCount >= fromDayCount || currentDayCount < toDayCount) return true;
           else return false;
       }
    }

    /**
     * Calculates the day number that the date corresponds to.
     * (Jan 1st = 1, Dec 31st = 372, etc) (Each month has 32 days in our world)
     * @param month The number value corresponding to the month
     * @param day The number value corresponding to the day
     * @return The value of the day in the year (out of 372 days)
     */
    public int getDayCount(int month, int day){
        return ((month-1)*31)+day;
    }

    /**
     * getter
     * @return double the outside temp value
     */
    public double getOutsidetemp(){
        if (outsidetempvalue.getText().split("°")[0].equalsIgnoreCase(""))return 0;
        String s = outsidetempvalue.getText();
        String[] sp = s.split("°");
        return (double)Integer.parseInt(sp[0]);
    }

    /**
     * setter for summer temp
     * @param tem int new summer temp
     */
    public void setSummertemp(int tem){summertemp = tem;}

    /**
     * setter for witner temp
     * @param temp int new winter temp
     */
    public void setWintertemp(int temp){wintertemp = temp;}

    /**
     * Sets the label of the room temp spinner to either "Temp:" or "Temp: (Overridden)"
     * depending on the boolean value passed
     * @param overridden If true, includes "(Overridden)" in the label; If false, removes the "(Overriden)" from the label
     */
    public void setRoomTempOverridden(boolean overridden) {
        if(overridden){
            roomTempLabel.setText("Temp:(Overridden)");
        }
        else{
            roomTempLabel.setText("Temp:");
        }
    }

    /**
     * getter method for testing
     * @return Jspinner
     */
    public JSpinner getTempZoneSpinner(){return  tempZoneSpinner;}

    /**
     * getter method for TESTING
     * @return Jbutton
     */
    public JButton getSetZoneTempButton(){return setZoneTempButton;}

    /**
     * getter method for testing
     * @return JComboBox<String>
     */
    public JComboBox<String> getPeriodCombo(){return periodCombo;}

    /**
     * getter method for testing
     * @return JComboBox<String>
     */
    public JComboBox<String> getZonesCombo(){return zonesCombo;}

    /**
     * getter method for testing
     * @return SHH
     */
    public SHH getShh(){return shh;}

    /**
     * For testing
     * Getter of the creatZone button.
     * @return
     */
    public JButton getCreateZone(){return createZone;}

    /**
     * For testing
     * Getter for the addRoom button.
     * @return
     */
    public JButton getAddRoom(){ return addRoom;}

    /**
     * For testing
     * Getter for the save zone button.
     * @return
     */
    public JButton getSaveZoneButton(){return saveZoneButton;}
}