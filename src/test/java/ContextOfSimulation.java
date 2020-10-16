import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContextOfSimulation {

    @Test
    public void testBlockWindow() {
        House testHouse = HouseReader.readAndLoadHouse("Houselayout.txt");
        Room room1 = testHouse.getRoomAtIndex(0);
        assertEquals(true, room1.blockWindow(room1.getName(), true));

    }

    @Test
    public void testSetTemperature() {
        SmartHomeDashboard smartHomeDashboard = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent1", "Houselayout.txt");

        smartHomeDashboard.getOutSideTemp().setValue(25);

        assertEquals(25, smartHomeDashboard.getOutSideTemp().getValue());
    }

    @Test
    public void testChangeLocation() {
        SmartHomeDashboard smartHomeDashboard = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent1", "Houselayout.txt");
        JComboBox<String> comboLocation = smartHomeDashboard.getComboLocation();

        comboLocation.setSelectedIndex(2);

        assertEquals(LocationType.KITCHEN.toString(), comboLocation.getItemAt(comboLocation.getSelectedIndex()));
    }

    @Test
    public void testStartSimulation() {
        SmartHomeDashboard smartHomeDashboard = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent1", "Houselayout.txt");
        smartHomeDashboard.getOnOff().doClick();
        assertEquals(true, smartHomeDashboard.getOnOff().isSelected());
    }
}
