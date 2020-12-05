import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * Child class of User class
 */
public class Child extends User{

    private HashMap<LocationType, ArrayList<AccessibilityType>> accessibilities = new HashMap<>();
    /**
     *constructor that calls the parent User's constructor
     * @param username String the username of the user
     * @param password String the password of the user
     */
    public Child(String username, String password) {
        super(username, password);
        this.accessibilities = assignAccessibilities(accessibilities);
    }

    /**
     * Assigns accessibilities to each location
     * @param accessibilities
     * @return
     */
    private HashMap<LocationType, ArrayList<AccessibilityType>> assignAccessibilities(HashMap<LocationType, ArrayList<AccessibilityType>> accessibilities) {
        EnumSet.allOf(LocationType.class)
                .forEach(location -> accessibilities.put(location, new ArrayList()));
        accessibilities.get(LocationType.CURRENT).add(AccessibilityType.LIGHTCONTROL);
        accessibilities.get(LocationType.CURRENT).add(AccessibilityType.WINDOWCONTROL);
        //Away mode can only be enabled when all user's are outside
        accessibilities.get(LocationType.OUTSIDE).add(AccessibilityType.AWAYMODE);
        return accessibilities;
    }

    /**
     * Getter for Accessibilities
     * @return
     */
    @Override
    public HashMap<LocationType, ArrayList<AccessibilityType>> getAccessibilities() {
        return accessibilities;
    }

    /**
     * Setter for accessibilities
     * @param accessibilities
     */
    @Override
    public void setAccessibilities(HashMap<LocationType, ArrayList<AccessibilityType>> accessibilities) {
        this.accessibilities = accessibilities;
    }
}
