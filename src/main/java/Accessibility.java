import javax.swing.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * Setting up accessibility on the frontend
 */
public class Accessibility {
    private static HashMap<AccessibilityType, String> accessibilitiesToString = new HashMap<>();
    private static HashMap<String, AccessibilityType> stringToAccessibilities = new HashMap<>();
    private static HashMap<SmartObjectType, AccessibilityType> smartObjToAccessibility = new HashMap<>();

    /**
     * initial setup fro the disabled dropdown
     * @param comboDisabledAccessibility
     */
    public static void setDisabledAccessibilityDropdown(JComboBox<String> comboDisabledAccessibility, LocationType location) {
        for(AccessibilityType accessibility : AccessibilityType.values()) {
            //away mode is only allowed to be used when user is outside
            if(accessibility == AccessibilityType.AWAYMODE) {
                if(location == LocationType.OUTSIDE) {
                    comboDisabledAccessibility.addItem(accessibilitiesToString.get(accessibility));
                }
            } else {
                comboDisabledAccessibility.addItem(accessibilitiesToString.get(accessibility));
            }
        }
    }

    /**
     * enabled dropdown
     * @param comboEnabledAccessibility
     * @param comboDisabledAccessibility
     * @param comboLocationAccessiblity
     * @param comboUsers
     */
    public static void setAccessibilitiesDropdown(JComboBox<String> comboEnabledAccessibility, JComboBox<String> comboDisabledAccessibility, JComboBox<String> comboLocationAccessiblity, JComboBox<String> comboUsers) {
        // Deleting everything in accessibility dropdowns
        comboEnabledAccessibility.removeAllItems();
        comboDisabledAccessibility.removeAllItems();
        // Initial setup of disabled accessibility dropdown
        setDisabledAccessibilityDropdown(comboDisabledAccessibility, LocationType.valueOf(comboLocationAccessiblity.getSelectedItem().toString()));
        //Check if null
        if(comboUsers.getSelectedItem() != null) {
            //Getting correct information to get user accessibilities
            User user = UserManager.findExistingUser(comboUsers.getSelectedItem().toString());
            LocationType locationType = LocationType.valueOf(comboLocationAccessiblity.getSelectedItem().toString());
            ArrayList accessibilityType = user.getAccessibilities().get(locationType);
            //Add accessibility to the correct dropdown
            for(Object type : accessibilityType) {
                String itemInDropdown = accessibilitiesToString.get(type);
                comboEnabledAccessibility.addItem(itemInDropdown);
                comboDisabledAccessibility.removeItem(itemInDropdown);
            }
        }
    }

    /**
     * add accessibility
     * @param comboEnabledAccessibility
     * @param comboDisabledAccessibility
     * @param comboLocationAccessiblity
     * @param comboUsers
     */
    public static void addAccessibility(JComboBox<String> comboEnabledAccessibility, JComboBox<String> comboDisabledAccessibility, JComboBox<String> comboLocationAccessiblity, JComboBox<String> comboUsers) {
        //Getting selected user
        User user = UserManager.findExistingUser(comboUsers.getSelectedItem().toString());
        // Getting location selected
        LocationType locationType = LocationType.valueOf(comboLocationAccessiblity.getSelectedItem().toString());
        //Getting Accessibility to be added
        AccessibilityType accessibilityType = stringToAccessibilities.get(comboDisabledAccessibility.getSelectedItem().toString());
        addAccessibilityForUser(locationType, user, accessibilityType);
        //Need to update dropdowns with new accessibilities
        setAccessibilitiesDropdown(comboEnabledAccessibility,comboDisabledAccessibility, comboLocationAccessiblity,comboUsers);
    }

    /**
     * This updates the user's accessibilities
     * @param locationType
     * @param user
     * @param accessibilityType
     */
    protected static void addAccessibilityForUser(LocationType locationType, User user, AccessibilityType accessibilityType) {
        //To make sure no duplicated values are added
        Boolean checkDuplicate = false;
        //If the location is INSIDE all locations should be updated except for OUTSIDE
        if(locationType == LocationType.INSIDE) {
            for (HashMap.Entry<LocationType, ArrayList<AccessibilityType>> entry : user.getAccessibilities().entrySet()) {
                if(entry.getKey() != LocationType.OUTSIDE){
                    ArrayList<AccessibilityType> updatedAccessibilities = entry.getValue();
                    //Make sure that no duplicate values are added
                    for(Object type: updatedAccessibilities) {
                        if(accessibilityType == type) {
                            checkDuplicate = true;
                            break;
                        }
                    }
                    if(!checkDuplicate) {
                        updatedAccessibilities.add(accessibilityType);
                    }
                }
                checkDuplicate = false;
            }
        } else {
            //Location accessibility updated
            user.getAccessibilities().get(locationType).add(accessibilityType);
        }
    }

