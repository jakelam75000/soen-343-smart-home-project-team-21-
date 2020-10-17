import javax.swing.*;
import java.awt.*;

public class WindowComponent extends RoomComponent{

    private ImageIcon image = new ImageIcon("C:\\Users\\azakh\\IdeaProjects\\soen-343-smart-home-project\\src\\main\\java\\icons\\window_closed.png");
    private ImageIcon scaledImage;

    private boolean open = false;

    public WindowComponent(JPanel panel, Rectangle roomRect){
        super(panel, roomRect);
        scaledImage = new ImageIcon(image.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    }

    @Override
    public void draw(Graphics g) {
        System.out.println(image.getIconWidth() + "///" + image.getIconHeight());
        scaledImage.paintIcon(getContainer(), g, 0, 0);


        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.drawOval(getRelX()+getRoomRect().getWidthandHeight()/3, getRelY()+10, 20,20);
    }


}
