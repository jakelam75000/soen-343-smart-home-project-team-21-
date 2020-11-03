import javax.swing.*;

/**
 * Setting up location
 */
public class Location {
    /**
     * initial location dropdown
     * @param locations
     * @param comboLocation
     * @param comboLocationAccessiblity
     */
    public static void setLocationDropdowns(String[] locations, JComboBox<String> comboLocation, JComboBox comboLocationAccessiblity) {
        for(String x : locations) {
            comboLocation.addItem(x);
            comboLocationAccessiblity.addItem(x);
        }
        comboLocation.addItem(LocationType.OUTSIDE.toString());
        comboLocationAccessiblity.addItem(LocationType.OUTSIDE.toString());
        comboLocationAccessiblity.addItem(LocationType.INSIDE.toString());
        comboLocationAccessiblity.addItem(LocationType.CURRENT.toString());
    }

    /**
     * user location label
     * @param username
     * @param comboLocation
     * @param currentLocLabel
     */
    public static void setUserLocation(String username, JComboBox<String> comboLocation, JLabel currentLocLabel) {
        currentLocLabel.setText(comboLocation.getItemAt(comboLocation.getSelectedIndex()));
        UserManager.changeUserLocation(username,currentLocLabel.getText());
    }
}
