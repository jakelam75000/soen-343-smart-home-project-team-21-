import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContextOfSimulationTest {

    /**
     * Description: This tests if windows are blocked correctly
     * Context: House is set with rooms and their respective smart objects
     * Expected: true
     */
    //Delivery 1
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
    //Delivery 1
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
    //Delivery 1
    @Test
    public void testChangeLocation() {
        UserManager.addUser("Parent1", "passwordabc", UserTypes.PARENT);
        UserManager.changeUserLocation("Parent1", "KITCHEN");
        User user = UserManager.findUser("Parent1", "passwordabc");
        assertEquals(LocationType.KITCHEN.toString(), user.getLocation());
    }

    /**
     * Description: This tests if simulation can be started
     * Context: SmartHomeDashboard is created and onOFF is clicked
     * Expected: True
     */
    //Delivery 1
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
    //Delivery 1
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

    /**
     * TimerspeedTest, tests if the speed modifier is working
     * the timer in the test is because the timer needs time to update the delay
     */
    //Delivery 2: 3.1
    @Test
    public void timespeedtest() {
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        JSpinner speed = shd.getSpeedspinner();
        speed.setValue(3);
        shd.run(true);
        Timer t = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = shd.getTimerDelay();
                assertEquals(i, 1000 / 3);
            }
        });
    }
}
