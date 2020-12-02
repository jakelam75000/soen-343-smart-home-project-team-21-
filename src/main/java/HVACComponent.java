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
        double desiredTemp = room.getDesiredTemp();
        double currentTemp = room.getTemperature();
        String displayTemp = "" +currentTemp;

        if (desiredTemp != currentTemp) {
            if(desiredTemp < currentTemp) {
                scaledImage = new ImageIcon(coolingIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
            }
            else
                scaledImage = new ImageIcon(heatingIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

            FontMetrics metrics = g.getFontMetrics();
            int xIcon = getRelX() + getRoomRect().getWidthandHeight()/2 - scaledImage.getIconWidth();
            int yIcon = getRelY() + getRoomRect().getWidthandHeight()*2/3 - scaledImage.getIconHeight();

            scaledImage.paintIcon(getContainer(), g, xIcon, yIcon);

            Graphics2D g2d = (Graphics2D) g;

            Stroke stroke1 = new BasicStroke(2f);

            int stringY = yIcon + scaledImage.getIconHeight()*75/100;
            int stringX = xIcon + scaledImage.getIconWidth();

            g2d.drawString(displayTemp, stringX, stringY);
        }

    }
}
