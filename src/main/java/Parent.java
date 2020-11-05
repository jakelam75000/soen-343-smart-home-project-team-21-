import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * Child class of user for permission checking
 */
public class Parent extends User{
    private HashMap<LocationType, ArrayList<AccessibilityType>> accessibilities = new HashMap<>();


    public Parent(String username, String password) {
        super(username, password);
        this.accessibilities = assignAccessibilities(accessibilities);
    }

    /**
     * Assigns accessibilities to each location
     * @param accessibilities
     * @return
     */
    private HashMap<LocationType, ArrayList<AccessibilityType>> assignAccessibilities(HashMap<LocationType, ArrayList<AccessibilityType>> accessibilities) {
        for(LocationType location : LocationType.values()) {
            accessibilities.put(location, assignAccessibilityTypes(location));
        }
        return accessibilities;
    }

    /**
     * Creates ArrayList of accessibilities
     * @return
     */
    private ArrayList<AccessibilityType> assignAccessibilityTypes (LocationType locationType) {
        ArrayList<AccessibilityType> accessibilityTypes = new ArrayList<AccessibilityType>();
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
    public void setAccessibilities(HashMap<LocationType, ArrayList<AccessibilityType>> accessibilities) {
        this.accessibilities = accessibilities;
    }

    /**
     * getter
     * @return
     */
    @Override
    public HashMap<LocationType, ArrayList<AccessibilityType>> getAccessibilities() {
        return accessibilities;
    }
}
