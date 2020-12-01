import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Zone {
    private String name;
    private Map<PeriodsOfDay, Double> desiredTemperature = new HashMap<PeriodsOfDay, Double>();
    private ArrayList<Room> rooms;

    public Zone(String name, double desiredTemperature, ArrayList<Room> rooms){
        this.name = name;

        for(PeriodsOfDay period : PeriodsOfDay.values()){
            this.desiredTemperature.put(period, desiredTemperature);
        }

        this.rooms = rooms;
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

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}
