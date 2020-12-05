import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;
public class SmartHomeHeatingTest {
    /**
     * Description: Test functionality for create zones
     * Context:initialize smart home dashboard and create a new zoneB
     * Expected: the new zone is stored in zone manager with the name apples
     */
    //Delivery 3: 3.1
    @Test
    public void createZonesTest(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        CreateZone cz = CreateZone.getInstance();
        cz.getZoneName().setText("apples");
        cz.getCreateZoneButton().doClick();
        assertEquals( "apples" , ZoneManager.getZoneList().get(0).getName());
    }
    /**
     * Description: Test functionality for setting zone temp
     * Context:initialize smart home dashboard and create a new zone and change the temperature of the zone and check the rooms desired temperate
     * Expected: the new desired temperature of the room should match what was changed
     */
    //Delivery 3: 3.2
    @Test
    public void SettingsZoneTemperatureTest(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        CreateZone cz = CreateZone.getInstance();

        cz.getZoneName().setText("apples");
        cz.getCreateZoneButton().doClick();

        //ZONE IS CREATED SUCCESSFULLY AT THIS POINT.

        shd.getOnOff().doClick();
        Timer t = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shd.updateZoneRooms();
                shd.getAddRoom().doClick();
                shd.getSaveZoneButton().doClick();
                shd.getPeriodCombo().setSelectedIndex(2);
                shd.getTempZoneSpinner().setValue(34);
                shd.getSetZoneTempButton().doClick();

                assertEquals(34, ZoneManager.getZoneList().get(0).getRooms().get(0).getDesiredTemp());
            }
        });

    }
}