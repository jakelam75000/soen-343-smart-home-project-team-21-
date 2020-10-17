import javax.xml.stream.Location;

/**
 * Parent class of all user types
 */
public class User {
    private String username;
    private String password;
    private String location;

    /**
     * Constructor
     * @param username String the name of the account
     * @param password String the apssword
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.location = LocationType.OUTSIDE.toString();
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
}
