import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The class that handles user maintenance and interaction
 */
public class UserManager {
    private static HashMap<String, String> authenticate = new HashMap<String, String>();
    private static HashMap<String, Parent> userParent = new HashMap<String, Parent>();
    private static HashMap<String, Child> userChild = new HashMap<String, Child>();
    private static HashMap<String, Guest> userGuest = new HashMap<String, Guest>();
    private static HashMap<String, Stranger> userStranger = new HashMap<String, Stranger>();
    private static User admin;

    /**
     * Checks if username/password combination is valid.
     *
     * @param username String username to be checked
     * @param password String password to be checked
     * @return boolean true if authentication is successfu, false otherwise.
     */
    public static boolean isUserValid(String username, String password) {
        return authenticate.get(username) != null && authenticate.get(username).equals(password);
    }

    /**
     * Returns the User object of a specific user.
     *
     * @param username String username to search
     * @param password String password linked to user
     * @return User pointer to the User object matching the username and password or null otherwise.
     */
    public static User findUser(String username, String password) {
        if(isUserValid(username, password)) {
            if(userParent.get(username) != null) { return userParent.get(username);}
            if(userChild.get(username) != null) { return userChild.get(username);}
            if(userGuest.get(username) != null) { return userGuest.get(username);}
            if(userStranger.get(username) != null) { return userStranger.get(username);}
            return null;
        }
        // User it not valid
        return null;
    }

    /**
     * Returns the User object of a specific user without checking password.
     *
     * @param username String username to search
     * @return User pointer to the User object matching the username and password or null otherwise.
     */
    public static User findExistingUser(String username) {
        if(userParent.get(username) != null) { return userParent.get(username);}
        if(userChild.get(username) != null) { return userChild.get(username);}
        if(userGuest.get(username) != null) { return userGuest.get(username);}
        if(userStranger.get(username) != null) { return userStranger.get(username);}
        return null;
    }

    /**
     * Updates the location of a user.
     *
     * @param username String username of user to be updated
     * @param location String new location of the user
     */
    public static void changeUserLocation(String username, String location) {
        if(userParent.get(username) != null) {
            userParent.get(username).setLocation(location);
        }
        else if(userChild.get(username) != null) {
            userChild.get(username).setLocation(location);
        }
        else if(userGuest.get(username) != null) {
            userGuest.get(username).setLocation(location);
        }
        else if(userStranger.get(username) != null) {
            userStranger.get(username).setLocation(location);
        }
    }

    /**
     * Adds a new user.
     *
     * @param username String username of user to be added
     * @param password String password of user to be added
     * @param type String type of user to be added
     */
    public static boolean addUser(String username, String password, UserTypes type) {
        if (authenticate.get(username) != null){
            return false;
        }

        // Add user to the hashmap of its respective type
        if(type == UserTypes.PARENT) {
            if(userParent.size() >= 2) return false;
            userParent.put(username, new Parent(username, password));
        } else if(type == UserTypes.CHILD) {
            userChild.put(username, new Child(username, password));
        } else if(type == UserTypes.GUEST){
            userGuest.put(username, new Guest(username, password));
        }
        else if (type == UserTypes.STRANGER){
            userStranger.put(username, new Stranger(username));
        }

        // Add user to the authenticate hashmap
        if (type != UserTypes.STRANGER) {
            authenticate.put(username, password);
        } else {
            authenticate.put(username, "null");
        }

        return true;
    }

    /**
     * Deletes a user.
     *
     * @param username String username to be deleted
     */
    public static boolean removeUser(String username, String password) {
        if(getUserType(username) == UserTypes.STRANGER){
            userStranger.remove(username);
            authenticate.remove(username);
            return true;
        }
        if (isAdmin(username) || !isUserValid(username, password)){
            return false;
        }
        authenticate.remove(username);
        if(userParent.get(username) != null) {
            userParent.remove(username);
        } else if(userChild.get(username) != null) {
            userChild.remove(username);
        } else if(userGuest.get(username) != null) {
            userGuest.remove(username);
        }

        return true;
    }

