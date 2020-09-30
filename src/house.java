public class house {
    room[] rooms;
    String name;
    public house(room[] listrom, String nam ){
        rooms = listrom;
        name = nam;
    }
    //shouldnt be used for now
    public void addrooms(room newroom){
        rooms[rooms.length] = newroom;
    }
    public void setlistrooms(room[] listorooms){
        rooms = listorooms;
    }
    public String toString(){
        String s = name + " has " + rooms.length + " rooms.\n\n";
        for (int i =0; i< rooms.length; i++){
             if (rooms[i] != null) s = s.concat(rooms[i].toString());
        }
        return s;
    }
}
