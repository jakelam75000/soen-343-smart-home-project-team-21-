import javax.swing.*;
import java.awt.*;

/**
 * Displays the doors in each room.
 */
public class DoorComponent extends RoomComponent {
    // Getting the image of the close door
    private ImageIcon imageClose = new ImageIcon("src/main/java/icons/closed-door.png");
    private ImageIcon imageOpen = new ImageIcon("src/main/java/icons/opened-door.png");
    private ImageIcon Imagepeople = new ImageIcon("src/main/java/icons/employees.png");
    // This variable will be used for scaling the images
    private ImageIcon scaledImage;
    // This variable checks for the open state of the door
    private boolean open = false;
    // This get the name of the room we are in
    private Room room;
    // This is a counter for visitors
    private int vistorCount;

    /**
     * Parameterized constructor.
     *
     * @param panel the panel in which the house layout is displayed.
     * @param roomRect the RoomRectangle object in which this window component is drawn.
     * @param room the name of the room that corresponds to the roomRect.
     */
    public DoorComponent(JPanel panel, RoomRectangle roomRect, Room room){
        super(panel, roomRect);
        scaledImage = new ImageIcon(imageClose.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        this.room = room;
    }

    // This method is used for drawing the door in the House View
    @Override
    public void draw(Graphics g) {
        Smartobj[] objects = room.getSmartObjects();

        // This Boolean checks for the condition of if a room hasa a door or not
        boolean containsDoor = false;

        // This form loop checks the current room we are located in to change the boolean value
        for(Smartobj obj : objects){
            if(obj.getType() == SmartObjectType.DOOR) {
                containsDoor = true;
                break;
            }
        }

        //
        if(!containsDoor) return;

        boolean open = false;
        Door door = null;
        String isLocked = "";

        vistorCount = 0;

        String[] usernames = UserManager.getUsernames();
        for(String username : usernames){
            if(UserManager.getUserLocation(username).equalsIgnoreCase(room.getName()+"STOOP")) vistorCount++;
        }

        for(Smartobj obj : objects){
            if(obj.getType() == SmartObjectType.DOOR){
                door = (Door) obj;
                if(door.isOpen()){
                    open = true;
                }
                else{
                    open = false;
                }
                break;
            }
        }

        if (!open && door.islocked() == true) {
            isLocked = "Locked";
            scaledImage = new ImageIcon(imageClose.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        }
        else if (!open && door.islocked() == false){
            isLocked = "Unlocked";
            scaledImage = new ImageIcon(imageClose.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        }
        else{
            isLocked = "Unlocked";
            scaledImage = new ImageIcon(imageOpen.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        }

        FontMetrics metrics = g.getFontMetrics();


        if(vistorCount == 0) {

            int xIcon = getRelX() + (getRoomRect().getWidthandHeight()/2) - scaledImage.getIconWidth()/2;
            int yIcon = getRelY() - 2*(metrics.getHeight()) + getRoomRect().getWidthandHeight();
            int endxIcon = xIcon + 25;
            int endyIcon = yIcon + 25;
            scaledImage.paintIcon(getContainer(), g, xIcon, yIcon);


            Graphics2D g2d = (Graphics2D) g;

            Stroke stroke1 = new BasicStroke(2f);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(stroke1);
        }
        else{
            metrics = g.getFontMetrics();
            int xIcon = getRelX() + (getRoomRect().getWidthandHeight()/2) - scaledImage.getIconWidth()/2 - 20;
            int yIcon = getRelY() - 2*(metrics.getHeight()) + getRoomRect().getWidthandHeight();
            int endxIcon = xIcon + 25;
            int endyIcon = yIcon + 25;
            scaledImage.paintIcon(getContainer(), g, xIcon, yIcon);


            Graphics2D g2d = (Graphics2D) g;

            Stroke stroke1 = new BasicStroke(2f);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(stroke1);

            int vistorY = yIcon;
            int vistorX = xIcon + scaledImage.getIconWidth();
            scaledImage = new ImageIcon(Imagepeople.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

            scaledImage.paintIcon(getContainer(), g, vistorX, vistorY);

            int stringX = vistorX + scaledImage.getIconWidth() + 3;
            int stringY = vistorY + scaledImage.getIconHeight()/3*2;

            g2d.drawString(""+vistorCount, stringX, stringY);
        }


    }
}
