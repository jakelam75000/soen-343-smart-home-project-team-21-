import javax.swing.*;
import java.awt.*;

public class WindowComponent extends RoomComponent{

    private ImageIcon image = new ImageIcon("src/main/java/icons/window.png");
    private ImageIcon scaledImage;

    private boolean open = false;
    private Room room;

    public WindowComponent(JPanel panel, RoomRectangle roomRect, Room room){
        super(panel, roomRect);
        scaledImage = new ImageIcon(image.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        this.room = room;
    }

    @Override
    public void draw(Graphics g) {

        int openWindows = 0;
        Smartobj[] objects = room.getSmartObjects();

        boolean containsWindows = false;
        for(Smartobj obj : objects){
            if(obj.getType() == SmartObjectType.WINDOW) {
                containsWindows = true;
                break;
            }
        }

        if(!containsWindows) return;

        for(Smartobj obj : objects){
            if(obj.getType() == SmartObjectType.WINDOW){
                Window window = (Window) obj;
                if(window.isOpen()){
                    openWindows++;
                }
            }
        }

        FontMetrics metrics = g.getFontMetrics();
        int xIcon = getRelX() + (getRoomRect().getWidthandHeight()/2) - scaledImage.getIconWidth() - getRoomRect().getWidthandHeight()/10;
        int yIcon = getRelY() + metrics.getHeight() + getRoomRect().getWidthandHeight()/20;
        scaledImage.paintIcon(getContainer(), g, xIcon, yIcon);

        Graphics2D g2d = (Graphics2D) g;

        Stroke stroke1 = new BasicStroke(2f);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(stroke1);

        int stringY = yIcon + scaledImage.getIconHeight()/3*2;
        int stringX = xIcon - (int)(metrics.stringWidth(""+openWindows)*2);

        g2d.drawString(""+openWindows, stringX, stringY);

    }


}
