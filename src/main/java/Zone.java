import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * heating and cooling zones, used for keeping track of the desired temps, rooms and overriden rooms
 */
public class Zone {
    private String name;
    private Map<PeriodsOfDay, Double> desiredTemperature = new HashMap<PeriodsOfDay, Double>();
    private ArrayList<Room> rooms;
    private ArrayList<String> overriddenRooms = new ArrayList<>();

    /**
     * parameterized constructor of zone
     * @param name the name of the room
     * @param desiredTemperature the base desired tempreture for all periods
     * @param rooms the arraylist of rooms
     */
    public Zone(String name, double desiredTemperature, ArrayList<Room> rooms){
        this.name = name;

        for(PeriodsOfDay period : PeriodsOfDay.values()){
            this.desiredTemperature.put(period, desiredTemperature);
        }

        this.rooms = rooms;
    }

    /**
     * Copy constructor
     * @param zone the zone to be copied
     */
    public Zone(Zone zone){
        this.name = zone.getName();

        zone.desiredTemperature.forEach((k, v) -> this.desiredTemperature.put(k, v));

        if(zone.rooms == null)
            rooms = new ArrayList<>();
        else
            this.rooms = (ArrayList<Room>) zone.rooms.clone();
    }

    /**
     * getter, returns the name
     * @return String name of the room
     */
    public String getName(){
        return name;
    }

    /**
     * getter, retrns the temp for the selected period
     * @param period the period to check
     * @return boolean the temp at that period
     */
    public double getDesiredTemperature(PeriodsOfDay period){
        return (double)this.desiredTemperature.get(period);
    }

    /**
     * returns an array lsit of the rooms it holds
     * @return ArrayList of all room objects that belond to this zone
     */
    public ArrayList<Room> getRooms(){
        return rooms;
    }

    /**
     * setter, sets the name of the zone
     * @param name String new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the deisred temp of the zone for a set period
     * @param period the period that is associated wiht the new temp
     * @param desiredTemperature the new desired temp
     */
    public void setDesiredTemperature(PeriodsOfDay period, double desiredTemperature) {
        if(this.desiredTemperature.containsKey(period)) {
            this.desiredTemperature.remove(period);
            this.desiredTemperature.put(period, desiredTemperature);
        }

    }

    /**
     * updates all rooms (that are not overridden) desired tempreture for the period indicated
     * @param period PeriodOfDay the period of which it should be upated to
     */
    public void updateDesiredTempPeriod(PeriodsOfDay period){
        for (Room room:rooms) {
            if(!overriddenRooms.contains(room.getName())) room.setDesiredTemp(desiredTemperature.get(period));
        }
    }

    /**
     * set the list of rooms to a new list of rooms
     * @param rooms Room[] the new list
     */
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * checks if a room is in this zone
     * @param room the room object to check if it is here
     * @return boolean if it is in the zone's list of rooms
     */
    public boolean containsRoom(Room room){
        for (Room curoom:rooms) {
            if(curoom.getName().contains(room.getName()))return true;
        }
        return false;
    }

    /**
     * adds a room name to the overridden list
     * @param roomName the room's name to be overridden
     */
    public void addOverridden(String roomName){
        if(overriddenRooms.contains(roomName)) return;

        overriddenRooms.add(roomName);
    }

    /**
     * clears the overridden list
     */
    public void clearOverridden(){
        overriddenRooms.clear();
    }

    /**
     * clones the zone and returns a new zone
     * @return zone the clone
     */
    public Zone clone(){
        return new Zone(this);
    }

}
