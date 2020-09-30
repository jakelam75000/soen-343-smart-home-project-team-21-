import java.util.Arrays;

public class room {
    Smartobj[] smartobjects;
    String name;
    double tempreture;
    double width;
    double length;
    public room(Smartobj[] a, String names, double temp, double wid, double len ){
        smartobjects = a;
        name = names;
        tempreture = temp;
        width = wid;
        length = len;
    }
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        String s;
        s = "This room is called " + name + ". its tempreture is " + tempreture + "c. \nits width and length are " + width
                 +"m ," + length + "m.\n";
        if (smartobjects.length == 1) s = s.concat("it has 1 smart object:\n");
        else s = s.concat("it has " + smartobjects.length +" smart objects:\n");
        for (int i =0; i<smartobjects.length; i++){
            s = s.concat(smartobjects[i].toString());
        }
        s = s.concat("\n");
        return s;
    }
}
