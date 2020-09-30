/*
class room, which holds the name of the room and smartobjects in that room.
@author Jake Lamothe
@version 0.1
@since 2020-09-30
 */
public class room {
    Smartobj[] smartobjects;
    String name;
    double temperature;
    double width;
    double length;
    /*
    constructor
    @param a is the list of smart objects
     @param names is the name of the room
     @param temp is the tempreture of the room
     @param wid  is the width of the room
     @param len is the length of the room
     */
    public room(Smartobj[] a, String names, double temp, double wid, double len ){
        smartobjects = a;
        name = names;
        temperature = temp;
        width = wid;
        length = len;
    }
    /*
    accesor method
     */
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        String s;
        s = "This room is called " + name + ". its tempreture is " + temperature + "c. \nits width and length are " + width
                 +"m ," + length + "m.\n";
        if (smartobjects.length == 1) s = s.concat("it has 1 smart object:\n");
        else s = s.concat("it has " + smartobjects.length +" smart objects:\n");
        for (int i =0; i<smartobjects.length; i++){
            s = s.concat(smartobjects[i].toString());
        }
        s = s.concat("\n");
        return s;
    }
    /*
    temporary mutator function
    @param desiredtemp is the new temperature of the room
     */
    public void setTemperature(double desiredtemp){
        temperature = desiredtemp;
    }
}
