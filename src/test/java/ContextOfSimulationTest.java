import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContextOfSimulationTest {

    /**
     * Description: This tests if windows are blocked correctly
     * Context: House is set with rooms and their respective smart objects
     * Expected: true
     */
    @Test
    public void testBlockWindow() {
        House testHouse = HouseReader.readAndLoadHouse("Houselayout.txt");
        Room room1 = testHouse.getRoomAtIndex(0);
        assertEquals(true, room1.blockWindow(room1.getName(), true));
    }

    /**
     * Description: This tests if temperature is set correctly
     * Context: SmartHomeDashboard is created and the outside temperature is set to 25
     * Expected: 25
     */
    @Test
    public void testSetTemperature() {
        SmartHomeDashboard smartHomeDashboard = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent1", "Houselayout.txt");

        smartHomeDashboard.getOutSideTemp().setValue(25);

        assertEquals(25, smartHomeDashboard.getOutSideTemp().getValue());
    }

    /**
     * Description: This tests if user location can be changed
     * Context: SmartHomeDashboard is created and index 2 of comboLocation is selected
     * Expected: KITCHEN
     */
    @Test
    public void testChangeLocation() {
        SmartHomeDashboard smartHomeDashboard = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent1", "Houselayout.txt");
        JComboBox<String> comboLocation = smartHomeDashboard.getComboLocation();

        comboLocation.setSelectedIndex(2);

        assertEquals(LocationType.KITCHEN.toString(), comboLocation.getItemAt(comboLocation.getSelectedIndex()));
    }

    /**
     * Description: This tests if simulation can be started
     * Context: SmartHomeDashboard is created and onOFF is clicked
     * Expected: True
     */
    @Test
    public void testStartSimulation() {
        SmartHomeDashboard smartHomeDashboard = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent1", "Houselayout.txt");
        smartHomeDashboard.getOnOff().doClick();
        assertEquals(true, smartHomeDashboard.getOnOff().isSelected());
    }

    /**
     * Description: This tests if date and time can be modified
     * Context: SmartHomeDashboard is created, index 1, 2, 2, 0 are selected for comboDate, comboDay, comboMonth, comboYear in that respective order
     * Expected: first assertAll: assertEquals should give the following results in their respective order: "2", "Wednesday", "March", "2000"
     *           second assertAll: assertEquals should give the following results in their respective order: 6, 12, 33
     */
    @Test
    public void testModifyDateAndTime() {
        SmartHomeDashboard smartHomeDashboard = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent1", "Houselayout.txt");
        JComboBox<String> comboDate = smartHomeDashboard.getComboDate();
        JComboBox<String> comboDay = smartHomeDashboard.getComboDay();
        JComboBox<String> comboMonth = smartHomeDashboard.getComboMonth();
        JComboBox<String> comboYear = smartHomeDashboard.getComboYear();

        comboDate.setSelectedIndex(1);
        comboDay.setSelectedIndex(2);
        comboMonth.setSelectedIndex(2);
        comboYear.setSelectedIndex(0);

        assertAll(
                "Make sure date is set properly",
                () -> assertEquals("2", comboDate.getItemAt(comboDate.getSelectedIndex())),
                () -> assertEquals("Wednesday", comboDay.getItemAt(comboDay.getSelectedIndex())),
                () -> assertEquals("March", comboMonth.getItemAt(comboMonth.getSelectedIndex())),
                () -> assertEquals("2000", comboYear.getItemAt(comboYear.getSelectedIndex()))

        );

        smartHomeDashboard.getHourSpinner().setValue(6);
        smartHomeDashboard.getMinuteSpinner().setValue(12);
        smartHomeDashboard.getSecondSpinner().setValue(33);

        assertAll(
                "Make sure time is set properly",
                () -> assertEquals(6, smartHomeDashboard.getHourSpinner().getValue()),
                () -> assertEquals(12, smartHomeDashboard.getMinuteSpinner().getValue()),
                () -> assertEquals(33, smartHomeDashboard.getSecondSpinner().getValue())

        );

    }
}
