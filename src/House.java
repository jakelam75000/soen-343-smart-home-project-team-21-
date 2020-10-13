/*
class house, holds the house rooms, name and smartobjects by extension
@author Jake Lamothe
@version 0.1
@since 2020-09-30
 */
public class House {
    Room[] rooms;
    String name;
    /*
    constructor
     */
    public House(Room[] listrom, String nam ){
        rooms = listrom;
        name = nam;
    }
    //shouldnt be used for now
    /*
    a mutator function which for now shouldn't be used
    @param newroom This is the newest single room object to be added
     */
    public void addrooms(Room newroom){
        Room[] temprooms = new Room[rooms.length+1];
        for (int i =0; i< rooms.length; i++){
            temprooms[i] = rooms[i];
        }
        temprooms[temprooms.length] = newroom;
        rooms = temprooms;
    }
    /*
    a mutator function which for now shouldn't be used
    @param newroom This is the newest list of room object to be added
     */
    public void setlistrooms(Room[] listorooms){
        rooms = listorooms;
    }
    /*
    to string override
     */
    public String toString(){
        String s = name + " has " + rooms.length + " rooms.\n\n";
        for (int i =0; i< rooms.length; i++){
             if (rooms[i] != null) s = s.concat(rooms[i].toString());
        }
        return s;
    }
    public String[] getroomnames(){
        String[] s = new String[rooms.length];
        for (int i=0;i< rooms.length; i++){
            s[i]=rooms[i].getName();
        }
        return s;
    }
}
