import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateZone extends JFrame{
    private JTextField zoneName;
    private JPanel zoneNameWarning;
    private JComboBox roomNameCombo;
    private JButton addRoomButton;
    private JComboBox addedRoomNameCombo;
    private JButton removeRoomButton;
    private JButton createZoneButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private SmartHomeDashboard caller;
    private Room[] rooms;
    private String[] roomNames;
    private CreateZone self;
    private static CreateZone instance = new CreateZone("CreateZone");

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
    private CreateZone(String title) {
        super(title);
        this.setContentPane(mainPanel);
        this.pack();
        self = this;
        this.setResizable(true);
        this.setBounds(x, y, width, height);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.zoneNameWarning.setVisible(false);
        addActionListeners();
    }

    /**
     * Returns the single instance of CreateZone class.
     * @return
     */
    public static CreateZone getInstance(){
        return instance;
    }

    /**
     * Sets the caller attribute.
     * @param caller SmartHomeDashboard object.
     */
    public void setCaller(SmartHomeDashboard caller){
        this.caller = caller;
    }

    public void setRooms(Room[] rooms){
        this.rooms = rooms;
    }

    public void setRoomNames(String[] roomNames){
        this.roomNames = roomNames;
        ZoneManager.setRoomNameDropdown(roomNameCombo, roomNames);
    }

    /**
     * Adds all the action listeners for the class.
     */
    public void addActionListeners() {

        cancelButton.addActionListener(new ActionListener() {
            /**
             * Creates the zone
             *
             * @param e ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                self.setVisible(false);
                clearAllField();
            }
        });

        addRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(zoneName.getText().equals("")){
                    caller.printToConsole("Please enter zone name before performing this action.");
                    return;
                }
                if(ZoneManager.removeOrAddRoom(roomNameCombo.getSelectedItem(), roomNameCombo, addedRoomNameCombo)) {
                    caller.printToConsole("Room has successfully been added");
                } else {
                    caller.printToConsole("Cannot perform this action.");
                }
            }
        });

        removeRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(zoneName.getText().equals("")){
                    caller.printToConsole("Please enter zone name before performing this action.");
                    return;
                }
                if(ZoneManager.removeOrAddRoom(addedRoomNameCombo.getSelectedItem(), addedRoomNameCombo, roomNameCombo)) {
                    caller.printToConsole("Room has successfully been removed");
                } else {
                    caller.printToConsole("Cannot perform this action.");
                }
            }
        });

        createZoneButton.addActionListener(new ActionListener() {
            /**
             * Creates the zone
             *
             * @param e ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!ZoneManager.createZone(rooms, zoneName.getText(), addedRoomNameCombo)) {
                    zoneNameWarning.setVisible(true);
                    caller.printToConsole("This zone name already exists.");
                    return;
                }
                self.setVisible(false);
                caller.printToConsole(zoneName.getText() + " has been successfully created.");
                clearAllField();
                caller.updateZoneRooms();
                caller.updateZoneTempBlock();
                caller.updateRoomTempBlock();
            }
        });
    }

    private void clearAllField() {
        zoneName.setText("");
        roomNameCombo.removeAllItems();
        addedRoomNameCombo.removeAllItems();
    }
}
