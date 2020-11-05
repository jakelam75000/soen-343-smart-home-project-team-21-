import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserDatabaseManager {

    /**
     * Loads all the users from an external .txt file.
     *
     * Sets up usernames, passwords, type and accessibilities. Also assigns an admin account.
     */
    public static void loadUsers(){
        try {
            Scanner sc = new Scanner(new File("Users.txt"));
            //Getting rid of the line with the format example and the other useless lines
            sc.nextLine();
            sc.nextLine();

            String adminUsername = sc.nextLine().split("=")[1];
            sc.nextLine();

            while(sc.hasNextLine()){
                String userLine = sc.nextLine();
                String[] userSplit = userLine.split("/");
                String username = userSplit[0];
                String password = userSplit[1];
                UserTypes type = UserTypes.valueOf(userSplit[2]);


                String[] accesses = userSplit[3].split(";");

                UserManager.addUser(username, password, type);

                User user = UserManager.findExistingUser(username);

                if(username.equals(adminUsername)) UserManager.setAdmin(user);

                HashMap<LocationType, ArrayList<AccessibilityType>> userAccessHash = user.getAccessibilities();

                for(String locAcc : accesses){
                    String[] loc_Acc = locAcc.split(":");
                    if(loc_Acc.length >= 2) {
                        for (String accessType : loc_Acc[1].split(",")) {
                            ArrayList<AccessibilityType> arrList = userAccessHash.get(LocationType.valueOf(loc_Acc[0]));
                            AccessibilityType accType = AccessibilityType.valueOf(accessType);

                            if(!arrList.contains(accType)) arrList.add(accType);
                        }
                    }
                }

            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the user database file with all the new user information.
     */
    public static void updateUserFile(){
        try{
            String fileName = "Users.txt";
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));

            //Printing the usual formatting and other information to the Users file.
            pw.println("Format of file -> Username/Password/Type/LocationA:Access1,Access2,Access3;LocationB:Access1, Access2");
            pw.println("The admin is the first user in the file");
            pw.println("");

            pw.println("Admin=" + UserManager.getAdminUsername());
            pw.println("");

            //Loop to print every user info to the file.
            String userInfo = "";
            for(String username : UserManager.getUsernames()) {
                userInfo += username + "/";
                userInfo += UserManager.getUserPassword(username) + "/";
                userInfo += UserManager.getUserType(username) + "/";

                User user = UserManager.findExistingUser(username);
                String access = "";
                HashMap<LocationType, ArrayList<AccessibilityType>> userAccess = user.getAccessibilities();
                for(LocationType location : userAccess.keySet()){
                    access += location + ":";
                    for(AccessibilityType accessibility : userAccess.get(location)){
                        access += accessibility + ",";
                    }
                    access += ";";
                }

                userInfo += access;
                pw.println(userInfo);
                userInfo = "";
            }

            pw.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


}
