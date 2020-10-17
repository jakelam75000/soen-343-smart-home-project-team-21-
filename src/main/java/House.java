import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
class house, holds the house rooms, name and smartobjects by extension
@version 0.1
@since 2020-09-30
 */
public class House {
    private Room[] rooms;
    private String name;
    private HashMap<SmartObjectType, List<String>> houseItems = new HashMap<SmartObjectType, List<String>>();
    /**
    constructor
     */
    public House(Room[] listrom, String nam ){
        rooms = listrom;
        name = nam;

        for(Room room : rooms){
            for (Smartobj obj : room.getSmartObjects()) {
                if (!houseItems.containsKey(obj.type)) houseItems.put(obj.getType(), new ArrayList<String>());
                List<String> tempList = houseItems.get(obj.getType());
                tempList.add(obj.getName());
            }
        }
    }
    //shouldnt be used for now
    /**
     * a mutator function which for now shouldn't be used
     * @param newroom This is the newest single room object to be added
     */
    public void addRooms(Room newroom){
        Room[] temprooms = new Room[rooms.length+1];
        for (int i =0; i< rooms.length; i++){
            temprooms[i] = rooms[i];
        }
        temprooms[temprooms.length] = newroom;
        rooms = temprooms;
    }

    /**
    a mutator function which for now shouldn't be used
    @param listOfRooms This is the newest list of room object to be added
     */
    public void setListRooms(Room[] listOfRooms){
        rooms = listOfRooms;
    }

    /**
     * toString() override
     * @return String listing information about the house.
     */
    public String toString(){
        String s = name + " has " + rooms.length + " rooms.\n\n";
        for (int i =0; i< rooms.length; i++){
             if (rooms[i] != null) s = s.concat(rooms[i].toString());
        }
        return s;
    }

    /**
     * Meant to retrieve all the names of the rooms.
     * @return String[] a list of all the rooms named
     */
    public String[] getRoomNames(){
        String[] s = new String[rooms.length];
        for (int i=0;i< rooms.length; i++){
            s[i]=rooms[i].getName();
        }
        return s;
    }

    public int getRoomCount() {
        return rooms.length;
    }

    /**
     * Similar to getRoomNames() but returns the objects instead
     *
     * @return Room[] a list of all room objects
     */
    public Room[] getRoomsList(){
        Room[] list = new Room[rooms.length];

        for(int i=0; i<rooms.length; i++){
            list[i] = rooms[i].clone();
        }

        return list;
    }

    /**
     * returns the room at a specified index
     * @param i int index to be checked
     * @return Room returns the room specified at the index i
     */
    public Room getRoomAtIndex(int i) {
        return rooms[i];
    }

    public HashMap<SmartObjectType, List<String>> getHouseItems(){
        return (HashMap<SmartObjectType, List<String>>) houseItems.clone();
    }

    public List<SmartObjectType> getHouseItemsKeys(){
        List<SmartObjectType> keys = new ArrayList<SmartObjectType>();

        houseItems.forEach((k, v) -> keys.add(k));

        return keys;
    }

    /**
     *
     * @param key SmartObjectType the smartobject type to retrieve the names for.
     * @return returns a lsit of all objects of object type key
     */
    public List<String> getHouseItemValue(SmartObjectType key){
        return houseItems.get(key);
    }

    /**
     * (Un)Blocks a window with an arbitrary object.
     *
     * @param name String name of the window to be (un)blocked.
     * @param blocked boolean true if window must be blocked, false if it must be unblocked.
     */
    public void blockWindow(String name, boolean blocked){
        for(Room room : rooms){
            room.blockWindow(name, blocked);
        }
    }

    /**
     * Searches all the rooms for the specified object and opens/closes it.
     *
     * @param name String name of the object to be opened/closed.
     * @param open boolean true if object should be opened, false if it should be closed.
     * @return boolean true if the object was successfully opened/closed, false otherwise.
     */
    public boolean openCloseObject(String name, boolean open){
        boolean success = false;
        for(Room room : rooms){
            success = room.openCloseObject(name, open);
            if(success) return success;

        }
        return success;
    }
}
