import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * Child class of User class
 */
public class Child extends User{

    private HashMap<LocationType, ArrayList> accessibilities = new HashMap<>();
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
    private HashMap assignAccessibilities(HashMap<LocationType, ArrayList> accessibilities) {
        EnumSet.allOf(LocationType.class)
                .forEach(location -> accessibilities.put(location, new ArrayList()));
        accessibilities.get(LocationType.CURRENT).add(AccessibilityType.LIGHTCONTROL);
        accessibilities.get(LocationType.CURRENT).add(AccessibilityType.WINDOWCONTROL);
        //Away mode can only be enabled when all user's are outside
        accessibilities.get(LocationType.OUTSIDE).add(AccessibilityType.AWAYMODE);
        return accessibilities;
    }

    @Override
    public HashMap<LocationType, ArrayList> getAccessibilities() {
        return accessibilities;
    }

    @Override
    public void setAccessibilities(HashMap<LocationType, ArrayList> accessibilities) {
        this.accessibilities = accessibilities;
    }
}
