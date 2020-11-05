/**
 * The main driver
 */
public class Main {

    public static void main(String[] args){

        Login loginFrame = new Login("Login");
        loginFrame.setVisible(true);

        // Creates users
        UserDatabaseManager.loadUsers();
        // Initialize accessibility corresponding strings
        Accessibility.initializeAccessibilities();

    }
}
