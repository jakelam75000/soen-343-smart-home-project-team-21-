import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

public class AwayModeTest {

    /**
     * Description: Test functionality of awayMode checkbox when users are inside the house
     * Context: initialise user database with default users, initialise smarthomedashboard and select awayMode, change location of user to KITCHEN
     * Expected: make sure the checkbox is no longer selected
     */
    //Delivery 2: 3.4
    @Test
    public void testPplInHouse(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        JCheckBox awayCheck = shd.getAwayModeCheckbox();

        awayCheck.setSelected(true);

        UserManager.changeUserLocation("Parent", "KITCHEN");

        awayCheck.getActionListeners()[0].actionPerformed(new ActionEvent(this, 1 , "a"));

        assertFalse(awayCheck.isSelected());
    }

    /**
     * Description: Test functionality of awayMode checkbox when users enter the house
     * Context: initialise user database with default users, set them all outside , initialise smarthomedashboard and select awayMode
     * Expected: away mode should no longer be selected when parent enters house
     */
    //Delivery 2: 3.4
    @Test
    public void testUserEntersHouse(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        JCheckBox awayCheck = shd.getAwayModeCheckbox();
        shd.setCurrentTime("11:11:11");

        for(String user : UserManager.getUsernames()){
            UserManager.changeUserLocation(user, "OUTSIDE");
        }

        awayCheck.setSelected(true);

        awayCheck.getActionListeners()[0].actionPerformed(new ActionEvent(this, 1 , "a"));

        assertTrue(awayCheck.isSelected());

        UserManager.changeUserLocation("Parent", "KITCHEN");

        shd.notifyObservers(shd);

        assertFalse(awayCheck.isSelected());
    }

    /**
     * Description: Test functionality of alerting the cops
     * Context: initialise user database with default users, set them all outside , initialise smarthomedashboard and select awayMode, set time 11:11:11
     * Expected: after the timer set by the user passes the following message should be printed: "COPS ARE ON THEIR WAY"
     */
    //Delivery 2: 3.4
    @Test
    public void testAlertCops(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        JCheckBox awayCheck = shd.getAwayModeCheckbox();
        shd.setCurrentTime("11:11:11");

        for(String user : UserManager.getUsernames()){
            UserManager.changeUserLocation(user, "OUTSIDE");
        }

        awayCheck.setSelected(true);

        awayCheck.getActionListeners()[0].actionPerformed(new ActionEvent(this, 1 , "a"));

        UserManager.changeUserLocation("Parent", "OUTSIDE");

        assertTrue(awayCheck.isSelected());

        UserManager.changeUserLocation("Stranger", "KITCHEN");

        shd.notifyObservers(shd);

        assertTrue(shd.getConsoleText().contains("COPS ARE ON THEIR WAY!!!"));

    }

    /**
     * This is to clear all users
     */
    @AfterEach
    public void cleanUp() {
        UserManager.clearUsers();
    }
}
