import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
public class SimulationParameterTest {

    /**
     * Make sure their are no users
     */
    public void isEmpty() {
        assertAll(
                "Make sure all hashMaps are empty",
                () -> assertEquals(0, UserManager.sizeAllUsers()),
                () -> assertEquals(0, UserManager.sizeUserParent()),
                () -> assertEquals(0, UserManager.sizeUserChild()),
                () -> assertEquals(0, UserManager.sizeUserGuest())
        );
    }

    /**
     * Initialize 5 users
     */
    public static void initialize() {
        UserManager.addUser("Parent1", "passwordabc", UserTypes.PARENT);
        UserManager.addUser("Parent2", "password123", UserTypes.PARENT);
        UserManager.addUser("Child1", "abc", UserTypes.CHILD);
        UserManager.addUser("Child2", "123", UserTypes.CHILD);
        UserManager.addUser("Guest", "password", UserTypes.GUEST);
    }

    /**
     * Description: test if users can be added
     * Context: users are initialized
     * Expected: assertAll: assertEquals should give the following results in their respective order: 5, 2, 2, 1
     */
    @Test
    public void testAddUser() {
        isEmpty();

        initialize();
        // Should not be added because username already exists
        UserManager.addUser("Parent1", "password", UserTypes.PARENT);

        assertAll(
                "Make sure user have been added correctly",
                () -> assertEquals(5, UserManager.sizeAllUsers()),
                () -> assertEquals(2, UserManager.sizeUserParent()),
                () -> assertEquals(2, UserManager.sizeUserChild()),
                () -> assertEquals(1, UserManager.sizeUserGuest())
        );
    }

    /**
     * Description: test if users can be edited
     * Context: users are initialized and then remove the following users: "Parent2", "Guest", "Child1"
     * Expected: assertAll: assertEquals should give the following results in their respective order: 2, 1, 1, 0
     */
    @Test
    public void testDeleteUser() {
        isEmpty();

        // This should not remove anything since there are no users
        UserManager.removeUser("Parent2", "password123");
        isEmpty();

        initialize();

        UserManager.removeUser("Parent2", "password123");
        UserManager.removeUser("Guest", "password");
        UserManager.removeUser("Child1", "abc");
        //Should not remove since username does not exist
        UserManager.removeUser("Friend", "password");
        //Should not remove because password not valid
        UserManager.removeUser("Child2", "fail");

        assertAll(
                "Make sure user have been deleted correctly",
                () -> assertEquals(2, UserManager.sizeAllUsers()),
                () -> assertEquals(1, UserManager.sizeUserParent()),
                () -> assertEquals(1, UserManager.sizeUserChild()),
                () -> assertEquals(0, UserManager.sizeUserGuest())
        );
    }

    /**
     * Description: test if users can be edited
     * Context: users are initialized
     * Expected: assertAll: assertEquals should give the following results in their respective order: false, false, true, true
     */
    @Test
    public void testEditUser() {
        initialize();

        assertAll(
                "Make sure user have been edited correctly",
                () -> assertEquals(false, UserManager.editUser("", "", "", null)),
                () -> assertEquals(false, UserManager.editUser("Parent2", "wrong", "new", UserTypes.PARENT)),
                () -> assertEquals(true, UserManager.editUser("Parent2", "password123", "new", UserTypes.PARENT)),
                () -> assertEquals(true, UserManager.editUser("Guest", "password", "hello", UserTypes.CHILD))
        );
    }

    /**
     * Description: This tests if user that already exists can be logged in
     * Context: Login object is set, users are initialized
     * Expected: assertAll: assertEquals should give the following results in their respective order: false, true, true
     */
    @Test
    public void testLogin() {
        Login login = new Login("test");
        initialize();

        assertAll(
                "Make sure user is logged in correctly",
                () -> assertEquals(false, login.loginClicked("Child1", "123", "Houselayout.txt")),
                () -> assertEquals(true, login.loginClicked("Parent1", "passwordabc", "Houselayout.txt")),
                () -> assertEquals(true, login.loginClicked("Child1", "abc", "Houselayout.txt"))

        );
    }

    /**
     * Description: This tests if date and time can be set
     * Context: SmartHomeDashboard is created, index 1, 2, 2, 0 are selected for comboDate, comboDay, comboMonth, comboYear in that respective order
     * Expected: first assertAll: assertEquals should give the following results in their respective order: "2", "Wednesday", "March", "2000"
     *           second assertAll: assertEquals should give the following results in their respective order: 6, 12, 33
     */
    @Test
    public void testSetDateAndTime() {
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
     * This is to clear all users
     */
    @AfterEach
    public void cleanUp() {
        UserManager.clearUsers();
    }
}
