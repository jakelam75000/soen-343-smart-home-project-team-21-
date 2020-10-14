import java.io.File;

public class Main {

    public static void main(String[] args){

        SmartHomeDashboard.setUpLoginFrame();
        // Creates users
        UserManager.initialize();

        // This is to read house layout file submitted by user (right now it is hardcoded to HouseLayout.txt
        //HouseReader.readHouseLayout("Houselayout.txt");
    }
}
