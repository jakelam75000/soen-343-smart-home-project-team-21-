import javax.swing.*;
import java.util.ArrayList;

/**
 * SHH observer class that manages the SHH tab in the SmartHomeDashboard and updates the HVAC
 */
public class SHH implements Observer{

    private PeriodsOfDay period;
    private static SHH instance;
    private SmartHomeDashboard caller;
    private static boolean hvacon = false;
    private boolean[] hvacturnon;
    private boolean first = true;
    private boolean first2 = true;
    static {
        instance = new SHH();
    }
    private double autodesiredtemp;

    /**
     * empty constructor for shh
     */
    private SHH(){}

    /**
     * singleton implementation
     * @return the only allowed instance
     */
    public static SHH getInstance(){
        return instance;
    }

    /**
     * sets the caller object to shd (for getting rooms and other functions associated with SmartHomeDashboard
     * @param shd smarthomedashboard that is using this instace of shh
     */
    public void setCaller(SmartHomeDashboard shd){caller = shd;}

    /**
     * method used to set up the zone temperature block including the periods and desired temp spinner
     */
    public void setUpZoneTempBlock(){
        caller.clearZoneTemp();

        for(Zone zone : ZoneManager.getZoneList()){
            caller.addZonesComboItem(zone.getName());
        }

        caller.addPeriodComboItem("Morning (6:00 - 14:00)");
        caller.addPeriodComboItem("Evening (14:00 - 22:00)");
        caller.addPeriodComboItem("Night (22:00 - 6:00)");

        caller.setTempZoneSpinnerModel(new SpinnerNumberModel(0,-90,57,1));
    }

    /**
     * method to set up the room temperature block for override
     */
    public void setUpRoomTempBlock(){
        caller.clearRoomTemp();

        for(Room room : caller.getHouse().getRoomsList()){
            String roomName = room.getName();
            if(roomName.contains("STOOP") || roomName.equalsIgnoreCase("OUTSIDE"))
                continue;
            else
                caller.addRoomTempComboItem(room.getName());
        }

        caller.setTempZoneSpinnerModel(new SpinnerNumberModel(0, -90, 57, 1));
    }

    /**
     * updates the zoneblock by clearing and getting the new values
     */
    public void updateZoneBlock(){
        caller.clearZoneTemp();

        for(Zone zone : ZoneManager.getZoneList()){
            caller.addZonesComboItem(zone.getName());
        }

        updateZoneTempSpinner();
    }

    /**
     *updates the zone temp spinner with the current zone tempreture
     */
    public void updateZoneTempSpinner(){
        String periodName = caller.getSelectedPeriod();
        if(periodName == null)
            return;

        periodName = periodName.split(" ")[0].toUpperCase();

        String zoneName = caller.getSelectedZone();

        double temperature = 0;

        PeriodsOfDay period = PeriodsOfDay.valueOf(periodName);

        for (Zone zone : ZoneManager.getZoneList()){
            if(zone.getName().equals(zoneName))
                temperature = zone.getDesiredTemperature(period);
        }

        caller.setTempZoneSpinnerValue(temperature);
    }

    /**
     *method to update the room tempreture spinner
     */
    public void updateRoomTempSpinner(){
        ZoneManager.updateDesiredTempPeriod(period);

        String roomName = caller.getSelectedRoom();
        double temperature = 0;
        for (Room room : caller.getHouse().getRoomsList()){
            if(room.getName().equals(roomName))
                temperature = room.getDesiredTemp();
        }

        caller.setTempRoomSpinnerValue(temperature);

        checkOverride(roomName, temperature, false);
    }

    /**
     *sets the zone temperature to the one from the spinner and clears overrides
     */
    public void setZoneTemperature(){
        String zoneName = caller.getSelectedZone();
        double temperature = caller.getTempZoneSpinnerValue();

        String periodName = caller.getSelectedPeriod();
        periodName = periodName.split(" ")[0].toUpperCase();

        PeriodsOfDay period = PeriodsOfDay.valueOf(periodName);

        ZoneManager.setZoneTemp(zoneName, period, temperature);
        ZoneManager.clearOverridden(zoneName);
        caller.updateHouseLayout();
    }

    /**
     *sets the desired temperature of the selected room
     */
    public void setRoomTemperature(){
        String roomName = caller.getSelectedRoom();
        double temperature = caller.getTempRoomSpinnerValue();

        caller.getHouse().setRoomDesiredTemp(roomName, temperature);

        checkOverride(roomName, temperature, true);
    }

