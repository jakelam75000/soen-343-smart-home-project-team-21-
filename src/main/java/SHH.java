import javax.swing.*;
import java.util.ArrayList;

public class SHH implements Observer{
private PeriodsOfDay period;

    private static SHH instance;
    private SmartHomeDashboard caller;
    static {
        instance = new SHH();
    }

    private SHH(){}

    public static SHH getInstance(){
        return instance;
    }

    public void setCaller(SmartHomeDashboard shd){caller = shd;}

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

    public void updateZoneBlock(){
        caller.clearZoneTemp();

        for(Zone zone : ZoneManager.getZoneList()){
            caller.addZonesComboItem(zone.getName());
        }

        updateZoneTempValue();
    }

    public void updateZoneTempValue(){
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

    public void updateRoomTempValue(){
        String roomName = caller.getSelectedRoom();
        double temperature = 0;
        for (Room room : caller.getHouse().getRoomsList()){
            if(room.getName().equals(roomName))
                temperature = room.getDesiredTemp();
        }

        caller.setTempRoomSpinnerValue(temperature);
    }

    public void setZoneTemperature(){
        String zoneName = caller.getSelectedZone();
        double temperature = caller.getTempZoneSpinnerValue();

        String periodName = caller.getSelectedPeriod();
        periodName = periodName.split(" ")[0].toUpperCase();

        PeriodsOfDay period = PeriodsOfDay.valueOf(periodName);

        ZoneManager.setZoneTemp(zoneName, period, temperature);
        caller.updateHouseLayout();
    }

    public void setRoomTemperature(){
        String roomName = caller.getSelectedRoom();
        double temperature = caller.getTempRoomSpinnerValue();

        caller.getHouse().setRoomDesiredTemp(roomName, temperature);
        caller.updateHouseLayout();
    }
    /**
     * heats the rooms according to their own preferred temperature
     * @param rooms the list of rooms to be checked
     * @param shd a connection to smart home dashboard to get appropriate variables
     */
    private void heatrooms(Room[] rooms, SmartHomeDashboard shd){
        for (int i =0; i < rooms.length; i ++){
            if (rooms[i].getDesiredTemp()> rooms[i].getTemperature()){
                rooms[i].setTemperature(rooms[i].getTemperature() + 0.1);
            }
        }
    }

    /**
     * cools the rooms according to their own preferred temperature
     * @param rooms the list of rooms to be checked
     * @param shd a connection to smart home dashboard to get appropriate variables
     */
    private void coolrooms(Room[] rooms, SmartHomeDashboard shd){
        for (int i =0; i < rooms.length; i ++){
            if (rooms[i].getDesiredTemp() < rooms[i].getTemperature()){
                if (shd.getOutsidetemp() < rooms[i].getTemperature()) if (!rooms[i].openAllwindows())shd.printToConsole("a Window in room " + rooms[i].getName()+" was blocked!");
                if (shd.getOutsidetemp() >= rooms[i].getTemperature()) shd.getHouse().closeAllWindows();
                if (rooms[i].getTemperature() <1){
                    shd.printToConsole("Warning! Cold temperatures may burst pipes, changing this rooms zone temperature");
                    for (Zone zone:ZoneManager.getZoneList()) {if(zone.containsRoom(rooms[i]))zone.setDesiredTemperature(period,1); }
                    rooms[i].setDesiredTemp(1);
                }
                else  rooms[i].setTemperature(rooms[i].getTemperature() - 0.05);
            }
        }
    }
    /**
     * heats the rooms according to the set seasonal preferred temperature
     * @param rooms the lsit of rooms to be checked
     * @param shd a connection to smart home dashboard to get appropriate variables
     */
    private void autoheatrooms(Room[] rooms,SmartHomeDashboard shd){
        for (int i =0; i < rooms.length; i ++){
            if (shd.getWintertemp() > rooms[i].getTemperature()){
                rooms[i].setTemperature(rooms[i].getDesiredTemp() + 0.1);
            }
        }
    }
    /**
     * cools the rooms according to the set seasonal preferred temperature
     * @param rooms the lsit of rooms to be checked
     * @param shd a connection to smart home dashboard to get appropriate variables
     */
    private void autocoolrooms(Room[] rooms, SmartHomeDashboard shd){
        for (int i =0; i < rooms.length; i ++){
            if (shd.getSummertemp() < rooms[i].getTemperature()){
                if (shd.getOutsidetemp() < rooms[i].getTemperature()) if (!rooms[i].openAllwindows())shd.printToConsole("a Window in room " + rooms[i].getName()+" was blocked!");
                if (shd.getOutsidetemp() >= rooms[i].getTemperature()) shd.getHouse().closeAllWindows();
                rooms[i].setTemperature(rooms[i].getDesiredTemp() - 0.05);
                if (rooms[i].getTemperature() <=0){
                    shd.printToConsole("Warning cold temperatures may burst pipes, raising temperature to 1 Celsius");
                    shd.setSummertemp(1);
                }
                //add windows condition
            }
        }
    }

    /**
     * the hvac system which changes the rooms temperature based on the zone or away mode
     * @param o
     */
    @Override
    public void update(Observable o) {
        SmartHomeDashboard shd = (SmartHomeDashboard)o;
        Room[] rooms = shd.getallrooms();
        int[] time = shd.Breakdowntime(shd.getCurrentTime());
        if (time[2] >=6 &&time[2] <14 ){
            if (period!= PeriodsOfDay.MORNING){
                ZoneManager.updateDesiredTempPeriod( PeriodsOfDay.MORNING);
            }
            period = PeriodsOfDay.MORNING;
        }
        else if (time[2] >=14 &&time[2] <22 ){
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

        for (int i =0; i < rooms.length; i ++){
            if (rooms[i].getName().contains("STOOP"))continue;
            if (shd.isAwayModeOn()){
                if (isWinter){
                    autoheatrooms(rooms,shd);
                }else {
                    autocoolrooms(rooms,shd);
                }
                shd.updateHouseLayout();
            }
            else{
                if (isWinter){
                    heatrooms(rooms,shd);
                }else {
                    coolrooms(rooms,shd);
                }
                shd.updateHouseLayout();
            }
        }

    }

}
