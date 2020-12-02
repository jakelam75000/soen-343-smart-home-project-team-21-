import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ZoneManager {

    private static HashMap<String,Zone> zones = new HashMap();
    //List rooms and their corresponding zone
    private static HashMap<String,String> roomToZone =new HashMap<>();

    //This is for the create zone form
    public static void setRoomNameDropdown(JComboBox roomNameCombo, String[] roomNames) {
        for(String room : roomNames) {
            if(room.contains(LocationType.STOOP.name()) || roomToZone.get(room) != null) {
                continue;
            }
            roomNameCombo.addItem(room);
        }
    }

    public static void setZoneDropdown(JComboBox zoneNameCombo) {
        zoneNameCombo.removeAllItems();
        for(String zone : zones.keySet()) {
            zoneNameCombo.addItem(zone);
        }
    }

    public static void setRoomDropdown(String zoneName, JComboBox listOfRooms, JComboBox addedRoomsList, String[] roomNames) {
        listOfRooms.removeAllItems();
        addedRoomsList.removeAllItems();
        Zone zone = zones.get(zoneName);
        ArrayList<Room> roomsForAddedRoomList = zone.getRooms();

        for(Room room : roomsForAddedRoomList) {
            addedRoomsList.addItem(room.getName());
        }

        for(String roomName : roomNames) {
            Boolean notAdded = true;
            for(Room room : roomsForAddedRoomList) {
                if(roomName.equals(room.getName()) || roomName.contains(LocationType.STOOP.name()) ||
                        (roomToZone.get(roomName) != null && !zoneName.equals(roomToZone.get(roomName)))) {
                    notAdded = false;
                    break;
                }
            }
            if(notAdded) {
                // have to check again
                if(!roomName.contains(LocationType.STOOP.name())) {
                    listOfRooms.addItem(roomName);
                }
            }
        }
    }

    public static boolean createZone(Room[] rooms, String name, JComboBox addedRoomNameCombo) {
        if(zones.get(name) != null) {
            return false;
        }
        Zone zone = new Zone(name, 0, createRoomsArrayForZone(addedRoomNameCombo, rooms, name));
        zones.put(name,zone);
        return true;
    }

    private static ArrayList<Room> createRoomsArrayForZone(JComboBox addedRooms, Room[] rooms, String name) {
        ArrayList<Room> roomsForZone = new ArrayList<>();
        for(int i = 0; i < addedRooms.getItemCount(); i++) {
            for(int j = 0; j < rooms.length;j++) {
                if(rooms[j].getName().equals(addedRooms.getItemAt(i).toString())) {
                    roomsForZone.add(rooms[j]);
                    roomToZone.put(rooms[j].getName(),name);
                }
            }
        }
        return roomsForZone;
    }

    public static boolean removeOrAddRoom(Object roomToBeMoved, JComboBox removeRoom, JComboBox addRoom) {
        if(removeRoom.getItemCount() == 0) {
            return false;
        };
        removeRoom.removeItem(roomToBeMoved);
        addRoom.addItem(roomToBeMoved);
        return true;
    }

    public static void modifyZone(Room[] rooms, String zoneName, JComboBox listOfRooms, JComboBox addedRoomsList) {
        Zone zone = zones.get(zoneName);
        zone.setRooms(createRoomsArrayForZone(addedRoomsList, rooms, zoneName));
        removeRooms(listOfRooms,zoneName);
    }

    public static void removeRooms(JComboBox listOfRooms,String zoneName) {
        for(int i = 0; i < listOfRooms.getItemCount(); i++) {
            if(null != roomToZone.get(listOfRooms.getItemAt(i).toString()) && roomToZone.get(listOfRooms.getItemAt(i).toString()).equals(zoneName)) {
                roomToZone.remove(listOfRooms.getItemAt(i).toString());
            }
        }
    }

    public static int getZoneSize() {
        return zones.size();
    }

    public static ArrayList<Zone> getZoneList(){
        ArrayList<Zone> zoneList = new ArrayList<Zone>();

        zones.forEach((k, v) -> zoneList.add(v.clone()));

        return zoneList;
    }

    public static void setZoneTemp(String zoneName,PeriodsOfDay period, double temperature){
        for(String name : zones.keySet()){
            if(name.equals(zoneName)){
                zones.get(name).setDesiredTemperature(period, temperature);
                return;
            }
        }
    }
    public static void updatezonesDesiredTempPeriod(PeriodsOfDay period){
        for (Zone zone:zones.values()) { zone.updatedesiredTempPeriod(period); }
    }
}