    /**
     *checks if a given room is overridden (for the shh gui to show overridden)
     * @param roomName
     * @param temperature
     * @param addToOverride
     */
    private void checkOverride(String roomName, double temperature, boolean addToOverride){
        boolean roomInZone = false;

        for(Zone zone : ZoneManager.getZoneList()){
            if(zone.containsRoom(new Room(new Smartobj[0], roomName, 0, 1, 1))){

                if(addToOverride) ZoneManager.addOverridden(roomName, zone.getName());

                roomInZone = true;

                if(zone.getDesiredTemperature(period) != temperature) caller.setRoomTempOverridden(true);
                else caller.setRoomTempOverridden(false);

                break;
            }
        }



        if(!roomInZone) caller.setRoomTempOverridden(false);
    }

    /**
     * cools or heats the rooms according to their own preferred temperature
     * @param rooms the list of rooms to be checked
     * @param shd a connection to smart home dashboard to get appropriate variables
     */
    private void ManageRoomtemp(Room[] rooms, SmartHomeDashboard shd, PeriodsOfDay p){
        boolean closeallwindows = true;
        for (int i =0; i < rooms.length; i ++) {
            if (!hvacturnon[i])continue;
            if (rooms[i].getDesiredTemp() < rooms[i].getTemperature() - 0.04) {
                if (rooms[i].getName().contains("STOOP") ) continue;
                closeallwindows = false;
                if (shd.getOutsidetemp() < rooms[i].getTemperature() && !caller.isItWinter())
                    if (!rooms[i].openAllwindows()) {
                        rooms[i].closeAllWindows();
                        shd.printToConsole("a Window in room " + rooms[i].getName() + " was blocked!");
                    }
                if (shd.getOutsidetemp() >= rooms[i].getTemperature()|| caller.isItWinter())
                    if (!rooms[i].closeAllWindows())
                        shd.printToConsole("a Window in room " + rooms[i].getName() + " was blocked!");
                if (rooms[i].getTemperature() < 1) {
                    rooms[i].setDesiredTemp(1);
                    shd.printToConsole("Warning! Cold temperatures may burst pipes, changing this rooms zone temperature");
                    for (Zone zone : ZoneManager.getZoneList()) {
                        if (zone.containsRoom(rooms[i])) zone.setDesiredTemperature(period, 1);
                        else rooms[i].setDesiredTemp(1);
                    }
                } else if (!rooms[i].isAWindowopen()) rooms[i].setTemperature(rooms[i].getTemperature() - 0.1);
                else rooms[i].setTemperature(rooms[i].getTemperature() - 0.1);
                if (rooms[i].getDesiredTemp() > rooms[i].getTemperature() + 0.04)
                    rooms[i].setTemperature(rooms[i].getDesiredTemp());
            } else if (rooms[i].getDesiredTemp() > rooms[i].getTemperature() + 0.04) {

                closeallwindows = false;

                rooms[i].setTemperature(rooms[i].getTemperature() + 0.1);
                if (rooms[i].getDesiredTemp() < rooms[i].getTemperature() + 0.04)
                    rooms[i].setTemperature(rooms[i].getDesiredTemp());
            }
            else hvacturnon[i] = false;
        }
        if (closeallwindows){
            hvacon = false;
            shd.getHouse().closeAllWindows();
        }
    }

    /**
     * cools or heats the rooms according to the defualt season setting
     * @param rooms list of all rooms in the house
     * @param shd the smart home dashboard object
     * @param outsideTemp the temperature of the desired home temperature
     */
    public void autoManageRoomtemp(Room[] rooms, SmartHomeDashboard shd, double outsideTemp){
        boolean closeallwindows = true;
        for (int i =0; i < rooms.length; i ++){
            if (rooms[i].getName().contains("STOOP") || !hvacturnon[i])continue;
            if (outsideTemp < rooms[i].getTemperature()-0.04 || outsideTemp > rooms[i].getTemperature()+0.04){
                closeallwindows = true;
                if (shd.getOutsidetemp() < rooms[i].getTemperature() && !shd.isItWinter()) if (!rooms[i].openAllwindows()){
                    rooms[i].closeAllWindows();
                    shd.printToConsole("a Window in room " + rooms[i].getName()+" was blocked!");
                }
                if (shd.getOutsidetemp() >= rooms[i].getTemperature() && !shd.isItWinter()) if (!rooms[i].closeAllWindows())shd.printToConsole("a Window in room " + rooms[i].getName()+" was blocked!");
                if (shd.isItWinter())if (!rooms[i].closeAllWindows())shd.printToConsole("a Window in room " + rooms[i].getName()+" was blocked!");
                if (rooms[i].getTemperature() <1 && shd.getSummertemp() != 1 && shd.getWintertemp() != 1){
                    shd.printToConsole("Warning cold temperatures may burst pipes, keeping temperature to 1 Celsius");
                    shd.setSummertemp(1);
                    shd.setWintertemp(1);
                }
                else if (outsideTemp < rooms[i].getTemperature()-0.04){
                    rooms[i].setTemperature(rooms[i].getTemperature() - 0.1);
                    if (outsideTemp > rooms[i].getTemperature() - 0.04)rooms[i].setTemperature(outsideTemp);
                }
                else if (outsideTemp > rooms[i].getTemperature()+0.04){

                    rooms[i].setTemperature(rooms[i].getTemperature() + 0.1);
                    if (outsideTemp < rooms[i].getTemperature() + 0.04)rooms[i].setTemperature(outsideTemp);
                }
                //add windows condition
            }
            else hvacturnon[i] = false;
        }
        if (closeallwindows){
            shd.getHouse().closeAllWindows();
        }

    }

