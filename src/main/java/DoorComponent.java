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
    private ImageIcon doorLight = new ImageIcon("src/main/java/icons/icons8-doorlight.png");
    // This variable will be used for scaling the images
    private ImageIcon scaledImage;
    // This variable checks for the open state of the door
    private boolean open = false;
    // This get the name of the room we are in
    private Room room;
    private Room stooproom;
    // This is a counter for visitors
    private int vistorCount;

    /**
     * Parameterized constructor.
     *
     * @param panel the panel in which the house layout is displayed.
     * @param roomRect the RoomRectangle object in which this window component is drawn.
     * @param room the name of the room that corresponds to the roomRect.
     */
    public DoorComponent(JPanel panel, RoomRectangle roomRect, Room room,Room stoop){
        super(panel, roomRect);
        scaledImage = new ImageIcon(imageClose.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        this.room = room;
        stooproom = stoop;
    }

    /**
     * This method is used for drawing the door in the House View
     */
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

        // if no door are found we don't continue the function
        if(!containsDoor) return;

        // This boolean will be used maybe ana future context for "really" locking the door.
        boolean open = false;
        Door door = null;
        String isLocked = "";

        // Setting counter to 0 for starting getting who is at the door.
        vistorCount = 0;

        // Getting the user that is in front of the door
        String[] usernames = UserManager.getUsernames();

        // This For loop checks if someone is at the door and increments if it 1 or more.
        for(String username : usernames){
            if(UserManager.getUserLocation(username).equalsIgnoreCase(room.getName()+" STOOP")) vistorCount++;
        }

        // This for loop checks if the door is open or closed.
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

        // This if else condition assignes the right icon depending if the door is open or closed.
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



        // If no visitor are ditected we only draw the door else we include the icon of visitors and a counter
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

        boolean lightIsOn = false;
        Light light;
        objects = stooproom.getSmartObjects();
        for(Smartobj obj : objects){
            if(obj.getType() == SmartObjectType.LIGHT && stooproom.getName().contains("STOOP")){
                light = (Light) obj;
                if(light.isON())
                    lightIsOn = true;
                else lightIsOn = false;
            }
        }


        if (lightIsOn) {
            scaledImage = new ImageIcon(doorLight.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
            int xdoor = getRelX() + (getRoomRect().getWidthandHeight() / 2) - scaledImage.getIconWidth() / 2;
            int ydoor = getRelY() - 3 * metrics.getHeight() + getRoomRect().getWidthandHeight();
            scaledImage.paintIcon(getContainer(), g, xdoor, ydoor);
        }

    }
}
