import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HouseReader {
    public static void main(String[] args) {
        //try, catch and lines 12,13,14,15,18-20 were taken from https://www.w3schools.com/java/java_files_read.asp
        house mainhouse;
        room[] listofrooms;
        Smartobj[] smartobjByRoom;
        //temporary string for holding return values
        String s = null;
        String temphousename = null;
        int tempnumrooms = 0;
        String temproomname = null;
        double temproomwidth = 0.0;
        double temproomtempreture = 0.0;
        double temproomlength = 0.0;
        int tempnumsmartobj;
        String smartobjType = null;
        String smartobjName = null;
        int smartobjtypenum = 0;
        try {
            File houseFile = new File("Houselayout.txt");
            Scanner lineReader = new Scanner(houseFile);
            if (lineReader.hasNextLine()){
                s = lineReader.nextLine();
                s = s.substring(s.indexOf(':')+2);
                temphousename = s;
                s = lineReader.nextLine();
                s = s.substring(s.indexOf(':')+2);
                tempnumrooms = Integer.parseInt(s);
                listofrooms = new room[tempnumrooms];
                for (int i= 0; i < tempnumrooms; i++){
                    s = lineReader.nextLine();
                    temproomname = s.substring(s.indexOf(':')+2);
                    s = lineReader.nextLine();
                    temproomtempreture = Double.parseDouble(s.substring(s.indexOf(':')+2));
                    s = lineReader.nextLine();
                    temproomwidth = Double.parseDouble(s.substring(s.indexOf(':')+2));
                    s = lineReader.nextLine();
                    temproomlength = Double.parseDouble(s.substring(s.indexOf(':')+2));
                    s = lineReader.nextLine();
                    tempnumsmartobj = Integer.parseInt(s.substring(s.indexOf(':')+2));
                    smartobjByRoom = new Smartobj[tempnumsmartobj];
                    for (int j = 0; j< tempnumsmartobj; j++){
                        s = lineReader.nextLine();
                        smartobjType = s.substring(s.indexOf(':')+2);
                        s = lineReader.nextLine();
                        smartobjName = s.substring(s.indexOf(':')+2);
                        //to be exapnded with more types and if statements
                        if (smartobjType.equalsIgnoreCase("window")) smartobjtypenum = 1;
                        switch(smartobjtypenum){
                            case 1:
                                smartobjByRoom[j] = new window(smartobjName);
                                break;
                            default:
                                System.out.println("an error has occured in assigning the type of the smart object " + smartobjName );
                        }
                        smartobjtypenum = 0;
                    }
                    listofrooms[i] = new room(smartobjByRoom,temproomname,temproomtempreture,temproomwidth,temproomlength);
                }
                mainhouse = new house(listofrooms,temphousename);
                System.out.println(mainhouse);
            }

            lineReader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("an error has occured, file may be missing or misspelled");
            e.printStackTrace();
            return ;
        }
    }
}



