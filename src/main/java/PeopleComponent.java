import javax.swing.*;
import java.awt.*;

public class PeopleComponent extends RoomComponent{

    private ImageIcon image = new ImageIcon("src/main/java/icons/person.png");
    private ImageIcon scaledImage;

    private String roomName;

    public PeopleComponent(JPanel panel, RoomRectangle roomRect, String roomName){
        super(panel, roomRect);
        scaledImage = new ImageIcon(image.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        this.roomName = roomName;


    }

    @Override
    public void draw(Graphics g) {

        int userCount = 0;

        String[] usernames = UserManager.getUsernames();
        for(String username : usernames){
            if(UserManager.getUserLocation(username).equalsIgnoreCase(roomName)) userCount++;
        }

        FontMetrics metrics = g.getFontMetrics();
        int xIcon = getRelX() + (getRoomRect().getWidthandHeight()/2) + getRoomRect().getWidthandHeight()/10;
        int yIcon = getRelY() + metrics.getHeight() + getRoomRect().getWidthandHeight()/20;
        scaledImage.paintIcon(getContainer(), g, xIcon, yIcon);

        Graphics2D g2d = (Graphics2D) g;

        Stroke stroke1 = new BasicStroke(2f);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(stroke1);

        int stringX = xIcon + scaledImage.getIconWidth();
        int stringY = yIcon + scaledImage.getIconHeight()/3*2;

        g2d.drawString(""+userCount, stringX, stringY);
    }
}
