import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static HashMap<String, String> authenticate = new HashMap<String, String>();
    private static HashMap<String, Parent> userParent = new HashMap<String, Parent>();
    private static HashMap<String, Child> userChild = new HashMap<String, Child>();
    private static HashMap<String, Guest> userGuest = new HashMap<String, Guest>();

    public static boolean isUserValid(String username, String password) {
        return authenticate.get(username) != null && authenticate.get(username).equals(password);
    }

    public static User findUser(String username, String password) {
        if(isUserValid(username, password)) {
            if(userParent.get(username) != null) { return userParent.get(username);}
            if(userChild.get(username) != null) { return userChild.get(username);}
            if(userGuest.get(username) != null) { return userGuest.get(username);}
            return null;
        }
        // User it not valid
        return null;
    }

    public static void addUser(String username, String password, String type) {
        if(authenticate.get(username) == null) {
            authenticate.put(username, password);
            if(type.equals(UserTypes.PARENT.toString())) {
                userParent.put(username, new Parent(username, password));
            } else if(type.equals(UserTypes.CHILD.toString())) {
                userChild.put(username, new Child(username, password));
            } else if(type.equals(UserTypes.GUEST.toString())){
                userGuest.put(username, new Guest(username, password));
            }
            System.out.println("Successfully added");
        } else {
            System.out.println("Username already exists");
        }

    }

    public static void removeUser(String username) {
        if(authenticate.get(username) != null) {
            if(userParent.get(username) != null) {
                userParent.remove(username);
            } else if(userChild.get(username) != null) {
                userChild.remove(username);
            } else if(userGuest.get(username) != null) {
                userGuest.remove(username);
            }
            System.out.println("Successfully removed");
        }
        else {
            System.out.println("User doesn't exist");
        }
    }

    public static void initialize() {
        addUser("Parent1", "passwordabc", UserTypes.PARENT.toString());
        addUser("Parent2", "password123", UserTypes.PARENT.toString());
        addUser("Child1", "abc", UserTypes.CHILD.toString());
        addUser("Child2", "123", UserTypes.CHILD.toString());
        addUser("Guest", "password", UserTypes.GUEST.toString());
    }

    public static HashMap<String, String> getAuthenticate() {
        return authenticate;
    }

    public static void setAuthenticate(HashMap<String, String> authenticate) {
        UserManager.authenticate = authenticate;
    }

    public static HashMap<String, Parent> getUserParent() {
        return userParent;
    }

    public static void setUserParent(HashMap<String, Parent> userParent) {
        UserManager.userParent = userParent;
    }

    public static HashMap<String, Child> getUserChild() {
        return userChild;
    }

    public static void setUserChild(HashMap<String, Child> userChild) {
        UserManager.userChild = userChild;
    }

    public static HashMap<String, Guest> getUserGuest() {
        return userGuest;
    }

    public static void setUserGuest(HashMap<String, Guest> userGuest) {
        UserManager.userGuest = userGuest;
    }
}
