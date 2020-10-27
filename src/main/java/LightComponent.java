import javax.swing.*;
import java.awt.*;

public class LightComponent extends RoomComponent{

    private ImageIcon lightOnIcon = new ImageIcon("src/main/java/icons/light_on.png");
    private ImageIcon lightOffIcon = new ImageIcon("src/main/java/icons/light_off.png");
    private ImageIcon scaledImage;

    private boolean on = false;
    private Room room;

    public LightComponent(JPanel panel, RoomRectangle roomRect, Room room){
        super(panel, roomRect);
        scaledImage = new ImageIcon(lightOffIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        this.room = room;
    }

    @Override
    public void draw(Graphics g) {

        Smartobj[] objects = room.getSmartObjects();
        boolean on = false;
        String isOn = "Off";

        for(Smartobj obj : objects){
            if(obj.getType() == SmartObjectType.LIGHT){
                Light light = (Light) obj;
                if(light.isON()){
                    on = true;
                    break;
                }
            }
        }

        if (on) {
            scaledImage = new ImageIcon(lightOnIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
            isOn = "On";
        }

        FontMetrics metrics = g.getFontMetrics();
        int xIcon = getRelX() + (getRoomRect().getWidthandHeight()/2) + getRoomRect().getWidthandHeight()/10;
        int yIcon = getRelY() + metrics.getHeight() + getRoomRect().getWidthandHeight()/10 + scaledImage.getIconHeight();
        scaledImage.paintIcon(getContainer(), g, xIcon, yIcon);

        Graphics2D g2d = (Graphics2D) g;

        Stroke stroke1 = new BasicStroke(2f);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(stroke1);

        int stringY = yIcon + scaledImage.getIconHeight()*75/100;
        int stringX = xIcon + scaledImage.getIconWidth();

       g2d.drawString(isOn, stringX, stringY);
    }
}
