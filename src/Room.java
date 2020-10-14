import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
class room, which holds the name of the room and smartobjects in that room.
@author Jake Lamothe
@version 0.1
@since 2020-09-30
 */
public class Room {
    Smartobj[] smartobjects;
    HashMap<SmartObjectType, List<String>> itemMap = new HashMap<SmartObjectType, List<String>>();
    String name;
    double temperature;
    double width;
    double length;

    /**
    constructor
    @param a is the list of smart objects
     @param names is the name of the room
     @param temp is the tempreture of the room
     @param wid  is the width of the room
     @param len is the length of the room
     */
    public Room(Smartobj[] a, String names, double temp, double wid, double len ){
        smartobjects = a;
        name = names;
        temperature = temp;
        width = wid;
        length = len;

        for(Smartobj obj : smartobjects) {
            if(!itemMap.containsKey(obj.type)) itemMap.put(obj.getType(), new ArrayList<String>());
            List<String> tempList = itemMap.get(obj.getType());
            tempList.add(obj.getName());
        }
    }

    public Room(Room room){
        this.name = room.name;
        this.temperature = room.temperature;
        this.width = room.width;
        this.length = room.length;

        this.smartobjects = new Smartobj[room.smartobjects.length];
        for(int i=0; i<smartobjects.length; i++){
            this.smartobjects[i] = room.smartobjects[i].clone();
        }

        for(Smartobj obj : this.smartobjects) {
            if (!this.itemMap.containsKey(obj.type)) this.itemMap.put(obj.getType(), new ArrayList<String>());
            List<String> tempList = this.itemMap.get(obj.getType());
            tempList.add(obj.getName());
        }
    }

    /**
     * Accessor for name attribute
     *
     * @return String
     */
    public String getName(){
        return name;
    }

    /**
     * Accessor method for smartObjects
     *
     * @return Smartobj[] list of all smartObjects in room.
     */
    public Smartobj[] getSmartObjects(){
        Smartobj[] objects = new Smartobj[smartobjects.length];

        for(int i=0; i<objects.length; i++){
            objects[i] = smartobjects[i].clone();
        }
        return objects;
    }

    /**
     * Accessor method that return the itemMap hashmap
     *
     * @return HashMap a clone of itemMap.
     */
    public HashMap<SmartObjectType, String> getItemMap(){
        return (HashMap<SmartObjectType, String>)itemMap.clone();
    }

    /**
     * Accessor method that return all the key to ItemMap hashmap.
     * @return List<SmartObjectType> a list of all the keys to the ItemMap hashmap
     */
    public List<SmartObjectType> getItemMapKeys(){
        List<SmartObjectType> keys = new ArrayList<SmartObjectType>();

        itemMap.forEach((k, v) -> keys.add(k));

        return keys;
    }

    /**
     * Accessor method that returns the List of Strings corresponding to the SmartObjectType in the room.
     *
     * @param key SmartObjectType that is the key of the itemMap hashmap with all smartobjects
     * @return List<String> names of all the smartobjects of type key.
     */
    public List<String> getItemMapValue(SmartObjectType key){
        List<String> value = itemMap.get(key);
        return value;
    }


    public String[] getItemNames(){
        String[] names = new String[smartobjects.length];

        for(int i=0; i<names.length; i++){
            names[i] = smartobjects[i].getName();
        }

        return names;
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

    /**
     * Methods that can block and unblock a given window.
     * @param name String name of the window to be blocked/unblocked
     * @param blocked Boolean value that determines whether the window should be blocked or unblocked
     */
    public void blockWindow(String name, boolean blocked){
        for(Smartobj obj : smartobjects){
            if(obj.getName().equalsIgnoreCase(name) && obj.getType() == SmartObjectType.WINDOW){
                Window window = (Window)obj;
                window.setblocked(blocked);
            }
        }
    }
    public Room clone(){
        return new Room(this);
    }

}
