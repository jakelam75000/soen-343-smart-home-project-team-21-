import javax.swing.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * Setting up accessibility on the frontend
 */
public class Accessibility {
    private static HashMap<AccessibilityType, String> accessibilities = new HashMap<>();

    /**
     * initial setup fro the disabled dropdown
     * @param comboDisabledAccessibility
     */
    public static void setDisabledAccessibilityDropdown(JComboBox<String> comboDisabledAccessibility) {
        EnumSet.allOf(AccessibilityType.class)
                .forEach(accessibility -> comboDisabledAccessibility.addItem(accessibilities.get(accessibility)));
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
        setDisabledAccessibilityDropdown(comboDisabledAccessibility);
        //Check if null
        if(comboUsers.getSelectedItem() != null) {
            //Getting correct information to get user accessibilities
            User user = UserManager.findExistingUser(comboUsers.getSelectedItem().toString());
            LocationType locationType = LocationType.valueOf(comboLocationAccessiblity.getSelectedItem().toString());
            ArrayList accessibilityType = user.getAccessibilities().get(locationType);
            //Add accessibility to the correct dropdown
            for(Object type : accessibilityType) {
                String itemInDropdown = accessibilities.get(type);
                comboEnabledAccessibility.addItem(itemInDropdown);
                comboDisabledAccessibility.removeItem(itemInDropdown);
            }
        }
    }

    /**
     * intializing corresponding string
     */
    public static void initializeAccessibilities() {
        accessibilities.put(AccessibilityType.WINDOWCONTROL, "Open/Close windows");
        accessibilities.put(AccessibilityType.LIGHTCONTROL, "Turn on/off lights");
        accessibilities.put(AccessibilityType.DOORCONTROL, "Lock/Unlock doors");
        accessibilities.put(AccessibilityType.AWAYMODE, "Turn on/off away mode");
    }
}
