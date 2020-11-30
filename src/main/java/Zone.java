import java.util.ArrayList;

public class Zone {
    private String name;
    private double desiredTemperature;
    private ArrayList<Room> rooms;

    public Zone(String name, double desiredTemperature, ArrayList<Room> rooms){
        this.name = name;
        this.desiredTemperature = desiredTemperature;
        this.rooms = rooms;
    }

    public String getName(){
        return name;
    }

    public double getDesiredTemperature(){
        return desiredTemperature;
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesiredTemperature(double desiredTemperature) {
        this.desiredTemperature = desiredTemperature;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}
