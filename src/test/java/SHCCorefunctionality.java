import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test case class holding tests pertaining to SHC
 */
public class SHCCorefunctionality {

    /**
     * Description: This method tests if the objects states are being changed form the front end to the back end
     * Context: smart home dashboard object is set
     * Expected: State of each object (1 light, 1 window, 1 door) should first be off and then on
     */
    //Delivery 2: 3.3
    @Test
    public void SHCfunctionalitytest(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        JCheckBox[] j = shd.getOpenClosePanel();
        JList<SmartObjectType> d = shd.getListItems();
        d.setSelectedIndex(0);
        Timer t = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assertTrue(!shd.getHouse().getObjectState(j[0].getText()));
                j[0].setSelected(true);
                assertTrue(shd.getHouse().getObjectState(j[0].getText()));
                d.setSelectedIndex(2);
                Timer t1 = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        assertTrue(!shd.getHouse().getObjectState(j[0].getText()));
                        j[0].setSelected(true);
                        assertTrue(shd.getHouse().getObjectState(j[0].getText()));
                        d.setSelectedIndex(1);
                        Timer t2 = new Timer(5000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                assertTrue(!shd.getHouse().getObjectState(j[0].getText()));
                                j[0].setSelected(true);
                                assertTrue(shd.getHouse().getObjectState(j[0].getText()));
                            }
                        });
                    }
                });
            }
        });


    }


    /**
     * Description: This method tests if the auto mode checkbox applies the changes to the state of the light when the checkbox is checked.
     * Context: smart home dashboard object is set, onOff button & auto mode checkbox have been selected, checking state of lights
     * Expected: State of the light object should be changed
     */
    //Delivery 2: 3.3
    @Test
   public void autoModeClickedTest(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard dash = new SmartHomeDashboard("Light test", "PARENT", "Parent", "HouseLayout.txt");

        //Sets the simulation to on
        JButton onOff = dash.getOnOff();
        onOff.setSelected(false);
        //calls the action listener for that button
        onOff.getActionListeners()[0].actionPerformed(new ActionEvent(this, 0 , ""));


        //sets auto mode to on
        JCheckBox automode = dash.getAutoModeCheckBox();
        automode.setSelected(true);
        automode.getActionListeners()[0].actionPerformed(new ActionEvent(this, 0 , ""));



        //gets location of the user
        String location = UserManager.getUserLocation("Parent");
        //gets the state of the light in the user's location
        boolean lightState = dash.getHouse().getObjectState(location +" light");

        assertTrue(lightState);
    }


    /**
     * Description: This method tests if the state of lights changes when a user leaves or enters a new location
     * Context: smart home dashboard object is set, onOff button & auto mode checkbox have been selected, locations have changed, autolights
     *          method is being called on the changes in location, and state of lights are being checked.
     * Expected: assertAll: assertEquals should give the following results in their respective order: false, true, true
     */
    //Delivery 2: 3.3
    @Test
    public void changeUserLocation(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard dash = new SmartHomeDashboard("Light test", "PARENT", "Parent", "HouseLayout.txt");

        //Sets the simulation to on
        JButton onOff = dash.getOnOff();
        onOff.setSelected(false);
        //calls the action listener for that button
        onOff.getActionListeners()[0].actionPerformed(new ActionEvent(this, 0 , ""));


        //sets auto mode to on
        JCheckBox automode = dash.getAutoModeCheckBox();
        automode.setSelected(true);
        automode.getActionListeners()[0].actionPerformed(new ActionEvent(this, 0 , ""));

        //getting user's old location
        String oldLoc = UserManager.getUserLocation("Child");
        String GuestOldLoc = UserManager.getUserLocation("Guest");

        //changing user's location
        UserManager.changeUserLocation("Child", "KITCHEN");
        UserManager.changeUserLocation("Guest", "BEDROOM");


        //calling the autolights method for the changed locations
        dash.autoLights(oldLoc, "KITCHEN");
        dash.autoLights(GuestOldLoc, "BEDROOM");


        //Checking if the state of the lights have been changed
        assertAll(
                "Check if state of lights in the rooms have been changed",
                () -> assertEquals(false, dash.getHouse().getObjectState(oldLoc +" light")),
                () -> assertEquals(true, dash.getHouse().getObjectState(UserManager.getUserLocation("Child") +" light")),
                () -> assertEquals(true, dash.getHouse().getObjectState(UserManager.getUserLocation("Guest") +" light"))
        );


    }


}

