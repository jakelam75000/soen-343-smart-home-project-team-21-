import javax.swing.*;
import java.util.ArrayList;

public class SHH implements Observer{

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

        for(Zone zone : shd.getHouse().getZones()){
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

        for (Zone zone : shd.getHouse().getZones()){
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

        shd.getHouse().setZoneTemp(zoneName, period, temperature);
    }

    public void setRoomTemperature(SmartHomeDashboard shd){
        String roomName = shd.getSelectedRoom();
        double temperature = shd.getTempRoomSpinnerValue();

        shd.getHouse().setRoomDesiredTemp(roomName, temperature);
    }

    @Override
    public void update(Observable o) {

    }
}
