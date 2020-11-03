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
     * @param Username
     * @param comboLocation
     * @param currentLocLabel
     */
    public static void setUserLocation(JLabel Username, JComboBox<String> comboLocation, JLabel currentLocLabel) {
        currentLocLabel.setText(comboLocation.getItemAt(comboLocation.getSelectedIndex()));
        UserManager.changeUserLocation(Username.getText(),currentLocLabel.getText());
    }
}
