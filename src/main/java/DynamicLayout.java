
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

/**
 * DynamicLayout is a class that generates a 2-d room layout based on the room objects that are passed to it.
 */
public class DynamicLayout extends JPanel {
    private final Room[] rooms;
    private final int roomCount;


    //actual width and height is 360 x 360 but we need some extra space for outside
    private static double insideWidthAndHeight = 480;
    private List<RoomRectangle> drawRooms = new ArrayList<>();
    private Random random = new Random();

    /**
     * constructor with parameters
     * @param rooms Room[] is the array of rooms
     */
    public DynamicLayout(Room[] rooms) {
        int nonStoopRooms = 0;
        for (int i=0;i<rooms.length;i++){
            if (!rooms[i].getName().contains("STOOP"))nonStoopRooms++;
        }
        this.rooms = new Room[nonStoopRooms];
        int k =0;
        for (int i=0; i<rooms.length; i++){
            if (!rooms[i].getName().contains("STOOP")){
                this.rooms[k] = rooms[i];
                k++;
            }
        }
        this.roomCount = nonStoopRooms;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(520, 520));

        generateAllRooms();
    }

    /**
     * generates the 2-d layoud based on the room array that is passed to it through the constructor.
     */
    public void generateAllRooms() {
        //width and height are the same
        int widthAndHeight;

        // We don't start at (0,0) because that belongs to the outside
        int initial = 20;
        int x = 0;
        int y = 0;
        int roomsDone = 0;

        // row will be determined in the while loop
        int row = 0;
        // Determine how many columns we want
        int col = (int)Math.ceil(Math.sqrt(roomCount));

        // dimension of each room
        widthAndHeight = (int)insideWidthAndHeight/col;

        while(roomsDone != roomCount) {
            y = initial + row*widthAndHeight;
            for(int i = 0; i < col; i++) {
                //break out of for loop if all rooms are done
                if(roomsDone == roomCount) {
                    break;
                }
                x = initial + i*widthAndHeight;
                addRoom(roomsDone, x, y, widthAndHeight);
                roomsDone++;
            }
            //set x back to initial
            x = initial;
            //update row
            row++;
        }
    }

    /**
     * Method that ads an individual room to the drawRooms array list
     * @param roomAdded int the index of the room to be added
     * @param x int the x position of the room
     * @param y int the y position of the room
     * @param widthAndHeight int the dimensions of the room
     */
    public void addRoom(int roomAdded, int x, int y, int widthAndHeight) {
        //TO-DO: window, door and light are set to 0 for now but we would have to create getters in Room.java to get the proper count
        int windowCount = 0;
        int doorCount = 0;
        int lightCount = 0;
        String roomName = rooms[roomAdded].getName();
        //Need to add logic for windows
        drawRooms.add(new RoomRectangle(x, y, widthAndHeight, windowCount, doorCount, lightCount, roomName));
        repaint();

    }

    /**
     *
     * @param g Graphics draws all the rooms from the drawrooms array lsit
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i=0; i<drawRooms.size(); i++) {
            RoomRectangle r = drawRooms.get(i);
            r.draw(g);
            new WindowComponent(this, r, rooms[i]).draw(g);
            new PeopleComponent(this, r, rooms[i].getName()).draw(g);
            new LightComponent(this, r, rooms[i]).draw(g);
            new DoorComponent(this, r, rooms[i]).draw(g);
        }


    }

}