import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * a manager class that stores and manipulates all zone objects
 */
public class ZoneManager {

    private static HashMap<String,Zone> zones = new HashMap();
    //List rooms and their corresponding zone
    private static HashMap<String,String> roomToZone =new HashMap<>();
    private static House house;

    //This is for the create zone form

    /**
     * prepares the dropdown for adding rooms to zone
     * @param roomNameCombo the combo box that is having elements added to it
     * @param roomNames the list of all rooms as strings
     */
    public static void setRoomNameDropdown(JComboBox roomNameCombo, String[] roomNames) {
        for(String room : roomNames) {
            if(room.contains(LocationType.STOOP.name()) || roomToZone.get(room) != null) {
                continue;
            }
            roomNameCombo.addItem(room);
        }
    }

    /**
     * prepares the combobox with all the zone names
     * @param zoneNameCombo the combobox that gets updated
     */
    public static void setZoneDropdown(JComboBox zoneNameCombo) {
        zoneNameCombo.removeAllItems();
        for(String zone : zones.keySet()) {
            zoneNameCombo.addItem(zone);
        }
    }

    /**
     * similar to setRoomNameDropdown except this method is for the window in shh whereas the other is meant for the create zone frame
     * @param zoneName the name of the zone
     * @param listOfRooms the combobox to be updated
     * @param addedRoomsList the combobox to be updated
     * @param roomNames the list of all room names
     */
    public static void setRoomDropdown(String zoneName, JComboBox listOfRooms, JComboBox addedRoomsList, String[] roomNames) {
        listOfRooms.removeAllItems();
        addedRoomsList.removeAllItems();
        Zone zone = zones.get(zoneName);
        ArrayList<Room> roomsForAddedRoomList = zone.getRooms();

        for(Room room : roomsForAddedRoomList) {
            addedRoomsList.addItem(room.getName());
        }

        for(String roomName : roomNames) {
            if(roomToZone.get(roomName) == null && !roomName.contains(LocationType.STOOP.name())) {
                listOfRooms.addItem(roomName);
            }
        }
    }

    /**
     *used in create zone to create a new zone
     * @param rooms arraylsit of rooms
     * @param name name of the new zone
     * @param addedRoomNameCombo the combobox where the selected rooms to add are
     * @return boolean if it was possible or not
     */
    public static boolean createZone(Room[] rooms, String name, JComboBox addedRoomNameCombo) {
        if(zones.get(name) != null) {
            return false;
        }
        Zone zone = new Zone(name, 22, createRoomsArrayForZone(addedRoomNameCombo, rooms, name));
        zones.put(name,zone);
        return true;
    }

    /**
     *used in zone manager to turn the combobox rooms into actual room objects
     * @param addedRooms the combobox of rooms to be added
     * @param rooms the array of room objects
     * @param name the name of the zone
     * @return teh arraylist of rooms
     */
    private static ArrayList<Room> createRoomsArrayForZone(JComboBox addedRooms, Room[] rooms, String name) {
        ArrayList<Room> roomsForZone = new ArrayList<>();
        for(int i = 0; i < addedRooms.getItemCount(); i++) {
            for(int j = 0; j < rooms.length;j++) {
                if(rooms[j].getName().equals(addedRooms.getItemAt(i).toString())) {
                    roomsForZone.add(rooms[j]);
                    roomToZone.put(rooms[j].getName(), name);
                }
            }
        }
        return roomsForZone;
    }

    /**
     * method to manipulate individual rooms from the zones
     * @param roomToBeMoved the room object
     * @param removeRoom the combobox where the room is selected
     * @param addRoom the comboboc where the room is selected
     * @return
     */
    public static boolean removeOrAddRoom(Object roomToBeMoved, JComboBox removeRoom, JComboBox addRoom) {
        if(removeRoom.getItemCount() == 0) {
            return false;
        };
        removeRoom.removeItem(roomToBeMoved);
        addRoom.addItem(roomToBeMoved);
        return true;
    }

    /**
     *saves the modifications of the zone to the related zone object
     * @param rooms the array of all room objects in house
     * @param zoneName the name of the zone
     * @param listOfRooms combobox that holds the list of all rooms combobox (minus added rooms and stoops)
     * @param addedRoomsList combobox that holds the list of all rooms to be added
     */
    public static void modifyZone(Room[] rooms, String zoneName, JComboBox listOfRooms, JComboBox addedRoomsList) {
        Zone zone = zones.get(zoneName);
        zone.setRooms(createRoomsArrayForZone(addedRoomsList, rooms, zoneName));
        removeRooms(listOfRooms,zoneName);
    }

    /**
     *removes rooms (identifies by the listofrooms combobox) from the zone (identified by zone name)
     * @param listOfRooms combobox that holds the list of all rooms (minus added rooms and stoops)
     * @param zoneName the name of the zone to have the rooms removed
     */
    public static void removeRooms(JComboBox listOfRooms,String zoneName) {
        for(int i = 0; i < listOfRooms.getItemCount(); i++) {
            if(null != roomToZone.get(listOfRooms.getItemAt(i).toString()) && roomToZone.get(listOfRooms.getItemAt(i).toString()).equals(zoneName)) {
                roomToZone.remove(listOfRooms.getItemAt(i).toString());
            }
        }
    }

    /**
     * returns the zone size
     * @return int zone size
     */
    public static int getZoneSize() {
        return zones.size();
    }

    /**
     * returns all the zones as an array list
     * @return array list of Zones
     */
    public static ArrayList<Zone> getZoneList(){
        ArrayList<Zone> zoneList = new ArrayList<Zone>();

        zones.forEach((k, v) -> zoneList.add(v.clone()));

        return zoneList;
    }

    /**
     * sets the deisred tempreture for a zone at a given period of the day
     * @param zoneName the name of the zone to be updated
     * @param period the time of day
     * @param temperature the new desired temp
     */
    public static void setZoneTemp(String zoneName,PeriodsOfDay period, double temperature){
        for(String name : zones.keySet()){
            if(name.equals(zoneName)){
                zones.get(name).setDesiredTemperature(period, temperature);
                return;
            }
        }
    }

    /**
     * updates all the rooms desired temperatures to the current zone values at the given period
     * @param period the period of the day
     */
    public static void updateDesiredTempPeriod(PeriodsOfDay period){
        for (Zone zone:zones.values()) { zone.updatedesiredTempPeriod(period); }
    }

    /**
     * adds an overrridden room to the zones (temporarily ignores change
     * @param roomName the name of the room thats being overwritten
     * @param zoneName the name of the zone that contains the room
     */
    public static void addOverridden(String roomName, String zoneName){
        zones.get(zoneName).addOverridden(roomName);
    }

    /**
     * clears the overridden rooms in a given zone
     * @param zoneName the zone to have its overridden list cleared
     */
    public static void clearOverridden(String zoneName){
        zones.get(zoneName).clearOverridden();
    }

    /**
     * setter method
     * @param h sets the house to h
     *
     */
    public static void setHouse(House h){
        house = h;

    }


}
