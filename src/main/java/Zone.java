import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Zone {
    private String name;
    private Map<PeriodsOfDay, Double> desiredTemperature = new HashMap<PeriodsOfDay, Double>();
    private ArrayList<Room> rooms;
    private ArrayList<String> overriddenRooms = new ArrayList<>();

    public Zone(String name, double desiredTemperature, ArrayList<Room> rooms){
        this.name = name;

        for(PeriodsOfDay period : PeriodsOfDay.values()){
            this.desiredTemperature.put(period, desiredTemperature);
        }

        this.rooms = rooms;
    }

    public Zone(Zone zone){
        this.name = zone.getName();

        zone.desiredTemperature.forEach((k, v) -> this.desiredTemperature.put(k, v));

        if(zone.rooms == null)
            rooms = new ArrayList<>();
        else
            this.rooms = (ArrayList<Room>) zone.rooms.clone();
    }

    public String getName(){
        return name;
    }

    public double getDesiredTemperature(PeriodsOfDay period){
        return (double)this.desiredTemperature.get(period);
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesiredTemperature(PeriodsOfDay period, double desiredTemperature) {
        if(this.desiredTemperature.containsKey(period)) {
            this.desiredTemperature.remove(period);
            this.desiredTemperature.put(period, desiredTemperature);
        }

    }

    public void updatedesiredTempPeriod(PeriodsOfDay period){
        for (Room room:rooms) {
            if(!overriddenRooms.contains(room.getName())) room.setDesiredTemp(desiredTemperature.get(period));
        }
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public boolean containsRoom(Room room){
        for (Room curoom:rooms) {
            if(curoom.getName().contains(room.getName()))return true;
        }
        return false;
    }

    public void addOverridden(String roomName){
        if(overriddenRooms.contains(roomName)) return;

        overriddenRooms.add(roomName);
    }

    public void clearOverridden(){
        overriddenRooms.clear();
    }

    public Zone clone(){
        return new Zone(this);
    }

}
