import java.util.HashMap;

public class UserManager {
    private static HashMap<String, String> authenticate = new HashMap<String, String>();
    private static HashMap<String, User> user = new HashMap<String, User>();

    public static boolean isUserValid(String username, String password) {
        return authenticate.get(username) != null && authenticate.get(username).equals(password);
    }

    public static User findUser(String username, String password) {
        if(isUserValid(username, password)) {
            return user.get(username);
        }
        // User it not valid
        return null;
    }

    public static void addUser(String username, String password) {
        if(user.get(username) == null && authenticate.get(username) == null) {
            authenticate.put(username, password);
            // Usernames that start with "P" have parent authorizations
            if(username.charAt(0) == 'P') {
                user.put(username, new Parent(username, password));
            } else {
                user.put(username, new Child(username, password));
            }
            System.out.println("Successfully added");
        } else {
            System.out.println("User already exists");
        }

    }

    public static void removeUser(String username) {
        if(user.get(username) != null && authenticate.get(username) != null) {
            user.remove(username);
            authenticate.remove(username);
            System.out.println("Successfully removed");
        } else {
            System.out.println("User doesn't exist");
        }
    }

    public static void initialUserSetup() {
        addUser("Parent1", "passwordabc");
        addUser("Parent2", "password123");
        addUser("Child1", "abc");
        addUser("Child2", "123");
    }

    public static HashMap<String, String> getAuthenticate() {
        return authenticate;
    }

    public static void setAuthenticate(HashMap<String, String> authenticate) {
        UserManager.authenticate = authenticate;
    }

    public static HashMap<String, User> getUser() {
        return user;
    }

    public static void setUser(HashMap<String, User> user) {
        UserManager.user = user;
    }
}
