import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*
temporary main driver that reads a house layout file (no error checking) and puts the information into a house class, its corresponding room arrays and
the rooms corresponding smartobj array

will eventually be convered to a function call like public house HouseReader ()
@author Jake Lamothe
@version 0.1
@since 2020-09-30
 */
public class HouseReader {
    public static void main(String[] args) {
        //try, catch and lines 12,13,14,15,18-20 were taken from https://www.w3schools.com/java/java_files_read.asp
        //these variables are storages for the objects
        house mainhouse;
        room[] listofrooms;
        Smartobj[] smartobjByRoom;
        //temporary string for holding return values
        String s = null;
        //temp variables for storing values to be sent to constructors
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
                        //to be exapanded with more types and if statements
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



