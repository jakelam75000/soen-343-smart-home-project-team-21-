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
        for(LocationType location : LocationType.values()) {
            accessibilities.put(location, assignAccessibilityTypes(location));
        }
        return accessibilities;
    }

    /**
     * Creates ArrayList of accessibilities
     * @return
     */
    private ArrayList assignAccessibilityTypes (LocationType locationType) {
        ArrayList accessibilityTypes = new ArrayList();
        for(AccessibilityType accessibility : AccessibilityType.values()) {
            if(accessibility == AccessibilityType.AWAYMODE) {
                if(locationType == LocationType.OUTSIDE) {
                    accessibilityTypes.add(accessibility);
                }
            } else {
                accessibilityTypes.add(accessibility);
            }
        }
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
