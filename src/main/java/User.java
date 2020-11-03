import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * Parent class of all user types
 */
public class User {
    private String username;
    private String password;
    private String location;
    private HashMap<LocationType, ArrayList> accessibilities = new  HashMap<>();

    /**
     * Constructor
     * @param username String the name of the account
     * @param password String the password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.location = LocationType.OUTSIDE.toString();
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
        return accessibilities;
    }
    /**
     * getter
     * @return String username
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter
     * @param username String the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter
     * @return String returns password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter
     * @param password String new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * setter
     * @param loc String lcoation of the user
     */
    public void setLocation(String loc){
        location = loc;
    }

    /**
     * getter
     * @return String the lcoation of the user
     */
    public String getLocation(){
        return location;
    }

    /**
     * getter
     * @return
     */
    public HashMap<LocationType, ArrayList> getAccessibilities() {
        return accessibilities;
    }

    /**
     * setter
     * @param accessibilities
     */
    public void setAccessibilities(HashMap<LocationType, ArrayList> accessibilities) {
        this.accessibilities = accessibilities;
    }
}
