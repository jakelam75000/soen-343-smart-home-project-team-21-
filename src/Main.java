import javax.swing.*;

public class Main {

    public static void main(String[] args){
        SmartHomeDashboard.setUpLoginFrame();
        // Creates users
        UserManager.initialize();
    }
}
