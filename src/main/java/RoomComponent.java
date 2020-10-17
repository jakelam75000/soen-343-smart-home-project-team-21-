import javax.swing.*;
import java.awt.*;

public abstract class RoomComponent{

    private JPanel container;
    private Rectangle RoomRect;
    private int relX;
    private int relY;

    public RoomComponent(JPanel panel, Rectangle roomRect){
        this.RoomRect = roomRect;
        this.container = panel;
        this.relX = roomRect.getX();
        this.relY = roomRect.getY();
    }

    public abstract void draw(Graphics g);

    /**
     * Getter for container attribute.
     *
     * @return container attribute
     */
    public JPanel getContainer(){
        return container;
    }

    /**
     * Getter for relative X attribute.
     *
     * @return relX attribute
     */
    public int getRelX(){
        return relX;
    }

    /**
     * Getter for relative y attribute.
     *
     * @return relY attribute
     */
    public int getRelY(){
        return relY;
    }

    /**
     * Getter for roomRect attribute.
     *
     * @return roomRect attribute.
     */
    public Rectangle getRoomRect(){
        return RoomRect;
    }


}
