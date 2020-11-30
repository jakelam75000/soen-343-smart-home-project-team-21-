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

//    public void setUpZoneTemperature(SmartHomeDashboard shd){
//        ArrayList<Zone> zones = shd.getHouse().getZones();
//
//        for(Zone zone : zones){
//            shd.addZonesComboItem(zone.getName());
//        }
//
//        periodCombo.addItem("Morning (6am - 2pm)");
//        periodCombo.addItem("Evening (2pm - 10pm)");
//        periodCombo.addItem("Night (10pm - 6am)");
//
//        tempZoneSpinner.setModel(new SpinnerNumberModel(0,-90,57,1));
//
//    }

    @Override
    public void update(Observable o) {

    }
}