    /**
     * Delete accessibility
     * @param comboEnabledAccessibility
     * @param comboDisabledAccessibility
     * @param comboLocationAccessiblity
     * @param comboUsers
     */
    public static void removeAccessibility(JComboBox<String> comboEnabledAccessibility, JComboBox<String> comboDisabledAccessibility, JComboBox<String> comboLocationAccessiblity, JComboBox<String> comboUsers) {
        //Getting selected user
        User user = UserManager.findExistingUser(comboUsers.getSelectedItem().toString());
        // Getting location selected
        LocationType locationType = LocationType.valueOf(comboLocationAccessiblity.getSelectedItem().toString());
        //Getting Accessibility to be added
        AccessibilityType accessibilityType = stringToAccessibilities.get(comboEnabledAccessibility.getSelectedItem().toString());
        removeAccessibilityForUser(locationType, user, accessibilityType);
        //Need to update dropdowns with new accessibilities
        setAccessibilitiesDropdown(comboEnabledAccessibility,comboDisabledAccessibility, comboLocationAccessiblity,comboUsers);
    }

    protected static void removeAccessibilityForUser(LocationType locationType, User user, AccessibilityType accessibilityType) {
        ArrayList updatedAccessibilities;
        //If the location is INSIDE all locations should be updated except for OUTSIDE
        if(locationType == LocationType.INSIDE) {
            for (HashMap.Entry<LocationType, ArrayList<AccessibilityType>> entry : user.getAccessibilities().entrySet()) {
                if(entry.getKey() != LocationType.OUTSIDE){
                    updatedAccessibilities = entry.getValue();
                    for(int i = 0; i < updatedAccessibilities.size(); i++) {
                        if(accessibilityType == updatedAccessibilities.get(i)) {
                            updatedAccessibilities.remove(i);
                            break;
                        }
                    }
                }
            }
        } else {
            updatedAccessibilities = user.getAccessibilities().get(locationType);
            for(int i = 0; i < updatedAccessibilities.size(); i++) {
                if(accessibilityType == updatedAccessibilities.get(i)) {
                    updatedAccessibilities.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Checks if user is outside and that away mode is in their accessibility list
     * @param username
     * @return
     */
    public static boolean allowAwayMode(String username) {
        User user = UserManager.findExistingUser(username);
        String location = UserManager.getUserLocation(username);
        if(location.equalsIgnoreCase("outside")) {
            ArrayList accessibilities = user.getAccessibilities().get(LocationType.OUTSIDE);
            for(Object accessibility : accessibilities) {
                if(accessibility == AccessibilityType.AWAYMODE) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * intializing string nad accessibilityType
     */
    public static void initializeAccessibilities() {
        accessibilitiesToString.put(AccessibilityType.WINDOWCONTROL, "Open/Close windows");
        accessibilitiesToString.put(AccessibilityType.LIGHTCONTROL, "Turn on/off lights");
        accessibilitiesToString.put(AccessibilityType.DOORCONTROL, "Lock/Unlock doors");
        accessibilitiesToString.put(AccessibilityType.AWAYMODE, "Turn on/off away mode");

        stringToAccessibilities.put("Open/Close windows", AccessibilityType.WINDOWCONTROL);
        stringToAccessibilities.put("Turn on/off lights", AccessibilityType.LIGHTCONTROL);
        stringToAccessibilities.put("Lock/Unlock doors", AccessibilityType.DOORCONTROL);
        stringToAccessibilities.put("Turn on/off away mode", AccessibilityType.AWAYMODE);

        smartObjToAccessibility.put(SmartObjectType.WINDOW, AccessibilityType.WINDOWCONTROL);
        smartObjToAccessibility.put(SmartObjectType.LIGHT, AccessibilityType.LIGHTCONTROL);
        smartObjToAccessibility.put(SmartObjectType.DOOR, AccessibilityType.DOORCONTROL);
    }

    /**
     * this is to get accessibility for the corresponding object
     * @return
     */
    public static HashMap<SmartObjectType, AccessibilityType> getSmartObjToAccessibility() {
        return smartObjToAccessibility;
    }
}
