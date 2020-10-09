import javax.swing.*;

public class Main {

    //Frames
    private static JFrame loginFrame = new Login("Login");
    private static JFrame dashboard;

    //Bounds variables
    private static int xPos = 300;
    private static int yPos = 200;
    private static int xPosD = 100;
    private static int yPosD = 100;
    private static int frameWidth = 400;
    private static int frameHeight = 300;
    private static int DashWidth = 1000;
    private static int DashHeight = 600;


    public static void loginClicked(String username, String password){
        // User Authentication
        User user = UserManager.findUser(username, password);
        if(user != null) {
            loginFrame.setVisible(false);

            // User type
            if(user instanceof Child) {
                System.out.println("It is a child");
                // Show house simulator for child
                dashboard = new SmartHomeDashboard("Smart Home Simulator", "Child", username);
                dashboard.setBounds(xPosD, yPosD, DashWidth, DashHeight);
                dashboard.setVisible(true);
            } else if (user instanceof Parent) {
                System.out.println("It is a parent");
                // Show house simulator for parent
                dashboard = new SmartHomeDashboard("Smart Home Simulator", "Parent", username);
                dashboard.setBounds(xPosD, yPosD, DashWidth, DashHeight);
                dashboard.setVisible(true);
            } else if (user instanceof Guest) {
                System.out.println("It is a guest");
                // Show house simulator for guest
                dashboard = new SmartHomeDashboard("Smart Home Simulator", "Guest", username);
                dashboard.setBounds(xPosD, yPosD, DashWidth, DashHeight);
                dashboard.setVisible(true);
            }
        } else {
            System.out.println("Login failed");
            //display failed login message
        }

    }

    public static void main(String[] args){

        // Creates users
        UserManager.initialize();

        //Show login frame
        loginFrame.setBounds(xPos, yPos, frameWidth, frameHeight);
        loginFrame.setVisible(true);
    }
}
