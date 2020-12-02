import javax.swing.*;
import java.util.ArrayList;

public class SHH implements Observer{

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

        caller.addPeriodComboItem("Morning (6am - 2pm)");
        caller.addPeriodComboItem("Evening (2pm - 10pm)");
        caller.addPeriodComboItem("Night (10pm - 6am)");

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
    }

    public void setRoomTemperature(){
        String roomName = caller.getSelectedRoom();
        double temperature = caller.getTempRoomSpinnerValue();

        caller.getHouse().setRoomDesiredTemp(roomName, temperature);
    }

    @Override
    public void update(Observable o) {
        //Todo, move rooms temps to desired temp (check zone temp first)
        //Todo, if summer, open windows instead of ac if outside is cooler
        //Todo, if <0 rasie desired temp and sent a message to dashboard
        //Todo,if away mode set all rooms desired to winter or summer default
        //Todo move tmep by 0.1 deg for heat and 0.05 deg if cold
        /*
        SmartHomeDashboard shd = (SmartHomeDashboard)o;
        Room[] rooms = shd.getallrooms();
        for (int i =0; i < rooms.length; i ++){

        }
        */
    }
}
