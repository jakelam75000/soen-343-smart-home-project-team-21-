
public class Main {

    public static void main(String[] args){

        Login loginFrame = new Login("Login");
        loginFrame.setVisible(true);

        // Creates users
        UserManager.initialize();

        // This is to read house layout file submitted by user (right now it is hardcoded to HouseLayout.txt
        //HouseReader.readHouseLayout("Houselayout.txt");
    }
}
