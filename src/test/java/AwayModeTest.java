import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

public class AwayModeTest {

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





}
