import javax.swing.*;
import java.awt.*;

/**
 * Displays the number of windows of each room.
 */
public class WindowComponent extends RoomComponent{

    private ImageIcon image = new ImageIcon("src/main/java/icons/window.png");
    private ImageIcon scaledImage;
    private ImageIcon blockedImage = new ImageIcon("src/main/java/icons/X.png");
    private ImageIcon scaledBlocked;

    private boolean open = false;
    private Room room;

    /**
     * Parameterized constructor.
     *
     * @param panel the panel in which the house loyout is displayed.
     * @param roomRect the RoomRectangle object in which this window component is drawn.
     * @param room the Room object that corresponds to the roomRect.
     */
    public WindowComponent(JPanel panel, RoomRectangle roomRect, Room room){
        super(panel, roomRect);
        scaledImage = new ImageIcon(image.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        this.room = room;
    }

    /**
     * Draws the window component.
     *
     * @param g Graphics object.
     */
    @Override
    public void draw(Graphics g) {

        int openWindows = 0;
        Smartobj[] objects = room.getSmartObjects();
        int blocked = 0;

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
                if(window.isBlocked()) {
                    blocked++;
                }
            }
        }

        scaledBlocked = new ImageIcon(blockedImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        FontMetrics metrics = g.getFontMetrics();
        int xIcon = getRelX() + (getRoomRect().getWidthandHeight()/2) - scaledImage.getIconWidth() - getRoomRect().getWidthandHeight()/10;
        int yIcon = getRelY() + metrics.getHeight() + getRoomRect().getWidthandHeight()/20;
        scaledImage.paintIcon(getContainer(), g, xIcon, yIcon);

        int xBlock = xIcon;
        int yBlock = getRelY() + metrics.getHeight() + getRoomRect().getWidthandHeight()/10 + scaledImage.getIconHeight();
        scaledImage.paintIcon(getContainer(), g, xBlock, yBlock);
        scaledBlocked.paintIcon(getContainer(), g, xBlock, yBlock);

        Graphics2D g2d = (Graphics2D) g;

        Stroke stroke1 = new BasicStroke(2f);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(stroke1);

        int stringY = yIcon + scaledImage.getIconHeight()/3*2;
        int stringX = xIcon - (int)(metrics.stringWidth(""+openWindows)*2);

        g2d.drawString(""+openWindows, stringX, stringY);

        int stringBY = yBlock + scaledImage.getIconHeight()*75/100;
        g2d.drawString(""+blocked, stringX, stringBY);


    }


}
