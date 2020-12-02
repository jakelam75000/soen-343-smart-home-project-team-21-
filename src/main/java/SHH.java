import javax.swing.*;
import java.util.ArrayList;

public class SHH implements Observer{
private PeriodsOfDay period;

    private static SHH instance;
    static {
        instance = new SHH();
    }

    private SHH(){}

    public static SHH getInstance(){
        return instance;
    }

    public void setUpZoneTempBlock(SmartHomeDashboard shd){
        shd.clearZoneTemp();

        for(Zone zone : ZoneManager.getZoneList()){
            shd.addZonesComboItem(zone.getName());
        }

        shd.addPeriodComboItem("Morning (6am - 2pm)");
        shd.addPeriodComboItem("Evening (2pm - 10pm)");
        shd.addPeriodComboItem("Night (10pm - 6am)");

        shd.setTempZoneSpinnerModel(new SpinnerNumberModel(0,-90,57,1));
    }

    public void setUpRoomTempBlock(SmartHomeDashboard shd){
        shd.clearRoomTemp();

        for(Room room : shd.getHouse().getRoomsList()){
            String roomName = room.getName();
            if(roomName.contains("STOOP") || roomName.equalsIgnoreCase("OUTSIDE"))
                continue;
            else
                shd.addRoomTempComboItem(room.getName());
        }

        shd.setTempZoneSpinnerModel(new SpinnerNumberModel(0, -90, 57, 1));
    }

    public void updateZoneTempValue(SmartHomeDashboard shd){
        String zoneName = shd.getSelectedZone();

        double temperature = 0;

        String periodName = shd.getSelectedPeriod();
        periodName = periodName.split(" ")[0].toUpperCase();

        PeriodsOfDay period = PeriodsOfDay.valueOf(periodName);

        for (Zone zone : ZoneManager.getZoneList()){
            if(zone.getName().equals(zoneName))
                temperature = zone.getDesiredTemperature(period);
        }

        shd.setTempZoneSpinnerValue(temperature);
    }

    public void updateRoomTempValue(SmartHomeDashboard shd){
        String roomName = shd.getSelectedRoom();
        double temperature = 0;
        for (Room room : shd.getHouse().getRoomsList()){
            if(room.getName().equals(roomName))
                temperature = room.getDesiredTemp();
        }

        shd.setTempRoomSpinnerValue(temperature);
    }

    public void setZoneTemperature(SmartHomeDashboard shd){
        String zoneName = shd.getSelectedZone();
        double temperature = shd.getTempZoneSpinnerValue();

        String periodName = shd.getSelectedPeriod();
        periodName = periodName.split(" ")[0].toUpperCase();

        PeriodsOfDay period = PeriodsOfDay.valueOf(periodName);

        ZoneManager.setZoneTemp(zoneName, period, temperature);
    }

    public void setRoomTemperature(SmartHomeDashboard shd){
        String roomName = shd.getSelectedRoom();
        double temperature = shd.getTempRoomSpinnerValue();

        shd.getHouse().setRoomDesiredTemp(roomName, temperature);
    }
    /**
     * heats the rooms according to their own preferred temperature
     * @param rooms the list of rooms to be checked
     * @param shd a connection to smart home dashboard to get appropriate variables
     */
    private void heatrooms(Room[] rooms, SmartHomeDashboard shd){
        for (int i =0; i < rooms.length; i ++){
            if (rooms[i].getDesiredTemp()> rooms[i].getTemperature()){
                rooms[i].setTemperature(rooms[i].getDesiredTemp() + 0.1);
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
                if (shd.getOutsidetemp() < rooms[i].getTemperature())
                    if (!rooms[i].openAllwindows())shd.printToConsole("a Window in room " + rooms[i].getName()+" was blocked!");
                rooms[i].setTemperature(rooms[i].getDesiredTemp() - 0.05);
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
            if (shd.getWintertemp() < rooms[i].getTemperature()){
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
            if (shd.getSummertemp() > rooms[i].getTemperature()){
                if (shd.getOutsidetemp() < rooms[i].getTemperature())
                    if (!rooms[i].openAllwindows())shd.printToConsole("a Window in room " + rooms[i].getName()+" was blocked!");
                rooms[i].setTemperature(rooms[i].getDesiredTemp() - 0.05);
                if (rooms[i].getTemperature() <=0){
                    shd.printToConsole("Warning cold tempretures may burst pipes, raising temperature to 1 Celsius");
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
        //TOdo need to make the room objects in zone the same as the ones in shd (believe they are clones atm)
        //Todo need to find a way to set the desired temp of a zone from here
        SmartHomeDashboard shd = (SmartHomeDashboard)o;
        Room[] rooms = shd.getallrooms();
        int[] time = shd.Breakdowntime(shd.getCurrentTime());
        //ZoneManager.updatezonesDesiredTempPeriod(period);
        if (time[2] >=6 &&time[2] <14 ){
            if (period!= PeriodsOfDay.MORNING){
                ZoneManager.updatezonesDesiredTempPeriod( PeriodsOfDay.MORNING);
            }
            period = PeriodsOfDay.MORNING;
        }
        else if (time[2] >=14 &&time[2] <22 ){
            if (period!= PeriodsOfDay.EVENING){
                ZoneManager.updatezonesDesiredTempPeriod(PeriodsOfDay.EVENING);
            }
            period = PeriodsOfDay.EVENING;
        }
        else {
            if (period!= PeriodsOfDay.NIGHT){
                ZoneManager.updatezonesDesiredTempPeriod(PeriodsOfDay.NIGHT);
            }
            period = PeriodsOfDay.NIGHT;
        }
        if (time[2] == 6 && time[1] == 0 && time[0] == 0)ZoneManager.updatezonesDesiredTempPeriod(period);
        else if (time[2] == 14 && time[1] == 0 && time[0] == 0)ZoneManager.updatezonesDesiredTempPeriod(period);
        else if (time[2] == 22 && time[1] == 0 && time[0] == 0)ZoneManager.updatezonesDesiredTempPeriod(period);
        //remove l8r, this line is for testing
        ZoneManager.updatezonesDesiredTempPeriod(period);
        for (int i =0; i < rooms.length; i ++){
            if (rooms[i].getName().contains("STOOP"))continue;
            if (shd.isAwayModeOn()){
                if (shd.isItWinter()){
                    autoheatrooms(rooms,shd);
                }else {
                    autocoolrooms(rooms,shd);
                }
            }
            else{
                if (shd.isItWinter()){
                    heatrooms(rooms,shd);
                }else {
                    coolrooms(rooms,shd);
                }
            }
        }

    }

}