    /**
     * Checks if the user is the admin user or not.
     *
     * @param username Username of the user to be checked.
     * @return true if the user is the admin; false otherwise.
     */
    public static boolean isAdmin(String username) {
        return admin == findUser(username, authenticate.get(username));
    }

    /**
     * Edits a user's information.
     *
     * @param username Username of the user to be edited.
     * @param oldPassword the old password of the user to be edited.
     * @param newPassword the new password of the user to be edited.
     * @param type the type the user will be set to.
     * @return true if the operation was successful; false otherwise.
     */
    public static boolean editUser(String username, String oldPassword, String newPassword, UserTypes type) {
        if (!isUserValid(username, oldPassword)) {
            return false;
        }
        //Checking if we are converting and account to parent user.
        if(!(findUser(username, oldPassword) instanceof Parent) && type == UserTypes.PARENT){
            //Checking if it is possible to add a new parent user.
            if(userParent.size() >= 2) return false;
        }

        return removeUser(username, oldPassword) && addUser(username, newPassword, type);
    }

    /**
     * Returns all usernames.
     *
     * @return String[] containing all usernames.
     */
    public static String[] getUsernames() {
        return authenticate.keySet().toArray(new String[authenticate.size()]);
    }

    /**
     * Deletes all users
     */
    public static void clearUsers() {
        authenticate.clear();
        userParent.clear();
        userChild.clear();
        userGuest.clear();
        userStranger.clear();
    }

    /**
     * Returns the count of all users.
     *
     * @return int total number of users
     */
    public static int sizeAllUsers() {
        return authenticate.size();
    }

    /**
     * Returns the count of parent users.
     *
     * @return int number of parent users
     */
    public static int sizeUserParent() {
        return userParent.size();
    }

    /**
     * Returns the count of child users.
     *
     * @return int number of child users
     */
    public static int sizeUserChild() {
        return userChild.size();
    }

    /**
     * Returns the count of guest users.
     *
     * @return int number of guest users
     */
    public static int sizeUserGuest() {
        return userGuest.size();
    }

    /**
     * Returns the count of stranger users.
     *
     * @return int number of stranger users
     */
    public static int sizeUserStranger(){
        return userStranger.size();
    }

    /**
     * Returns the location of the passed username.
     *
     * @param username String username of user
     * @return String the location of the passed username.
     */
    public static String getUserLocation(String username){
        if(userParent.get(username) != null) {
            return userParent.get(username).getLocation();
        }
        else if(userChild.get(username) != null) {
            return userChild.get(username).getLocation();
        }
        else if(userGuest.get(username) != null) {
            return userGuest.get(username).getLocation();
        }
        else if(userStranger.get(username) != null) {
            return userStranger.get(username).getLocation();
        }
        return "Outside";
    }

    /**
     * Returns the type of the given username.
     *
     * @param username username of the user who's type is wanted.
     * @return The type of the user corresponding to the given username.
     */
    public static UserTypes getUserType(String username){
        if(userParent.get(username) != null) {
            return UserTypes.PARENT;
        }
        else if(userChild.get(username) != null) {
            return UserTypes.CHILD;
        }
        else if(userGuest.get(username) != null) {
            return UserTypes.GUEST;
        }
        else if(userStranger.get(username) != null) {
            return UserTypes.STRANGER;
        }

        return null;
    }

    /**
     * Getter for admin username.
     *
     * @return the username of the admin account
     */
    public static String getAdminUsername(){
        return admin.getUsername();
    }

    /**
     * Setter for the admin attribute.
     *
     * @param user User object that will be set as the admin account.
     */
    public static void setAdmin(User user){
        admin = user;
    }

    /**
     * Returns the password of a specific user.
     *
     * @param username String username of the user.
     * @return String password of the user.
     */
    public static String getUserPassword(String username){
        return findExistingUser(username).getPassword();
    }

}
