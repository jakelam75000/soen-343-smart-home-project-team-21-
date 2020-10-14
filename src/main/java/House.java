import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
class house, holds the house rooms, name and smartobjects by extension
@author Jake Lamothe
@version 0.1
@since 2020-09-30
 */
public class House {
    Room[] rooms;
    String name;
    private HashMap<SmartObjectType, List<String>> houseItems = new HashMap<SmartObjectType, List<String>>();
    /*
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
    a mutator function which for now shouldn't be used
    @param newroom This is the newest single room object to be added
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
    to string override
     */
    public String toString(){
        String s = name + " has " + rooms.length + " rooms.\n\n";
        for (int i =0; i< rooms.length; i++){
             if (rooms[i] != null) s = s.concat(rooms[i].toString());
        }
        return s;
    }
    public String[] getRoomNames(){
        String[] s = new String[rooms.length];
        for (int i=0;i< rooms.length; i++){
            s[i]=rooms[i].getName();
        }
        return s;
    }

    public Room[] getRoomsList(){
        Room[] list = new Room[rooms.length];

        for(int i=0; i<rooms.length; i++){
            list[i] = rooms[i].clone();
        }

        return list;
    }

    public HashMap<SmartObjectType, String> getHouseItems(){
        return (HashMap<SmartObjectType, String>)houseItems.clone();
    }

    public List<SmartObjectType> getHouseItemsKeys(){
        List<SmartObjectType> keys = new ArrayList<SmartObjectType>();

        houseItems.forEach((k, v) -> keys.add(k));

        return keys;
    }

    public List<String> getHouseItemValue(SmartObjectType key){
        return houseItems.get(key);
    }

    public void blockWindow(String name, boolean blocked){
        for(Room room : rooms){
            room.blockWindow(name, blocked);
        }
    }
}
