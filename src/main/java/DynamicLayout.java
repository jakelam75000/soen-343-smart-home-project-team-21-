
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class DynamicLayout extends JPanel {
    private Room[] rooms;
    private int roomCount;

    //actual width and height is 360 x 360 but we need some extra space for outside
    private static double insideWidthAndHeight = 340;
    private List<Rectangle> drawRooms = new ArrayList<>();
    private Random random = new Random();

    public DynamicLayout(Room[] rooms) {
        this.rooms = rooms;
        this.roomCount = rooms.length;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(360, 360));

        generateAllRooms();
    }

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
                addRoom(i, x, y, widthAndHeight);
                roomsDone++;
            }
            //set x back to initial
            x = initial;
            //update row
            row++;
        }
    }

    public void addRoom(int roomAdded, int x, int y, int widthAndHeight) {
        //TO-DO: window, door and light are set to 0 for now but we would have to create getters in Room.java to get the proper count
        int windowCount = 0;
        int doorCount = 0;
        int lightCount = 0;
        String roomName = rooms[roomAdded].getName();
        //Need to add logic for windows
        drawRooms.add(new Rectangle(x, y, widthAndHeight, windowCount, doorCount, lightCount, roomName));
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Rectangle s : drawRooms) {
            s.draw(g);
        }
    }
}