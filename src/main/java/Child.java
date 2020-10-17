/**
 * Child class of User class
 */
public class Child extends User{

    /**
     *constructor that calls the parent User's constructor
     * @param username String the username of the user
     * @param password String the password of the user
     */
    public Child(String username, String password) {
        super(username, password);
    }
}
