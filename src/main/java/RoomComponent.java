import javax.swing.*;
import java.awt.*;

public abstract class RoomComponent{

    private JPanel container;
    private RoomRectangle RoomRect;
    private int relX;
    private int relY;

    /**
     * Parameterised constructor.
     *
     * @param panel the panel inside which the room component will be drawn.
     * @param roomRect the roomRect inside which the room component belongs.
     */
    public RoomComponent(JPanel panel, RoomRectangle roomRect){
        this.RoomRect = roomRect;
        this.container = panel;
        this.relX = roomRect.getX();
        this.relY = roomRect.getY();
    }

    /**
     * Abstract method that all room component subclasses must implement to define how they should be drawn.
     *
     * @param g Graphics object.
     */
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
    public RoomRectangle getRoomRect(){
        return RoomRect;
    }


}
