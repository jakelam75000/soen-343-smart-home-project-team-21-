import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
public class SimulationParameterTest {

    /**
     * Make sure there are no users
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
        UserManager.addUser("Guest", "Guest", UserTypes.GUEST);
    }

    /**
     * Description: test if users can be added
     * Context: users are initialized
     * Expected: assertAll: assertEquals should give the following results in their respective order: 5, 2, 2, 1
     */
    // Delivery 1
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
    // Delivery 1
    @Test
    public void testDeleteUser() {
        isEmpty();

        // This should not remove anything since there are no users
        UserManager.removeUser("Parent2", "password123");
        isEmpty();

        initialize();

        UserManager.removeUser("Parent2", "password123");
        UserManager.removeUser("Guest", "Guest");
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
    // Delivery 1
    @Test
    public void testEditUser() {
        initialize();
        User b = UserManager.findExistingUser("Parent1");
        assertAll(
                "Make sure user have been edited correctly",
                () -> assertEquals(false, UserManager.editUser("", "", "", null)),
                () -> assertEquals(false, UserManager.editUser("Parent2", "wrong", "new", UserTypes.PARENT)),
                () -> assertEquals(true, UserManager.editUser("Parent1", "passwordabc", "new", UserTypes.PARENT)),
                () -> assertEquals(true, UserManager.editUser("Guest", "Guest", "hello", UserTypes.CHILD))
        );
    }

    /**
     * Description: This tests if user that already exists can be logged in
     * Context: Login object is set, users are initialized
     * Expected: assertAll: assertEquals should give the following results in their respective order: false, true, true
     */
    // Delivery 1
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
    // Delivery 1
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
     * Description: This tests accessibilities are added
     * Context: Child is created and is initalized with its default accessibility, then LIGHTCONTROL is added for BEDROOM
     * Expected: assertFalse (before LIGHTCONTROL is added) we check if BEDROOM does not have LIGHTCONTROL
     *           assertTrue (after LIGHTCONTROL is added) we check if BEDROOM has LIGHTCONTROL
     */
    // Delivery 2: 3.1
    @Test
    public void testAddingAccessibilites() {
        initialize();
        User child = UserManager.findUser("Child1", "abc");
        HashMap<LocationType, ArrayList<AccessibilityType>> accessibilities = child.getAccessibilities();

        assertFalse(accessibilities.get(LocationType.BEDROOM).contains(AccessibilityType.LIGHTCONTROL));

        Accessibility.addAccessibilityForUser(LocationType.BEDROOM, child, AccessibilityType.LIGHTCONTROL);

        assertTrue(accessibilities.get(LocationType.BEDROOM).contains(AccessibilityType.LIGHTCONTROL));

    }

    /**
     * Description: This tests accessibilities are removed
     * Context: Parent is created and is initalized with its default accessibility, then WINDOWCONTROL is removed for LIVINGROOM
     * Expected: assertTrue (before WINDOWCONTROL is removed) we check if LIVINGROOM has WINDOWCONTROL
     *           assertFalse (after WINDOWCONTROL is removed) we check if LIVINGROOM does not have WINDOWCONTROL
     */
    // Delivery 2: 3.1
    @Test
    public void testRemovingAccessibilites() {
        initialize();
        User parent = UserManager.findUser("Parent1", "passwordabc");
        HashMap<LocationType, ArrayList<AccessibilityType>> accessibilities = parent.getAccessibilities();

        assertTrue(accessibilities.get(LocationType.LIVINGROOM).contains(AccessibilityType.WINDOWCONTROL));

        Accessibility.removeAccessibilityForUser(LocationType.LIVINGROOM, parent, AccessibilityType.WINDOWCONTROL);

        assertFalse(accessibilities.get(LocationType.LIVINGROOM).contains(AccessibilityType.WINDOWCONTROL));
    }


    /**
     * Description: This tests storing users in a file and loading them back from that file.
     * Context: Users are loaded; User "a" does not exist; "a" is added; users file is update and users are cleared; Users are loaded back; "a" is a valid user.
     * Expected: assertFalse (before User "a" is added) we check if "a" exists.
     *           assertTrue (after "a" is added) we check if was properly added.
     *           assertFalse (after all users are cleared) we check if "a" has been removed.
     *           assertTrue (after users are loaded back) we check if was loaded from the file.
     */
    // Delivery 2: 3.1
    @Test
    public void testLoadPreviousUsers(){
        UserDatabaseManager.loadUsers(false);

        assertFalse(UserManager.isUserValid("a", "a"));

        UserManager.addUser("a","a", UserTypes.PARENT);

        assertTrue(UserManager.isUserValid("a", "a"));

        UserDatabaseManager.updateUserFile();

        UserManager.clearUsers();

        assertFalse(UserManager.isUserValid("a", "a"));

        UserDatabaseManager.loadUsers(true);

        assertTrue(UserManager.isUserValid("a", "a"));
    }

    /**
     * Description: This tests storing user accessibilities in a file and loading them back from that file.
     * Context: Users are loaded from file; "Guest" is missing windowcontrol for living room; "Guest" is given
     *          the WINDOWCONTROL accessibility for LIVINGROOM; Users are cleared and loaded back from file;
     *          "Guest" has WINDOWCONTROL for LIVINGROOM;
     * Expected: assertNotNull (after loading users) we check if "Guest" exists.
     *           assertFalse (after loading users) we check if "Guest" has WINDOWCONTROL for LIVINGROOM.
     *           assertTrue (after adding accessibility to "Guest") we check if "Guest" has WINDOWCONTROL for LIVINGROOM.
     *           assertTrue (after users are loaded back) we check if was loaded from the file.
     *           assertNotNull (after reloading users) we check if "Guest" exists.
     *           assertTrue (after adding accessibility to "Guest") we check if "Guest" has WINDOWCONTROL for LIVINGROOM.
     */
    // Delivery 2: 3.1
    @Test
    public void testLoadPreviousUsersAccessibility(){
        UserDatabaseManager.loadUsers(false);
        User guest = UserManager.findUser("Guest", "Guest");

        assertNotNull(guest);

        HashMap<LocationType, ArrayList<AccessibilityType>> accessibilities = guest.getAccessibilities();

        assertFalse(accessibilities.get(LocationType.LIVINGROOM).contains(AccessibilityType.WINDOWCONTROL));

        Accessibility.addAccessibilityForUser(LocationType.LIVINGROOM, guest, AccessibilityType.WINDOWCONTROL);

        assertTrue(accessibilities.get(LocationType.LIVINGROOM).contains(AccessibilityType.WINDOWCONTROL));

        UserDatabaseManager.updateUserFile();

        UserManager.clearUsers();

        UserDatabaseManager.loadUsers(true);
        guest = UserManager.findUser("Guest", "Guest");

        assertNotNull(guest);

        accessibilities = guest.getAccessibilities();

        assertTrue(accessibilities.get(LocationType.LIVINGROOM).contains(AccessibilityType.WINDOWCONTROL));
    }


    /**
     * This is to clear all users
     */
    @AfterEach
    public void cleanUp() {
        UserManager.clearUsers();
    }



}
