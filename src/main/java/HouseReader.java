import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*
reads a house layout file (no error checking) and puts the information into a house class, its corresponding room arrays and
the rooms corresponding smartobj array

will eventually be convered to a function call like public house HouseReader ()
@author Jake Lamothe
@version 0.1
@since 2020-09-30
 */
public class HouseReader {

    private static house mainhouse;
    private static room[] listofrooms;
    private static Smartobj[] smartobjByRoom;

    private static File houseFile;
    private static Scanner lineReader;

    public static void readHouseLayout(String path) {
        try {
            houseFile = new File(path);
            lineReader = new Scanner(houseFile);

            //temporary string for holding return values
            String s = null;
            //temp variables for storing values to be sent to constructors
            String temphousename = null;

            if (lineReader.hasNextLine()){
                s = lineReader.nextLine();
                s = s.substring(s.indexOf(':')+2);
                temphousename = s;
                s = lineReader.nextLine();
                s = s.substring(s.indexOf(':')+2);

                listofrooms = new room[Integer.parseInt(s)];

                // Variables needed for outer for loop
                String temproomname;
                double temproomtempreture;
                double temproomwidth;
                double temproomlength;

                // Variables needed for inner for loop
                String smartobjType;
                String smartobjName;

                for (int i= 0; i < listofrooms.length; i++){
                    s = lineReader.nextLine();
                    temproomname = s.substring(s.indexOf(':')+2);
                    s = lineReader.nextLine();
                    temproomtempreture = Double.parseDouble(s.substring(s.indexOf(':')+2));
                    s = lineReader.nextLine();
                    temproomwidth = Double.parseDouble(s.substring(s.indexOf(':')+2));
                    s = lineReader.nextLine();
                    temproomlength = Double.parseDouble(s.substring(s.indexOf(':')+2));
                    s = lineReader.nextLine();

                    smartobjByRoom = new Smartobj[Integer.parseInt(s.substring(s.indexOf(':')+2))];

                    for (int j = 0; j< smartobjByRoom.length; j++){
                        s = lineReader.nextLine();
                        smartobjType = s.substring(s.indexOf(':')+2);
                        s = lineReader.nextLine();
                        smartobjName = s.substring(s.indexOf(':')+2);
                        //to be exapanded with more types and if statements
                        smartobjByRoom[j] = smartobjType.equalsIgnoreCase("window")? new window(smartobjName) : null;

                        if(smartobjByRoom == null) {
                            System.out.println("an error has occured in assigning the type of the smart object " + smartobjName );
                        }
                    }
                    listofrooms[i] = new room(smartobjByRoom,temproomname,temproomtempreture,temproomwidth,temproomlength);
                }
                mainhouse = new house(listofrooms,temphousename);
                System.out.println(mainhouse);
            }

            lineReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("an error has occured, file may be missing or misspelled");
            e.printStackTrace();
            return;
        }
    }
}



