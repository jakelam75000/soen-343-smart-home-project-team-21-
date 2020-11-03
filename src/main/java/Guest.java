import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * Child class of User
 */
public class Guest extends User{
    private HashMap<LocationType, ArrayList> accessibilities = new HashMap<>();
    /**
     *
     * @param username
     * @param password
     */
    public Guest(String username, String password) {
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
        return accessibilities;
    }

    /**
     * getter
     * @return
     */
    @Override
    public HashMap<LocationType, ArrayList> getAccessibilities() {
        return accessibilities;
    }

    /**
     * setter
     * @param accessibilities
     */
    @Override
    public void setAccessibilities(HashMap<LocationType, ArrayList> accessibilities) {
        this.accessibilities = accessibilities;
    }
}