    /**
     * the hvac system which changes the rooms temperature based on the zone or away mode
     * @param o The object to be observed and updated
     */
    @Override
    public void update(Observable o) {
        SmartHomeDashboard shd = (SmartHomeDashboard)o;
        if (shd.isItWinter())autodesiredtemp = shd.getWintertemp();
        else autodesiredtemp = shd.getSummertemp();
        Room[] rooms = shd.getallrooms();
        if (first){
            first = false;
            hvacturnon = new boolean[rooms.length];
        }
        int[] time = shd.breakDownTime(shd.getCurrentTime());
        if (time[0] >=6 &&time[0] <14 ){
            if (period!= PeriodsOfDay.MORNING){
                ZoneManager.updateDesiredTempPeriod( PeriodsOfDay.MORNING);
            }
            period = PeriodsOfDay.MORNING;
        }
        else if (time[0] >=14 &&time[0] <22 ){
            if (period!= PeriodsOfDay.EVENING){
                ZoneManager.updateDesiredTempPeriod(PeriodsOfDay.EVENING);
            }
            period = PeriodsOfDay.EVENING;
        }
        else {
            if (period!= PeriodsOfDay.NIGHT){
                ZoneManager.updateDesiredTempPeriod(PeriodsOfDay.NIGHT);
            }
            period = PeriodsOfDay.NIGHT;
        }
        ZoneManager.updateDesiredTempPeriod(period);
        boolean isWinter = shd.isItWinter();
        if (first2){
            if (shd.isAwayModeOn()) {
                for (int i = 0; i < rooms.length; i++) {
                    if ((Math.abs(rooms[i].getTemperature() - autodesiredtemp) >=1 || Math.abs(rooms[i].getTemperature() - autodesiredtemp) >= 1 ) && ! rooms[i].getName().contains("STOOP") )first2 = false;
                }
            }
            else {
                for (int i = 0; i < rooms.length; i++) {
                    if (Math.abs(rooms[i].getTemperature() - rooms[i].getDesiredTemp()) >= 1 && ! rooms[i].getName().contains("STOOP"))first2 = false;
                }
            }

        }
            if (shd.isAwayModeOn() && !first2) {
                    autoManageRoomtemp(rooms, shd,autodesiredtemp);
            } else if (!first2) ManageRoomtemp(rooms, shd, period);
        fluctuate(rooms);
        if (shd.isAwayModeOn() && !first2) {
            for (int i = 0; i < rooms.length; i++) {
                if ((Math.abs(rooms[i].getTemperature() - autodesiredtemp) >=0.25 || Math.abs(rooms[i].getTemperature() - autodesiredtemp) >= 0.25 ) && ! rooms[i].getName().contains("STOOP") )hvacturnon[i] = true;
            }
        }
        else if (!first2){
            for (int i = 0; i < rooms.length; i++) {
                if (Math.abs(rooms[i].getTemperature() - rooms[i].getDesiredTemp()) >= 0.25 && ! rooms[i].getName().contains("STOOP"))hvacturnon[i] = true;
            }
        }
       shd.updateHouseLayout();
    }

    /**
     * fluctuates the room temp when its stable
     * @param rooms list of all rooms
     */
    public void fluctuate( Room[] rooms){
        for (int i =0; i < rooms.length; i++){
            if (hvacturnon[i] || Math.abs(caller.getOutsidetemp() - rooms[i].getTemperature() ) < 0.01)continue;
            if (caller.getOutsidetemp() < rooms[i].getTemperature()){
                rooms[i].setTemperature(rooms[i].getTemperature() - 0.05);
            }
            else rooms[i].setTemperature(rooms[i].getTemperature() + 0.05);
        }
    }

    /**
     * boolean check to see if the given room has its hvac turned on
     * @param r the room to be checked
     * @return boolean if its hvac is on
     */
    public boolean isHvacon(Room r ){
        Room[] rooms = caller.getallrooms();
    for (int i= 0 ; i < rooms.length; i++){
        if (hvacturnon == null)return false;
        if (rooms[i].getName().equalsIgnoreCase(r.getName()))return hvacturnon[i];
    }
    return false;
    }
}
