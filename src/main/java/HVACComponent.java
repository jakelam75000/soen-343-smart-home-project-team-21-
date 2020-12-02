import javax.swing.*;
import java.awt.*;

public class HVACComponent extends RoomComponent {
    private ImageIcon heatingIcon = new ImageIcon("src/main/java/icons/hot.png");
    private ImageIcon coolingIcon = new ImageIcon("src/main/java/icons/cold.png");

    private ImageIcon scaledImage;

    private Room room;

    public HVACComponent(JPanel panel, RoomRectangle roomRect, Room room){
        super(panel, roomRect);
        this.room = room;
    }


    @Override
    public void draw(Graphics g) {



    }
}
