import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * Child class of user for permission checking
 */
public class Parent extends User{
    private HashMap<LocationType, ArrayList> accessibilities = new HashMap<>();
    public Parent(String username, String password) {
        super(username, password);
        this.accessibilities = assignAccessibilities(accessibilities);
    }

    /**
     * Assigns accessibilities to each location
     * @param accessibilities
     * @return
     */
    private HashMap assignAccessibilities(HashMap<LocationType, ArrayList> accessibilities) {
        EnumSet.allOf(LocationType.class)
                .forEach(location -> accessibilities.put(location, assignAccessibilityTypes()));
        return accessibilities;
    }

    /**
     * Creates ArrayList of accessibilities
     * @return
     */
    private ArrayList assignAccessibilityTypes () {
        ArrayList accessibilityTypes = new ArrayList();
        EnumSet.allOf(AccessibilityType.class)
                .forEach(accessibility -> accessibilityTypes.add(accessibility));
        return accessibilityTypes;
    }

    /**
     * setter
     * @param accessibilities
     */
    @Override
    public void setAccessibilities(HashMap<LocationType, ArrayList> accessibilities) {
        this.accessibilities = accessibilities;
    }

    /**
     * getter
     * @return
     */
    @Override
    public HashMap<LocationType, ArrayList> getAccessibilities() {
        return accessibilities;
    }
}
